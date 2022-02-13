package mrunknown404.primalrework.blocks;

import java.util.function.Supplier;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.EnumBlockInfo;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class SBGroundItem extends StagedBlock implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE = box(3, 0, 3, 13, 1, 13);
	
	public SBGroundItem(String name, SoundType sound, Supplier<StagedItem> dropInstead) {
		super("ground_" + name, PRStages.STAGE_0, 64, PRItemGroups.BLOCKS, Material.DECORATION, sound, false, false, 0, EnumBlockInfo.instant, false,
				BlockStateType.random_direction, BlockModelType.none,
				dropInstead == null ? HarvestInfo.HAND : new HarvestInfo(EnumToolType.none, EnumToolMaterial.hand, DropInfo.item(dropInstead)));
		registerDefaultState(defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)));
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		BlockPos blockpos = ctx.getClickedPos();
		BlockState blockstate = ctx.getLevel().getBlockState(blockpos);
		if (blockstate.is(this)) {
			return blockstate.setValue(WATERLOGGED, Boolean.valueOf(false));
		}
		
		return defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(ctx.getLevel().getFluidState(blockpos).getType() == Fluids.WATER));
	}
	
	@Override
	public BlockState updateShape(BlockState state0, Direction direction, BlockState state2, IWorld world, BlockPos pos0, BlockPos pos2) {
		if (state0.getValue(WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(pos0, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		
		return direction == Direction.DOWN && !canSurvive(state0, world, pos0) ? Blocks.AIR.defaultBlockState() : state0;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
	
	@Override
	public boolean isPathfindable(BlockState state, IBlockReader block, BlockPos pos, PathType path) {
		switch (path) {
			case LAND:
				return false;
			case WATER:
				return block.getFluidState(pos).is(FluidTags.WATER);
			case AIR:
				return false;
			default:
				return false;
		}
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
		return reader.getBlockState(pos.below()).isFaceSturdy(reader, pos, Direction.UP);
	}
	
	@Override
	public boolean isPossibleToRespawnInThis() {
		return true;
	}
	
	@Override
	public ItemType getItemType() {
		return ItemType.itemblock;
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
