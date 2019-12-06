package mrunknown404.primalrework.world;

import java.util.Random;

import mrunknown404.primalrework.blocks.BlockGroundItem;
import mrunknown404.primalrework.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator {
	
	// TODO replace all worldgen (just need decoration), add random crop gen
	
	@Override
	public void generate(Random r, int chunkX, int chunkZ, World w, IChunkGenerator gen, IChunkProvider prov) {
		if (w.provider.getDimensionType() == DimensionType.OVERWORLD) {
			generateGrassSlabs(w, new BlockPos(chunkX * 16 + 8, 63, chunkZ * 16 + 8));
			generateGroundItems(w, r, new BlockPos(chunkX * 16 + 8, 128, chunkZ * 16 + 8));
			
			for (int i = 63; i > 20; i--) {
				if (r.nextInt(4) == 0) {
					generateSalt(w, r.nextInt(2) + 3, new BlockPos(chunkX * 16 + 8, i, chunkZ * 16 + 8));
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void generateGrassSlabs(World w, BlockPos pos) {
		for (int xx = 0; xx < 16; xx++) {
			for (int zz = 0; zz < 16; zz++) {
				yCheck:
				for (int yy = 62; yy < 255; yy++) {
					BlockPos bpos = new BlockPos(pos.getX() + xx, yy, pos.getZ() + zz);
					Block b = w.getBlockState(bpos).getBlock();
					
					if ((b == Blocks.GRASS || b == Blocks.SNOW_LAYER) && w.getBlockState(bpos.up()).getMaterial().isReplaceable()) {
						BlockPos toCheck = b == Blocks.GRASS ? bpos.up() : bpos;
						int amount = 0;
						
						if (w.getBlockState(toCheck.north()).getBlock() == Blocks.GRASS || w.getBlockState(toCheck.north()).getBlock() == Blocks.DIRT) {amount++;}
						if (w.getBlockState(toCheck.east()).getBlock() == Blocks.GRASS || w.getBlockState(toCheck.east()).getBlock() == Blocks.DIRT) {amount++;}
						if (w.getBlockState(toCheck.south()).getBlock() == Blocks.GRASS || w.getBlockState(toCheck.south()).getBlock() == Blocks.DIRT) {amount++;}
						if (w.getBlockState(toCheck.west()).getBlock() == Blocks.GRASS || w.getBlockState(toCheck.west()).getBlock() == Blocks.DIRT) {amount++;}
						
						if (b == Blocks.SNOW_LAYER) {
							if (w.getBlockState(toCheck.north()).getBlock() == Blocks.STONE) {amount++;}
							if (w.getBlockState(toCheck.east()).getBlock() == Blocks.STONE) {amount++;}
							if (w.getBlockState(toCheck.south()).getBlock() == Blocks.STONE) {amount++;}
							if (w.getBlockState(toCheck.west()).getBlock() == Blocks.STONE) {amount++;}
						}
						
						if (amount >= 2) {
							if (b == Blocks.GRASS) {
								w.setBlockState(bpos.up(), ModBlocks.GRASS_SLAB.getStateFromMeta(0), 2);
								w.setBlockState(bpos, Blocks.DIRT.getDefaultState(), 2);
								
								if (w.getBlockState(bpos.up().up()).getMaterial().isReplaceable()) {
									w.setBlockState(bpos.up().up(), Blocks.AIR.getDefaultState(), 2);
								}
								
								break yCheck;
							} else if (b == Blocks.SNOW_LAYER) {
								w.setBlockState(bpos, Blocks.SNOW_LAYER.getStateFromMeta(3), 2);
								break yCheck;
							}
						}
					}
				}
			}
		}
	}
	
	private void generateGroundItems(World w, Random r, BlockPos pos) {
		for (int i = 0; i < 8; ++i) {
			for (int h = 63; h < 255; h++) {
				BlockGroundItem b = getNewGroundBlock(r);
				BlockPos blockpos = new BlockPos(pos.getX() + r.nextInt(16), h, pos.getZ() + r.nextInt(16));
				
				if (w.isAirBlock(blockpos) && b.canBlockStay(w, blockpos)) {
					BlockPos newCheck = new BlockPos(blockpos).down();
					
					if (w.getBlockState(newCheck).getBlock() != Blocks.LEAVES && w.getBlockState(newCheck).getBlock() != Blocks.LEAVES2 &&
							w.getBlockState(newCheck).getBlock() != Blocks.BROWN_MUSHROOM_BLOCK && w.getBlockState(newCheck).getBlock() != Blocks.RED_MUSHROOM_BLOCK) {
						w.setBlockState(blockpos, b.getDefaultState(), 2);
					}
				}
			}
		}
	}
	
	private void generateSalt(World w, int size, BlockPos pos) {
		pos = pos.add(w.rand.nextInt(16), 0, w.rand.nextInt(16));
		
		if (w.getBlockState(pos).getMaterial() == Material.WATER) {
			for (int k = pos.getX() - size; k <= pos.getX() + size; ++k) {
				for (int l = pos.getZ() - size; l <= pos.getZ() + size; ++l) {
					int i1 = k - pos.getX();
					int j1 = l - pos.getZ();
					
					if (i1 * i1 + j1 * j1 <= size * size) {
						for (int k1 = pos.getY() - 1; k1 <= pos.getY() + 1; ++k1) {
							BlockPos blockpos = new BlockPos(k, k1, l);
							Block block = w.getBlockState(blockpos).getBlock();
							
							if (block == Blocks.DIRT || block == Blocks.GRAVEL || block == Blocks.SAND) {
								w.setBlockState(blockpos, ModBlocks.SALT.getDefaultState(), 2);
							}
						}
					}
				}
			}
			
			return;
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
