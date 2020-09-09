package mrunknown404.primalrework.blocks.slabs;

import mrunknown404.primalrework.blocks.util.BlockStagedSlab;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockStoneSlab extends BlockStagedSlab {
	
	public BlockStoneSlab(String name, boolean isDouble) {
		super(name, Material.ROCK, SoundType.STONE, 1.5f, 1.5f, isDouble, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.flint));
	}
	
	@Override
	public BlockStagedSlab getSingleVersion() {
		return (BlockStagedSlab) InitBlocks.STONE_SLAB;
	}
	
	@Override
	public BlockStagedSlab getDoubleVersion() {
		return (BlockStagedSlab) InitBlocks.STONE_DOUBLE_SLAB;
	}
}
