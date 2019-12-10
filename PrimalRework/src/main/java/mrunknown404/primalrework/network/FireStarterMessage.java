package mrunknown404.primalrework.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class FireStarterMessage implements IMessage {
	
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
}
