package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCharcoalBlock extends BlockBase {

	public BlockCharcoalBlock() {
		super("charcoal_block", Material.ROCK, SoundType.STONE, 4, 20, EnumStage.stage2, new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.axe, EnumToolMaterial.flint));
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 8;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 10;
	}
}
