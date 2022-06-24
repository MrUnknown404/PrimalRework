package mrunknown404.primalrework.events;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.BlockH;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HarvestEvents {
	@SubscribeEvent
	public void breakSpeed(PlayerEvent.BreakSpeed e) {
		Item item = e.getPlayer().getMainHandItem().getItem();
		
		if ((item != Items.AIR && !(item instanceof StagedItem)) || !(e.getState().getBlock() instanceof StagedBlock)) {
			e.setNewSpeed(0);
			return;
		}
		
		StagedBlock block = (StagedBlock) e.getState().getBlock();
		ToolType toolType = item instanceof StagedItem ? ((StagedItem) item).toolType : ToolType.NONE;
		ToolMaterial toolMat = item instanceof StagedItem ? ((StagedItem) item).toolMat : ToolMaterial.HAND;
		HarvestInfo info = BlockH.getBlockHarvestInfo(block, toolType);
		
		if (toolMat == ToolMaterial.UNBREAKABLE) {
			e.setNewSpeed(0);
			return;
		}
		
		if (info != null) {
			if (info.toolType == ToolType.NONE) {
				e.setNewSpeed(ToolMaterial.HAND.speed);
			} else if (toolType == info.toolType && toolMat.level >= info.toolMat.level) {
				e.setNewSpeed(toolMat.speed);
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
		Item item = e.getPlayer().getMainHandItem().getItem();
		
		if (e.getPlayer().isCreative()) {
			return;
		} else if ((item != Items.AIR && !(item instanceof StagedItem)) || !(e.getState().getBlock() instanceof StagedBlock)) {
			e.setCanceled(true);
			return;
		}
		
		StagedBlock block = (StagedBlock) e.getState().getBlock();
		ToolType toolType = item instanceof StagedItem ? ((StagedItem) item).toolType : ToolType.NONE;
		ToolMaterial toolMat = item instanceof StagedItem ? ((StagedItem) item).toolMat : ToolMaterial.HAND;
		HarvestInfo info = BlockH.getBlockHarvestInfo(block, toolType);
		
		if (info != null && ((toolType == info.toolType && toolMat.level >= info.toolMat.level) || info.toolType == ToolType.NONE)) {
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
