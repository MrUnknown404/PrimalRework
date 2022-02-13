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
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumBlockInfo;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;

public class StagedBlock extends Block {
	private final Map<EnumToolType, HarvestInfo> harvestInfos = new HashMap<EnumToolType, HarvestInfo>();
	
	public final Supplier<Stage> stage;
	public final int stackSize;
	public final ItemGroup tab;
	public final EnumBlockInfo blockInfo;
	private final String name;
	private final BlockStateType blockStateType;
	private final BlockModelType blockModelType;
	private final List<ITextComponent> tooltips = new ArrayList<ITextComponent>();
	private boolean overridesVanillaBlock, overridesVanillaItem;
	
	protected StagedBlock(String name, Supplier<Stage> stage, int stackSize, ItemGroup tab, Material material, SoundType sound, boolean hasCollision, boolean canOcclude,
			int lightLevel, EnumBlockInfo blockInfo, boolean isRandomTick, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info,
			HarvestInfo... extraInfos) {
		super(toProperties(material, sound, hasCollision, canOcclude, lightLevel, blockInfo, isRandomTick));
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
	
	public Map<EnumToolType, HarvestInfo> getHarvest() {
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
	
	public StagedBlock overrideVanilla() {
		overridesVanillaBlock = true;
		return this;
	}
	
	public StagedBlock overrideVanilla(boolean overridesVanillaItem) {
		this.overridesVanillaItem = overridesVanillaItem;
		return overrideVanilla();
	}
	
	public boolean overridesVanillaBlock() {
		return overridesVanillaBlock;
	}
	
	public boolean overridesVanillaItem() {
		return overridesVanillaItem;
	}
	
	protected static Properties toProperties(Material material, SoundType sound, boolean hasCollision, boolean canOcclude, int lightLevel, EnumBlockInfo blockInfo,
			boolean isRandomTick) {
		Properties p = Properties.of(material).sound(sound).strength(blockInfo.hardness, blockInfo.blast).lightLevel((light) -> {
			return lightLevel;
		});
		
		if (!hasCollision) {
			p.noCollission();
		}
		if (isRandomTick) {
			p.randomTicks();
		}
		if (!canOcclude) {
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
	
	public static class Builder {
		private final String name;
		private final Supplier<Stage> stage;
		private final Material material;
		private final SoundType sound;
		private final EnumBlockInfo blockInfo;
		private final HarvestInfo info;
		private final HarvestInfo[] extraInfos;
		
		private int stackSize = 64;
		private ItemGroup tab = PRItemGroups.BLOCKS;
		private boolean hasCollision = true, isRandomTick = false, canOcclude = true;
		private int lightLevel = 0;
		private BlockStateType blockStateType = BlockStateType.normal;
		private BlockModelType blockModelType = BlockModelType.normal;
		
		public Builder(String name, Supplier<Stage> stage, Material material, SoundType sound, EnumBlockInfo blockInfo, HarvestInfo info, HarvestInfo... extraInfos) {
			this.name = name;
			this.stage = stage;
			this.material = material;
			this.sound = sound;
			this.blockInfo = blockInfo;
			this.info = info;
			this.extraInfos = extraInfos;
		}
		
		public Builder setStackSize(int stackSize) {
			this.stackSize = stackSize;
			return this;
		}
		
		public Builder setGroup(ItemGroup tab) {
			this.tab = tab;
			return this;
		}
		
		public Builder disableCollision() {
			this.hasCollision = false;
			return this;
		}
		
		public Builder enableRandomTick() {
			this.isRandomTick = true;
			return this;
		}
		
		public Builder noOcclusion() {
			canOcclude = false;
			return this;
		}
		
		public Builder setLightLevel(int lightLevel) {
			this.lightLevel = lightLevel;
			return this;
		}
		
		public Builder setBlockStateType(BlockStateType blockStateType) {
			this.blockStateType = blockStateType;
			return this;
		}
		
		public Builder setBlockModelType(BlockModelType blockModelType) {
			this.blockModelType = blockModelType;
			return this;
		}
		
		public StagedBlock create() {
			return new StagedBlock(name, stage, stackSize, tab, material, sound, hasCollision, canOcclude, lightLevel, blockInfo, isRandomTick, blockStateType, blockModelType,
					info, extraInfos);
		}
	}
}
