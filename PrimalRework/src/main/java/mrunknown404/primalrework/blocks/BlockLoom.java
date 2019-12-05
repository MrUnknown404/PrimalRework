package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockDirectionalBase;
import mrunknown404.primalrework.tileentity.TileEntityLoom;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLoom extends BlockDirectionalBase implements ITileEntityProvider {
	
	public static final PropertyInteger STRING_LEVEL = PropertyInteger.create("stringlevel", 0, 7);
	
	public BlockLoom() {
		super("loom", Material.WOOD, SoundType.WOOD, BlockRenderLayer.CUTOUT, 2, 2, FULL_BLOCK_AABB, FULL_BLOCK_AABB, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.axe, EnumToolMaterial.flint));
		setDefaultState(blockState.getBaseState().withProperty(STRING_LEVEL, 0));
		hasTileEntity = true;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.getTileEntity(pos) instanceof TileEntityLoom) {
			TileEntityLoom te = (TileEntityLoom) world.getTileEntity(pos);
			ItemStack stack = player.getHeldItemMainhand();
			
			if (stack.getItem() == Items.STRING) {
				if (te.click(true)) {
					stack.shrink(1);
				}
			} else {
				te.click(false);
			}
			
			world.setBlockState(pos, world.getBlockState(pos).getActualState(world, pos));
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntityLoom) {
			for (int i = 0; i < ((TileEntityLoom) te).getStringLevel(); i++) {
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.STRING)));
			}
			
			world.updateComparatorOutputLevel(pos, this);
		}
		
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLoom();
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, STRING_LEVEL });
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(STRING_LEVEL, ((TileEntityLoom) world.getTileEntity(pos)).getStringLevel());
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
