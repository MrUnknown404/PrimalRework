package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFirePit extends BlockBase {

	private static final AxisAlignedBB bb = new AxisAlignedBB(2.05 / 16, 0, 2.05 / 16, 14.05 / 16, 13.05 / 16, 14.05 / 16);
	
	public BlockFirePit() {
		super("fire_pit", Material.CIRCUITS, SoundType.WOOD, BlockRenderLayer.CUTOUT, ToolType.axe, -1, 1, 1, bb, bb);
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.CENTER;
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
		} else {
			return true;
		}
	}
}
