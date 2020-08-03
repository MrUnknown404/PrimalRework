package mrunknown404.primalrework.blocks.util;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.unknownlibs.utils.DoubleValue;
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

public class BlockStaged extends Block implements IBlockStaged<BlockStaged> {
	
	private final BlockRenderLayer renderType;
	private final AxisAlignedBB collisionAABB, visualAABB;
	private int amountOfTooltops = 0;
	
	protected final EnumStage stage;
	protected BlockHarvestInfo harvestInfo;
	
	protected BlockStaged(String name, Material material, SoundType soundType, BlockRenderLayer renderType, float hardness, float resistance, AxisAlignedBB collisionAABB,
			AxisAlignedBB visualAABB, EnumStage stage, DoubleValue<EnumToolType, EnumToolMaterial>... types) {
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
		this.harvestInfo = new BlockHarvestInfo(types);
		
		addToModList(this);
		
		if (types == null || types.length == 0) {
			System.err.println("Invalid types for " + getUnlocalizedName());
			this.harvestInfo = new BlockHarvestInfo();
			return;
		}
	}
	
	public BlockStaged(String name, Material material, SoundType soundType, float hardness, float resistance, EnumStage stage, DoubleValue<EnumToolType, EnumToolMaterial>... types) {
		this(name, material, soundType, BlockRenderLayer.SOLID, hardness, resistance, FULL_BLOCK_AABB, FULL_BLOCK_AABB, stage, types);
	}
	
	@Override
	public BlockStaged setAmountOfTooltops(int amountOfTooltops) {
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
	public boolean isOpaqueCube(IBlockState state) {
		return true;
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
