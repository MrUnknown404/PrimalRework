package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.block.BlockRenderType;
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
	
	protected SBUnlitPrimalTorch(String name, EnumStage stage, int light, BlockStateType blockStateType, HarvestInfo info) {
		super(name, stage, 64, InitItemGroups.BLOCKS, Material.DECORATION, SoundType.WOOD, false, light, 0, 0, false, blockStateType, BlockModelType.none, info);
	}
	
	public SBUnlitPrimalTorch() {
		this("unlit_primal_torch", EnumStage.stage0, 0, BlockStateType.normal, HarvestInfo.HAND);
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
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
