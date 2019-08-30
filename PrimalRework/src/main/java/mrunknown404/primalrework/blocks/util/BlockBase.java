package mrunknown404.primalrework.blocks.util;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.IHasModel;
import mrunknown404.primalrework.util.harvest.HarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class BlockBase extends Block implements IHasModel {

	private final BlockRenderLayer renderType;
	private final AxisAlignedBB collisionAABB, visualAABB;
	protected HarvestInfo harvestInfo;
	
	public BlockBase(String name, Material material, CreativeTabs tab, SoundType soundType, BlockRenderLayer renderType, float hardness, float resistance,
			AxisAlignedBB collisionAABB, AxisAlignedBB visualAABB) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setSoundType(soundType);
		setHardness(hardness);
		setResistance(resistance);
		
		this.renderType = renderType;
		this.collisionAABB = collisionAABB;
		this.visualAABB = visualAABB;
		
		setHarvestInfo();
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}
	
	public BlockBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness, float resistance,
			AxisAlignedBB collisionAABB, AxisAlignedBB visualAABB) {
		this(name, material, ModCreativeTabs.PRIMALREWORK_BLOCKS, soundType, renderType, hardness, resistance, collisionAABB, visualAABB);
	}
	
	public BlockBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness, float resistance) {
		this(name, material, ModCreativeTabs.PRIMALREWORK_BLOCKS, soundType, renderType, hardness, resistance, FULL_BLOCK_AABB, FULL_BLOCK_AABB);
	}
	
	public abstract void setHarvestInfo();
	
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
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
