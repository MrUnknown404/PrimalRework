package mrunknown404.primalrework.world;

import java.util.Random;

import mrunknown404.primalrework.blocks.util.BlockGroundItem;
import mrunknown404.primalrework.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator {

	@Override
	public void generate(Random r, int chunkX, int chunkZ, World w, IChunkGenerator gen, IChunkProvider prov) {
		if (w.provider.getDimensionType() == DimensionType.OVERWORLD) {
			generateGroundItems(w, r, new BlockPos(chunkX * 16 + 8, 128, chunkZ * 16 + 8));
		}
	}
	
	private void generateGroundItems(World w, Random r, BlockPos pos) {
		for (int i = 0; i < 12; ++i) {
			for (int h = 63; h < 255; h++) {
				BlockGroundItem b = getNewGroundBlock(r);
				BlockPos blockpos = new BlockPos(pos.getX() + r.nextInt(16), h, pos.getZ() + r.nextInt(16));
				
				if (w.isAirBlock(blockpos) && b.canBlockStay(w, blockpos)) {
					BlockPos newCheck = new BlockPos(blockpos).down();
					
					if (w.getBlockState(newCheck).getBlock() != Blocks.LEAVES && w.getBlockState(newCheck).getBlock() != Blocks.LEAVES2) {
						w.setBlockState(blockpos, b.getDefaultState(), 2);
					}
				}
			}
		}
	}
	
	private BlockGroundItem getNewGroundBlock(Random r) {
		int ri = r.nextInt(7);
		
		if (ri >= 0 && ri <= 3) {
			return (BlockGroundItem) ModBlocks.STICK;
		} else if (ri == 6) {
			return (BlockGroundItem) ModBlocks.FLINT;
		} else {
			return (BlockGroundItem) ModBlocks.ROCK;
		}
	}
}
