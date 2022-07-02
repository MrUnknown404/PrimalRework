package mrunknown404.primalrework.world.gen;

import java.util.Random;

import com.mojang.serialization.Codec;

import mrunknown404.primalrework.init.InitBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class PrimalSurfaceBuilder extends DefaultSurfaceBuilder {
	public PrimalSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
		super(codec);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void apply(Random random, IChunk chunk, Biome biome, int x, int y, int z, double var0, BlockState state0, BlockState state1, BlockState top, BlockState under,
			BlockState underwater, int var1) {
		BlockState blockstate = top;
		BlockState blockstate1 = under;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
		int i = -1;
		int j = (int) (var0 / 3d + 3d + random.nextDouble() * 0.25d);
		int k = x & 15;
		int l = y & 15;
		
		for (int i1 = z; i1 >= 0; --i1) {
			blockpos$mutable.set(k, i1, l);
			BlockState blockstate2 = chunk.getBlockState(blockpos$mutable);
			if (blockstate2.isAir()) {
				i = -1;
			} else if (blockstate2.is(state0.getBlock())) {
				if (i == -1) {
					if (j <= 0) {
						blockstate = Blocks.AIR.defaultBlockState();
						blockstate1 = state0;
					} else if (i1 >= var1 - 4 && i1 <= var1 + 1) {
						blockstate = top;
						blockstate1 = under;
					}
					
					if (i1 < var1 && (blockstate == null || blockstate.isAir())) {
						blockstate = biome.getTemperature(blockpos$mutable.set(x, i1, y)) < 0.15f ? Blocks.ICE.defaultBlockState() : state1; //TODO switch to my ice
						blockpos$mutable.set(k, i1, l);
					}
					
					i = j;
					if (i1 >= var1 - 1) {
						chunk.setBlockState(blockpos$mutable, blockstate, false);
					} else if (i1 < var1 - 7 - j) {
						blockstate = Blocks.AIR.defaultBlockState();
						blockstate1 = state0;
						chunk.setBlockState(blockpos$mutable, underwater, false);
					} else {
						chunk.setBlockState(blockpos$mutable, blockstate1, false);
					}
				} else if (i > 0) {
					i--;
					chunk.setBlockState(blockpos$mutable, blockstate1, false);
					
					if (i == 0 && blockstate1.is(InitBlocks.SAND.get()) && j > 1) {
						i = random.nextInt(4) + Math.max(0, i1 - 74);
						blockstate1 = InitBlocks.SANDSTONE.get().defaultBlockState();
					}
				}
			}
		}
	}
}
