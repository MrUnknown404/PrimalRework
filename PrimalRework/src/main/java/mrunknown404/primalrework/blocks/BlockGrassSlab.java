package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.blocks.util.BlockSlabBase;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGrassSlab extends BlockSlabBase {
	
	public BlockGrassSlab(String name, boolean isDouble) {
		super(name, Material.GROUND, SoundType.GROUND, 0.75f, 0.75f, isDouble, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.shovel, EnumToolMaterial.flint));
		setTickRandomly(true);
	}
	
	@Override
	public BlockSlabBase getSingleVersion() {
		return (BlockSlabBase) ModBlocks.GRASS_SLAB;
	}
	
	@Override
	public BlockSlabBase getDoubleVersion() {
		return (BlockSlabBase) ModBlocks.GRASS_DOUBLE_SLAB;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return !isDouble() ? super.getBlockLayer() : BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (!world.isRemote) {
			if (!world.isAreaLoaded(pos, 3)) {
				return;
			}
			
			if (world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2) {
				world.setBlockState(pos, ModBlocks.DIRT_SLAB.getStateFromMeta(world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos))));
			} else {
				if (world.getLightFromNeighbors(pos.up()) >= 9) {
					for (int i = 0; i < 4; ++i) {
						BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
						
						if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !world.isBlockLoaded(blockpos)) {
							return;
						}
						
						IBlockState iblockstate = world.getBlockState(blockpos.up());
						IBlockState iblockstate1 = world.getBlockState(blockpos);
						
						if (iblockstate1.getBlock() == Blocks.DIRT && iblockstate1.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && world.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(world, pos.up()) <= 2) {
							world.setBlockState(blockpos, Blocks.GRASS.getDefaultState());
						}
					}
				}
			}
		}
	}
}