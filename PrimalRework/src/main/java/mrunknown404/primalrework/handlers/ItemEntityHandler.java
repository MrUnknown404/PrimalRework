package mrunknown404.primalrework.handlers;

import java.util.ArrayDeque;

import mrunknown404.primalrework.entity.EntityItemDrop;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class ItemEntityHandler {
	private final ArrayDeque<PendingItemDrop> items = new ArrayDeque<>();
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityJoin(EntityJoinWorldEvent event) {
		if (event.getWorld().isRemote || event.isCanceled() || event.getEntity().isDead) {
			return;
		}
		
		if (event.getEntity().getClass() == EntityItem.class) {
			EntityItem item = (EntityItem) event.getEntity();
			if (!item.getItem().isEmpty()) {
				items.add(new PendingItemDrop(item, new EntityItemDrop(item)));
			}
			
		}
	}
	
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) {
		while (!items.isEmpty()) {
			PendingItemDrop pend = items.poll();
			
			if (pend == null || pend.item.isDead || pend.item.getItem().isEmpty()) {
				continue;
			}
			
			pend.item.setDead();
			pend.item.setItem(ItemStack.EMPTY);
			pend.item.setInfinitePickupDelay();
			
			pend.item.world.spawnEntity(pend.loot);
		}
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		if (event.getWorld().isRemote || (event.getWorld().getMinecraftServer() != null && event.getWorld().getMinecraftServer().isServerRunning())) {
			return;
		}
		
		items.clear();
	}
	
	private class PendingItemDrop {
		private final EntityItem item;
		private final EntityItemDrop loot;
		
		private PendingItemDrop(EntityItem item, EntityItemDrop loot) {
			this.item = item;
			this.loot = loot;
		}
	}
}
