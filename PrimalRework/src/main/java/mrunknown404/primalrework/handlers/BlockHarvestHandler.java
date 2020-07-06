package mrunknown404.primalrework.handlers;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo.ItemDropInfo;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockHarvestHandler {
	@SubscribeEvent
	public void onHarvestDrop(HarvestDropsEvent e) {
		e.setDropChance(100);
		if (e.getHarvester() != null && !HarvestHelper.canBreak(e.getState().getBlock(), e.getHarvester().getHeldItemMainhand().getItem())) {
			e.getDrops().clear();
			return;
		}
		
		BlockHarvestInfo binfo = HarvestHelper.getHarvestInfo(e.getState().getBlock());
		if (binfo == null) {
			return;
		}
		
		HarvestDropInfo drop = binfo
				.getDrop(e.getHarvester() != null ? HarvestHelper.getItemsToolType(e.getState().getBlock(), e.getHarvester().getHeldItemMainhand().getItem()) : EnumToolType.none);
		if (drop == null) {
			return;
		}
		
		Random r = new Random();
		boolean isSilk = e.isSilkTouching();
		
		if (drop.isReplace()) {
			e.getDrops().clear();
		}
		
		for (ItemDropInfo itemDrop : drop.getDrops()) {
			if (itemDrop.needsSilk == isSilk) {
				int fort = itemDrop.usesFortune ? r.nextInt(e.getFortuneLevel() + 1) : 1;
				
				if (r.nextInt(100) + 1 <= itemDrop.dropChance * (itemDrop.chanceFortune + 1)) {
					int amount = itemDrop.dropAmount + (itemDrop.usesFortune ? fort : 0) + ThreadLocalRandom.current().nextInt(itemDrop.randomDropMin, itemDrop.randomDropMax + 1);
					
					e.getDrops().add(new ItemStack(itemDrop.item, amount, itemDrop.meta));
				}
			}
		}
	}
}
