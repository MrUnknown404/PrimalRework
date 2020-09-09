package mrunknown404.primalrework.world.gen;

import java.util.Random;

import mrunknown404.primalrework.init.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSlabs extends WorldGenerator {
	@SuppressWarnings("deprecation")
	@Override
	public boolean generate(World w, Random r, BlockPos pos) {
		pos = pos.down();
		Block b = w.getBlockState(pos).getBlock();
		
		if ((b == Blocks.GRASS || b == Blocks.SNOW_LAYER || b == InitBlocks.MUSHROOM_GRASS) && w.getBlockState(pos.up()).getMaterial().isReplaceable()) {
			BlockPos toCheck = b == Blocks.GRASS || b == InitBlocks.MUSHROOM_GRASS ? pos.up() : pos;
			int amount = 0;
			
			if (w.getBlockState(toCheck.north()).getBlock() == Blocks.GRASS || w.getBlockState(toCheck.north()).getBlock() == Blocks.DIRT || w.getBlockState(toCheck.north()).getBlock() == InitBlocks.MUSHROOM_GRASS) {amount++;}
			if (w.getBlockState(toCheck.east()).getBlock() == Blocks.GRASS || w.getBlockState(toCheck.east()).getBlock() == Blocks.DIRT || w.getBlockState(toCheck.east()).getBlock() == InitBlocks.MUSHROOM_GRASS) {amount++;}
			if (w.getBlockState(toCheck.south()).getBlock() == Blocks.GRASS || w.getBlockState(toCheck.south()).getBlock() == Blocks.DIRT || w.getBlockState(toCheck.south()).getBlock() == InitBlocks.MUSHROOM_GRASS) {amount++;}
			if (w.getBlockState(toCheck.west()).getBlock() == Blocks.GRASS || w.getBlockState(toCheck.west()).getBlock() == Blocks.DIRT || w.getBlockState(toCheck.west()).getBlock() == InitBlocks.MUSHROOM_GRASS) {amount++;}
			
			if (b == Blocks.SNOW_LAYER) {
				if (w.getBlockState(toCheck.north()).getBlock() == Blocks.STONE) {amount++;}
				if (w.getBlockState(toCheck.east()).getBlock() == Blocks.STONE) {amount++;}
				if (w.getBlockState(toCheck.south()).getBlock() == Blocks.STONE) {amount++;}
				if (w.getBlockState(toCheck.west()).getBlock() == Blocks.STONE) {amount++;}
			}
			
			if (amount >= 2) {
				if (b == Blocks.GRASS || b == InitBlocks.MUSHROOM_GRASS) {
					w.setBlockState(pos.up(), (b == Blocks.GRASS ? InitBlocks.GRASS_SLAB : InitBlocks.MUSHROOM_GRASS_SLAB).getStateFromMeta(0), 2);
					w.setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
					
					if (w.getBlockState(pos.up().up()).getMaterial().isReplaceable()) {
						w.setBlockState(pos.up().up(), Blocks.AIR.getDefaultState(), 2);
					}
				} else if (b == Blocks.SNOW_LAYER) {
					w.setBlockState(pos, Blocks.SNOW_LAYER.getStateFromMeta(3), 2);
				}
			}
		}
		
		return true;
	}
}
