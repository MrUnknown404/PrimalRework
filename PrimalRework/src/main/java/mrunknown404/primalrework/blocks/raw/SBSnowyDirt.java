package mrunknown404.primalrework.blocks.raw;

import java.util.function.Supplier;

import mrunknown404.primalrework.init.InitPRItemGroups;
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
	
	public SBSnowyDirt(Supplier<Stage> stage, BlockInfo blockInfo, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info, HarvestInfo... extraInfos) {
		super(stage, 64, InitPRItemGroups.BLOCKS, blockInfo, blockStateType, blockModelType, info, extraInfos);
		registerDefaultState(stateDefinition.any().setValue(SNOWY, false));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state0, Direction dir, BlockState state1, IWorld world, BlockPos pos0, BlockPos pos1) {
		return dir != Direction.UP ? super.updateShape(state0, dir, state1, world, pos0, pos1) : state0.setValue(SNOWY, state1.is(Blocks.SNOW_BLOCK) || state1.is(Blocks.SNOW));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		BlockState blockstate = ctx.getLevel().getBlockState(ctx.getClickedPos().above());
		return defaultBlockState().setValue(SNOWY, blockstate.is(Blocks.SNOW_BLOCK) || blockstate.is(Blocks.SNOW));
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(SNOWY);
	}
}
