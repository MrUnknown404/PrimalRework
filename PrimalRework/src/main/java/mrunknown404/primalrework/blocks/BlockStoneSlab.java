package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockSlabBase;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockStoneSlab extends BlockSlabBase {
	
	public BlockStoneSlab(String name, boolean isDouble) {
		super(name, Material.ROCK, SoundType.STONE, 1.5f, 1.5f, isDouble, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.flint));
	}
	
	@Override
	public BlockSlabBase getSingleVersion() {
		return (BlockSlabBase) ModBlocks.STONE_SLAB;
	}
	
	@Override
	public BlockSlabBase getDoubleVersion() {
		return (BlockSlabBase) ModBlocks.STONE_DOUBLE_SLAB;
	}
}
