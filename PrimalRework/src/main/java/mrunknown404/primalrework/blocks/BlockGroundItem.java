package mrunknown404.primalrework.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGroundItem extends BlockBase {

	private final Item dropInstead;
	
	public BlockGroundItem(String name, SoundType soundType) {
		this(name, soundType, null);
	}
	
	public BlockGroundItem(String name, SoundType soundType, @Nullable Item dropInstead) {
		super(name, Material.CIRCUITS, soundType, BlockRenderLayer.CUTOUT, 0, 0, NULL_AABB, new AxisAlignedBB(3.05 / 16, 0, 3.05 / 16, 13.05 / 16, 1.05 / 16, 13.05 / 16),
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand));
		this.dropInstead = dropInstead;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.CENTER_SMALL;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return dropInstead == null ? super.getItemDropped(state, rand, fortune) : dropInstead;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		checkForDrop(worldIn, pos, state);
	}
	
	public boolean canBlockStay(World worldIn, BlockPos pos) {
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
