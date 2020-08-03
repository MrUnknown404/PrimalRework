package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockStagedRotatedPillar;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDenseLog extends BlockStagedRotatedPillar {
	
	public BlockDenseLog() {
		super("dense_log", Material.WOOD, SoundType.WOOD, 3, 6, EnumStage.stage2, new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.axe, EnumToolMaterial.flint));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World w, BlockPos pos, Block block, BlockPos fromPos) {
		if (!w.isRemote) {
			loop:
			for (int z = -1; z < 2; z++) {
				for (int x = -1; x < 2; x++) {
					BlockPos npos = pos.add(x, 0, z);
					Block b = w.getBlockState(npos).getBlock();
					
					if (b == ModBlocks.CHARCOAL_PIT_MASTER) {
						((BlockCharcoalPitMaster) b).checkValid(w, npos);
						break loop;
					}
				}
			}
		}
		
		super.neighborChanged(state, w, pos, block, fromPos);
	}
	
	public static boolean isValidCharcoalPit(World w, BlockPos center) {
		Block b = w.getBlockState(center).getBlock();
		if (b != ModBlocks.DENSE_LOG && b != ModBlocks.CHARCOAL_PIT_MASTER) {
			return false;
		}
		
		for (int z = -2; z < 3; z++) {
			for (int x = -2; x < 3; x++) {
				if ((x == -2 && z == -2) || (x == 2 && z == -2) || (x == -2 && z == 2) || (x == 2 && z == 2)) {
					continue;
				}
				
				Block shouldBe = null;
				Block toCheck = w.getBlockState(center.add(x, 0, z)).getBlock();
				if (z == -2 && within(x, -1, 1)) {
					shouldBe = Blocks.DIRT;
				} else if (within(z, -1, 1)) {
					if (x == -2 || x == 2) {
						shouldBe = Blocks.DIRT;
					} else {
						shouldBe = ModBlocks.DENSE_LOG;
					}
				} else if (z == 2 && within(x, -1, 1)) {
					shouldBe = Blocks.DIRT;
				}
				
				if (shouldBe == null) {
					return false;
				} else if (shouldBe == Blocks.DIRT) {
					if (toCheck == Blocks.GRASS || toCheck == Blocks.DIRT || toCheck == ModBlocks.MUSHROOM_GRASS) {
						continue;
					}
					return false;
				} else if (shouldBe == ModBlocks.DENSE_LOG && (toCheck == ModBlocks.DENSE_LOG || toCheck == ModBlocks.CHARCOAL_PIT_MASTER)) {
					Block nb = w.getBlockState(center.add(x, 1, z)).getBlock();
					if (nb == Blocks.GRASS || nb == Blocks.DIRT || nb == ModBlocks.MUSHROOM_GRASS) {
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
	
	private static boolean within(int toCheck, int min, int max) {
		return toCheck >= min && toCheck <= max;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 5;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 5;
	}
}
