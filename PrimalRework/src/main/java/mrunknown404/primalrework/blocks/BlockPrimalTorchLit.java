package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.util.enums.EnumStage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPrimalTorchLit extends BlockPrimalTorchUnlit {
	
	public BlockPrimalTorchLit() {
		super("primal_torch_lit", EnumStage.stage1);
		setTickRandomly(true);
		setLightLevel(0.8f);
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		EnumFacing face = (EnumFacing) state.getValue(FACING);
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.65D;
		double d2 = (double) pos.getZ() + 0.5D;
		
		if (face.getAxis().isHorizontal()) {
			EnumFacing face1 = face.getOpposite();
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.3D * (double) face1.getFrontOffsetX(), d1 + 0.14D, d2 + 0.3D * (double) face1.getFrontOffsetZ(), 0, 0, 0);
			if (new Random().nextBoolean()) {
				world.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.3D * (double) face1.getFrontOffsetX(), d1 + 0.14D, d2 + 0.3D * (double) face1.getFrontOffsetZ(), 0, 0, 0);
			}
		} else {
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0, 0, 0);
			if (new Random().nextBoolean()) {
				world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0, 0, 0);
			}
		}
	}
}
