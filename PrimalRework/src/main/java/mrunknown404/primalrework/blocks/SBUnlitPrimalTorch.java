package mrunknown404.primalrework.blocks;

import java.util.function.Supplier;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.BlockInfo;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class SBUnlitPrimalTorch extends StagedBlock {
	private static final VoxelShape SHAPE = box(6, 0, 6, 10, 8, 10);
	
	protected SBUnlitPrimalTorch(String name, Supplier<Stage> stage, int light, BlockStateType blockStateType, HarvestInfo info) {
		super(name, stage, 64, PRItemGroups.BLOCKS, Material.DECORATION, SoundType.WOOD, false, false, light, BlockInfo.INSTANT, false, blockStateType, BlockModelType.none,
				info);
	}
	
	public SBUnlitPrimalTorch() {
		this("unlit_primal_torch", PRStages.STAGE_0, 0, BlockStateType.normal, HarvestInfo.HAND);
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
