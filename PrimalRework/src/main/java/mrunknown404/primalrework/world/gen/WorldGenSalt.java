package mrunknown404.primalrework.world.gen;

import java.util.Random;

import mrunknown404.primalrework.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSalt extends WorldGenerator {
	private final Block block = ModBlocks.SALT;
	private final int numberOfBlocks, rarity;
	
	public WorldGenSalt(int numberOfBlocks, int rarity) {
		this.numberOfBlocks = numberOfBlocks;
		this.rarity = rarity;
	}
	
	@Override
	public boolean generate(World w, Random r, BlockPos pos) {
		if (w.getBlockState(pos).getMaterial() != Material.WATER || r.nextInt(rarity) != 0) {
			return false;
		}
		
		int i = r.nextInt(this.numberOfBlocks - 2) + 2;
		
		for (int k = pos.getX() - i; k <= pos.getX() + i; ++k) {
			for (int l = pos.getZ() - i; l <= pos.getZ() + i; ++l) {
				int i1 = k - pos.getX();
				int j1 = l - pos.getZ();
				
				if (i1 * i1 + j1 * j1 <= i * i) {
					for (int k1 = pos.getY() - 1; k1 <= pos.getY() + 1; ++k1) {
						BlockPos blockpos = new BlockPos(k, k1, l);
						Block block = w.getBlockState(blockpos).getBlock();
						
						if (block == Blocks.DIRT || block == Blocks.SAND || block == Blocks.GRAVEL || block == Blocks.CLAY) {
							w.setBlockState(blockpos, this.block.getDefaultState(), 2);
						}
					}
				}
			}
		}
		
		return true;
	}
}
