package mrunknown404.primalrework.blocks.util;

import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDirectionalBase extends BlockBase {
	
	protected static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	protected BlockDirectionalBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness, float resistance, AxisAlignedBB collisionAABB,
			AxisAlignedBB visualAABB, EnumStage stage, DoubleValue<EnumToolType, EnumToolMaterial>... types) {
		super(name, material, soundType, renderType, hardness, resistance, collisionAABB, visualAABB, stage, types);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	protected BlockDirectionalBase(String name, Material material, SoundType soundType, float hardness, float resistance, EnumStage stage,
			DoubleValue<EnumToolType, EnumToolMaterial>[] types) {
		this(name, material, soundType, BlockRenderLayer.SOLID, hardness, resistance, FULL_BLOCK_AABB, FULL_BLOCK_AABB, stage, types);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getFront(meta);
		if (facing.getAxis() == EnumFacing.Axis.Y) {
			facing = EnumFacing.NORTH;
		}
		
		return getDefaultState().withProperty(FACING, facing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta = meta | state.getValue(FACING).getIndex();
		
		return meta;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
}
