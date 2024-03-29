package mrunknown404.primalrework.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.api.registry.PRRegistryObject;
import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.api.utils.IStageProvider;
import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.items.StagedItem.ItemType;
import mrunknown404.primalrework.recipes.IIngredientProvider;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.Element;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;

public class StagedBlock extends Block implements ISIProvider, IIngredientProvider, IStageProvider {
	private final Map<ToolType, HarvestInfo> harvestInfos = new HashMap<ToolType, HarvestInfo>();
	protected final Map<Element, Integer> elements = new LinkedHashMap<Element, Integer>();
	
	public final PRRegistryObject<Stage> stage;
	public final int stackSize;
	public final ItemGroup tab;
	public final BlockInfo blockInfo;
	private final BlockStateType blockStateType;
	private final BlockModelType blockModelType;
	private final List<ITextComponent> tooltips = new ArrayList<ITextComponent>();
	private boolean useVanillaNamespaceBlock, useVanillaNamespaceItem;
	
	public StagedBlock(PRRegistryObject<Stage> stage, int stackSize, ItemGroup tab, BlockInfo blockInfo, BlockStateType blockStateType, BlockModelType blockModelType,
			HarvestInfo info, HarvestInfo... extraInfos) {
		super(toProperties(blockInfo));
		this.stage = stage;
		this.stackSize = stackSize;
		this.tab = tab;
		this.blockStateType = blockStateType;
		this.blockModelType = blockModelType;
		this.blockInfo = blockInfo;
		
		harvestInfos.put(info.toolType, info);
		for (HarvestInfo is : extraInfos) {
			harvestInfos.put(is.toolType, is);
		}
	}
	
	public StagedBlock(PRRegistryObject<Stage> stage, BlockInfo blockInfo, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info,
			HarvestInfo... extraInfos) {
		this(stage, 64, InitItemGroups.BLOCKS, blockInfo, blockStateType, blockModelType, info, extraInfos);
	}
	
	public StagedBlock(PRRegistryObject<Stage> stage, BlockInfo blockInfo, HarvestInfo info, HarvestInfo... extraInfos) {
		this(stage, 64, InitItemGroups.BLOCKS, blockInfo, BlockStateType.normal, BlockModelType.normal, info, extraInfos);
	}
	
	public StagedBlock addTooltip(int amount) {
		String name = getRegistryName().getPath();
		for (int i = 0; i < amount; i++) {
			tooltips.add(WordH.translate("tooltips.item." + name + "." + tooltips.size()).withStyle(TextFormatting.GRAY));
		}
		return this;
	}
	
	public StagedBlock addTooltip() {
		return addTooltip(1);
	}
	
	public Map<Element, Integer> getElements() {
		return elements;
	}
	
	public List<ITextComponent> getTooltips() {
		return tooltips;
	}
	
	public Map<ToolType, HarvestInfo> getHarvest() {
		return harvestInfos;
	}
	
	public BlockStateType getBlockStateType() {
		return blockStateType;
	}
	
	public BlockModelType getBlockModelType() {
		return blockModelType;
	}
	
	public ItemType getItemType() {
		return ItemType.block;
	}
	
	public boolean coloredByBiome() {
		return false;
	}
	
	@Override
	public StagedItem getStagedItem() {
		Item item = asItem(); //Just used to fix data generation. unsure why StagedTags are even called tbh but they are being called
		return item == Items.AIR ? null : (StagedItem) item;
	}
	
	@Override
	public Ingredient getIngredient() {
		return Ingredient.createUsingItem(getStagedItem());
	}
	
	@Override
	public Stage getStage() {
		return stage.get();
	}
	
	@Deprecated
	@Override
	public Item asItem() {
		return super.asItem();
	}
	
	@Override
	public final int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return blockInfo.getFlammability();
	}
	
	@Override
	public final int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return blockInfo.getFireSpreadSpeed();
	}
	
	public StagedBlock useVanillaNamespaceBlock() {
		useVanillaNamespaceBlock = true;
		return this;
	}
	
	public StagedBlock useVanillaNamespaceItem() {
		useVanillaNamespaceItem = true;
		return this;
	}
	
	public StagedBlock useVanillaNamespaceFull() {
		useVanillaNamespaceBlock = true;
		useVanillaNamespaceItem = true;
		return this;
	}
	
	public boolean usesVanillaNamespaceBlock() {
		return useVanillaNamespaceBlock;
	}
	
	public boolean usesVanillaNamespaceItem() {
		return useVanillaNamespaceItem;
	}
	
	protected static Properties toProperties(BlockInfo blockInfo) {
		Properties p = Properties.of(blockInfo.getMaterial()).sound(blockInfo.getSound()).strength(blockInfo.getHardness(), blockInfo.getBlast())
				.lightLevel((state) -> blockInfo.getLight());
		
		if (!blockInfo.getMaterial().blocksMotion()) {
			p.noCollission();
		}
		if (blockInfo.isRandomTick()) {
			p.randomTicks();
		}
		if (!blockInfo.getMaterial().isSolidBlocking()) {
			p.noOcclusion();
		}
		
		return p;
	}
	
	public enum BlockStateType {
		none,
		normal,
		facing,
		facing_pillar,
		lit,
		random_direction,
		slab;
	}
	
	public enum BlockModelType {
		none,
		normal,
		facing_pillar,
		slab;
	}
}
