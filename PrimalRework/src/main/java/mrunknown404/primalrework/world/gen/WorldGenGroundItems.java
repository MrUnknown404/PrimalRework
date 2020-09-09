package mrunknown404.primalrework.world.gen;

import java.util.Random;

import mrunknown404.primalrework.blocks.BlockGroundItem;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.unknownlibs.utils.MathUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGroundItems extends WorldGenerator {
	
	@Override
	public boolean generate(World w, Random r, BlockPos pos) {
		BlockGroundItem b = getNewGroundBlock(r);
		
		if (w.isAirBlock(pos) && b.canBlockStay(w, pos)) {
			BlockPos newCheck = new BlockPos(pos).down();
			
			if (w.getBlockState(newCheck).getBlock() != Blocks.LEAVES && w.getBlockState(newCheck).getBlock() != Blocks.LEAVES2 &&
					w.getBlockState(newCheck).getBlock() != Blocks.BROWN_MUSHROOM_BLOCK && w.getBlockState(newCheck).getBlock() != Blocks.RED_MUSHROOM_BLOCK) {
				w.setBlockState(pos, b.getDefaultState(), 2);
			}
		}
		
		return true;
	}
	
	private BlockGroundItem getNewGroundBlock(Random r) {
		int ri = r.nextInt(7);
		
		if (MathUtils.within(ri, 0, 3)) {
			return (BlockGroundItem) InitBlocks.STICK;
		} else if (ri == 6) {
			return (BlockGroundItem) InitBlocks.FLINT;
		} else {
			return (BlockGroundItem) InitBlocks.ROCK;
		}
	}
}
