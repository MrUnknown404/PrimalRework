package mrunknown404.primalrework.events;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.stage.VanillaRegistry;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HarvestEvents {
	private static Block bsBlock;
	private static EnumToolType bsToolType;
	private static EnumToolMaterial bsToolMat;
	private static HarvestInfo bsInfo;
	private static Block obBlock;
	private static EnumToolType obToolType;
	private static EnumToolMaterial obToolMat;
	private static HarvestInfo obInfo;
	
	@SubscribeEvent
	public void breakSpeed(PlayerEvent.BreakSpeed e) {
		Block block = e.getState().getBlock();
		
		EnumToolType toolType;
		EnumToolMaterial toolMat;
		HarvestInfo info = null;
		if (bsBlock != null && bsBlock == block) {
			toolType = bsToolType;
			toolMat = bsToolMat;
			info = bsInfo;
		} else {
			ItemStack hand = e.getPlayer().getMainHandItem();
			if (hand.getItem() instanceof StagedItem) {
				toolType = ((StagedItem) hand.getItem()).toolType;
				toolMat = ((StagedItem) hand.getItem()).toolMat;
			} else {
				toolType = VanillaRegistry.getToolType(hand.getItem());
				toolMat = VanillaRegistry.getToolMaterial(hand.getItem());
			}
			
			if (block instanceof StagedBlock) {
				info = ((StagedBlock) block).getHarvest().get(toolType);
				if (info == null) {
					info = ((StagedBlock) block).getHarvest().get(EnumToolType.none);
				}
			} else {
				info = VanillaRegistry.getHarvestInfo(block, toolType);
				if (info == null) {
					info = VanillaRegistry.getHarvestInfo(block, EnumToolType.none);
				}
			}
			
			bsBlock = block;
			bsToolType = toolType;
			bsToolMat = toolMat;
			bsInfo = info;
		}
		
		if (toolMat == EnumToolMaterial.unbreakable) {
			e.setNewSpeed(0);
			return;
		}
		
		if (info != null) {
			if (info.toolType == EnumToolType.none) {
				e.setNewSpeed(EnumToolMaterial.hand.speed);
			} else if (toolType == info.toolType && toolMat.level >= info.toolMat.level) {
				e.setNewSpeed(toolMat.speed);
			} else {
				e.setNewSpeed(0.5f);
			}
		} else {
			e.setNewSpeed(0.5f);
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
		}
		
		Block block = e.getState().getBlock();
		EnumToolType toolType;
		EnumToolMaterial toolMat;
		HarvestInfo info = null;
		if (obBlock != null && obBlock == block) {
			toolType = obToolType;
			toolMat = obToolMat;
			info = obInfo;
		} else {
			ItemStack hand = e.getPlayer().getMainHandItem();
			if (hand.getItem() instanceof StagedItem) {
				toolType = ((StagedItem) hand.getItem()).toolType;
				toolMat = ((StagedItem) hand.getItem()).toolMat;
			} else {
				toolType = VanillaRegistry.getToolType(hand.getItem());
				toolMat = VanillaRegistry.getToolMaterial(hand.getItem());
			}
			
			if (block instanceof StagedBlock) {
				info = ((StagedBlock) block).getHarvest().get(toolType);
				if (info == null) {
					info = ((StagedBlock) block).getHarvest().get(EnumToolType.none);
				}
			} else {
				info = VanillaRegistry.getHarvestInfo(block, toolType);
				if (info == null) {
					info = VanillaRegistry.getHarvestInfo(block, EnumToolType.none);
				}
			}
			obBlock = block;
		}
		
		if (info != null && ((toolType == info.toolType && toolMat.level >= info.toolMat.level) || info.toolType == EnumToolType.none)) {
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
