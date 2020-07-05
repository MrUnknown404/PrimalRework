package mrunknown404.primalrework.network.message;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import mrunknown404.primalrework.init.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FireStarterMessage implements IMessage, IMessageHandler<FireStarterMessage, IMessage> {
	
	int dimension, playerID;
	BlockPos pos;
	EnumHand hand;
	boolean createdCharcoalPit;
	
	public FireStarterMessage() {}
	
	public FireStarterMessage(EntityPlayer player, BlockPos pos, EnumHand hand, boolean createdCharcoalPit) {
		this.dimension = player.dimension;
		this.playerID = player.getEntityId();
		this.pos = pos;
		this.hand = hand;
		this.createdCharcoalPit = createdCharcoalPit;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		dimension = buf.readInt();
		playerID = buf.readInt();
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		hand = EnumHand.values()[buf.readInt()];
		createdCharcoalPit = buf.readBoolean();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dimension);
		buf.writeInt(playerID);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(hand == EnumHand.MAIN_HAND ? 0 : hand == EnumHand.OFF_HAND ? 1 : -1);
		buf.writeBoolean(createdCharcoalPit);
	}
	
	@Override
	public IMessage onMessage(FireStarterMessage message, MessageContext ctx) {
		World world = DimensionManager.getWorld(message.dimension);
		
		if (world != null && !world.isRemote) {
			if (ctx.getServerHandler().player.getEntityId() == message.playerID) {
				EntityPlayerMP player = ctx.getServerHandler().player;
				
				player.getServer().addScheduledTask(new Runnable() {
					@Override
					public void run() {
						world.playSound(null, message.pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 0.5f, 0.75f);
						
						if (!player.getHeldItem(message.hand).isEmpty()) {
							player.getHeldItem(message.hand).damageItem(new Random().nextInt(3) + 1, player);
						}
						
						if (message.createdCharcoalPit) {
							world.setBlockState(message.pos.down(2), ModBlocks.CHARCOAL_PIT_MASTER.getDefaultState(), 11);
						} else {
							world.setBlockState(message.pos, Blocks.FIRE.getDefaultState(), 11);
						}
					}
				});
			}
		}
		
		return null;
	}
}
