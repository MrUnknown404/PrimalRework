package mrunknown404.primalrework.init;

import java.util.List;

import mrunknown404.primalrework.items.ItemBase;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item PLANT_FIBER = new ItemBase("plant_fiber", ModCreativeTabs.PRIMALREWORK_ITEMS);
	public static final Item PLANT_ROPE = new ItemBase("plant_rope", ModCreativeTabs.PRIMALREWORK_ITEMS);
	public static final Item GRAVEL = new ItemBase("gravel", ModCreativeTabs.PRIMALREWORK_ITEMS);
	
	public static Item find(ResourceLocation name) {
		for (Item item : ITEMS) {
			if (item.getRegistryName() == name) {
				return item;
			}
		}
		
		return null;
	}
}
