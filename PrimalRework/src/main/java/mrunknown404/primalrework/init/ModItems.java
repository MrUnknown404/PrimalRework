package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.items.ItemBase;
import mrunknown404.primalrework.items.ItemFireStarter;
import mrunknown404.primalrework.items.ItemToolBase;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	public static final ToolMaterial MATERIAL = EnumHelper.addToolMaterial("MATERIAL", 0, 0, 0, 0, 0);
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item PLANT_FIBER = new ItemBase("plant_fiber");
	public static final Item PLANT_ROPE = new ItemBase("plant_rope");
	public static final Item GRAVEL = new ItemBase("gravel");
	public static final Item FLINT_KNAPPED = new ItemBase("flint_knapped");
	public static final Item FLINT_POINT = new ItemBase("flint_point");
	public static final Item BONE_SHARD = new ItemBase("bone_shard");
	public static final Item FIRE_STARTER = new ItemFireStarter();
	
	public static final Item PLANT_MESH = new ItemBase("plant_mesh", EnumToolMaterial.flint);
	public static final Item STRING_MESH = new ItemBase("string_mesh", EnumToolMaterial.copper);
	
	public static final Item FLINT_CRAFTING_HAMMER = new ItemBase("flint_crafting_hammer", EnumToolMaterial.flint);
	
	public static final Item FLINT_KNIFE = new ItemToolBase("flint_knife", EnumToolType.knife, EnumToolMaterial.flint);
	public static final Item FLINT_PICKAXE = new ItemToolBase("flint_pickaxe", EnumToolType.pickaxe, EnumToolMaterial.flint);
	public static final Item FLINT_AXE = new ItemToolBase("flint_axe", EnumToolType.axe, EnumToolMaterial.flint);
	public static final Item FLINT_SHOVEL = new ItemToolBase("flint_shovel", EnumToolType.shovel, EnumToolMaterial.flint);
	
	public static final Item BONE_KNIFE = new ItemToolBase("bone_knife", EnumToolType.knife, EnumToolMaterial.bone);
	
	public static Item find(ResourceLocation name) {
		for (Item item : ITEMS) {
			if (item.getRegistryName() == name) {
				return item;
			}
		}
		
		return null;
	}
}
