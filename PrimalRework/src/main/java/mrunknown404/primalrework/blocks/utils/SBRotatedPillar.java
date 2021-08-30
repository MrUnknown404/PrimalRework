package mrunknown404.primalrework.blocks.utils;

import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumBlockInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;

public class SBRotatedPillar extends StagedBlock {
	
	protected SBRotatedPillar(String name, EnumStage stage, int stackSize, ItemGroup tab, Material material, SoundType sound, boolean hasCollision, int lightLevel,
			EnumBlockInfo blockInfo, boolean isRandomTick, HarvestInfo info, HarvestInfo... extraInfos) {
		super(name, stage, stackSize, tab, material, sound, hasCollision, lightLevel, blockInfo, isRandomTick, BlockStateType.facing_pillar, BlockModelType.facing_pillar, info,
				extraInfos);
		registerDefaultState(defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		switch (rotation) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.getValue(BlockStateProperties.AXIS)) {
					case X:
						return state.setValue(BlockStateProperties.AXIS, Direction.Axis.Z);
					case Z:
						return state.setValue(BlockStateProperties.AXIS, Direction.Axis.X);
					default:
						return state;
				}
			default:
				return state;
		}
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.AXIS);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(BlockStateProperties.AXIS, context.getClickedFace().getAxis());
	}
}
