package mrunknown404.primalrework.blocks.utils;

import java.util.function.Supplier;

import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.HarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SBSnowyDirt extends StagedBlock {//TODO switch to my snow
	public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
	
	public SBSnowyDirt(String name, Supplier<Stage> stage, BlockInfo blockInfo, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info,
			HarvestInfo... extraInfos) {
		super(name, stage, 64, PRItemGroups.BLOCKS, blockInfo, blockStateType, blockModelType, info, extraInfos);
		registerDefaultState(stateDefinition.any().setValue(SNOWY, Boolean.valueOf(false)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state0, Direction dir, BlockState state1, IWorld world, BlockPos pos0, BlockPos pos1) {
		return dir != Direction.UP ? super.updateShape(state0, dir, state1, world, pos0, pos1) :
				state0.setValue(SNOWY, Boolean.valueOf(state1.is(Blocks.SNOW_BLOCK) || state1.is(Blocks.SNOW)));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		BlockState blockstate = ctx.getLevel().getBlockState(ctx.getClickedPos().above());
		return defaultBlockState().setValue(SNOWY, Boolean.valueOf(blockstate.is(Blocks.SNOW_BLOCK) || blockstate.is(Blocks.SNOW)));
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(SNOWY);
	}
}
