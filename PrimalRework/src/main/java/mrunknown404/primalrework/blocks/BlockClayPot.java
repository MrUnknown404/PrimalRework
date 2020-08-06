package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.blocks.util.BlockStaged;
import mrunknown404.primalrework.client.gui.GuiHandler;
import mrunknown404.primalrework.tileentity.TileEntityClayPot;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockClayPot extends BlockStaged implements ITileEntityProvider {
	private static final AxisAlignedBB bb = new AxisAlignedBB(4.05f / 16, 0, 4.05f / 16, 12.05f / 16, 10.05f / 16, 12.05f / 16);
	
	public BlockClayPot() {
		super("clay_pot", Material.ROCK, SoundType.STONE, BlockRenderLayer.CUTOUT_MIPPED, 1.25f, 4.2f, bb, bb, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.hand));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			player.openGui(Main.main, GuiHandler.GuiID.CLAY_POT.toID(), world, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityClayPot();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntityClayPot) {
			InventoryHelper.dropInventoryItems(world, pos, (TileEntityClayPot) te);
			world.updateComparatorOutputLevel(pos, this);
		}
		
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
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
