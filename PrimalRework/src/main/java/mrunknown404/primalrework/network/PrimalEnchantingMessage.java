package mrunknown404.primalrework.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PrimalEnchantingMessage implements IMessage {
	
	int dimension, playerID;
	BlockPos pos;
	
	public PrimalEnchantingMessage() {}
	
	public PrimalEnchantingMessage(EntityPlayer player, BlockPos pos) {
		this.playerID = player.getEntityId();
		this.dimension = player.dimension;
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		
		playerID = buf.readInt();
		dimension = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		
		buf.writeInt(playerID);
		buf.writeInt(dimension);
	}
}
