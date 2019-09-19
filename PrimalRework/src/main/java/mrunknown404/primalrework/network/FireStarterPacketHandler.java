package mrunknown404.primalrework.network;

import java.util.Random;

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
	public IMessage onMessage(FireStarterMessage message, MessageContext msg) {
		World world = DimensionManager.getWorld(message.dimension);
		
		if (world != null && !world.isRemote) {
			if (msg.getServerHandler().player.getEntityId() == message.playerID) {
				EntityPlayerMP player = msg.getServerHandler().player;
				
				player.getServer().addScheduledTask(new Runnable() {
					public void run() {
						world.playSound(null, message.pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 0.5f, 0.75f);
						world.setBlockState(message.pos, Blocks.FIRE.getDefaultState(), 11);
						
						if (!player.getHeldItem(message.hand).isEmpty()) {
							player.getHeldItem(message.hand).damageItem(new Random().nextInt(3) + 1, player);
						}
					}
				});
			}
		}
		
		return null;
	}
}
