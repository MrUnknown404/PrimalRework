package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPrimalEnchantingTable extends BlockBase implements ITileEntityProvider {

	private static final AxisAlignedBB bb = new AxisAlignedBB(1.05 / 16, 0, 1.05 / 16, 15.05 / 16, 9.05 / 16, 15.05 / 16);
	
	public BlockPrimalEnchantingTable() {
		super("primal_enchanting_table", Material.ROCK, SoundType.STONE, BlockRenderLayer.CUTOUT, 3, 3, bb, bb,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.flint));
		hasTileEntity = true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPrimalEnchanting();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(Main.main, Main.GUI_ID_PRIMAL_ENCHANTING, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te instanceof TileEntityPrimalEnchanting) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityPrimalEnchanting) te);
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		
		super.breakBlock(worldIn, pos, state);
	}
}
