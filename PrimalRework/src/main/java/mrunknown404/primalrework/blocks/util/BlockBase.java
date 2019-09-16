package mrunknown404.primalrework.blocks.util;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.primalrework.util.harvest.HarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockBase extends Block implements IBlockBase {

	private final BlockRenderLayer renderType;
	private final AxisAlignedBB collisionAABB, visualAABB;
	protected BlockHarvestInfo harvestInfo;
	
	public BlockBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness, float resistance,
			AxisAlignedBB collisionAABB, AxisAlignedBB visualAABB) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_BLOCKS);
		setSoundType(soundType);
		setHardness(hardness);
		setResistance(resistance);
		
		this.renderType = renderType;
		this.collisionAABB = collisionAABB;
		this.visualAABB = visualAABB;
		
		addToModList(this);
		
		setupHarvestInfo();
	}
	
	public BlockBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness, float resistance) {
		this(name, material, soundType, renderType, hardness, resistance, FULL_BLOCK_AABB, FULL_BLOCK_AABB);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.addAll(getTooltip(getUnlocalizedName()));
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return renderType;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return renderType == BlockRenderLayer.SOLID ? true : false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return renderType == BlockRenderLayer.SOLID ? true : false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return collisionAABB;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return visualAABB;
	}
	
	@Override
	public HarvestInfo getHarvestInfo() {
		return harvestInfo;
	}
}
