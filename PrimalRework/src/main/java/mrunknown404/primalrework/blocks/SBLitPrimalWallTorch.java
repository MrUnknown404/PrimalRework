package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SBLitPrimalWallTorch extends SBUnlitPrimalWallTorch {
	public SBLitPrimalWallTorch() {
		super("lit_primal_wall_torch", PRStages.STAGE_1, BlockInfo.LIT_PRIMAL_TORCH, new HarvestInfo(ToolType.NONE, ToolMaterial.HAND, DropInfo.block(PRBlocks.LIT_PRIMAL_TORCH)));
	}
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		double d0 = pos.getX() + 0.5;
		double d1 = pos.getY() + 0.6;
		double d2 = pos.getZ() + 0.5;
		
		Direction op = state.getValue(FACING).getOpposite();
		world.addParticle(ParticleTypes.SMOKE, d0 + 0.3 * op.getStepX(), d1 + 0.14, d2 + 0.3 * op.getStepZ(), 0, 0, 0);
		if (new Random().nextBoolean()) {
			world.addParticle(ParticleTypes.FLAME, d0 + 0.3 * op.getStepX(), d1 + 0.14, d2 + 0.3 * op.getStepZ(), 0, 0, 0);
		}
	}
}
