package mrunknown404.primalrework.items;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.api.utils.IStageProvider;
import mrunknown404.primalrework.init.InitPRItemGroups;
import mrunknown404.primalrework.init.InitToolMaterials;
import mrunknown404.primalrework.recipes.IIngredientProvider;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.Element;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class StagedItem extends Item implements ISIProvider, IIngredientProvider, IStageProvider {
	public final Supplier<Stage> stage;
	public final ToolType toolType;
	public final ToolMaterial toolMat;
	private ItemType itemType;
	private boolean usesVanillaNamespace;
	
	protected final Map<Element, Integer> elements = new LinkedHashMap<Element, Integer>();
	private final List<ITextComponent> tooltips = new ArrayList<ITextComponent>();
	private Multimap<Attribute, AttributeModifier> defaultModifiers;
	
	protected StagedItem(Supplier<Stage> stage, int maxStackSize, ToolType toolType, ToolMaterial toolMat, ItemGroup tab, Rarity rarity, Food food, boolean isFireResistant,
			boolean canRepair, ItemType itemType) {
		super(toProperties(maxStackSize, toolMat.durability, tab, rarity, food, isFireResistant, canRepair));
		this.stage = stage;
		this.toolType = toolType;
		this.toolMat = toolMat;
		this.itemType = itemType;
		
		setupModifiers();
	}
	
	public StagedItem(Supplier<Stage> stage) {
		this(stage, 64, ToolType.NONE, InitToolMaterials.HAND.get(), InitPRItemGroups.ITEMS, Rarity.COMMON, null, false, false, ItemType.generated);
	}
	
	public StagedItem(Supplier<Stage> stage, int stackSize) {
		this(stage, stackSize, ToolType.NONE, InitToolMaterials.HAND.get(), InitPRItemGroups.ITEMS, Rarity.COMMON, null, false, false, ItemType.generated);
	}
	
	public StagedItem(Supplier<Stage> stage, ItemGroup tab) {
		this(stage, 64, ToolType.NONE, InitToolMaterials.HAND.get(), tab, Rarity.COMMON, null, false, false, ItemType.generated);
	}
	
	public StagedItem(Supplier<Stage> stage, int stackSize, ItemGroup tab) {
		this(stage, stackSize, ToolType.NONE, InitToolMaterials.HAND.get(), tab, Rarity.COMMON, null, false, false, ItemType.generated);
	}
	
	public StagedItem addTooltip(int amount) {
		String name = getRegistryName().getPath();
		for (int i = 0; i < amount; i++) {
			tooltips.add(WordH.translate("tooltips.item." + name + "." + tooltips.size()).withStyle(TextFormatting.GRAY));
		}
		return this;
	}
	
	public StagedItem addTooltip() {
		return addTooltip(1);
	}
	
	public Map<Element, Integer> getElements() {
		return elements;
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
	
	public StagedItem useVanillaNamespace() {
		usesVanillaNamespace = true;
		return this;
	}
	
	public boolean usesVanillaNamespace() {
		return usesVanillaNamespace;
	}
	
	@Override
	public StagedItem getStagedItem() {
		return this;
	}
	
	@Override
	public Ingredient getIngredient() {
		return Ingredient.createUsingItem(this);
	}
	
	@Override
	public Stage getStage() {
		return stage.get();
	}
	
	protected static Properties toProperties(int maxStackSize, int maxDamage, ItemGroup tab, Rarity rarity, Food food, boolean isFireResistant, boolean canRepair) {
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
		itemblock;
	}
}
