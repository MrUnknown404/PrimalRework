package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCraftingStump extends BlockBase {

	private static final AxisAlignedBB bb = new AxisAlignedBB(2.05 / 16, 0, 2.05 / 16, 14.05 / 16, 1, 14.05 / 16);
	
	public BlockCraftingStump() {
		super("crafting_stump", Material.WOOD, SoundType.WOOD, BlockRenderLayer.CUTOUT, 2f, 2f, bb, bb);
	}
	
	@Override
	public void setupHarvestInfo() {
		this.harvestInfo = new BlockHarvestInfo(this, new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.axe, EnumToolMaterial.flint));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			//player.openGui(Main.main, Main.GUI_ID_STUMP_CRAFTING, world, pos.getX(), pos.getY(), pos.getZ());
			player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
		}
		
		return true;
	}
}
