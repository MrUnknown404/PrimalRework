package mrunknown404.primalrework.util.harvest;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.item.Item;

public class ItemHarvestInfo extends HarvestInfo<Item> {
	
	private final EnumToolMaterial material;
	
	public ItemHarvestInfo(Item item, List<EnumToolType> toolTypes, EnumToolMaterial material) {
		super(item, toolTypes);
		this.material = material;
	}
	
	public ItemHarvestInfo(Item item, EnumToolType toolType, EnumToolMaterial material) {
		this(item, Arrays.asList(toolType), material);
	}
	
	public EnumToolMaterial getMaterial() {
		return material;
	}
	
	public int getEnchantability() {
		return material.enchantability;
	}
}
