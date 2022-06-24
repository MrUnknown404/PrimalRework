package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.SBRotatedPillar;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.BlockInfo;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class SBStrippedLog extends SBRotatedPillar {
	private static final VoxelShape XX = box(0, 1, 1, 16, 15, 15);
	private static final VoxelShape YY = box(1, 0, 1, 15, 16, 15);
	private static final VoxelShape ZZ = box(1, 1, 0, 15, 15, 16);
	private static final VoxelShape NONE = box(1, 1, 1, 15, 15, 15);
	
	public SBStrippedLog(String name) {
		super("stripped_" + name + "_log", PRStages.STAGE_0, 32, PRItemGroups.BLOCKS, Material.WOOD, SoundType.WOOD, true, false, 0, BlockInfo.WOOD, false,
				HarvestInfo.AXE_MIN);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(BlockStateProperties.AXIS)) {
			case X:
				return XX;
			case Y:
				return YY;
			case Z:
				return ZZ;
			default:
				return NONE;
		}
	}
	
	@Override
	public BlockModelType getBlockModelType() {
		return BlockModelType.none;
	}
	
	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 5;
	}
	
	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 5;
	}
}
