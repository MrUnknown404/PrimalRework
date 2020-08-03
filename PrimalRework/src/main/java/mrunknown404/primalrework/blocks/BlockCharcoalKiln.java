package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.blocks.util.BlockStagedDirectional;
import mrunknown404.primalrework.client.gui.GuiHandler;
import mrunknown404.primalrework.tileentity.TileEntityCharcoalKiln;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCharcoalKiln extends BlockStagedDirectional implements ITileEntityProvider {
	
	private static final PropertyBool IS_BURNING = PropertyBool.create("burning");
	private static final AxisAlignedBB bb = new AxisAlignedBB(0, 0, 0, 16 / 16, 15.05f / 16, 16 / 16);
	
	public BlockCharcoalKiln() {
		super("charcoal_kiln", Material.ROCK, SoundType.STONE, BlockRenderLayer.CUTOUT_MIPPED, 2f, 4f, bb, bb, EnumStage.stage3,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.stone));
		setTickRandomly(true);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(IS_BURNING, false));
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void randomDisplayTick(IBlockState state, World w, BlockPos pos, Random r) {
		TileEntity te = w.getTileEntity(pos);
		
		if (te instanceof TileEntityCharcoalKiln) {
			if (((TileEntityCharcoalKiln) te).isBurning()) {
				EnumFacing facing = state.getValue(FACING);
				double d0 = pos.getX() + 0.5;
				double d1 = pos.getY() + r.nextDouble() * 6 / 16;
				double d2 = pos.getZ() + 0.5;
				double d4 = r.nextDouble() * 0.6 - 0.3;
				
				if (r.nextDouble() < 0.1) {
					w.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1, 1, false);
				}
				
				switch (facing) {
					case WEST:
						w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52, d1, d2 + d4, 0, 0, 0);
						break;
					case EAST:
						w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52, d1, d2 + d4, 0, 0, 0);
						break;
					case NORTH:
						w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52, 0, 0, 0);
						break;
					case SOUTH:
						w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52, 0, 0, 0);
				}
			}
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, IS_BURNING });
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess w, BlockPos pos) {
		return getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(IS_BURNING, ((TileEntityCharcoalKiln) w.getTileEntity(pos)).isBurning());
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(IS_BURNING, false);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCharcoalKiln();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(Main.main, GuiHandler.GuiID.CHARCOAL_KILN.toID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntityCharcoalKiln) {
			InventoryHelper.dropInventoryItems(world, pos, (TileEntityCharcoalKiln) te);
			world.updateComparatorOutputLevel(pos, this);
		}
		
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
