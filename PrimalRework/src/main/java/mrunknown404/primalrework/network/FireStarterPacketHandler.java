package mrunknown404.primalrework.network;

import java.util.Random;

import mrunknown404.primalrework.init.ModBlocks;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FireStarterPacketHandler implements IMessageHandler<FireStarterMessage, IMessage> {
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
