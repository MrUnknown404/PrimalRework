package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockRotatedPillarBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockStrippedLog extends BlockRotatedPillarBase {
	
	private static final AxisAlignedBB VERTICAL_BB = new AxisAlignedBB(1.05 / 16, 0, 1.05 / 16, 15.05 / 16, 1, 15.05 / 16);
	private static final AxisAlignedBB NS_BB = new AxisAlignedBB(1.05 / 16, 1.05 / 16, 0, 15.05 / 16, 15.05 / 16, 1);
	private static final AxisAlignedBB EW_BB = new AxisAlignedBB(0, 1.05 / 16, 1.05 / 16, 1, 15.05 / 16, 15.05 / 16);
	private static final AxisAlignedBB NONE_BB = new AxisAlignedBB(1.05 / 16, 1.05 / 16, 1.05 / 16, 15.05 / 16, 15.05 / 16, 15.05 / 16);
	
	public BlockStrippedLog(String type) {
		super("stripped_" + type + "_log", Material.WOOD, SoundType.WOOD, BlockRenderLayer.CUTOUT, 2, 2, NONE_BB, NONE_BB, EnumStage.stage0,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.axe, EnumToolMaterial.flint));
		setDefaultState(blockState.getBaseState().withProperty(BlockLog.LOG_AXIS, EnumAxis.Y));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(BlockLog.LOG_AXIS)) {
			case X:
				return EW_BB;
			case Z:
				return NS_BB;
			case Y:
				return VERTICAL_BB;
			default:
				return NONE_BB;
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return getBoundingBox(state, source, pos);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 5;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 5;
	}
}
