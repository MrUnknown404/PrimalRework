package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.BlockInfo.Hardness;
import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.init.InitStages;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SBDenseLog extends SBRotatedPillar {
	public SBDenseLog() {
		super(InitStages.STAGE_2, 16, InitItemGroups.BLOCKS, BlockInfo.with(BlockInfo.WOOD, Hardness.HARD_0), HarvestInfo.AXE_MIN);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState state, World w, BlockPos pos, Block block, BlockPos fromPos, boolean flag) {
		/*
		if (!w.isClientSide) {
			//loop:
			for (int z = -1; z < 2; z++) {
				for (int x = -1; x < 2; x++) {
					BlockPos npos = pos.offset(x, 0, z);
					Block b = w.getBlockState(npos).getBlock();
					
					if (b == InitBlocks.CHARCOAL_PIT_MASTER) {
						((BlockCharcoalPitMaster) b).checkValid(w, npos);
						break loop;
					
				}
			}
		}
		*/
		super.neighborChanged(state, w, pos, block, fromPos, flag);
	}
	
	/*
	public static boolean isValidCharcoalPit(World w, BlockPos center) {
		Block b = w.getBlockState(center).getBlock();
		if (b != PRBlocks.DENSE_LOG.get()) {// && b != InitBlocks.CHARCOAL_PIT_MASTER) {
			return false;
		}
		
		for (int z = -2; z < 3; z++) {
			for (int x = -2; x < 3; x++) {
				if ((x == -2 && z == -2) || (x == 2 && z == -2) || (x == -2 && z == 2) || (x == 2 && z == 2)) {
					continue;
				}
				
				Block shouldBe = null;
				Block toCheck = w.getBlockState(center.offset(x, 0, z)).getBlock();
				if (z == -2 && MathH.within(x, -1, 1)) {
					shouldBe = Blocks.DIRT;
				} else if (MathH.within(z, -1, 1)) {
					if (x == -2 || x == 2) {
						shouldBe = Blocks.DIRT;
					} else {
						shouldBe = PRBlocks.DENSE_LOG.get();
					}
				} else if (z == 2 && MathH.within(x, -1, 1)) {
					shouldBe = Blocks.DIRT;
				}
				
				if (shouldBe == null) {
					return false;
				} else if (shouldBe == Blocks.DIRT) {
					if (toCheck == Blocks.GRASS || toCheck == Blocks.DIRT) {
						continue;
					}
					return false;
				} else if (shouldBe == PRBlocks.DENSE_LOG.get() && (toCheck == PRBlocks.DENSE_LOG.get())) {// || toCheck == InitBlocks.CHARCOAL_PIT_MASTER)) {
					Block nb = w.getBlockState(center.offset(x, 1, z)).getBlock();
					if (nb == Blocks.GRASS || nb == Blocks.DIRT) {
						continue;
					}
					return false;
				} else {
					return false;
				}
			}
		}
		
		return true;
	}
	*/
}
