package mrunknown404.primalrework.network.message;

import io.netty.buffer.ByteBuf;
import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PrimalEnchantingMessage implements IMessage, IMessageHandler<PrimalEnchantingMessage, IMessage> {
	
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
	
	@Override
	public IMessage onMessage(PrimalEnchantingMessage message, MessageContext ctx) {
		World world = DimensionManager.getWorld(message.dimension);
		
		if (world != null && !world.isRemote) {
			if (ctx.getServerHandler().player.getEntityId() == message.playerID) {
				EntityPlayerMP player = ctx.getServerHandler().player;
				
				player.getServer().addScheduledTask(new Runnable() {
					@Override
					public void run() {
						((TileEntityPrimalEnchanting) world.getTileEntity(message.pos)).getContainer().enchantItem(player);
					}
				});
			}
		}
		
		return null;
	}
}
