package mrunknown404.primalrework.events;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import mrunknown404.primalrework.utils.helpers.BlockH;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HarvestEvents {
	@SubscribeEvent
	public void breakSpeed(PlayerEvent.BreakSpeed e) {
		if (!(e.getPlayer().getMainHandItem().getItem() instanceof StagedItem) || !(e.getState().getBlock() instanceof StagedBlock)) {
			e.setNewSpeed(0);
			return;
		}
		
		StagedBlock block = (StagedBlock) e.getState().getBlock();
		ItemStack hand = e.getPlayer().getMainHandItem();
		StagedItem item = (StagedItem) hand.getItem();
		HarvestInfo info = BlockH.getBlockHarvestInfo(block, item.toolType);
		
		if (item.toolMat == EnumToolMaterial.unbreakable) {
			e.setNewSpeed(0);
			return;
		}
		
		if (info != null) {
			if (info.toolType == EnumToolType.none) {
				e.setNewSpeed(EnumToolMaterial.hand.speed);
			} else if (item.toolType == info.toolType && item.toolMat.level >= info.toolMat.level) {
				e.setNewSpeed(item.toolMat.speed);
			} else {
				e.setNewSpeed(0);
			}
		} else {
			e.setNewSpeed(0);
		}
	}
	
	@SubscribeEvent
	public void harvestCheck(PlayerEvent.HarvestCheck e) {
		e.setCanHarvest(false);
	}
	
	@SubscribeEvent
	public void onBreak(BlockEvent.BreakEvent e) {
		if (e.getPlayer().isCreative()) {
			return;
		} else if (!(e.getPlayer().getMainHandItem().getItem() instanceof StagedItem) || !(e.getState().getBlock() instanceof StagedBlock)) {
			e.setCanceled(true);
			return;
		}
		
		StagedBlock block = (StagedBlock) e.getState().getBlock();
		ItemStack hand = e.getPlayer().getMainHandItem();
		StagedItem item = (StagedItem) hand.getItem();
		HarvestInfo info = BlockH.getBlockHarvestInfo(block, item.toolType);
		
		if (info != null && ((item.toolType == info.toolType && item.toolMat.level >= info.toolMat.level) || info.toolType == EnumToolType.none)) {
			BlockPos p = e.getPos();
			
			if (info.hasDrops()) {
				for (DropInfo drop : info.drops) {
					ItemStack stack = drop.getItem();
					if (!stack.isEmpty()) {
						e.getWorld().addFreshEntity(new ItemEntity((World) e.getWorld(), p.getX() + 0.5d, p.getY(), p.getZ() + 0.5d, stack));
					}
				}
			} else {
				e.getWorld().addFreshEntity(new ItemEntity((World) e.getWorld(), p.getX() + 0.5d, p.getY(), p.getZ() + 0.5d, new ItemStack(block)));
			}
		}
	}
}
