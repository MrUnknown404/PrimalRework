package mrunknown404.primalrework.helpers;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.SIBlock;
import mrunknown404.primalrework.stage.VanillaRegistry;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

@SuppressWarnings("deprecation")
public class BlockH {
	public static HarvestInfo getBlockHarvestInfo(Block block, EnumToolType toolType) {
		boolean flag = block instanceof StagedBlock;
		HarvestInfo info = flag ? ((StagedBlock) block).getHarvest().get(toolType) : VanillaRegistry.getHarvestInfo(block, toolType);
		return info == null ? flag ? ((StagedBlock) block).getHarvest().get(EnumToolType.none) : VanillaRegistry.getHarvestInfo(block, EnumToolType.none) : info;
	}
	
	public static List<HarvestInfo> getBlockHarvestInfos(Block block) {
		return block instanceof StagedBlock ? new ArrayList<HarvestInfo>(((StagedBlock) block).getHarvest().values()) : VanillaRegistry.getHarvestInfos(block);
	}
	
	public static Block getBlockFromItem(Item item) {
		return item instanceof SIBlock ? ((SIBlock) item).getBlock() : ((BlockItem) item).getBlock();
	}
}
