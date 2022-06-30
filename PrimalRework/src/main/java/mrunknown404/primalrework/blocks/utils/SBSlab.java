package mrunknown404.primalrework.blocks.utils;

import java.util.function.Supplier;

import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.HarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class SBSlab extends StagedBlock implements IWaterLoggable {
	public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape BOTTOM_AABB = Block.box(0, 0, 0, 16, 8, 16);
	private static final VoxelShape TOP_AABB = Block.box(0, 8, 0, 16, 16, 16);
	
	public SBSlab(Supplier<Stage> stage, BlockInfo blockInfo, HarvestInfo info) {
		super(stage, 64, PRItemGroups.BLOCKS, blockInfo, BlockStateType.slab, BlockModelType.slab, info);
		registerDefaultState(defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, false));
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return state.getValue(TYPE) != SlabType.DOUBLE;
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(TYPE, WATERLOGGED);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctx) {
		switch (state.getValue(TYPE)) {
			case DOUBLE:
				return VoxelShapes.block();
			case TOP:
				return TOP_AABB;
			default:
				return BOTTOM_AABB;
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		BlockPos blockpos = ctx.getClickedPos();
		BlockState blockstate = ctx.getLevel().getBlockState(blockpos);
		if (blockstate.is(this)) {
			return blockstate.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, false);
		}
		
		BlockState blockstate1 = defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, ctx.getLevel().getFluidState(blockpos).getType() == Fluids.WATER);
		Direction direction = ctx.getClickedFace();
		return direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getClickLocation().y - blockpos.getY() > 0.5)) ? blockstate1 :
				blockstate1.setValue(TYPE, SlabType.TOP);
	}
	
	@Override
	public boolean canBeReplaced(BlockState state, BlockItemUseContext ctx) {
		SlabType slabtype = state.getValue(TYPE);
		if (slabtype != SlabType.DOUBLE && ctx.getItemInHand().getItem() == asStagedItem()) {
			if (ctx.replacingClickedOnBlock()) {
				boolean flag = ctx.getClickLocation().y - ctx.getClickedPos().getY() > 0.5;
				Direction direction = ctx.getClickedFace();
				
				if (slabtype == SlabType.BOTTOM) {
					return direction == Direction.UP || flag && direction.getAxis().isHorizontal();
				}
				
				return direction == Direction.DOWN || !flag && direction.getAxis().isHorizontal();
			}
			
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
	
	@Override
	public boolean placeLiquid(IWorld world, BlockPos pos, BlockState state, FluidState fluidState) {
		return state.getValue(TYPE) != SlabType.DOUBLE ? IWaterLoggable.super.placeLiquid(world, pos, state, fluidState) : false;
	}
	
	@Override
	public boolean canPlaceLiquid(IBlockReader world, BlockPos pos, BlockState state, Fluid fluid) {
		return state.getValue(TYPE) != SlabType.DOUBLE ? IWaterLoggable.super.canPlaceLiquid(world, pos, state, fluid) : false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state0, Direction dir, BlockState state1, IWorld world, BlockPos pos0, BlockPos pos1) {
		if (state0.getValue(WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(pos0, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		
		return super.updateShape(state0, dir, state1, world, pos0, pos1);
	}
	
	@Override
	public boolean isPathfindable(BlockState state, IBlockReader block, BlockPos pos, PathType path) {
		return path == PathType.WATER && block.getFluidState(pos).is(FluidTags.WATER);
	}
}
