package mrunknown404.primalrework.blocks;

import java.util.Random;
import java.util.function.Supplier;

import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.stage.Stage;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SBFalling extends StagedBlock {
	public SBFalling(Supplier<Stage> stage, int stackSize, BlockInfo blockInfo, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info,
			HarvestInfo[] extraInfos) {
		super(stage, stackSize, InitItemGroups.BLOCKS, blockInfo, blockStateType, blockModelType, info, extraInfos);
	}
	
	public SBFalling(Supplier<Stage> stage, BlockInfo blockInfo, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info, HarvestInfo... extraInfos) {
		super(stage, 64, InitItemGroups.BLOCKS, blockInfo, blockStateType, blockModelType, info, extraInfos);
	}
	
	public SBFalling(Supplier<Stage> stage, BlockInfo blockInfo, HarvestInfo info, HarvestInfo... extraInfos) {
		super(stage, 64, InitItemGroups.BLOCKS, blockInfo, BlockStateType.normal, BlockModelType.normal, info, extraInfos);
	}
	
	@Override
	public void onPlace(BlockState state0, World world, BlockPos pos, BlockState state1, boolean flag) {
		world.getBlockTicks().scheduleTick(pos, this, 2);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state0, Direction dir, BlockState state1, IWorld world, BlockPos pos0, BlockPos pos1) {
		world.getBlockTicks().scheduleTick(pos0, this, 2);
		return super.updateShape(state0, dir, state1, world, pos0, pos1);
	}
	
	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.isEmptyBlock(pos.below()) || isFree(world.getBlockState(pos.below())) && pos.getY() >= 0) {
			FallingBlockEntity fallingblockentity = new FallingBlockEntity(world, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, world.getBlockState(pos));
			falling(fallingblockentity);
			world.addFreshEntity(fallingblockentity);
		}
	}
	
	protected void falling(@SuppressWarnings("unused") FallingBlockEntity entity) {
		
	}
	
	@SuppressWarnings("deprecation")
	private static boolean isFree(BlockState state) {
		Material material = state.getMaterial();
		return state.isAir() || state.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
	}
	
	@SuppressWarnings("unused")
	public void onLand(World world, BlockPos pos, BlockState state0, BlockState state1, FallingBlockEntity entity) {
		
	}
	
	@SuppressWarnings("unused")
	public void onBroken(World world, BlockPos pos, FallingBlockEntity entity) {
		
	}
}
