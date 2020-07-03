package mrunknown404.primalrework.network;

import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PrimalEnchantingPacketHandler implements IMessageHandler<PrimalEnchantingMessage, IMessage> {
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
