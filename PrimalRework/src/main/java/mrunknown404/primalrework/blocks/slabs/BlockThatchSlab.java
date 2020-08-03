package mrunknown404.primalrework.blocks.slabs;

import mrunknown404.primalrework.blocks.util.BlockStagedSlab;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockThatchSlab extends BlockStagedSlab {
	
	public BlockThatchSlab(String name, boolean isDouble) {
		super(name, Material.LEAVES, SoundType.PLANT, 0.15f, 0.15f, isDouble, EnumStage.stage0,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand),
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.knife, EnumToolMaterial.flint));
	}
	
	@Override
	public BlockStagedSlab getSingleVersion() {
		return (BlockStagedSlab) ModBlocks.THATCH_SLAB;
	}
	
	@Override
	public BlockStagedSlab getDoubleVersion() {
		return (BlockStagedSlab) ModBlocks.THATCH_DOUBLE_SLAB;
	}
}
