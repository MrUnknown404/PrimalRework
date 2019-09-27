package mrunknown404.primalrework.blocks.util;

import java.util.List;
import java.util.Random;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import net.minecraft.block.BlockPurpurSlab;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockSlabBase extends BlockSlab implements ISlabBase<BlockSlabBase> {
	
	private final BlockRenderLayer renderType;
	private final boolean isDouble;
	protected final EnumStage stage;
	private int amountOfTooltops = 0;
	protected BlockHarvestInfo harvestInfo;
	
	public BlockSlabBase(String name, Material material, SoundType soundType, float hardness, float resistance, boolean isDouble, EnumStage stage,
			DoubleValue<EnumToolType, EnumToolMaterial>... types) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_BLOCKS);
		setSoundType(soundType);
		setHardness(hardness);
		setResistance(resistance);
		
		IBlockState iblockstate = blockState.getBaseState();
		if (!isDouble()) {
			iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		}
		this.setDefaultState(iblockstate.withProperty(BlockPurpurSlab.VARIANT, BlockPurpurSlab.Variant.DEFAULT));
		
		this.isDouble = isDouble;
		this.renderType = isDouble ? BlockRenderLayer.SOLID : BlockRenderLayer.CUTOUT;
		this.useNeighborBrightness = !isDouble();
		this.stage = stage;
		
		addToModList(this);
		
		if (types == null || types.length == 0) {
			System.err.println("Invalid types for " + getUnlocalizedName());
			setHarvestInfo(BlockHarvestInfo.create());
			return;
		}
		
		setHarvestInfo(BlockHarvestInfo.create(types));
	}
	
	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName();
	}
	
	@Override
	public IProperty<?> getVariantProperty() {
		return BlockPurpurSlab.VARIANT;
	}
	
	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return BlockPurpurSlab.Variant.DEFAULT;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(getSingleVersion());
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(getSingleVersion());
	}
	
	@Override
	public final IBlockState getStateFromMeta(int meta) {
		IBlockState blockstate = getDefaultState().withProperty(BlockPurpurSlab.VARIANT, BlockPurpurSlab.Variant.DEFAULT);
		
		if (!isDouble()) {
			blockstate = blockstate.withProperty(HALF, (meta == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP));
		}
		
		return blockstate;
	}
	
	@Override
	public final int getMetaFromState(IBlockState state) {
		int meta = 0;
		
		if (!isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP) {
			meta = 1;
		}
		
		return meta;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return isDouble() ? new BlockStateContainer(this, new IProperty[] {BlockPurpurSlab.VARIANT}) :
			new BlockStateContainer(this, new IProperty[] {HALF, BlockPurpurSlab.VARIANT});
	}
	
	@Override
	public boolean isDouble() {
		return isDouble;
	}
	
	@Override
	public void setHarvestInfo(BlockHarvestInfo info) {
		this.harvestInfo = info;
	}
	
	@Override
	public BlockSlabBase setAmountOfTooltops(int amountOfTooltops) {
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
	public BlockHarvestInfo getHarvestInfo() {
		return harvestInfo;
	}
	
	@Override
	public EnumStage getStage() {
		return stage;
	}
	
	public static enum Variant implements IStringSerializable {
		DEFAULT;
		
		@Override
		public String getName() {
			return "default";
		}
	}
}
