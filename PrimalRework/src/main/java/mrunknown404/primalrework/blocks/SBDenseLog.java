package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.SBRotatedPillar;
import mrunknown404.primalrework.helpers.MathH;
import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SBDenseLog extends SBRotatedPillar {
	
	public SBDenseLog() {
		super("dense_log", EnumStage.stage2, 16, PRItemGroups.BLOCKS, Material.WOOD, SoundType.WOOD, true, 0, 3, 6, false, HarvestInfo.AXE_MIN);
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
					if (toCheck == Blocks.GRASS || toCheck == Blocks.DIRT || toCheck == PRBlocks.MUSHROOM_GRASS.get()) {
						continue;
					}
					return false;
				} else if (shouldBe == PRBlocks.DENSE_LOG.get() && (toCheck == PRBlocks.DENSE_LOG.get())) {// || toCheck == InitBlocks.CHARCOAL_PIT_MASTER)) {
					Block nb = w.getBlockState(center.offset(x, 1, z)).getBlock();
					if (nb == Blocks.GRASS || nb == Blocks.DIRT || nb == PRBlocks.MUSHROOM_GRASS.get()) {
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
	
	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 5;
	}
	
	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 5;
	}
}
