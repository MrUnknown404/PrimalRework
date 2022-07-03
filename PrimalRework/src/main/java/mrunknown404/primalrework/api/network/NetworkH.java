package mrunknown404.primalrework.api.network;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.logging.log4j.util.TriConsumer;

import mrunknown404.primalrework.api.network.packet.IPacket;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.Pair;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public abstract class NetworkH {
	private final HashMap<Class<?>, Pair<BiFunction<PacketBuffer, Field, ?>, TriConsumer<PacketBuffer, Field, ?>>> handlers = new HashMap<Class<?>, Pair<BiFunction<PacketBuffer, Field, ?>, TriConsumer<PacketBuffer, Field, ?>>>();
	private final HashMap<Class<?>, Field[]> cache = new HashMap<Class<?>, Field[]>();
	private final SimpleChannel instance;
	private int packetCount;
	
	public NetworkH(String modid, String name, String protocolVersion) {
		this.instance = NetworkRegistry.newSimpleChannel(new ResourceLocation(modid, name), () -> protocolVersion, protocolVersion::equals, protocolVersion::equals);
		
		this.<Byte>mapHandler(byte.class, PacketBuffer::readByte, PacketBuffer::writeByte);
		this.<Short>mapHandler(short.class, PacketBuffer::readShort, PacketBuffer::writeShort);
		this.<Integer>mapHandler(int.class, PacketBuffer::readInt, PacketBuffer::writeInt);
		this.<Long>mapHandler(long.class, PacketBuffer::readLong, PacketBuffer::writeLong);
		this.<Float>mapHandler(float.class, PacketBuffer::readFloat, PacketBuffer::writeFloat);
		this.<Double>mapHandler(double.class, PacketBuffer::readDouble, PacketBuffer::writeDouble);
		this.<Boolean>mapHandler(boolean.class, PacketBuffer::readBoolean, PacketBuffer::writeBoolean);
		this.<Character>mapHandler(char.class, PacketBuffer::readChar, PacketBuffer::writeChar);
		
		mapHandler(BlockPos.class, PacketBuffer::readBlockPos, PacketBuffer::writeBlockPos);
		mapHandler(UUID.class, PacketBuffer::readUUID, PacketBuffer::writeUUID);
		mapHandler(CompoundNBT.class, PacketBuffer::readNbt, PacketBuffer::writeNbt);
		mapHandler(ItemStack.class, PacketBuffer::readItem, PacketBuffer::writeItem);
		mapHandler(String.class, (buf) -> buf.readUtf(), PacketBuffer::writeUtf);
		mapHandler(Stage.class, (buf) -> InitStages.byID(buf.readByte()), (buf, stage) -> buf.writeByte(stage.id));
		
		registerPackets();
	}
	
	public static NetworkH create(String modid, String name, String protocolVersion, Consumer<NetworkH> registerPackets) {
		return new NetworkH(modid, name, protocolVersion) {
			@Override
			protected void registerPackets() {
				registerPackets.accept(this);
			}
		};
	}
	
	protected abstract void registerPackets();
	
	public <T extends IPacket> void registerPacket(Class<T> clazz, NetworkDirection dir) {
		instance.registerMessage(packetCount++, clazz, (obj, buf) -> {
			try {
				for (Field f : getClassFields(obj.getClass())) {
					Class<?> type = f.getType();
					if (allowField(f, type)) {
						getHandler(type).getRight().accept(buf, f, f.get(obj));
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}, (buf) -> {
			try {
				T msg = clazz.newInstance();
				for (Field f : getClassFields(clazz)) {
					Class<?> type = f.getType();
					if (allowField(f, type)) {
						f.set(msg, getHandler(type).getLeft().apply(buf, f));
					}
				}
				return msg;
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}, (msg, ctx) -> {
			NetworkEvent.Context context = ctx.get();
			if (context.getDirection() != dir) {
				return;
			}
			
			context.enqueueWork(() -> msg.handle(context));
			context.setPacketHandled(true);
		});
	}
	
	public void sendPacketToServer(IPacket packet) {
		instance.sendToServer(packet);
	}
	
	public void sendPacketToTarget(ServerPlayerEntity target, IPacket packet) {
		instance.send(PacketDistributor.PLAYER.with(() -> target), packet);
	}
	
	public void sendPacketToAll(IPacket packet) {
		instance.send(PacketDistributor.ALL.noArg(), packet);
	}
	
	@SuppressWarnings("unchecked")
	private <T> Pair<BiFunction<PacketBuffer, Field, ?>, TriConsumer<PacketBuffer, Field, T>> getHandler(Class<?> clazz) {
		Pair<BiFunction<PacketBuffer, Field, ?>, TriConsumer<PacketBuffer, Field, ?>> pair = handlers.get(clazz);
		if (pair == null) {
			throw new RuntimeException("No handler for  " + clazz);
		}
		return Pair.of(pair.getLeft(), (TriConsumer<PacketBuffer, Field, T>) pair.getRight());
	}
	
	private boolean allowField(Field f, Class<?> type) {
		int mod = f.getModifiers();
		return Modifier.isFinal(mod) || Modifier.isStatic(mod) || Modifier.isTransient(mod) ? false : handlers.containsKey(type);
	}
	
	private <T> void mapHandler(Class<T> type, Function<PacketBuffer, T> readerLower, BiConsumer<PacketBuffer, T> writerLower) {
		mapHandler(type, (buf, field) -> readerLower.apply(buf), (buf, field, t) -> writerLower.accept(buf, t));
	}
	
	@SuppressWarnings("unchecked")
	private <T> void mapHandler(Class<T> type, BiFunction<PacketBuffer, Field, T> reader, TriConsumer<PacketBuffer, Field, T> writer) {
		BiFunction<PacketBuffer, Field, T[]> arrayReader = (buf, field) -> {
			int count = buf.readInt();
			T[] array = (T[]) Array.newInstance(type, count);
			
			for (int i = 0; i < count; i++) {
				array[i] = reader.apply(buf, field);
			}
			
			return array;
		};
		
		TriConsumer<PacketBuffer, Field, T[]> arrayWriter = (buf, field, t) -> {
			buf.writeInt(t.length);
			
			for (int i = 0; i < t.length; i++) {
				writer.accept(buf, field, t[i]);
			}
		};
		
		handlers.put(type, Pair.of(reader, writer));
		handlers.put(Array.newInstance(type, 0).getClass(), Pair.of(arrayReader, arrayWriter));
	}
	
	private Field[] getClassFields(Class<?> clazz) {
		if (cache.containsKey(clazz)) {
			return cache.get(clazz);
		}
		
		Field[] fields = clazz.getFields();
		Arrays.sort(fields, Comparator.comparing(Field::getName));
		cache.put(clazz, fields);
		return fields;
	}
}
