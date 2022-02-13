package mrunknown404.primalrework.world.gen.feature;

import java.util.Random;

import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.utils.helpers.BlockH;
import net.minecraft.block.BlockState;
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
		if (BlockH.canSupportPlant(seed.getBlockState(pos.below()).getBlock())) {
			int ri = r.nextInt(6);
			if (ri == 5) {
				setBlock(seed, pos,  PRBlocks.GROUND_FLINT.get().defaultBlockState());
			} else if (ri >= 3) {
				setBlock(seed, pos, PRBlocks.GROUND_ROCK.get().defaultBlockState());
			} else {
				setBlock(seed, pos, PRBlocks.GROUND_STICK.get().defaultBlockState());
			}
			
			return true;
		}
		
		return false;
	}
}
