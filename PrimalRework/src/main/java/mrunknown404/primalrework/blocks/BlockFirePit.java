package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.tileentity.TileEntityFirePit;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFirePit extends BlockBase implements ITileEntityProvider {
	
	private static final AxisAlignedBB bb = new AxisAlignedBB(2.05 / 16, 0, 2.05 / 16, 14.05 / 16, 13.05 / 16, 14.05 / 16);
	
	public BlockFirePit() {
		super("fire_pit", Material.CIRCUITS, SoundType.WOOD, BlockRenderLayer.CUTOUT, 1, 1, bb, bb, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.axe, EnumToolMaterial.hand));
		hasTileEntity = true;
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (((TileEntityFirePit) world.getTileEntity(pos)).isBurning()) {
			Random r = new Random();
			
			for (int i = 0; i < 5; i++) {
				float xAdd = r.nextInt(21) / 100f, yAdd = r.nextInt(11) / 100f, zAdd = r.nextInt(21) / 100f;
				float xsAdd = r.nextInt(11) / 5000f, ysAdd = r.nextInt(11) / 5000f, zsAdd = r.nextInt(11) / 5000f;
				
				if (r.nextBoolean()) {
					xAdd = -xAdd;
				}
				if (r.nextBoolean()) {
					zAdd = -zAdd;
				}
				
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5 + xAdd, pos.getY() + yAdd, pos.getZ() + 0.5 + zAdd, 0.0D + xsAdd, 0.01D + ysAdd, 0.0D + zsAdd);
				
				if (i == 0) {
					world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5 + xAdd, pos.getY() + yAdd + 0.1, pos.getZ() + 0.5 + zAdd, 0.0D + xsAdd, 0.01D + ysAdd, 0.0D + zsAdd);
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(Main.main, Main.GUI_ID_FIRE_PIT, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te instanceof TileEntityFirePit) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFirePit) te);
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFirePit();
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		checkForDrop(worldIn, pos, state);
	}
	
	private boolean canBlockStay(World worldIn, BlockPos pos) {
		return worldIn.isBlockFullCube(pos.down());
	}
	
	private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (!canBlockStay(worldIn, pos)) {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
			return false;
		}
		
		return true;
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
