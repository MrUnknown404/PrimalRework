package mrunknown404.primalrework.blocks;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import mrunknown404.primalrework.api.registry.PRRegistryObject;
import mrunknown404.primalrework.blocks.BlockInfo.UniqueRawBlockInfo;
import mrunknown404.primalrework.blocks.HarvestInfo.DropInfo;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.init.InitToolMaterials;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class SBUnlitPrimalWallTorch extends SBUnlitPrimalTorch {
	protected static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(5.5, 3, 11, 10.5, 11.25, 16), Direction.SOUTH,
			Block.box(5.5, 3, 0, 10.5, 11.25, 5), Direction.WEST, Block.box(11, 3, 5.5, 16, 11.25, 10.5), Direction.EAST, Block.box(0, 3, 5.5, 5, 11.25, 10.5)));
	
	SBUnlitPrimalWallTorch(PRRegistryObject<Stage> stage, UniqueRawBlockInfo blockInfo, HarvestInfo info) {
		super(stage, blockInfo, BlockStateType.facing, info);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}
	
	public SBUnlitPrimalWallTorch() {
		this(InitStages.STAGE_BEFORE, BlockInfo.UNLIT_PRIMAL_TORCH, new HarvestInfo(ToolType.NONE, InitToolMaterials.HAND, DropInfo.of(InitBlocks.UNLIT_PRIMAL_TORCH)));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state.getValue(FACING));
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
		Direction direction = state.getValue(FACING);
		BlockPos blockpos = pos.relative(direction.getOpposite());
		return reader.getBlockState(blockpos).isFaceSturdy(reader, blockpos, direction);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = defaultBlockState();
		IWorldReader iworldreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		
		for (Direction direction : context.getNearestLookingDirections()) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction1 = direction.getOpposite();
				blockstate = blockstate.setValue(FACING, direction1);
				if (blockstate.canSurvive(iworldreader, blockpos)) {
					return blockstate;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState state2, IWorld world, BlockPos pos, BlockPos pos2) {
		return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : state;
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
