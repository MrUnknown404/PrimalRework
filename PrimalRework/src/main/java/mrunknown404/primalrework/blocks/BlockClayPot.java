package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockClayPot extends BlockBase {
	private static final AxisAlignedBB bb = new AxisAlignedBB(4.05f / 16, 0, 4.05f / 16, 12.05f / 16, 10.05f / 16, 12.05f / 16);
	
	public BlockClayPot() {
		super("clay_pot", Material.ROCK, SoundType.STONE, BlockRenderLayer.CUTOUT_MIPPED, 1.25f, 4.2f, bb, bb, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.hand));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		//TODO add inventory or something?
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
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
