package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.tileentity.TileEntityCharcoalPitMaster;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCharcoalPitMaster extends BlockBase implements ITileEntityProvider {
	
	public BlockCharcoalPitMaster() {
		super("charcoal_pit_master", Material.WOOD, SoundType.WOOD, 3, 6, EnumStage.no_show, new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.axe, EnumToolMaterial.flint));
		setCreativeTab(null);
		setTickRandomly(true);
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random r) {
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntityCharcoalPitMaster && ((TileEntityCharcoalPitMaster) te).isBurning()) {
			for (int z = -1; z < 2; z++) {
				for (int x = -1; x < 2; x++) {
					if (r.nextBoolean()) {
						double xx = r.nextDouble(), zz = r.nextDouble();
						
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + x + xx, pos.getY() + 2, pos.getZ() + z + zz, 0, 0, 0);
					}
				}
			}
		}
		
		super.randomDisplayTick(state, world, pos, r);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World w, BlockPos pos, Block block, BlockPos fromPos) {
		checkValid(w, pos);
		super.neighborChanged(state, w, pos, block, fromPos);
	}
	
	public void checkValid(World w, BlockPos pos) {
		if (!BlockDenseLog.isValidCharcoalPit(w, pos)) {
			w.setBlockState(pos, ModBlocks.DENSE_LOG.getDefaultState(), 11);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCharcoalPitMaster();
	}
}
