package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.items.ItemChangeWithWater;
import mrunknown404.primalrework.items.ItemFireStarter;
import mrunknown404.primalrework.items.util.ItemBase;
import mrunknown404.primalrework.items.util.ItemDamageableBase;
import mrunknown404.primalrework.items.util.ItemFoodBase;
import mrunknown404.primalrework.items.util.ItemToolBase;
import mrunknown404.primalrework.util.enums.EnumStage;
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
	public static final Item PLANT_FIBER = new ItemBase("plant_fiber", EnumStage.stage0);
	public static final Item PLANT_ROPE = new ItemBase("plant_rope", EnumStage.stage0);
	public static final Item GRAVEL = new ItemBase("gravel", EnumStage.stage0);
	public static final Item FLINT_KNAPPED = new ItemBase("flint_knapped", EnumStage.stage0);
	public static final Item FLINT_POINT = new ItemBase("flint_point", EnumStage.stage0);
	public static final Item BONE_SHARD = new ItemBase("bone_shard", EnumStage.stage0);
	public static final Item FIRE_STARTER = new ItemFireStarter();
	public static final Item BARK = new ItemBase("bark", EnumStage.stage0);
	public static final Item MAGIC_DUST_RED = new ItemBase("magic_dust_red", EnumStage.stage2).setAmountOfTooltops(1);
	public static final Item MAGIC_DUST_GREEN = new ItemBase("magic_dust_green", EnumStage.stage2).setAmountOfTooltops(1);
	public static final Item MAGIC_DUST_BLUE = new ItemBase("magic_dust_blue", EnumStage.stage2).setAmountOfTooltops(1);
	public static final Item CLOTH = new ItemBase("cloth", EnumStage.stage1);
	public static final Item OAK_PLANK = new ItemBase("oak_plank", EnumStage.stage1);
	public static final Item SPRUCE_PLANK = new ItemBase("spruce_plank", EnumStage.stage1);
	public static final Item BIRCH_PLANK = new ItemBase("birch_plank", EnumStage.stage1);
	public static final Item JUNGLE_PLANK = new ItemBase("jungle_plank", EnumStage.stage1);
	public static final Item DARK_OAK_PLANK = new ItemBase("dark_oak_plank", EnumStage.stage1);
	public static final Item ACACIA_PLANK = new ItemBase("acacia_plank", EnumStage.stage1);
	public static final Item SALT = new ItemBase("salt", EnumStage.stage1);
	public static final Item ANIMAL_PELT = new ItemBase("animal_pelt", EnumStage.stage0);
	public static final Item WET_HIDE = new ItemBase("wet_hide", EnumStage.stage1);
	public static final Item RAW_HIDE = new ItemChangeWithWater("raw_hide", WET_HIDE, EnumStage.stage1).setAmountOfTooltops(1);
	public static final Item CLEANED_HIDE = new ItemBase("cleaned_hide", EnumStage.stage1);
	public static final Item SALTED_HIDE = new ItemBase("salted_hide", EnumStage.stage1);
	public static final Item DRIED_HIDE = new ItemBase("dried_hide", EnumStage.stage1);
	public static final Item WET_TANNED_HIDE = new ItemBase("wet_tanned_hide", EnumStage.stage1);
	public static final Item DRY_TANNED_HIDE = new ItemChangeWithWater("dry_tanned_hide", WET_TANNED_HIDE, EnumStage.stage1).setAmountOfTooltops(1);
	public static final Item ROPE = new ItemBase("rope", EnumStage.stage1);
	public static final Item LEATHER_STRIP = new ItemBase("leather_strip", EnumStage.stage1);
	
	//TODO add clay items, add shears
	
	// ITEMS WITH DURABILITY
	public static final Item PLANT_MESH = new ItemDamageableBase("plant_mesh", EnumToolMaterial.flint, EnumStage.stage0);
	public static final Item STRING_MESH = new ItemDamageableBase("string_mesh", EnumToolMaterial.copper, EnumStage.stage1);
	public static final Item FLINT_CRAFTING_HAMMER = new ItemDamageableBase("flint_crafting_hammer", EnumToolMaterial.flint, EnumStage.stage1);
	public static final Item MORTAR_PESTLE = new ItemDamageableBase("mortar_pestle", EnumToolMaterial.wood, EnumStage.stage1);
	
	// FOOD
	public static final Item DOUGH = new ItemFoodBase("dough", EnumStage.stage1, 1, 1f);
	public static final Item COOKED_CARROT = new ItemFoodBase("cooked_carrot", EnumStage.stage0, 5, 0.6f);
	public static final Item COOKED_BEETROOT = new ItemFoodBase("cooked_beetroot", EnumStage.stage0, 5, 0.6f);
	
	// TOOL HEADS
	public static final Item STONE_PICKAXE_HEAD = new ItemBase("stone_pickaxe_head", 1, EnumStage.stage2);
	public static final Item STONE_AXE_HEAD = new ItemBase("stone_axe_head", 1, EnumStage.stage2);
	public static final Item STONE_SHOVEL_HEAD = new ItemBase("stone_shovel_head", 1, EnumStage.stage2);
	public static final Item STONE_SWORD_HEAD = new ItemBase("stone_sword_head", 1, EnumStage.stage2);
	public static final Item STONE_KNIFE_HEAD = new ItemBase("stone_knife_head", 1, EnumStage.stage2);
	public static final Item STONE_HOE_HEAD = new ItemBase("stone_hoe_head", 1, EnumStage.stage2);
	public static final Item STONE_SAW_HEAD = new ItemBase("stone_saw_head", 1, EnumStage.stage2);
	
	// TOOLS
	public static final Item FLINT_PICKAXE = new ItemToolBase("flint_pickaxe", EnumToolType.pickaxe, EnumToolMaterial.flint, EnumStage.stage1);
	public static final Item FLINT_AXE = new ItemToolBase("flint_axe", EnumToolType.axe, EnumToolMaterial.flint, EnumStage.stage0);
	public static final Item FLINT_SHOVEL = new ItemToolBase("flint_shovel", EnumToolType.shovel, EnumToolMaterial.flint, EnumStage.stage1);
	public static final Item FLINT_KNIFE = new ItemToolBase("flint_knife", EnumToolType.knife, EnumToolMaterial.flint, EnumStage.stage0);
	public static final Item FLINT_HOE = new ItemToolBase("flint_hoe", EnumToolType.hoe, EnumToolMaterial.flint, EnumStage.stage1);
	public static final Item FLINT_SAW = new ItemToolBase("flint_saw", EnumToolType.saw, EnumToolMaterial.flint, EnumStage.stage1);
	
	public static final Item BONE_KNIFE = new ItemToolBase("bone_knife", EnumToolType.knife, EnumToolMaterial.bone, EnumStage.stage0);
	
	public static final Item STONE_KNIFE = new ItemToolBase("stone_knife", EnumToolType.knife, EnumToolMaterial.stone, EnumStage.stage2);
	public static final Item STONE_SAW = new ItemToolBase("stone_saw", EnumToolType.saw, EnumToolMaterial.stone, EnumStage.stage2);
	
	// VANILLA REPLACEMENTS
	public static final Item DIAMOND_AXE = new ItemToolBase("diamond_axe", EnumToolType.axe, EnumToolMaterial.diamond, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item IRON_AXE = new ItemToolBase("iron_axe", EnumToolType.axe, EnumToolMaterial.iron, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item STONE_AXE = new ItemToolBase("stone_axe", EnumToolType.axe, EnumToolMaterial.stone, EnumStage.stage2).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item GOLDEN_AXE = new ItemToolBase("golden_axe", EnumToolType.axe, EnumToolMaterial.gold, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item WOODEN_AXE = new ItemToolBase("wooden_axe", EnumToolType.axe, EnumToolMaterial.wood, EnumStage.stage0).setCreativeTab(CreativeTabs.TOOLS);
	
	public static final Item DIAMOND_HOE = new ItemToolBase("diamond_hoe", EnumToolType.hoe, EnumToolMaterial.diamond, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item IRON_HOE = new ItemToolBase("iron_hoe", EnumToolType.hoe, EnumToolMaterial.iron, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item STONE_HOE = new ItemToolBase("stone_hoe", EnumToolType.hoe, EnumToolMaterial.stone, EnumStage.stage2).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item GOLDEN_HOE = new ItemToolBase("golden_hoe", EnumToolType.hoe, EnumToolMaterial.gold, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item WOODEN_HOE = new ItemToolBase("wooden_hoe", EnumToolType.hoe, EnumToolMaterial.wood, EnumStage.stage0).setCreativeTab(CreativeTabs.TOOLS);
	
	public static Item findBlock(Block block) {
		for (Item item : ITEMS) {
			if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().getUnlocalizedName().equalsIgnoreCase(block.getUnlocalizedName())) {
				return item;
			}
		}
		
		return Item.getItemFromBlock(block);
	}
}
