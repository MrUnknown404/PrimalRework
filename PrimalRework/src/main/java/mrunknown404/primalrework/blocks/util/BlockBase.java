package mrunknown404.primalrework.blocks.util;

import java.util.List;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.ColorH;
import mrunknown404.primalrework.util.IEasyToolTip;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.primalrework.util.harvest.HarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockBase extends Block {

	private final BlockRenderLayer renderType;
	private final AxisAlignedBB collisionAABB, visualAABB;
	protected BlockHarvestInfo harvestInfo;
	
	protected TextComponentTranslation tooltip;
	
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
		
		if (this instanceof IEasyToolTip) {
			((IEasyToolTip) this).setTooltip();
		}
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setUnlocalizedName(name).setRegistryName(name));
		
		setupHarvestInfo();
	}
	
	public BlockBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness, float resistance) {
		this(name, material, soundType, renderType, hardness, resistance, FULL_BLOCK_AABB, FULL_BLOCK_AABB);
	}
	
	public abstract void setupHarvestInfo();
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (this.tooltip == null) {
			super.addInformation(stack, world, tooltip, advanced);
			return;
		}
		
		String[] tips = this.tooltip.getUnformattedText().trim().split("\\\\n");
		
		for (String t : tips) {
			tooltip.add(ColorH.addColor(t));
		}
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
	
	public HarvestInfo getHarvestInfo() {
		return harvestInfo;
	}
	
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
