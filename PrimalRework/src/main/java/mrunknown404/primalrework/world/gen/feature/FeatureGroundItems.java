package mrunknown404.primalrework.world.gen.feature;

import java.util.Random;

import mrunknown404.primalrework.init.InitBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class FeatureGroundItems extends Feature<NoFeatureConfig> {
	public FeatureGroundItems() {
		super(NoFeatureConfig.CODEC);
	}
	
	@Override
	protected void setBlock(IWorldWriter world, BlockPos pos, BlockState state) {
		world.setBlock(pos, state, 19);
	}
	
	@Override
	public boolean place(ISeedReader seed, ChunkGenerator gen, Random r, BlockPos pos, NoFeatureConfig config) {
		BlockState state = seed.getBlockState(pos.below());
		if (state.isFaceSturdy(seed, pos, Direction.UP) && seed.getBlockState(pos).getMaterial().isReplaceable()) {
			int ri = r.nextInt(6);
			if (ri == 5) {
				setBlock(seed, pos, InitBlocks.GROUND_FLINT.get().defaultBlockState());
			} else if (ri == 4) {
				setBlock(seed, pos, InitBlocks.GROUND_CLAY.get().defaultBlockState());
			} else if (ri >= 2) {
				setBlock(seed, pos, InitBlocks.GROUND_ROCK.get().defaultBlockState());
			} else {
				setBlock(seed, pos, InitBlocks.GROUND_STICK.get().defaultBlockState());
			}
		}
		
		return true;
	}
}
