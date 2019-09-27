package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.items.ItemChangeWithWater;
import mrunknown404.primalrework.items.ItemFireStarter;
import mrunknown404.primalrework.items.util.ItemBase;
import mrunknown404.primalrework.items.util.ItemDamageableBase;
import mrunknown404.primalrework.items.util.ItemToolBase;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	public static final ToolMaterial MATERIAL = EnumHelper.addToolMaterial("MATERIAL", 0, 0, 0, 0, 0);
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	// ITEMS
	public static final Item PLANT_FIBER = new ItemBase("plant_fiber");
	public static final Item PLANT_ROPE = new ItemBase("plant_rope");
	public static final Item GRAVEL = new ItemBase("gravel");
	public static final Item FLINT_KNAPPED = new ItemBase("flint_knapped");
	public static final Item FLINT_POINT = new ItemBase("flint_point");
	public static final Item BONE_SHARD = new ItemBase("bone_shard");
	public static final Item FIRE_STARTER = new ItemFireStarter();
	public static final Item BARK = new ItemBase("bark");
	public static final Item MAGIC_DUST_RED = new ItemBase("magic_dust_red").setAmountOfTooltops(1);
	public static final Item MAGIC_DUST_GREEN = new ItemBase("magic_dust_green").setAmountOfTooltops(1);
	public static final Item MAGIC_DUST_BLUE = new ItemBase("magic_dust_blue").setAmountOfTooltops(1);
	public static final Item CLOTH = new ItemBase("cloth");
	public static final Item MORTAR_PESTLE = new ItemBase("mortar_pestle");
	public static final Item OAK_PLANK = new ItemBase("oak_plank");
	public static final Item SPRUCE_PLANK = new ItemBase("spruce_plank");
	public static final Item BIRCH_PLANK = new ItemBase("birch_plank");
	public static final Item JUNGLE_PLANK = new ItemBase("jungle_plank");
	public static final Item DARK_OAK_PLANK = new ItemBase("dark_oak_plank");
	public static final Item ACACIA_PLANK = new ItemBase("acacia_plank");
	public static final Item SALT = new ItemBase("salt");
	public static final Item ANIMAL_PELT = new ItemBase("animal_pelt");
	public static final Item WET_HIDE = new ItemBase("wet_hide");
	public static final Item RAW_HIDE = new ItemChangeWithWater("raw_hide", WET_HIDE);
	public static final Item CLEANED_HIDE = new ItemBase("cleaned_hide");
	public static final Item SALTED_HIDE = new ItemBase("salted_hide");
	public static final Item DRIED_HIDE = new ItemBase("dried_hide");
	public static final Item WET_TANNED_HIDE = new ItemBase("wet_tanned_hide");
	public static final Item DRY_TANNED_HIDE = new ItemChangeWithWater("dry_tanned_hide", WET_TANNED_HIDE);
	
	//TODO add clay
	
	// ITEMS WITH DURABILITY
	public static final Item PLANT_MESH = new ItemDamageableBase("plant_mesh", EnumToolMaterial.flint);
	public static final Item STRING_MESH = new ItemDamageableBase("string_mesh", EnumToolMaterial.copper);
	public static final Item FLINT_CRAFTING_HAMMER = new ItemDamageableBase("flint_crafting_hammer", EnumToolMaterial.flint);
	
	// TOOL HEADS
	public static final Item STONE_PICKAXE_HEAD = new ItemBase("stone_pickaxe_head", 1);
	public static final Item STONE_AXE_HEAD = new ItemBase("stone_axe_head", 1);
	public static final Item STONE_SHOVEL_HEAD = new ItemBase("stone_shovel_head", 1);
	public static final Item STONE_SWORD_HEAD = new ItemBase("stone_sword_head", 1);
	public static final Item STONE_KNIFE_HEAD = new ItemBase("stone_knife_head", 1);
	public static final Item STONE_HOE_HEAD = new ItemBase("stone_hoe_head", 1);
	
	// TOOLS
	public static final Item FLINT_PICKAXE = new ItemToolBase("flint_pickaxe", EnumToolType.pickaxe, EnumToolMaterial.flint);
	public static final Item FLINT_AXE = new ItemToolBase("flint_axe", EnumToolType.axe, EnumToolMaterial.flint);
	public static final Item FLINT_SHOVEL = new ItemToolBase("flint_shovel", EnumToolType.shovel, EnumToolMaterial.flint);
	public static final Item FLINT_KNIFE = new ItemToolBase("flint_knife", EnumToolType.knife, EnumToolMaterial.flint);
	
	public static final Item BONE_KNIFE = new ItemToolBase("bone_knife", EnumToolType.knife, EnumToolMaterial.bone);
	
	public static final Item STONE_KNIFE = new ItemToolBase("stone_knife", EnumToolType.knife, EnumToolMaterial.stone);
	
	// VANILLA REPLACEMENTS
	public static final Item DIAMOND_AXE = new ItemToolBase("diamond_axe", EnumToolType.axe, EnumToolMaterial.diamond).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item IRON_AXE = new ItemToolBase("iron_axe", EnumToolType.axe, EnumToolMaterial.iron).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item STONE_AXE = new ItemToolBase("stone_axe", EnumToolType.axe, EnumToolMaterial.stone).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item GOLDEN_AXE = new ItemToolBase("golden_axe", EnumToolType.axe, EnumToolMaterial.gold).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item WOODEN_AXE = new ItemToolBase("wooden_axe", EnumToolType.axe, EnumToolMaterial.wood).setCreativeTab(CreativeTabs.TOOLS);
	
	public static final Item DIAMOND_HOE = new ItemToolBase("diamond_hoe", EnumToolType.hoe, EnumToolMaterial.diamond).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item IRON_HOE = new ItemToolBase("iron_hoe", EnumToolType.hoe, EnumToolMaterial.iron).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item STONE_HOE = new ItemToolBase("stone_hoe", EnumToolType.hoe, EnumToolMaterial.stone).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item GOLDEN_HOE = new ItemToolBase("golden_hoe", EnumToolType.hoe, EnumToolMaterial.gold).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item WOODEN_HOE = new ItemToolBase("wooden_hoe", EnumToolType.hoe, EnumToolMaterial.wood).setCreativeTab(CreativeTabs.TOOLS);
	
	public static Item findBlock(Block block) {
		for (Item item : ITEMS) {
			if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().getUnlocalizedName().equalsIgnoreCase(block.getUnlocalizedName())) {
				return item;
			}
		}
		
		return Item.getItemFromBlock(block);
	}
}
