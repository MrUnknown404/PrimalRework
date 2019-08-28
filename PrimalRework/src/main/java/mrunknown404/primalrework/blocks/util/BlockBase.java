package mrunknown404.primalrework.blocks.util;

import javax.annotation.Nullable;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.IHasModel;
import mrunknown404.primalrework.util.ToolType;
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

public class BlockBase extends Block implements IHasModel {

	private final BlockRenderLayer renderType;
	private final AxisAlignedBB collisionAABB, visualAABB;
	
	/**
	 * @param name
	 * @param material
	 * @param tab
	 * @param soundType
	 * @param renderType
	 * @param toolType
	 * @param harvestLevel
	 * @param hardness
	 * @param resistance
	 * @param collisionAABB
	 * @param visualAABB
	 */
	public BlockBase(String name, Material material, CreativeTabs tab, SoundType soundType, BlockRenderLayer renderType, @Nullable ToolType toolType, int harvestLevel,
			float hardness, float resistance, AxisAlignedBB collisionAABB, AxisAlignedBB visualAABB) {
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
		
		if (toolType != null) {
			setHarvestLevel(toolType.toString(), harvestLevel);
		}
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}
	
	/**
	 * @param name
	 * @param material
	 * @param soundType
	 * @param renderType
	 * @param toolType
	 * @param harvestLevel
	 * @param hardness
	 * @param resistance
	 * @param collisionAABB
	 * @param visualAABB
	 */
	public BlockBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, ToolType toolType, int harvestLevel, float hardness, float resistance,
			AxisAlignedBB collisionAABB, AxisAlignedBB visualAABB) {
		this(name, material, ModCreativeTabs.PRIMALREWORK_BLOCKS, soundType, renderType, toolType, harvestLevel, hardness, resistance, collisionAABB, visualAABB);
	}
	
	/**
	 * @param name
	 * @param material
	 * @param soundType
	 * @param renderType
	 * @param hardness_resistance
	 * @param collisionAABB
	 * @param visualAABB
	 */
	public BlockBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness_resistance, AxisAlignedBB collisionAABB, AxisAlignedBB visualAABB) {
		this(name, material, ModCreativeTabs.PRIMALREWORK_BLOCKS, soundType, renderType, null, -1, hardness_resistance, hardness_resistance, collisionAABB, visualAABB);
	}
	
	/**
	 * @param name
	 * @param material
	 * @param soundType
	 * @param renderType
	 * @param toolType
	 * @param harvestLevel
	 * @param hardness
	 * @param resistance
	 */
	public BlockBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, ToolType toolType, int harvestLevel, float hardness, float resistance) {
		this(name, material, ModCreativeTabs.PRIMALREWORK_BLOCKS, soundType, renderType, toolType, harvestLevel, hardness, resistance, FULL_BLOCK_AABB, FULL_BLOCK_AABB);
	}
	
	/**
	 * @param name
	 * @param material
	 * @param soundType
	 * @param renderType
	 * @param hardness_resistance
	 */
	public BlockBase(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness_resistance) {
		this(name, material, ModCreativeTabs.PRIMALREWORK_BLOCKS, soundType, renderType, null, -1, hardness_resistance, hardness_resistance, FULL_BLOCK_AABB, FULL_BLOCK_AABB);
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
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
