package mrunknown404.primalrework.blocks.util;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
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

public class BlockBase extends Block implements IBlockBase<BlockBase> {

	private final BlockRenderLayer renderType;
	private final AxisAlignedBB collisionAABB, visualAABB;
	protected final EnumStage stage;
	private int amountOfTooltops = 0;
	protected BlockHarvestInfo harvestInfo;
	
	protected BlockBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness, float resistance,
			AxisAlignedBB collisionAABB, AxisAlignedBB visualAABB, EnumStage stage, DoubleValue<EnumToolType, EnumToolMaterial>... types) {
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
		this.stage = stage;
		
		addToModList(this);
		
		if (types == null || types.length == 0) {
			System.err.println("Invalid types for " + getUnlocalizedName());
			setHarvestInfo(BlockHarvestInfo.create());
			return;
		}
		
		setHarvestInfo(BlockHarvestInfo.create(types));
	}
	
	public BlockBase(String name, Material material, SoundType soundType, float hardness, float resistance, EnumStage stage, DoubleValue<EnumToolType, EnumToolMaterial>... types) {
		this(name, material, soundType, BlockRenderLayer.SOLID, hardness, resistance, FULL_BLOCK_AABB, FULL_BLOCK_AABB, stage, types);
	}
	
	@Override
	public void setHarvestInfo(BlockHarvestInfo info) {
		this.harvestInfo = info;
	}
	
	@Override
	public BlockBase setAmountOfTooltops(int amountOfTooltops) {
		this.amountOfTooltops = amountOfTooltops;
		return this;
	}
	
	@Override
	public int getAmountOfTooltips() {
		return amountOfTooltops;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.addAll(getTooltips(getUnlocalizedName()));
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
	public BlockHarvestInfo getHarvestInfo() {
		return harvestInfo;
	}
	
	@Override
	public EnumStage getStage() {
		return stage;
	}
}
