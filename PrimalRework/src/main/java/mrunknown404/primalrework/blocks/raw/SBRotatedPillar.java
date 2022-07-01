package mrunknown404.primalrework.blocks.raw;

import java.util.function.Supplier;

import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.HarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;

public class SBRotatedPillar extends StagedBlock {
	public SBRotatedPillar(Supplier<Stage> stage, int stackSize, ItemGroup tab, BlockInfo blockInfo, HarvestInfo info, HarvestInfo... extraInfos) {
		super(stage, stackSize, tab, blockInfo, BlockStateType.facing_pillar, BlockModelType.facing_pillar, info, extraInfos);
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
