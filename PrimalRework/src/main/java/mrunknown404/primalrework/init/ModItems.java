package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import mrunknown404.primalrework.items.ItemCastable;
import mrunknown404.primalrework.items.ItemChangeWithWater;
import mrunknown404.primalrework.items.ItemClayBucket;
import mrunknown404.primalrework.items.ItemClayBucketMilk;
import mrunknown404.primalrework.items.ItemClayVessel;
import mrunknown404.primalrework.items.ItemFireStarter;
import mrunknown404.primalrework.items.ItemOreNugget;
import mrunknown404.primalrework.items.util.ItemStaged;
import mrunknown404.primalrework.items.util.ItemDamageable;
import mrunknown404.primalrework.items.util.ItemStagedFood;
import mrunknown404.primalrework.items.util.ItemStagedTool;
import mrunknown404.primalrework.util.enums.EnumAlloy;
import mrunknown404.primalrework.util.enums.EnumOreValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModItems {
	public static final ToolMaterial MATERIAL = EnumHelper.addToolMaterial("MATERIAL", 0, 0, 0, 0, 0);
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final Map<Fluid, ItemClayBucket> CLAY_BUCKETS = new HashMap<Fluid, ItemClayBucket>();
	public static final Map<EnumToolType, ItemCastable> CASTABLES = new HashMap<EnumToolType, ItemCastable>();
	
	// TODO add copper ingots/tools
	
	//MISC
	public static final Item PLANT_FIBER = new ItemStaged("plant_fiber", EnumStage.stage0);
	public static final Item PLANT_ROPE = new ItemStaged("plant_rope", EnumStage.stage0);
	public static final Item GRAVEL = new ItemStaged("gravel", EnumStage.stage0);
	public static final Item FLINT_KNAPPED = new ItemStaged("flint_knapped", EnumStage.stage0);
	public static final Item FLINT_POINT = new ItemStaged("flint_point", EnumStage.stage0);
	public static final Item BONE_SHARD = new ItemStaged("bone_shard", EnumStage.stage0);
	public static final Item BARK = new ItemStaged("bark", EnumStage.stage0);
	public static final Item MAGIC_DUST_RED = new ItemStaged("magic_dust_red", EnumStage.stage2).setAmountOfTooltops(1);
	public static final Item MAGIC_DUST_GREEN = new ItemStaged("magic_dust_green", EnumStage.stage2).setAmountOfTooltops(1);
	public static final Item MAGIC_DUST_BLUE = new ItemStaged("magic_dust_blue", EnumStage.stage2).setAmountOfTooltops(1);
	public static final Item CLOTH = new ItemStaged("cloth", EnumStage.stage1);
	public static final Item OAK_PLANK = new ItemStaged("oak_plank", EnumStage.stage1);
	public static final Item SPRUCE_PLANK = new ItemStaged("spruce_plank", EnumStage.stage1);
	public static final Item BIRCH_PLANK = new ItemStaged("birch_plank", EnumStage.stage1);
	public static final Item JUNGLE_PLANK = new ItemStaged("jungle_plank", EnumStage.stage1);
	public static final Item DARK_OAK_PLANK = new ItemStaged("dark_oak_plank", EnumStage.stage1);
	public static final Item ACACIA_PLANK = new ItemStaged("acacia_plank", EnumStage.stage1);
	public static final Item SALT = new ItemStaged("salt", EnumStage.stage1);
	public static final Item ANIMAL_PELT = new ItemStaged("animal_pelt", EnumStage.stage0);
	public static final Item WET_HIDE = new ItemStaged("wet_hide", EnumStage.stage1);
	public static final Item RAW_HIDE = new ItemChangeWithWater("raw_hide", WET_HIDE, EnumStage.stage1).setAmountOfTooltops(1);
	public static final Item CLEANED_HIDE = new ItemStaged("cleaned_hide", EnumStage.stage1);
	public static final Item SALTED_HIDE = new ItemStaged("salted_hide", EnumStage.stage1);
	public static final Item DRIED_HIDE = new ItemStaged("dried_hide", EnumStage.stage1);
	public static final Item WET_TANNED_HIDE = new ItemStaged("wet_tanned_hide", EnumStage.stage1);
	public static final Item DRY_TANNED_HIDE = new ItemChangeWithWater("dry_tanned_hide", WET_TANNED_HIDE, EnumStage.stage1).setAmountOfTooltops(1);
	public static final Item ROPE = new ItemStaged("rope", EnumStage.stage1);
	public static final Item LEATHER_STRIP = new ItemStaged("leather_strip", EnumStage.stage1);
	public static final Item WHITE_DYE = new ItemStaged("white_dye", EnumStage.stage1);
	public static final Item BLUE_DYE = new ItemStaged("blue_dye", EnumStage.stage1);
	public static final Item BLACK_DYE = new ItemStaged("black_dye", EnumStage.stage1);
	public static final Item BROWN_DYE = new ItemStaged("brown_dye", EnumStage.stage1);
	public static final Item CLAY_VESSEL = new ItemClayVessel();
	
	//ORE NUGGETS
	public static final Item COPPER_NUGGET_POOR = new ItemOreNugget(EnumStage.stage2, EnumAlloy.copper, EnumOreValue.poor);
	public static final Item COPPER_NUGGET_LOW = new ItemOreNugget(EnumStage.stage2, EnumAlloy.copper, EnumOreValue.low);
	public static final Item COPPER_NUGGET_MEDIUM = new ItemOreNugget(EnumStage.stage2, EnumAlloy.copper, EnumOreValue.medium);
	public static final Item COPPER_NUGGET_GOOD = new ItemOreNugget(EnumStage.stage2, EnumAlloy.copper, EnumOreValue.good);
	public static final Item COPPER_NUGGET_HIGH = new ItemOreNugget(EnumStage.stage2, EnumAlloy.copper, EnumOreValue.high);
	public static final Item COPPER_NUGGET_PERFECT = new ItemOreNugget(EnumStage.stage2, EnumAlloy.copper, EnumOreValue.perfect);
	
	public static final Item TIN_NUGGET_POOR = new ItemOreNugget(EnumStage.stage2, EnumAlloy.tin, EnumOreValue.poor);
	public static final Item TIN_NUGGET_LOW = new ItemOreNugget(EnumStage.stage2, EnumAlloy.tin, EnumOreValue.low);
	public static final Item TIN_NUGGET_MEDIUM = new ItemOreNugget(EnumStage.stage2, EnumAlloy.tin, EnumOreValue.medium);
	public static final Item TIN_NUGGET_GOOD = new ItemOreNugget(EnumStage.stage2, EnumAlloy.tin, EnumOreValue.good);
	public static final Item TIN_NUGGET_HIGH = new ItemOreNugget(EnumStage.stage2, EnumAlloy.tin, EnumOreValue.high);
	public static final Item TIN_NUGGET_PERFECT = new ItemOreNugget(EnumStage.stage2, EnumAlloy.tin, EnumOreValue.perfect);
	
	//FOOD
	public static final Item DOUGH = new ItemStagedFood("dough", EnumStage.stage1, 1, 0.5f);
	public static final Item COOKED_CARROT = new ItemStagedFood("cooked_carrot", EnumStage.stage0, 5, 0.6f);
	public static final Item COOKED_BEETROOT = new ItemStagedFood("cooked_beetroot", EnumStage.stage0, 5, 0.6f);
	
	//TOOL HEADS
	public static final Item STONE_PICKAXE_HEAD = new ItemStaged("stone_pickaxe_head", 1, EnumStage.stage2);
	public static final Item STONE_AXE_HEAD = new ItemStaged("stone_axe_head", 1, EnumStage.stage2);
	public static final Item STONE_SHOVEL_HEAD = new ItemStaged("stone_shovel_head", 1, EnumStage.stage2);
	public static final Item STONE_SWORD_HEAD = new ItemStaged("stone_sword_head", 1, EnumStage.stage2);
	public static final Item STONE_KNIFE_HEAD = new ItemStaged("stone_knife_head", 1, EnumStage.stage2);
	public static final Item STONE_HOE_HEAD = new ItemStaged("stone_hoe_head", 1, EnumStage.stage2);
	
	//ITEMS WITH DURABILITY
	public static final Item PLANT_MESH = new ItemDamageable("plant_mesh", EnumToolMaterial.flint, EnumStage.stage0);
	public static final Item STRING_MESH = new ItemDamageable("string_mesh", EnumToolMaterial.copper, EnumStage.stage1);
	public static final Item FLINT_CRAFTING_HAMMER = new ItemDamageable("flint_crafting_hammer", EnumToolMaterial.flint, EnumStage.stage1);
	public static final Item MORTAR_PESTLE = new ItemDamageable("mortar_pestle", EnumToolMaterial.wood, EnumStage.stage1);
	public static final Item CLAY_BUCKET_EMPTY = new ItemClayBucket(null);
	public static final Item CLAY_BUCKET_MILK = new ItemClayBucketMilk();
	
	//TOOLS
	public static final Item FIRE_STARTER = new ItemFireStarter();
	
	public static final Item FLINT_PICKAXE = new ItemStagedTool("flint_pickaxe", EnumToolType.pickaxe, EnumToolMaterial.flint, EnumStage.stage1);
	public static final Item FLINT_AXE = new ItemStagedTool("flint_axe", EnumToolType.axe, EnumToolMaterial.flint, EnumStage.stage0);
	public static final Item FLINT_SHOVEL = new ItemStagedTool("flint_shovel", EnumToolType.shovel, EnumToolMaterial.flint, EnumStage.stage1);
	public static final Item FLINT_KNIFE = new ItemStagedTool("flint_knife", EnumToolType.knife, EnumToolMaterial.flint, EnumStage.stage0);
	public static final Item FLINT_HOE = new ItemStagedTool("flint_hoe", EnumToolType.hoe, EnumToolMaterial.flint, EnumStage.stage1);
	public static final Item FLINT_SAW = new ItemStagedTool("flint_saw", EnumToolType.saw, EnumToolMaterial.flint, EnumStage.stage1);
	public static final Item FLINT_SHEARS = new ItemStagedTool("flint_shears", EnumToolType.shears, EnumToolMaterial.flint, EnumStage.stage1);
	
	public static final Item BONE_KNIFE = new ItemStagedTool("bone_knife", EnumToolType.knife, EnumToolMaterial.bone, EnumStage.stage0);
	
	public static final Item STONE_KNIFE = new ItemStagedTool("stone_knife", EnumToolType.knife, EnumToolMaterial.stone, EnumStage.stage2);
	
	//VANILLA REPLACEMENTS
	public static final Item DIAMOND_AXE = new ItemStagedTool("minecraft", "diamond_axe", EnumToolType.axe, EnumToolMaterial.diamond, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item IRON_AXE = new ItemStagedTool("minecraft", "iron_axe", EnumToolType.axe, EnumToolMaterial.iron, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item STONE_AXE = new ItemStagedTool("minecraft", "stone_axe", EnumToolType.axe, EnumToolMaterial.stone, EnumStage.stage2).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item GOLDEN_AXE = new ItemStagedTool("minecraft", "golden_axe", EnumToolType.axe, EnumToolMaterial.gold, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item WOODEN_AXE = new ItemStagedTool("minecraft", "wooden_axe", EnumToolType.axe, EnumToolMaterial.wood, EnumStage.stage0).setCreativeTab(CreativeTabs.TOOLS);
	
	public static final Item DIAMOND_HOE = new ItemStagedTool("minecraft", "diamond_hoe", EnumToolType.hoe, EnumToolMaterial.diamond, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item IRON_HOE = new ItemStagedTool("minecraft", "iron_hoe", EnumToolType.hoe, EnumToolMaterial.iron, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item STONE_HOE = new ItemStagedTool("minecraft", "stone_hoe", EnumToolType.hoe, EnumToolMaterial.stone, EnumStage.stage2).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item GOLDEN_HOE = new ItemStagedTool("minecraft", "golden_hoe", EnumToolType.hoe, EnumToolMaterial.gold, EnumStage.do_later).setCreativeTab(CreativeTabs.TOOLS);
	public static final Item WOODEN_HOE = new ItemStagedTool("minecraft", "wooden_hoe", EnumToolType.hoe, EnumToolMaterial.wood, EnumStage.stage0).setCreativeTab(CreativeTabs.TOOLS);
	
	public static Item findBlock(Block block) {
		for (Item item : ITEMS) {
			if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().getUnlocalizedName().equalsIgnoreCase(block.getUnlocalizedName())) {
				return item;
			}
		}
		
		return Item.getItemFromBlock(block);
	}
	
	static {
		//CLAY_BUCKETS.put(FluidRegistry.LAVA, new ItemClayBucket(FluidRegistry.LAVA));
		CLAY_BUCKETS.put(FluidRegistry.WATER, new ItemClayBucket(FluidRegistry.WATER));
		
		for (EnumToolType type : EnumToolType.values()) {
			CASTABLES.put(type, new ItemCastable(type, EnumStage.stage3));
		}
	}
	
	public static ItemClayBucket getClayBucket(@Nullable Fluid f) {
		if (f == null) {
			return (ItemClayBucket) CLAY_BUCKET_EMPTY;
		}
		
		for (Entry<Fluid, ItemClayBucket> bucket : CLAY_BUCKETS.entrySet()) {
			if (bucket.getKey() == f) {
				return bucket.getValue();
			}
		}
		
		return null;
	}
}
