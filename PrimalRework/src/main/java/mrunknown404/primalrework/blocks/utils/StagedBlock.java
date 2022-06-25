package mrunknown404.primalrework.blocks.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.HarvestInfo;
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
import net.minecraft.world.IBlockReader;

public class StagedBlock extends Block {
	private final Map<ToolType, HarvestInfo> harvestInfos = new HashMap<ToolType, HarvestInfo>();
	
	public final Supplier<Stage> stage;
	public final int stackSize;
	public final ItemGroup tab;
	public final BlockInfo blockInfo;
	private final String name;
	private final BlockStateType blockStateType;
	private final BlockModelType blockModelType;
	private final List<ITextComponent> tooltips = new ArrayList<ITextComponent>();
	private boolean useVanillaNamespaceBlock, useVanillaNamespaceItem;
	
	public StagedBlock(String name, Supplier<Stage> stage, int stackSize, ItemGroup tab, BlockInfo blockInfo, BlockStateType blockStateType, BlockModelType blockModelType,
			HarvestInfo info, HarvestInfo... extraInfos) {
		super(toProperties(blockInfo));
		this.name = name;
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
	
	public StagedBlock(String name, Supplier<Stage> stage, BlockInfo blockInfo, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info,
			HarvestInfo... extraInfos) {
		this(name, stage, 64, PRItemGroups.BLOCKS, blockInfo, blockStateType, blockModelType, info, extraInfos);
	}
	
	public StagedBlock(String name, Supplier<Stage> stage, BlockInfo blockInfo, HarvestInfo info, HarvestInfo... extraInfos) {
		this(name, stage, 64, PRItemGroups.BLOCKS, blockInfo, BlockStateType.normal, BlockModelType.normal, info, extraInfos);
	}
	
	public StagedBlock addTooltip(int amount) {
		for (int i = 0; i < amount; i++) {
			tooltips.add(WordH.translate("tooltips.item." + name + "." + tooltips.size()).withStyle(WordH.STYLE_GRAY));
		}
		return this;
	}
	
	public StagedBlock addTooltip() {
		return addTooltip(1);
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
	
	public String getRegName() {
		return name;
	}
	
	public ItemType getItemType() {
		return ItemType.block;
	}
	
	public StagedItem asStagedItem() {
		Item item = asItem(); //Just used to fix data generation. unsure why StagedTags are even called tbh but they are being called
		return item == Items.AIR ? null : (StagedItem) item;
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
	
	public StagedBlock usesVanillaNamespaceFull() {
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
		normal_colored,
		facing_pillar,
		slab;
	}
}
