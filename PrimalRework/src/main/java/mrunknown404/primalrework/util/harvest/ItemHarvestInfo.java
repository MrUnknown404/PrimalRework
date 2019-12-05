package mrunknown404.primalrework.util.harvest;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.item.Item;

public class ItemHarvestInfo {
	
	private final Item item;
	private final List<EnumToolType> toolTypes;
	private final EnumToolMaterial material;
	
	public ItemHarvestInfo(Item item, List<EnumToolType> toolTypes, EnumToolMaterial material) {
		this.item = item;
		this.material = material;
		this.toolTypes = toolTypes;
	}
	
	public ItemHarvestInfo(Item item, EnumToolType toolType, EnumToolMaterial material) {
		this(item, Arrays.asList(toolType), material);
	}
	
	public Item getItem() {
		return item;
	}
	
	public EnumToolMaterial getMaterial() {
		return material;
	}
	
	public int getEnchantability() {
		return material.enchantability;
	}
	
	public List<EnumToolType> getToolTypes() {
		return toolTypes;
	}
}
