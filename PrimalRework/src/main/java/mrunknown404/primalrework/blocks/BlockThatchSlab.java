package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockSlabBase;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockThatchSlab extends BlockSlabBase {
	
	public BlockThatchSlab(String name, boolean isDouble) {
		super(name, Material.LEAVES, SoundType.PLANT, 0.15f, 0.15f, isDouble, EnumStage.stage0,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand),
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.knife, EnumToolMaterial.flint));
	}
	
	@Override
	public BlockSlabBase getSingleVersion() {
		return (BlockSlabBase) ModBlocks.THATCH_SLAB;
	}
	
	@Override
	public BlockSlabBase getDoubleVersion() {
		return (BlockSlabBase) ModBlocks.THATCH_DOUBLE_SLAB;
	}
}
