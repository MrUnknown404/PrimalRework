package mrunknown404.primalrework.items.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import mrunknown404.primalrework.events.client.TooltipCEvents;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class StagedItem extends Item {
	public final EnumStage stage;
	public final EnumToolType toolType;
	public final EnumToolMaterial toolMat;
	private final String name;
	private ItemType itemType;
	private final List<ITextComponent> tooltips = new ArrayList<ITextComponent>();
	
	private Multimap<Attribute, AttributeModifier> defaultModifiers;
	
	protected StagedItem(String name, EnumStage stage, int maxStackSize, EnumToolType toolType, EnumToolMaterial toolMat, ItemGroup tab, Rarity rarity, Food food,
			boolean isFireResistant, boolean canRepair, ItemType itemType) {
		super(toProperties(maxStackSize, toolMat.durability, tab, rarity, food, isFireResistant, canRepair));
		this.name = name;
		this.stage = stage;
		this.toolType = toolType;
		this.toolMat = toolMat;
		this.itemType = itemType;
		
		setupModifiers();
	}
	
	public StagedItem(String name, EnumStage stage) {
		this(name, stage, 64, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.ITEMS, Rarity.COMMON, null, false, false, ItemType.generated);
	}
	
	public StagedItem(String name, EnumStage stage, int stackSize) {
		this(name, stage, stackSize, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.ITEMS, Rarity.COMMON, null, false, false, ItemType.generated);
	}
	
	public StagedItem addTooltip(int amount) {
		for (int i = 0; i < amount; i++) {
			tooltips.add(new TranslationTextComponent("tooltips.item." + name + "." + tooltips.size()).withStyle(TooltipCEvents.STYLE_GRAY));
		}
		return this;
	}
	
	public StagedItem addTooltip() {
		return addTooltip(1);
	}
	
	public List<ITextComponent> getTooltips() {
		return tooltips;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {
		return slot == EquipmentSlotType.MAINHAND ? defaultModifiers : ImmutableMultimap.of();
	}
	
	protected void setupModifiers() {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE,
				new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", toolType.baseDamage + toolMat.extraDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", toolType.swingSpeed, AttributeModifier.Operation.ADDITION));
		defaultModifiers = builder.build();
	}
	
	public StagedItem overrideItemType(ItemType itemType) {
		this.itemType = itemType;
		return this;
	}
	
	public ItemType getItemType() {
		return itemType;
	}
	
	public String getRegName() {
		return name;
	}
	
	public static Properties toProperties(int maxStackSize, int maxDamage, ItemGroup tab, Rarity rarity, Food food, boolean isFireResistant, boolean canRepair) {
		Properties p = new Properties().stacksTo(maxStackSize).tab(tab).rarity(rarity).food(food);
		
		if (maxDamage != 0) {
			p.durability(maxDamage);
		}
		if (isFireResistant) {
			p.fireResistant();
		}
		if (!canRepair) {
			p.setNoRepair();
		}
		
		return p;
	}
	
	public enum ItemType {
		none,
		generated,
		handheld,
		handheld_rod,
		block,
		itemblock
	}
}
