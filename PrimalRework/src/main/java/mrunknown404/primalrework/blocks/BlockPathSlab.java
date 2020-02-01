package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.blocks.util.BlockSlabBase;
import mrunknown404.primalrework.blocks.util.ISlabBase;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockPathSlab extends BlockSlabBase {
	
	public BlockPathSlab(String name, boolean isDouble) {
		super(name, Material.GROUND, SoundType.PLANT, 0.75f, 0.75f, isDouble, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.shovel, EnumToolMaterial.flint));
		useNeighborBrightness = true;
		
		if (isDouble) {
			setLightOpacity(255);
		}
	}
	
	@Override
	public BlockSlabBase getSingleVersion() {
		return (BlockSlabBase) ModBlocks.PATH_SLAB;
	}
	
	@Override
	public BlockSlabBase getDoubleVersion() {
		return (BlockSlabBase) ModBlocks.PATH_DOUBLE_SLAB;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(((ISlabBase<BlockSlabBase>) ModBlocks.DIRT_SLAB).getSingleVersion());
	}
	
	protected static final AxisAlignedBB AABB_FULL = new AxisAlignedBB(0, 0, 0, 1, 15d / 16d, 1);
	protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0, 0, 0, 1, 7d / 16d, 1);
	protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0, 0.5, 0, 1, 15d / 16d, 1);
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (isDouble()) {
			return AABB_FULL;
		}
		
		return state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP ? AABB_TOP_HALF : AABB_BOTTOM_HALF;
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (isDouble()) {
			return face == EnumFacing.DOWN ? true : false;
		}
		
		return super.doesSideBlockRendering(state, world, pos, face);
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (isDouble()) {
			switch (side) {
				case UP:
					return true;
				case NORTH:
				case SOUTH:
				case WEST:
				case EAST:
					IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
					Block block = iblockstate.getBlock();
					return !iblockstate.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH;
				default:
					return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
			}
		}
		
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return super.getBlockLayer();
	}
}
