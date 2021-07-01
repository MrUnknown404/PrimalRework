package mrunknown404.primalrework.blocks.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.events.client.TooltipCEvents;
import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class StagedBlock extends Block {
	private final Map<EnumToolType, HarvestInfo> harvestInfos = new HashMap<EnumToolType, HarvestInfo>();
	
	public final EnumStage stage;
	public final int stackSize;
	public final ItemGroup tab;
	private final String name;
	private final BlockStateType blockStateType;
	private final BlockModelType blockModelType;
	private final List<ITextComponent> tooltips = new ArrayList<ITextComponent>();
	
	protected StagedBlock(String name, EnumStage stage, int stackSize, ItemGroup tab, Material material, SoundType sound, boolean hasCollision, int lightLevel, float hardness,
			float blastResist, boolean isRandomTick, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info, HarvestInfo... extraInfos) {
		super(toProperties(material, sound, hasCollision, lightLevel, hardness, blastResist, isRandomTick));
		this.name = name;
		this.stage = stage;
		this.stackSize = stackSize;
		this.tab = tab;
		this.blockStateType = blockStateType;
		this.blockModelType = blockModelType;
		
		harvestInfos.put(info.toolType, info);
		for (HarvestInfo is : extraInfos) {
			harvestInfos.put(is.toolType, is);
		}
	}
	
	public StagedBlock(String name, EnumStage stage, Material material, SoundType sound, float hardness, float blastResist, HarvestInfo info, HarvestInfo... extraInfos) {
		this(name, stage, 64, InitItemGroups.BLOCKS, material, sound, true, 0, hardness, blastResist, false, BlockStateType.normal, BlockModelType.normal, info, extraInfos);
	}
	
	public StagedBlock addTooltip(int amount) {
		for (int i = 0; i < amount; i++) {
			tooltips.add(new TranslationTextComponent("tooltips.item." + name + "." + tooltips.size()).withStyle(TooltipCEvents.STYLE_GRAY));
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
	
	protected static Properties toProperties(Material material, SoundType sound, boolean hasCollision, int lightLevel, float hardness, float blastResist, boolean isRandomTick) {
		Properties p = Properties.of(material).sound(sound).strength(hardness, blastResist).lightLevel((light) -> {
			return lightLevel;
		});
		
		if (!hasCollision) {
			p.noCollission();
		}
		if (isRandomTick) {
			p.randomTicks();
		}
		
		return p;
	}
	
	public enum BlockStateType {
		none,
		normal,
		facing,
		facing_pillar,
		lit,
		random_direction;
	}
	
	public enum BlockModelType {
		none,
		normal,
		facing_pillar;
	}
}
