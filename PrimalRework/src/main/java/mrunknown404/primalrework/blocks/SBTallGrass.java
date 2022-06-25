package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.registries.PRItems;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;

public class SBTallGrass extends StagedBlock {
	public SBTallGrass() {
		super("tall_grass", PRStages.STAGE_0, 64, PRItemGroups.BLOCKS, BlockInfo.of(BlockInfo.PLANT), BlockStateType.normal, BlockModelType.none,
				new HarvestInfo(ToolType.NONE, ToolMaterial.HAND, DropInfo.item(PRItems.PLANT_FIBER, 30)),
				new HarvestInfo(ToolType.KNIFE, ToolMaterial.CLAY, DropInfo.item(PRItems.PLANT_FIBER, 80)),
				new HarvestInfo(ToolType.HOE, ToolMaterial.CLAY, DropInfo.item(PRItems.PLANT_FIBER, 80) /* TODO add seeds here */),
				new HarvestInfo(ToolType.SHEARS, ToolMaterial.CLAY, DropInfo.block(PRBlocks.TALL_GRASS, 80)));
		useVanillaNamespaceItem();
	}
	
	@Override
	public ItemType getItemType() {
		return ItemType.itemblock;
	}
	
	//TODO implement tall grass features
}
