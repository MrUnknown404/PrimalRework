package mrunknown404.primalrework.blocks;

import java.util.function.Supplier;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.BlockInfo.UniqueRawBlockInfo;
import mrunknown404.primalrework.utils.HarvestInfo;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class SBUnlitPrimalTorch extends StagedBlock {
	private static final VoxelShape SHAPE = box(6, 0, 6, 10, 8, 10);
	
	SBUnlitPrimalTorch(Supplier<Stage> stage, UniqueRawBlockInfo blockInfo, BlockStateType blockstateType, HarvestInfo info) {
		super(stage, 64, PRItemGroups.BLOCKS, BlockInfo.of(blockInfo), blockstateType, BlockModelType.none, info);
	}
	
	public SBUnlitPrimalTorch() {
		super(PRStages.STAGE_0, 64, PRItemGroups.BLOCKS, BlockInfo.of(BlockInfo.UNLIT_PRIMAL_TORCH), BlockStateType.normal, BlockModelType.none, HarvestInfo.HAND);
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState state2, IWorld world, BlockPos pos, BlockPos pos2) {
		return direction == Direction.DOWN && !canSurvive(state, world, pos) ? Blocks.AIR.defaultBlockState() : state;
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		return canSupportCenter(world, pos.below(), Direction.UP);
	}
	
	@Override
	public ItemType getItemType() {
		return ItemType.itemblock;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
