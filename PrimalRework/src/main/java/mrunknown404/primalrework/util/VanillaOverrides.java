package mrunknown404.primalrework.util;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.blocks.util.BlockSlabBase;
import mrunknown404.primalrework.blocks.util.IBlockBase;
import mrunknown404.primalrework.blocks.util.ISlabBase;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.items.util.IItemBase;
import mrunknown404.primalrework.items.util.ItemBase;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo.ItemDropInfo;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class VanillaOverrides {
	
	public static void overrideAll() {
		resetHarvestLevels();
		overrideMaterials();
		
		registerBlocks();
		registerItems();
		
		overrideTools();
	}
	
	private static void resetHarvestLevels() {
		for (Block block : Block.REGISTRY) {
			if (block instanceof IBlockBase) {
				HarvestHelper.setHarvestLevel(block, ((IBlockBase<BlockBase>) block).getHarvestInfo());
			} else if (block instanceof ISlabBase) {
				HarvestHelper.setHarvestLevel(block, ((ISlabBase<BlockSlabBase>) block).getHarvestInfo());
			} else {
				HarvestHelper.setHarvestLevel(block, EnumToolType.none, EnumToolMaterial.unbreakable);
			}
		}
		
		for (Item item : Item.REGISTRY) {
			if (item instanceof IItemBase) {
				HarvestHelper.setHarvestLevel(item, ((IItemBase<ItemBase>) item).getToolType(), ((IItemBase<ItemBase>) item).getHarvestLevel());
			} else {
				HarvestHelper.setHarvestLevel(item, EnumToolType.none, EnumToolMaterial.hand);
			}
		}
	}
	
	private static void overrideMaterials() {
		ReflectionHelper.setPrivateValue(Material.class, Material.GRASS, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.GROUND, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.WOOD, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.ROCK, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.IRON, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.ANVIL, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.LEAVES, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.PLANTS, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.VINE, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.SPONGE, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.CLOTH, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.SAND, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.CIRCUITS, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.CARPET, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.GLASS, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.REDSTONE_LIGHT, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.TNT, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.CORAL, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.ICE, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.PACKED_ICE, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.SNOW, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.CRAFTED_SNOW, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.CACTUS, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.CLAY, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.GOURD, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.DRAGON_EGG, true, "requiresNoTool", "field_76241_J");
		ReflectionHelper.setPrivateValue(Material.class, Material.WEB, true, "requiresNoTool", "field_76241_J");
	}
	
	private static void overrideTools() {
		setupVanillaHoe((ItemHoe) Items.DIAMOND_HOE, EnumToolMaterial.diamond);
		setupVanillaHoe((ItemHoe) Items.IRON_HOE, EnumToolMaterial.iron);
		setupVanillaHoe((ItemHoe) Items.STONE_HOE, EnumToolMaterial.stone);
		setupVanillaHoe((ItemHoe) Items.WOODEN_HOE, EnumToolMaterial.wood);
		setupVanillaHoe((ItemHoe) Items.GOLDEN_HOE, EnumToolMaterial.gold);
		
		setupVanillaSword((ItemSword) Items.DIAMOND_SWORD, EnumToolMaterial.diamond);
		setupVanillaSword((ItemSword) Items.IRON_SWORD, EnumToolMaterial.iron);
		setupVanillaSword((ItemSword) Items.STONE_SWORD, EnumToolMaterial.stone);
		setupVanillaSword((ItemSword) Items.WOODEN_SWORD, EnumToolMaterial.wood);
		setupVanillaSword((ItemSword) Items.GOLDEN_SWORD, EnumToolMaterial.gold);
		
		setupVanillaTool((ItemTool) Items.DIAMOND_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.diamond);
		setupVanillaTool((ItemTool) Items.IRON_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.iron);
		setupVanillaTool((ItemTool) Items.STONE_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.stone);
		setupVanillaTool((ItemTool) Items.WOODEN_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.wood);
		setupVanillaTool((ItemTool) Items.GOLDEN_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.gold);
		
		setupVanillaTool((ItemTool) Items.DIAMOND_SHOVEL, EnumToolType.shovel, EnumToolMaterial.diamond);
		setupVanillaTool((ItemTool) Items.IRON_SHOVEL, EnumToolType.shovel, EnumToolMaterial.iron);
		setupVanillaTool((ItemTool) Items.STONE_SHOVEL, EnumToolType.shovel, EnumToolMaterial.stone);
		setupVanillaTool((ItemTool) Items.WOODEN_SHOVEL, EnumToolType.shovel, EnumToolMaterial.wood);
		setupVanillaTool((ItemTool) Items.GOLDEN_SHOVEL, EnumToolType.shovel, EnumToolMaterial.gold);
		
		setupVanillaTool((ItemTool) Items.DIAMOND_AXE, EnumToolType.axe, EnumToolMaterial.diamond);
		setupVanillaTool((ItemTool) Items.IRON_AXE, EnumToolType.axe, EnumToolMaterial.iron);
		setupVanillaTool((ItemTool) Items.STONE_AXE, EnumToolType.axe, EnumToolMaterial.stone);
		setupVanillaTool((ItemTool) Items.WOODEN_AXE, EnumToolType.axe, EnumToolMaterial.wood);
		setupVanillaTool((ItemTool) Items.GOLDEN_AXE, EnumToolType.axe, EnumToolMaterial.gold);
	}
	
	private static void setupVanillaHoe(ItemHoe item, EnumToolMaterial level) {
		ReflectionHelper.setPrivateValue(ItemHoe.class, item, 2, "speed", "field_185072_b");
		setupTool(item, level);
	}
	
	private static void setupVanillaSword(ItemSword item, EnumToolMaterial level) {
		ReflectionHelper.setPrivateValue(ItemSword.class, item, EnumToolType.sword.baseDamage + level.extraDamage, "attackDamage", "field_150934_a");
		setupTool(item, level);
	}
	
	private static void setupVanillaTool(ItemTool item, EnumToolType type, EnumToolMaterial level) {
		ReflectionHelper.setPrivateValue(ItemTool.class, item, type.swingSpeed, "attackSpeed", "field_185065_c");
		ReflectionHelper.setPrivateValue(ItemTool.class, item, type.baseDamage + level.extraDamage, "attackDamage", "field_77865_bY");
		setupTool(item, level);
	}
	
	private static void setupTool(Item item, EnumToolMaterial level) {
		item.setHarvestLevel("pickaxe", EnumToolMaterial.unbreakable.level);
		item.setHarvestLevel("axe", EnumToolMaterial.unbreakable.level);
		item.setHarvestLevel("shovel", EnumToolMaterial.unbreakable.level);
		item.setMaxDamage(level.durability);
		
		if (item instanceof ItemAxe || item instanceof ItemHoe) {
			item.setCreativeTab(null);
		}
	}
	
	private static final BlockHarvestInfo STONE_HARVEST_INFO = setupBlockInfo(Arrays.asList(
			new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.flint)),
			new HarvestDropInfo(EnumToolType.pickaxe, true,
					new ItemDropInfo(ModBlocks.ROCK, false, true, 100, 4, 0, 5, 0f),
					new ItemDropInfo(Blocks.COBBLESTONE, false, false, 10, 1, 0, 0, 0.1f)));
	private static final BlockHarvestInfo LEAVES_HARVEST_INFO = setupBlockInfo(Arrays.asList(
			new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand),
			new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.knife, EnumToolMaterial.flint)),
			new HarvestDropInfo(EnumToolType.none, false,
					new ItemDropInfo(Items.STICK, false, false, 10, 1, 0, 0, 0.1f)),
			new HarvestDropInfo(EnumToolType.knife, false,
					new ItemDropInfo(Items.STICK, false, false, 70, 1, 0, 1, 0.1f)));
	private static final BlockHarvestInfo TALLGRASS_HARVEST_INFO = setupBlockInfo(Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand),
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.knife, EnumToolMaterial.flint)),
				new HarvestDropInfo(EnumToolType.none, true,
						new ItemDropInfo(ModItems.PLANT_FIBER, false, false, 50, 1, 0, 0, 0f)),
				new HarvestDropInfo(EnumToolType.knife, false,
						new ItemDropInfo(ModItems.PLANT_FIBER, false, true, 100, 1, 1, 2, 0f)));
	
	private static final List<DoubleValue<EnumToolType, EnumToolMaterial>> PLANT_DV = Arrays.asList(
			new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand),
			new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.hoe, EnumToolMaterial.flint));
	
	private static BlockHarvestInfo setupBlockInfo(List<DoubleValue<EnumToolType, EnumToolMaterial>> info, HarvestDropInfo... drops) {
		return new BlockHarvestInfo(info).setDrops(drops);
	}
	
	private static void registerBlocks() {
		HarvestHelper.setHarvestLevel(Blocks.STONE,                         STONE_HARVEST_INFO);
		HarvestHelper.setHarvestLevel(Blocks.GRASS,                         EnumToolType.shovel,  EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.DIRT,                          EnumToolType.shovel,  EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.COBBLESTONE,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.PLANKS,                        EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.SAPLING,                       EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.BEDROCK,                       EnumToolType.none,    EnumToolMaterial.unbreakable);
		HarvestHelper.setHarvestLevel(Blocks.SAND,                          EnumToolType.shovel,  EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.GRAVEL, -1, Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.shovel, EnumToolMaterial.flint)),
				new HarvestDropInfo(EnumToolType.shovel, true,
						new ItemDropInfo(ModItems.GRAVEL, false, true, 100, 2, 1, 2, 0f)));
		HarvestHelper.setHarvestLevel(Blocks.GOLD_ORE,                      EnumToolType.pickaxe, EnumToolMaterial.bronze);
		HarvestHelper.setHarvestLevel(Blocks.IRON_ORE,                      EnumToolType.pickaxe, EnumToolMaterial.bronze);
		HarvestHelper.setHarvestLevel(Blocks.COAL_ORE, new BlockHarvestInfo(EnumToolType.pickaxe, EnumToolMaterial.stone).setDrops(
				new HarvestDropInfo(EnumToolType.pickaxe, true, new ItemDropInfo(Items.COAL, false, true, 100, 1, 0, 0, 0))));
		HarvestHelper.setHarvestLevel(Blocks.LOG,                           EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.LOG2,                          EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.LEAVES, 0.3f,                  LEAVES_HARVEST_INFO);
		HarvestHelper.setHarvestLevel(Blocks.LEAVES2, 0.3f,                 LEAVES_HARVEST_INFO);
		HarvestHelper.setHarvestLevel(Blocks.SPONGE,                        EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.GLASS,                         EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.LAPIS_ORE, new BlockHarvestInfo(EnumToolType.pickaxe, EnumToolMaterial.copper).setDrops(
				new HarvestDropInfo(EnumToolType.pickaxe, true, new ItemDropInfo(Items.DYE, 4, false, true, 100, 0, 4, 9, 0))));
		HarvestHelper.setHarvestLevel(Blocks.LAPIS_BLOCK,                   EnumToolType.pickaxe, EnumToolMaterial.copper);
		HarvestHelper.setHarvestLevel(Blocks.DISPENSER,                     EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.SANDSTONE,                     EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.NOTEBLOCK,                     EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.BED,                           EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.GOLDEN_RAIL,                   EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.DETECTOR_RAIL,                 EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.STICKY_PISTON,                 EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.WEB,                           EnumToolType.sword,   EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.TALLGRASS, -1,                 TALLGRASS_HARVEST_INFO);
		HarvestHelper.setHarvestLevel(Blocks.DEADBUSH,                      EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.PISTON,                        EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.PISTON_HEAD,                   EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.WOOL, Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.shears, EnumToolMaterial.hand),
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.knife, EnumToolMaterial.hand)));
		HarvestHelper.setHarvestLevel(Blocks.YELLOW_FLOWER,                 EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.RED_FLOWER,                    EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.BROWN_MUSHROOM,                EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.RED_MUSHROOM,                  EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.GOLD_BLOCK,                    EnumToolType.pickaxe, EnumToolMaterial.bronze);
		HarvestHelper.setHarvestLevel(Blocks.IRON_BLOCK,                    EnumToolType.pickaxe, EnumToolMaterial.bronze);
		HarvestHelper.setHarvestLevel(Blocks.DOUBLE_STONE_SLAB,             EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.STONE_SLAB,                    EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.BRICK_BLOCK,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.TNT,                           EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.BOOKSHELF,                     EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.MOSSY_COBBLESTONE,             EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.OBSIDIAN,                      EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.TORCH,                         EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.MOB_SPAWNER,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.OAK_STAIRS,                    EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.CHEST,                         EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.REDSTONE_WIRE,                 EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.DIAMOND_ORE, new BlockHarvestInfo(EnumToolType.pickaxe, EnumToolMaterial.steel).setDrops(
				new HarvestDropInfo(EnumToolType.pickaxe, true, new ItemDropInfo(Items.DIAMOND, false, true, 100, 1, 0, 0, 0))));
		HarvestHelper.setHarvestLevel(Blocks.DIAMOND_BLOCK,                 EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.CRAFTING_TABLE,                EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.WHEAT, 0.3f, PLANT_DV,
				new HarvestDropInfo(EnumToolType.none, true,
						new ItemDropInfo(Items.WHEAT, false, false, 100, 1, 0, 0, 0f)),
				new HarvestDropInfo(EnumToolType.hoe, true,
						new ItemDropInfo(Items.WHEAT_SEEDS, false, false, 100, 1, 0, 1, 0.2f),
						new ItemDropInfo(Items.WHEAT, false, true, 100, 1, 0, 0, 0.2f)));
		HarvestHelper.setHarvestLevel(Blocks.FARMLAND,                      EnumToolType.shovel,  EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.FURNACE,                       EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.LIT_FURNACE,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.STANDING_SIGN,                 EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.OAK_DOOR,                      EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.SPRUCE_DOOR,                   EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.BIRCH_DOOR,                    EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.JUNGLE_DOOR,                   EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.ACACIA_DOOR,                   EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.DARK_OAK_DOOR,                 EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.LADDER,                        EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.RAIL,                          EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.STONE_STAIRS,                  EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.WALL_SIGN,                     EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.LEVER,                         EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.STONE_PRESSURE_PLATE,          EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.IRON_DOOR,                     EnumToolType.pickaxe, EnumToolMaterial.bronze);
		HarvestHelper.setHarvestLevel(Blocks.WOODEN_PRESSURE_PLATE,         EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.REDSTONE_ORE, new BlockHarvestInfo(EnumToolType.pickaxe, EnumToolMaterial.iron).setDrops(
				new HarvestDropInfo(EnumToolType.pickaxe, true, new ItemDropInfo(Items.REDSTONE, false, true, 100, 0, 4, 5, 0))));
		HarvestHelper.setHarvestLevel(Blocks.LIT_REDSTONE_ORE, new BlockHarvestInfo(EnumToolType.pickaxe, EnumToolMaterial.iron).setDrops(
				new HarvestDropInfo(EnumToolType.pickaxe, true, new ItemDropInfo(Items.REDSTONE, false, true, 100, 0, 4, 5, 0))));
		HarvestHelper.setHarvestLevel(Blocks.UNLIT_REDSTONE_TORCH,          EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.REDSTONE_TORCH,                EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.STONE_BUTTON,                  EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.SNOW_LAYER,                    EnumToolType.shovel,  EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.ICE,                           EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.SNOW,                          EnumToolType.shovel,  EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.CACTUS,                        EnumToolType.shears,  EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.CLAY,                          EnumToolType.shovel,  EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.REEDS, 0.3f,                   EnumToolType.hoe,     EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.JUKEBOX,                       EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.OAK_FENCE,                     EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.SPRUCE_FENCE,                  EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.BIRCH_FENCE,                   EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.JUNGLE_FENCE,                  EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.DARK_OAK_FENCE,                EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.ACACIA_FENCE,                  EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.PUMPKIN,                       EnumToolType.hoe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.NETHERRACK,                    EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.SOUL_SAND,                     EnumToolType.shovel,  EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.GLOWSTONE,                     EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.LIT_PUMPKIN,                   EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.CAKE,                          EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.UNPOWERED_REPEATER,            EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.POWERED_REPEATER,              EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.TRAPDOOR,                      EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.MONSTER_EGG,                   STONE_HARVEST_INFO);
		HarvestHelper.setHarvestLevel(Blocks.STONEBRICK,                    EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.BROWN_MUSHROOM_BLOCK,          EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.RED_MUSHROOM_BLOCK,            EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.IRON_BARS,                     EnumToolType.pickaxe, EnumToolMaterial.bronze);
		HarvestHelper.setHarvestLevel(Blocks.GLASS_PANE,                    EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.MELON_BLOCK,                   EnumToolType.hoe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.PUMPKIN_STEM,                  EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.MELON_STEM,                    EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.VINE,                          EnumToolType.axe,     EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.OAK_FENCE_GATE,                EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.SPRUCE_FENCE_GATE,             EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.BIRCH_FENCE_GATE,              EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.JUNGLE_FENCE_GATE,             EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.DARK_OAK_FENCE_GATE,           EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.ACACIA_FENCE_GATE,             EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.BRICK_STAIRS,                  EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.STONE_BRICK_STAIRS,            EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.MYCELIUM,                      EnumToolType.shovel,  EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.WATERLILY,                     EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.NETHER_BRICK,                  EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.NETHER_BRICK_FENCE,            EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.NETHER_BRICK_STAIRS,           EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.NETHER_WART, 0.3f,             EnumToolType.hoe,     EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.ENCHANTING_TABLE,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.BREWING_STAND,                 EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.CAULDRON,                      EnumToolType.pickaxe, EnumToolMaterial.bronze);
		HarvestHelper.setHarvestLevel(Blocks.END_STONE,                     EnumToolType.pickaxe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Blocks.REDSTONE_LAMP,                 EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.LIT_REDSTONE_LAMP,             EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.DOUBLE_WOODEN_SLAB,            EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.WOODEN_SLAB,                   EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.COCOA, 0.3f,                   EnumToolType.hoe,     EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.SANDSTONE_STAIRS,              EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.EMERALD_ORE, new BlockHarvestInfo(EnumToolType.pickaxe, EnumToolMaterial.steel).setDrops(
				new HarvestDropInfo(EnumToolType.pickaxe, true, new ItemDropInfo(Items.EMERALD, false, true, 100, 1, 0, 0, 0))));
		HarvestHelper.setHarvestLevel(Blocks.ENDER_CHEST,                   EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.TRIPWIRE_HOOK,                 EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.TRIPWIRE,                      EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.EMERALD_BLOCK,                 EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.SPRUCE_STAIRS,                 EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.BIRCH_STAIRS,                  EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.JUNGLE_STAIRS,                 EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.BEACON,                        EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.COBBLESTONE_WALL,              EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.FLOWER_POT,                    EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.CARROTS, 0.3f, PLANT_DV,
				new HarvestDropInfo(EnumToolType.none, true,
						new ItemDropInfo(Items.CARROT, false, false, 100, 1, 0, 0, 0f)),
				new HarvestDropInfo(EnumToolType.hoe, true,
						new ItemDropInfo(Items.CARROT, false, true, 100, 1, 0, 1, 0.2f)));
		HarvestHelper.setHarvestLevel(Blocks.POTATOES, 0.3f, PLANT_DV,
				new HarvestDropInfo(EnumToolType.none, true,
						new ItemDropInfo(Items.POTATO, false, false, 100, 1, 0, 0, 0f)),
				new HarvestDropInfo(EnumToolType.hoe, true,
						new ItemDropInfo(Items.POTATO, false, true, 100, 1, 0, 1, 0.2f)));
		HarvestHelper.setHarvestLevel(Blocks.WOODEN_BUTTON,                 EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.SKULL,                         EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.ANVIL,                         EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.TRAPPED_CHEST,                 EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.UNPOWERED_COMPARATOR,          EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.POWERED_COMPARATOR,            EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.DAYLIGHT_DETECTOR,             EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.DAYLIGHT_DETECTOR_INVERTED,    EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.REDSTONE_BLOCK,                EnumToolType.pickaxe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Blocks.QUARTZ_ORE, new BlockHarvestInfo(EnumToolType.pickaxe, EnumToolMaterial.stone).setDrops(
				new HarvestDropInfo(EnumToolType.pickaxe, true, new ItemDropInfo(Items.QUARTZ, false, true, 100, 1, 0, 0, 0))));
		HarvestHelper.setHarvestLevel(Blocks.HOPPER,                        EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.QUARTZ_BLOCK,                  EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.QUARTZ_STAIRS,                 EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.ACTIVATOR_RAIL,                EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.DROPPER,                       EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.STAINED_HARDENED_CLAY,         EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.IRON_TRAPDOOR,                 EnumToolType.pickaxe, EnumToolMaterial.bronze);
		HarvestHelper.setHarvestLevel(Blocks.HAY_BLOCK,                     EnumToolType.axe,     EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.CARPET,                        EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.HARDENED_CLAY,                 EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.COAL_BLOCK,                    EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.PACKED_ICE,                    EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.ACACIA_STAIRS,                 EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.DARK_OAK_STAIRS,               EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.SLIME_BLOCK,                   EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.DOUBLE_PLANT,                  EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.STAINED_GLASS,                 EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.STAINED_GLASS_PANE,            EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.PRISMARINE,                    EnumToolType.pickaxe, EnumToolMaterial.copper);
		HarvestHelper.setHarvestLevel(Blocks.SEA_LANTERN,                   EnumToolType.pickaxe, EnumToolMaterial.copper);
		HarvestHelper.setHarvestLevel(Blocks.STANDING_BANNER,               EnumToolType.axe,     EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.WALL_BANNER,                   EnumToolType.axe,     EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.RED_SANDSTONE,                 EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.RED_SANDSTONE_STAIRS,          EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.DOUBLE_STONE_SLAB2,            EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.STONE_SLAB2,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.END_ROD,                       EnumToolType.pickaxe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Blocks.CHORUS_PLANT,                  EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.CHORUS_FLOWER,                 EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.PURPUR_BLOCK,                  EnumToolType.pickaxe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Blocks.PURPUR_PILLAR,                 EnumToolType.pickaxe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Blocks.PURPUR_STAIRS,                 EnumToolType.pickaxe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Blocks.PURPUR_DOUBLE_SLAB,            EnumToolType.pickaxe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Blocks.PURPUR_SLAB,                   EnumToolType.pickaxe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Blocks.END_BRICKS,                    EnumToolType.pickaxe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Blocks.BEETROOTS, 0.3f, PLANT_DV,
				new HarvestDropInfo(EnumToolType.none, true,
						new ItemDropInfo(Items.BEETROOT, false, false, 100, 1, 0, 0, 0f)),
				new HarvestDropInfo(EnumToolType.hoe, true,
						new ItemDropInfo(Items.BEETROOT_SEEDS, false, false, 100, 1, 0, 1, 0.2f),
						new ItemDropInfo(Items.BEETROOT, false, true, 100, 1, 0, 0, 0.2f)));
		HarvestHelper.setHarvestLevel(Blocks.GRASS_PATH,                    EnumToolType.shovel,  EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.FROSTED_ICE,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.MAGMA,                         EnumToolType.pickaxe, EnumToolMaterial.bronze);
		HarvestHelper.setHarvestLevel(Blocks.NETHER_WART_BLOCK,             EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.RED_NETHER_BRICK,              EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.BONE_BLOCK,                    EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.OBSERVER,                      EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.WHITE_SHULKER_BOX,             EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.ORANGE_SHULKER_BOX,            EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.MAGENTA_SHULKER_BOX,           EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.LIGHT_BLUE_SHULKER_BOX,        EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.YELLOW_SHULKER_BOX,            EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.LIME_SHULKER_BOX,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.PINK_SHULKER_BOX,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.GRAY_SHULKER_BOX,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.SILVER_SHULKER_BOX,            EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.CYAN_SHULKER_BOX,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.PURPLE_SHULKER_BOX,            EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.BLUE_SHULKER_BOX,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.BROWN_SHULKER_BOX,             EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.GREEN_SHULKER_BOX,             EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.RED_SHULKER_BOX,               EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.BLACK_SHULKER_BOX,             EnumToolType.pickaxe, EnumToolMaterial.steel);
		HarvestHelper.setHarvestLevel(Blocks.WHITE_GLAZED_TERRACOTTA,       EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.ORANGE_GLAZED_TERRACOTTA,      EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.MAGENTA_GLAZED_TERRACOTTA,     EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA,  EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.YELLOW_GLAZED_TERRACOTTA,      EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.LIME_GLAZED_TERRACOTTA,        EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.PINK_GLAZED_TERRACOTTA,        EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.GRAY_GLAZED_TERRACOTTA,        EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.SILVER_GLAZED_TERRACOTTA,      EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.CYAN_GLAZED_TERRACOTTA,        EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.PURPLE_GLAZED_TERRACOTTA,      EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.BLUE_GLAZED_TERRACOTTA,        EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.BROWN_GLAZED_TERRACOTTA,       EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.GREEN_GLAZED_TERRACOTTA,       EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.RED_GLAZED_TERRACOTTA,         EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.BLACK_GLAZED_TERRACOTTA,       EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Blocks.CONCRETE,                      EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.CONCRETE_POWDER,               EnumToolType.shovel,  EnumToolMaterial.flint);
	}
	
	private static void registerItems() {
		HarvestHelper.setHarvestLevel(Items.SHEARS, EnumToolType.shears, EnumToolMaterial.iron);
		
		HarvestHelper.setHarvestLevel(Items.DIAMOND_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.diamond);
		HarvestHelper.setHarvestLevel(Items.GOLDEN_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.gold);
		HarvestHelper.setHarvestLevel(Items.IRON_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Items.STONE_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Items.WOODEN_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.wood);
		
		HarvestHelper.setHarvestLevel(Items.DIAMOND_SHOVEL, EnumToolType.shovel, EnumToolMaterial.diamond);
		HarvestHelper.setHarvestLevel(Items.GOLDEN_SHOVEL, EnumToolType.shovel, EnumToolMaterial.gold);
		HarvestHelper.setHarvestLevel(Items.IRON_SHOVEL, EnumToolType.shovel, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Items.STONE_SHOVEL, EnumToolType.shovel, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Items.WOODEN_SHOVEL, EnumToolType.shovel, EnumToolMaterial.wood);
		
		HarvestHelper.setHarvestLevel(Items.DIAMOND_AXE, EnumToolType.axe, EnumToolMaterial.diamond);
		HarvestHelper.setHarvestLevel(Items.GOLDEN_AXE, EnumToolType.axe, EnumToolMaterial.gold);
		HarvestHelper.setHarvestLevel(Items.IRON_AXE, EnumToolType.axe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Items.STONE_AXE, EnumToolType.axe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Items.WOODEN_AXE, EnumToolType.axe, EnumToolMaterial.wood);
		
		HarvestHelper.setHarvestLevel(Items.DIAMOND_SWORD, EnumToolType.sword, EnumToolMaterial.diamond);
		HarvestHelper.setHarvestLevel(Items.GOLDEN_SWORD, EnumToolType.sword, EnumToolMaterial.gold);
		HarvestHelper.setHarvestLevel(Items.IRON_SWORD, EnumToolType.sword, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Items.STONE_SWORD, EnumToolType.sword, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Items.WOODEN_SWORD, EnumToolType.sword, EnumToolMaterial.wood);
		
		HarvestHelper.setHarvestLevel(Items.DIAMOND_HOE, EnumToolType.hoe, EnumToolMaterial.diamond);
		HarvestHelper.setHarvestLevel(Items.GOLDEN_HOE, EnumToolType.hoe, EnumToolMaterial.gold);
		HarvestHelper.setHarvestLevel(Items.IRON_HOE, EnumToolType.hoe, EnumToolMaterial.iron);
		HarvestHelper.setHarvestLevel(Items.STONE_HOE, EnumToolType.hoe, EnumToolMaterial.stone);
		HarvestHelper.setHarvestLevel(Items.WOODEN_HOE, EnumToolType.hoe, EnumToolMaterial.wood);
	}
}
