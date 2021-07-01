package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SBLitPrimalTorch extends SBUnlitPrimalTorch {
	public SBLitPrimalTorch() {
		super("lit_primal_torch", EnumStage.stage1, 8, BlockStateType.normal, HarvestInfo.HAND);
	}
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		double d0 = pos.getX() + 0.5;
		double d1 = pos.getY() + 0.6;
		double d2 = pos.getZ() + 0.5;
		
		world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0, 0, 0);
		if (random.nextBoolean()) {
			world.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0, 0, 0);
		}
	}
}
