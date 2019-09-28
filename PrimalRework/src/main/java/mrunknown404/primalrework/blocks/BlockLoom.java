package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;

public class BlockLoom extends BlockBase {

	private static final AxisAlignedBB bb = new AxisAlignedBB(1.05 / 16, 0, 1.05 / 16, 15.05 / 16, 13.05f / 16, 15.05 / 16);
	
	public BlockLoom() {
		super("loom", Material.ROCK, SoundType.STONE, BlockRenderLayer.CUTOUT, 2, 2, bb, bb, EnumStage.stage1, new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.flint));
	}
}
