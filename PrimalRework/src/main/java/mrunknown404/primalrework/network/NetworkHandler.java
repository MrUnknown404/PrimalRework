package mrunknown404.primalrework.network;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.network.packets.IPacket;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	private static final HashMap<Class<?>, Pair<Reader<?>, Writer<?>>> HANDLERS = new HashMap<Class<?>, Pair<Reader<?>, Writer<?>>>();
	private static final HashMap<Class<?>, Field[]> CACHE = new HashMap<Class<?>, Field[]>();
	
	private static final String PROTOCOL_VERSION = "1";
	private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(PrimalRework.MOD_ID, "main"), () -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	private static int packetCount;
	
	public static <T extends IPacket> void registerPacket(Class<T> clazz, NetworkDirection dir) {
		BiConsumer<T, PacketBuffer> encoder = NetworkHandler::writeObject;
		
		Function<PacketBuffer, T> decoder = (buf) -> {
			try {
				T msg = clazz.newInstance();
				NetworkHandler.readObject(msg, buf);
				return msg;
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		};
		
		BiConsumer<T, Supplier<NetworkEvent.Context>> consumer = (msg, supp) -> {
			NetworkEvent.Context context = supp.get();
			if (context.getDirection() != dir) {
				return;
			}
			
			context.enqueueWork(() -> {
				msg.handle(context);
			});
			context.setPacketHandled(true);
		};
		
		INSTANCE.registerMessage(packetCount++, clazz, encoder, decoder, consumer);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void sendPacketToServer(IPacket packet) {
		INSTANCE.sendToServer(packet);
	}
	
	public static void sendPacketToTarget(ServerPlayerEntity target, IPacket packet) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> target), packet);
	}
	
	public static void sendPacketToAll(IPacket packet) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
	}
	
	private static void readObject(Object obj, PacketBuffer buf) {
		try {
			Class<?> clazz = obj.getClass();
			Field[] clFields = getClassFields(clazz);
			for (Field f : clFields) {
				Class<?> type = f.getType();
				if (allowField(f, type)) {
					readField(obj, f, type, buf);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error reading message " + obj, e);
		}
	}
	
	private static void writeObject(Object obj, PacketBuffer buf) {
		try {
			Class<?> clazz = obj.getClass();
			Field[] clFields = getClassFields(clazz);
			for (Field f : clFields) {
				Class<?> type = f.getType();
				if (allowField(f, type)) {
					writeField(obj, f, type, buf);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error writing message " + obj, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T> Pair<Reader<?>, Writer<T>> getHandler(Class<?> clazz) {
		Pair<Reader<?>, Writer<?>> pair = HANDLERS.get(clazz);
		if (pair == null) {
			throw new RuntimeException("No handler for  " + clazz);
		}
		
		return Pair.of((Reader<?>) pair.getLeft(), (Writer<T>) pair.getRight());
	}
	
	private static boolean allowField(Field f, Class<?> type) {
		int mod = f.getModifiers();
		if (Modifier.isFinal(mod) || Modifier.isStatic(mod) || Modifier.isTransient(mod)) {
			return false;
		}
		
		return HANDLERS.containsKey(type);
	}
	
	private static <T> void mapHandler(Class<T> type, Function<PacketBuffer, T> readerLower, BiConsumer<PacketBuffer, T> writerLower) {
		Reader<T> reader = (buf, field) -> readerLower.apply(buf);
		Writer<T> writer = (buf, field, t) -> writerLower.accept(buf, t);
		mapHandler(type, reader, writer);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> void mapHandler(Class<T> type, Reader<T> reader, Writer<T> writer) {
		Class<T[]> arrayType = (Class<T[]>) Array.newInstance(type, 0).getClass();
		
		Reader<T[]> arrayReader = (buf, field) -> {
			int count = buf.readInt();
			T[] arr = (T[]) Array.newInstance(type, count);
			
			for (int i = 0; i < count; i++) {
				arr[i] = reader.read(buf, field);
			}
			
			return arr;
		};
		
		Writer<T[]> arrayWriter = (buf, field, t) -> {
			int count = t.length;
			buf.writeInt(count);
			
			for (int i = 0; i < count; i++) {
				writer.write(buf, field, t[i]);
			}
		};
		
		HANDLERS.put(type, Pair.of(reader, writer));
		HANDLERS.put(arrayType, Pair.of(arrayReader, arrayWriter));
	}
	
	private static Field[] getClassFields(Class<?> clazz) {
		if (CACHE.containsKey(clazz)) {
			return CACHE.get(clazz);
		}
		
		Field[] fields = clazz.getFields();
		Arrays.sort(fields, Comparator.comparing(Field::getName));
		CACHE.put(clazz, fields);
		return fields;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> void writeField(Object obj, Field f, Class<?> clazz, PacketBuffer buf) throws IllegalArgumentException, IllegalAccessException {
		Pair<Reader<?>, Writer<T>> handler = getHandler(clazz);
		handler.getRight().write(buf, f, (T) f.get(obj));
	}
	
	private static <T> void readField(Object obj, Field f, Class<?> clazz, PacketBuffer buf) throws IllegalArgumentException, IllegalAccessException {
		Pair<Reader<?>, Writer<T>> handler = getHandler(clazz);
		f.set(obj, handler.getLeft().read(buf, f));
	}
	
	private static interface Reader<T> {
		public T read(PacketBuffer buf, Field field);
	}
	
	private static interface Writer<T> {
		public void write(PacketBuffer buf, Field field, T t);
	}
	
	static {
		NetworkHandler.<Byte>mapHandler(byte.class, PacketBuffer::readByte, PacketBuffer::writeByte);
		NetworkHandler.<Short>mapHandler(short.class, PacketBuffer::readShort, PacketBuffer::writeShort);
		NetworkHandler.<Integer>mapHandler(int.class, PacketBuffer::readInt, PacketBuffer::writeInt);
		NetworkHandler.<Long>mapHandler(long.class, PacketBuffer::readLong, PacketBuffer::writeLong);
		NetworkHandler.<Float>mapHandler(float.class, PacketBuffer::readFloat, PacketBuffer::writeFloat);
		NetworkHandler.<Double>mapHandler(double.class, PacketBuffer::readDouble, PacketBuffer::writeDouble);
		NetworkHandler.<Boolean>mapHandler(boolean.class, PacketBuffer::readBoolean, PacketBuffer::writeBoolean);
		NetworkHandler.<Character>mapHandler(char.class, PacketBuffer::readChar, PacketBuffer::writeChar);
		
		mapHandler(BlockPos.class, PacketBuffer::readBlockPos, PacketBuffer::writeBlockPos);
		mapHandler(UUID.class, PacketBuffer::readUUID, PacketBuffer::writeUUID);
		mapHandler(CompoundNBT.class, PacketBuffer::readNbt, PacketBuffer::writeNbt);
		mapHandler(ItemStack.class, PacketBuffer::readItem, PacketBuffer::writeItem);
		mapHandler(String.class, NetworkHandler::readString, PacketBuffer::writeUtf);
		mapHandler(EnumStage.class, NetworkHandler::readStage, NetworkHandler::writeStage);
		mapHandler(List.class, NetworkHandler::readList, NetworkHandler::writeList);
	}
	
	private static String readString(PacketBuffer buf) {
		return buf.readUtf(32767);
	}
	
	private static EnumStage readStage(PacketBuffer buf) {
		return EnumStage.fromID(buf.readByte());
	}
	
	private static void writeStage(PacketBuffer buf, EnumStage stage) {
		buf.writeByte(stage.id);
	}
	
	// Everything below is ugly af but whatever i'm tired
	
	@SuppressWarnings("unchecked")
	private static <T> List<T> readList(PacketBuffer buf) {
		int size = buf.readInt();
		int strSize = buf.readInt();
		String name = buf.readUtf(strSize);
		
		List<T> list = new ArrayList<>(size);
		try {
			Class<?> clazz = Class.forName(name);
			
			if (clazz == Boolean.class) {
				for (int i = 0; i < size; i++) {
					list.add((T) new Boolean(buf.readBoolean()));
				}
			} else if (clazz == Character.class) {
				for (int i = 0; i < size; i++) {
					list.add((T) new Character(buf.readChar()));
				}
			} else if (clazz == Byte.class) {
				for (int i = 0; i < size; i++) {
					list.add((T) new Byte(buf.readByte()));
				}
			} else if (clazz == Short.class) {
				for (int i = 0; i < size; i++) {
					list.add((T) new Short(buf.readShort()));
				}
			} else if (clazz == Integer.class) {
				for (int i = 0; i < size; i++) {
					list.add((T) new Integer(buf.readInt()));
				}
			} else if (clazz == Long.class) {
				for (int i = 0; i < size; i++) {
					list.add((T) new Long(buf.readLong()));
				}
			} else if (clazz == Float.class) {
				for (int i = 0; i < size; i++) {
					list.add((T) new Float(buf.readFloat()));
				}
			} else if (clazz == Double.class) {
				for (int i = 0; i < size; i++) {
					list.add((T) new Double(buf.readDouble()));
				}
			} else if (clazz == String.class) {
				for (int i = 0; i < size; i++) {
					int s = buf.readInt();
					list.add((T) new String(buf.readUtf(s)));
				}
			} else {
				T obj = (T) clazz.newInstance();
				for (int i = 0; i < size; i++) {
					readObject(obj, buf);
				}
				list.add(obj);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	private static void writeList(PacketBuffer buf, List<?> list) {
		buf.writeInt(list.size());
		Class<?> clazz = list.get(0).getClass();
		String name = clazz.getName();
		buf.writeInt(name.length());
		buf.writeUtf(name, name.length());
		
		if (clazz == Boolean.class) {
			list.forEach(o -> {
				buf.writeBoolean(((Boolean) o).booleanValue());
			});
		} else if (clazz == Character.class) {
			list.forEach(o -> {
				buf.writeChar(((Character) o).charValue());
			});
		} else if (clazz == Byte.class) {
			list.forEach(o -> {
				buf.writeByte(((Byte) o).byteValue());
			});
		} else if (clazz == Short.class) {
			list.forEach(o -> {
				buf.writeShort(((Short) o).shortValue());
			});
		} else if (clazz == Integer.class) {
			list.forEach(o -> {
				buf.writeInt(((Integer) o).intValue());
			});
		} else if (clazz == Long.class) {
			list.forEach(o -> {
				buf.writeLong(((Long) o).longValue());
			});
		} else if (clazz == Float.class) {
			list.forEach(o -> {
				buf.writeFloat(((Float) o).floatValue());
			});
		} else if (clazz == Double.class) {
			list.forEach(o -> {
				buf.writeDouble(((Double) o).doubleValue());
			});
		} else if (clazz == String.class) {
			list.forEach(o -> {
				int s = ((String) o).length();
				buf.writeInt(s);
				buf.writeUtf((String) o, s);
			});
		} else {
			list.forEach(o -> {
				writeObject(o, buf);
			});
		}
	}
}
