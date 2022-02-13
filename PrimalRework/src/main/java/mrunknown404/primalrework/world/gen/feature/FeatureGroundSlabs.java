package mrunknown404.primalrework.world.gen.feature;

import java.util.Random;

import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.utils.helpers.BlockH;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class FeatureGroundSlabs extends Feature<NoFeatureConfig> {
	public FeatureGroundSlabs() {
		super(NoFeatureConfig.CODEC);
	}
	
	@Override
	protected void setBlock(IWorldWriter world, BlockPos pos, BlockState state) {
		world.setBlock(pos, state, 19);
	}
	
	@Override
	public boolean place(ISeedReader seed, ChunkGenerator gen, Random r, BlockPos pos, NoFeatureConfig config) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int yy = seed.getHeightmapPos(Heightmap.Type.WORLD_SURFACE, new BlockPos(pos.getX() + x, 0, pos.getZ() + z)).getY();
				
				yLoop:
				for (int y = gen.getSeaLevel() - 1; y < 255; y++) {
					BlockPos newPos = new BlockPos(pos.getX() + x, y, pos.getZ() + z), abovePos = newPos.above();
					
					if (y > yy) {
						break yLoop;
					}
					
					if (BlockH.canSupportPlant(seed.getBlockState(newPos).getBlock()) && seed.getBlockState(abovePos).getMaterial().isReplaceable()) {
						float count = 0;
						
						Block an = seed.getBlockState(abovePos.north()).getBlock();
						Block ae = seed.getBlockState(abovePos.east()).getBlock();
						Block as = seed.getBlockState(abovePos.south()).getBlock();
						Block aw = seed.getBlockState(abovePos.west()).getBlock();
						BlockState dn = seed.getBlockState(newPos.north());
						BlockState de = seed.getBlockState(newPos.east());
						BlockState ds = seed.getBlockState(newPos.south());
						BlockState dw = seed.getBlockState(newPos.west());
						
						if (BlockH.canSupportPlant(an) || an == PRBlocks.STONE.get()) {
							count++;
						} else if (an == PRBlocks.GRASS_SLAB.get()) {
							count += 0.5f;
						}
						if (BlockH.canSupportPlant(ae) || ae == PRBlocks.STONE.get()) {
							count++;
						} else if (ae == PRBlocks.GRASS_SLAB.get()) {
							count += 0.5f;
						}
						if (BlockH.canSupportPlant(as) || as == PRBlocks.STONE.get()) {
							count++;
						} else if (as == PRBlocks.GRASS_SLAB.get()) {
							count += 0.5f;
						}
						if (BlockH.canSupportPlant(aw) || aw == PRBlocks.STONE.get()) {
							count++;
						} else if (aw == PRBlocks.GRASS_SLAB.get()) {
							count += 0.5f;
						}
						
						if (dn.getMaterial().isReplaceable() || dn.getBlock() == PRBlocks.GRASS_SLAB.get()) {
							count -= 0.5f;
						}
						if (de.getMaterial().isReplaceable() || de.getBlock() == PRBlocks.GRASS_SLAB.get()) {
							count -= 0.5f;
						}
						if (ds.getMaterial().isReplaceable() || ds.getBlock() == PRBlocks.GRASS_SLAB.get()) {
							count -= 0.5f;
						}
						if (dw.getMaterial().isReplaceable() || dw.getBlock() == PRBlocks.GRASS_SLAB.get()) {
							count -= 0.5f;
						}
						
						if (count > 1) {
							setBlock(seed, newPos, PRBlocks.DIRT.get().defaultBlockState());
							setBlock(seed, abovePos, PRBlocks.GRASS_SLAB.get().defaultBlockState());
						}
					}
				}
			}
		}
		
		return true;
	}
}
