package mrunknown404.primalrework.blocks.util;

import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRotatedPillarBase extends BlockBase {
	protected BlockRotatedPillarBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness, float resistance, AxisAlignedBB collisionAABB,
			AxisAlignedBB visualAABB, EnumStage stage, DoubleValue<EnumToolType, EnumToolMaterial>... types) {
		super(name, material, soundType, renderType, hardness, resistance, collisionAABB, visualAABB, stage, types);
	}
	
	protected BlockRotatedPillarBase(String name, Material material, SoundType soundType, float hardness, float resistance, EnumStage stage,
			DoubleValue<EnumToolType, EnumToolMaterial>... types) {
		this(name, material, soundType, BlockRenderLayer.SOLID, hardness, resistance, FULL_BLOCK_AABB, FULL_BLOCK_AABB, stage, types);
	}
	
	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
		IBlockState state = world.getBlockState(pos);
		for (IProperty<?> prop : state.getProperties().keySet()) {
			if (prop.getName().equals("axis")) {
				world.setBlockState(pos, state.cycleProperty(prop));
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.getValue(BlockLog.LOG_AXIS)) {
					case X:
						return state.withProperty(BlockLog.LOG_AXIS, EnumAxis.Z);
					case Z:
						return state.withProperty(BlockLog.LOG_AXIS, EnumAxis.X);
					default:
						return state;
				}
			default:
				return state;
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumAxis axis = EnumAxis.Y;
		int i = meta & 12;
		
		if (i == 4) {
			axis = EnumAxis.X;
		} else if (i == 8) {
			axis = EnumAxis.Z;
		}
		
		return getDefaultState().withProperty(BlockLog.LOG_AXIS, axis);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		EnumAxis axis = state.getValue(BlockLog.LOG_AXIS);
		
		if (axis == EnumAxis.X) {
			i |= 4;
		} else if (axis == EnumAxis.Z) {
			i |= 8;
		}
		
		return i;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { BlockLog.LOG_AXIS });
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(BlockLog.LOG_AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		switch (state.getValue(BlockLog.LOG_AXIS)) {
			case NONE:
				return BlockFaceShape.UNDEFINED;
			case X:
				return face == EnumFacing.EAST || face == EnumFacing.WEST ? BlockFaceShape.SOLID : BlockFaceShape.MIDDLE_POLE;
			case Y:
				return face == EnumFacing.UP || face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.MIDDLE_POLE;
			case Z:
				return face == EnumFacing.NORTH || face == EnumFacing.SOUTH ? BlockFaceShape.SOLID : BlockFaceShape.MIDDLE_POLE;
		}
		
		return BlockFaceShape.MIDDLE_POLE;
	}
}
