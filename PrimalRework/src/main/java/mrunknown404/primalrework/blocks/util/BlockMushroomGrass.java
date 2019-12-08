package mrunknown404.primalrework.blocks.util;

import java.util.Random;

import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMushroomGrass extends BlockBase implements IGrowable {
	
	public static final PropertyBool SNOWY = PropertyBool.create("snowy");
	
	public BlockMushroomGrass() {
		super("mushroom_grass", Material.GRASS, SoundType.PLANT, 0.8f, 3f, EnumStage.stage0,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.shovel, EnumToolMaterial.flint));
		setDefaultState(blockState.getBaseState().withProperty(SNOWY, Boolean.valueOf(false)));
		setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World w, BlockPos pos, IBlockState state, Random r) {
		if (!w.isRemote) {
			if (!w.isAreaLoaded(pos, 3))
				return;
			if (w.getLightFromNeighbors(pos.up()) < 4 && w.getBlockState(pos.up()).getLightOpacity(w, pos.up()) > 2) {
				w.setBlockState(pos, Blocks.DIRT.getDefaultState());
			} else {
				if (w.getLightFromNeighbors(pos.up()) >= 9) {
					for (int i = 0; i < 4; ++i) {
						BlockPos bpos = pos.add(r.nextInt(3) - 1, r.nextInt(5) - 3, r.nextInt(3) - 1);
						
						if (bpos.getY() >= 0 && bpos.getY() < 256 && !w.isBlockLoaded(bpos)) {
							return;
						}
						
						IBlockState state1 = w.getBlockState(bpos);
						
						if (state1.getBlock() == Blocks.DIRT && state1.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && w.getLightFromNeighbors(bpos.up()) >= 4 &&
								w.getBlockState(bpos.up()).getLightOpacity(w, pos.up()) <= 2) {
							w.setBlockState(bpos, getDefaultState());
							
							if (r.nextInt(4) == 0) {
								w.setBlockState(bpos.up(), r.nextBoolean() ? Blocks.BROWN_MUSHROOM.getDefaultState() : Blocks.RED_MUSHROOM.getDefaultState());
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Blocks.DIRT.getItemDropped(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
	}
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}
	
	@Override
	public void grow(World w, Random r, BlockPos pos, IBlockState state) {
		for (int i = 0; i < 128; ++i) {
			BlockPos bpos1 = pos.up();
			int j = 0;
			
			while (true) {
				if (j >= i / 16) {
					if (w.isAirBlock(bpos1)) {
						if (r.nextInt(4) == 0) {
							IBlockState state1 = r.nextBoolean() ? Blocks.BROWN_MUSHROOM.getDefaultState() : Blocks.RED_MUSHROOM.getDefaultState();
							
							if (Blocks.BROWN_MUSHROOM.canBlockStay(w, bpos1, state1)) {
								w.setBlockState(bpos1, state1, 3);
							}
						} else {
							IBlockState state1 = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
							
							if (Blocks.TALLGRASS.canBlockStay(w, bpos1, state1)) {
								w.setBlockState(bpos1, state1, 3);
							}
						}
					}
					
					break;
				}
				
				bpos1 = bpos1.add(r.nextInt(3) - 1, (r.nextInt(3) - 1) * r.nextInt(3) / 2, r.nextInt(3) - 1);
				
				if (w.getBlockState(bpos1.down()).getBlock() != this || w.getBlockState(bpos1).isNormalCube()) {
					break;
				}
				
				++j;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return state.withProperty(SNOWY, Boolean.valueOf(block == Blocks.SNOW || block == Blocks.SNOW_LAYER));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { SNOWY });
	}
	
	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		return true;
	}
}
