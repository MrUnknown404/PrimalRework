package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.blocks.HarvestInfo.DropInfo;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.init.InitToolMaterials;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.BlockH;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class SBGrassSlab extends SBSlab { //TODO replace with my snow
	public SBGrassSlab() {
		super(InitStages.STAGE_BEFORE, BlockInfo.of(BlockInfo.DRY_GRASS), new HarvestInfo(ToolType.SHOVEL, InitToolMaterials.CLAY, DropInfo.of(InitBlocks.DIRT_SLAB)));
	}
	
	@Override
	public BlockStateType getBlockStateType() {
		return BlockStateType.none;
	}
	
	@Override
	public BlockModelType getBlockModelType() {
		return BlockModelType.none;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!canBeGrass(state, world, pos)) {
			if (!world.isAreaLoaded(pos, 3)) {
				return;
			}
			
			world.setBlockAndUpdate(pos, InitBlocks.DIRT_SLAB.get().defaultBlockState().setValue(SBSlab.TYPE, world.getBlockState(pos).getValue(SBSlab.TYPE)));
		} else {
			if (world.getMaxLocalRawBrightness(pos.above()) >= 9) {
				for (int i = 0; i < 4; i++) {
					BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					BlockState state1 = world.getBlockState(blockpos);
					
					if (state1.is(InitBlocks.DIRT.get()) && canPropagate(state1, world, blockpos)) {
						world.setBlockAndUpdate(blockpos, InitBlocks.GRASS_BLOCK.get().defaultBlockState().setValue(SBSnowyDirt.SNOWY,
								Boolean.valueOf(world.getBlockState(blockpos.above()).is(Blocks.SNOW))));
					} else if (state1.is(InitBlocks.DIRT_SLAB.get()) && canPropagate(state1, world, blockpos)) {
						world.setBlockAndUpdate(blockpos, defaultBlockState().setValue(SBSlab.TYPE, world.getBlockState(blockpos).getValue(SBSlab.TYPE)));
					}
				}
			}
		}
	}
	
	private static boolean canBeGrass(BlockState state, IWorldReader world, BlockPos pos) {
		BlockPos blockpos = pos.above();
		BlockState blockstate = world.getBlockState(blockpos);
		if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowBlock.LAYERS) == 1) {
			return true;
		} else if (blockstate.getFluidState().getAmount() == 8) {
			return false;
		} else {
			return BlockH.getLightBlockInto(world, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(world, blockpos)) < world.getMaxLightLevel();
		}
	}
	
	private static boolean canPropagate(BlockState state, IWorldReader world, BlockPos pos) {
		return canBeGrass(state, world, pos) && !world.getFluidState(pos.above()).is(FluidTags.WATER);
	}
	
	@Override
	public boolean coloredByBiome() {
		return true;
	}
}
