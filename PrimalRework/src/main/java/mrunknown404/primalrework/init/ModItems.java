package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.items.ItemBase;
import mrunknown404.primalrework.items.ItemToolBase;
import mrunknown404.primalrework.util.harvest.ToolHarvestLevel;
import mrunknown404.primalrework.util.harvest.ToolType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item PLANT_FIBER = new ItemBase("plant_fiber");
	public static final Item PLANT_ROPE = new ItemBase("plant_rope");
	public static final Item GRAVEL = new ItemBase("gravel");
	public static final Item FLINT_KNAPPED = new ItemBase("flint_knapped");
	public static final Item FLINT_POINT = new ItemBase("flint_point");
	
	public static final Item FLINT_KNIFE = new ItemToolBase("flint_knife", ToolType.knife, ToolHarvestLevel.flint, ModToolMaterials.FLINT_MATERIAL);
	
	public static Item find(ResourceLocation name) {
		for (Item item : ITEMS) {
			if (item.getRegistryName() == name) {
				return item;
			}
		}
		
		return null;
	}
}
