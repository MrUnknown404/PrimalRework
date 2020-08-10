package mrunknown404.primalrework.util;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.blocks.util.BlockStaged;
import mrunknown404.primalrework.blocks.util.BlockStagedSlab;
import mrunknown404.primalrework.blocks.util.IBlockStaged;
import mrunknown404.primalrework.blocks.util.ISlabStaged;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.items.util.IItemStaged;
import mrunknown404.primalrework.items.util.ItemStaged;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo.ItemDropInfo;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import mrunknown404.primalrework.util.helpers.StageHelper;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class VanillaOverrides {
	
	public static void overrideAll() {
		resetHarvestLevels();
		
		registerBlocks();
		registerItems();
		
		overrideMaterials();
		overrideTools();
		
		setupItemStages();
	}
	
	private static void resetHarvestLevels() {
		for (Block block : Block.REGISTRY) {
			if (block instanceof IBlockStaged) {
				HarvestHelper.setHarvestLevel(block, ((IBlockStaged<BlockStaged>) block).getHarvestInfo());
			} else if (block instanceof ISlabStaged) {
				HarvestHelper.setHarvestLevel(block, ((ISlabStaged<BlockStagedSlab>) block).getHarvestInfo());
			} else {
				HarvestHelper.setHarvestLevel(block, EnumToolType.none, EnumToolMaterial.unbreakable);
			}
		}
		
		for (Item item : Item.REGISTRY) {
			if (item instanceof IItemStaged) {
				HarvestHelper.setHarvestLevel(item, ((IItemStaged<ItemStaged>) item).getToolType(), ((IItemStaged<ItemStaged>) item).getHarvestLevel());
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
		HarvestHelper.setHarvestLevel(Blocks.DIRT,                          EnumToolType.shovel,  EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.COBBLESTONE,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.PLANKS,                        EnumToolType.axe,     EnumToolMaterial.flint);
		HarvestHelper.setHarvestLevel(Blocks.SAPLING,                       EnumToolType.none,    EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.BEDROCK,                       EnumToolType.none,    EnumToolMaterial.unbreakable);
		HarvestHelper.setHarvestLevel(Blocks.SAND,                          EnumToolType.shovel,  EnumToolMaterial.hand);
		HarvestHelper.setHarvestLevel(Blocks.GRAVEL, -1, Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.shovel, EnumToolMaterial.hand)),
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
	
	private static void setupItemStages() {
		for (Item item : ModItems.ITEMS) {
			if (item instanceof ItemBlock) {
				StageHelper.addStagedItem(((IBlockStaged<BlockStaged>) ((ItemBlock) item).getBlock()).getStage(), item);
			} else {
				StageHelper.addStagedItem(((IItemStaged<ItemStaged>) item).getStage(), item);
			}
		}
		
		for (int i = 0; i < 3; i++) {
			StageHelper.addStagedItem(EnumStage.stage0, Blocks.DIRT, i);
			StageHelper.addStagedItem(EnumStage.stage0, Blocks.TALLGRASS, i);
			StageHelper.addStagedItem(EnumStage.stage1, Blocks.SANDSTONE, i);
			StageHelper.addStagedItem(EnumStage.stage1, Blocks.RED_SANDSTONE, i);
		}
		for (int i = 0; i < 4; i++) {
			if (i <= 1) {
				StageHelper.addStagedItem(EnumStage.stage0, Blocks.LOG2, i);
				StageHelper.addStagedItem(EnumStage.stage0, Blocks.LEAVES2, i);
			}
			StageHelper.addStagedItem(EnumStage.stage0, Blocks.LOG, i);
			StageHelper.addStagedItem(EnumStage.stage0, Blocks.LEAVES, i);
			StageHelper.addStagedItem(EnumStage.stage0, Items.FISH, i);
		}
		for (int i = 0; i < 6; i++) {
			StageHelper.addStagedItem(EnumStage.stage1, Blocks.PLANKS, i);
			StageHelper.addStagedItem(EnumStage.stage1, Blocks.WOODEN_SLAB, i);
			StageHelper.addStagedItem(EnumStage.stage0, Blocks.SAPLING, i);
			StageHelper.addStagedItem(EnumStage.stage0, Blocks.DOUBLE_PLANT, i);
			StageHelper.addStagedItem(EnumStage.do_later, Items.SKULL, i);
			StageHelper.addStagedItem(EnumStage.no_show, Blocks.MONSTER_EGG, i);
		}
		for (int i = 0; i < 7; i++) {
			StageHelper.addStagedItem(EnumStage.stage0, Blocks.STONE, i);
		}
		for (int i = 0; i < 9; i++) {
			StageHelper.addStagedItem(EnumStage.stage0, Blocks.RED_FLOWER, i);
		}
		for (int i = 0; i < 16; i++) {
			StageHelper.addStagedItem(EnumStage.stage1, Items.DYE, i);
			StageHelper.addStagedItem(EnumStage.stage1, Blocks.WOOL, i);
			StageHelper.addStagedItem(EnumStage.stage1, Blocks.CARPET, i);
			StageHelper.addStagedItem(EnumStage.do_later, Blocks.STAINED_HARDENED_CLAY, i);
			StageHelper.addStagedItem(EnumStage.do_later, Blocks.STAINED_GLASS, i);
			StageHelper.addStagedItem(EnumStage.do_later, Blocks.STAINED_GLASS_PANE, i);
			StageHelper.addStagedItem(EnumStage.stage1, Items.BED, i);
			StageHelper.addStagedItem(EnumStage.do_later, Items.BANNER, i);
			StageHelper.addStagedItem(EnumStage.do_later, Blocks.CONCRETE, i);
			StageHelper.addStagedItem(EnumStage.do_later, Blocks.CONCRETE_POWDER, i);
		}
		
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.GRASS);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.COBBLESTONE);
		StageHelper.addStagedItem(EnumStage.no_show, Blocks.BEDROCK);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.SAND, 0);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.SAND, 1);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.GRAVEL);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GOLD_ORE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.IRON_ORE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.COAL_ORE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SPONGE, 0);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SPONGE, 1);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GLASS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.LAPIS_ORE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.LAPIS_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.DISPENSER);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.NOTEBLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GOLDEN_RAIL);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.DETECTOR_RAIL);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STICKY_PISTON);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.WEB);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.DEADBUSH);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PISTON);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.YELLOW_FLOWER);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.BROWN_MUSHROOM);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.RED_MUSHROOM);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GOLD_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.IRON_BLOCK);
		StageHelper.addStagedItem(EnumStage.stage1, Blocks.STONE_SLAB, 0);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 1);
		StageHelper.addStagedItem(EnumStage.no_show, Blocks.STONE_SLAB, 2);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 3);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 4);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 5);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 6);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 7);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BRICK_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.TNT);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BOOKSHELF);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.MOSSY_COBBLESTONE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.OBSIDIAN);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.TORCH);
		StageHelper.addStagedItem(EnumStage.no_show, Blocks.MOB_SPAWNER);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.OAK_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.CHEST);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.DIAMOND_ORE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.DIAMOND_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.CRAFTING_TABLE);
		StageHelper.addStagedItem(EnumStage.stage1, Blocks.FARMLAND);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.FURNACE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.LADDER);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.RAIL);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONE_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.LEVER);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONE_PRESSURE_PLATE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.WOODEN_PRESSURE_PLATE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.REDSTONE_ORE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.REDSTONE_TORCH);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONE_BUTTON);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.SNOW_LAYER);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.ICE);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.SNOW);
		StageHelper.addStagedItem(EnumStage.stage1, Blocks.CACTUS);
		StageHelper.addStagedItem(EnumStage.stage1, Blocks.CLAY);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.JUKEBOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.OAK_FENCE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SPRUCE_FENCE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BIRCH_FENCE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.JUNGLE_FENCE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.DARK_OAK_FENCE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ACACIA_FENCE);
		StageHelper.addStagedItem(EnumStage.stage1, Blocks.PUMPKIN);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.NETHERRACK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SOUL_SAND);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GLOWSTONE);
		StageHelper.addStagedItem(EnumStage.stage1, Blocks.LIT_PUMPKIN);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.TRAPDOOR);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONEBRICK, 0);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONEBRICK, 1);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONEBRICK, 2);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONEBRICK, 3);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.BROWN_MUSHROOM_BLOCK);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.RED_MUSHROOM_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.IRON_BARS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GLASS_PANE);
		StageHelper.addStagedItem(EnumStage.stage1, Blocks.MELON_BLOCK);
		StageHelper.addStagedItem(EnumStage.stage1, Blocks.VINE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.OAK_FENCE_GATE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SPRUCE_FENCE_GATE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BIRCH_FENCE_GATE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.JUNGLE_FENCE_GATE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.DARK_OAK_FENCE_GATE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ACACIA_FENCE_GATE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BRICK_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.STONE_BRICK_STAIRS);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.MYCELIUM);
		StageHelper.addStagedItem(EnumStage.stage0, Blocks.WATERLILY);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.NETHER_BRICK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.NETHER_BRICK_FENCE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.NETHER_BRICK_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ENCHANTING_TABLE);
		StageHelper.addStagedItem(EnumStage.no_show, Blocks.END_PORTAL_FRAME);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.END_STONE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.DRAGON_EGG);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.REDSTONE_LAMP);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SANDSTONE_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.EMERALD_ORE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ENDER_CHEST);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.TRIPWIRE_HOOK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.EMERALD_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SPRUCE_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BIRCH_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.JUNGLE_STAIRS);
		StageHelper.addStagedItem(EnumStage.no_show, Blocks.COMMAND_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BEACON);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.COBBLESTONE_WALL, 0);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.COBBLESTONE_WALL, 1);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.WOODEN_BUTTON);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ANVIL, 0);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ANVIL, 1);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ANVIL, 2);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.TRAPPED_CHEST);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.DAYLIGHT_DETECTOR);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.REDSTONE_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.QUARTZ_ORE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.HOPPER);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.QUARTZ_BLOCK, 0);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.QUARTZ_BLOCK, 1);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.QUARTZ_BLOCK, 2);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.QUARTZ_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ACTIVATOR_RAIL);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.DROPPER);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BARRIER);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.IRON_TRAPDOOR);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.HAY_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.HARDENED_CLAY);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.COAL_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PACKED_ICE);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ACACIA_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.DARK_OAK_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SLIME_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PRISMARINE, 0);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PRISMARINE, 1);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PRISMARINE, 2);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SEA_LANTERN);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.RED_SANDSTONE_STAIRS);
		StageHelper.addStagedItem(EnumStage.stage1, Blocks.STONE_SLAB2);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.END_ROD);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.CHORUS_PLANT);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.CHORUS_FLOWER);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PURPUR_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PURPUR_PILLAR);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PURPUR_STAIRS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PURPUR_SLAB);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.END_BRICKS);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GRASS_PATH);
		StageHelper.addStagedItem(EnumStage.no_show, Blocks.REPEATING_COMMAND_BLOCK);
		StageHelper.addStagedItem(EnumStage.no_show, Blocks.CHAIN_COMMAND_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.MAGMA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.NETHER_WART_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.RED_NETHER_BRICK);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BONE_BLOCK);
		StageHelper.addStagedItem(EnumStage.no_show, Blocks.STRUCTURE_VOID);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.OBSERVER);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.WHITE_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ORANGE_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.MAGENTA_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.LIGHT_BLUE_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.YELLOW_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.LIME_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PINK_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GRAY_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SILVER_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.CYAN_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PURPLE_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BLUE_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BROWN_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GREEN_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.RED_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BLACK_SHULKER_BOX);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.WHITE_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.ORANGE_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.MAGENTA_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.YELLOW_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.LIME_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PINK_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GRAY_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.SILVER_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.CYAN_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.PURPLE_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BLUE_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BROWN_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.GREEN_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.RED_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.do_later, Blocks.BLACK_GLAZED_TERRACOTTA);
		StageHelper.addStagedItem(EnumStage.no_show, Blocks.STRUCTURE_BLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_SHOVEL);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_PICKAXE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_AXE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.FLINT_AND_STEEL);
		StageHelper.addStagedItem(EnumStage.stage0, Items.APPLE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.BOW);
		StageHelper.addStagedItem(EnumStage.do_later, Items.ARROW);
		StageHelper.addStagedItem(EnumStage.do_later, Items.SPECTRAL_ARROW);
		StageHelper.addStagedItem(EnumStage.do_later, Items.COAL, 0);
		StageHelper.addStagedItem(EnumStage.stage2, Items.COAL, 1);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_INGOT);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLD_INGOT);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_SWORD);
		StageHelper.addStagedItem(EnumStage.stage1, Items.WOODEN_SWORD);
		StageHelper.addStagedItem(EnumStage.stage1, Items.WOODEN_SHOVEL);
		StageHelper.addStagedItem(EnumStage.stage1, Items.WOODEN_PICKAXE);
		StageHelper.addStagedItem(EnumStage.stage1, Items.WOODEN_AXE);
		StageHelper.addStagedItem(EnumStage.stage2, Items.STONE_SWORD);
		StageHelper.addStagedItem(EnumStage.stage2, Items.STONE_SHOVEL);
		StageHelper.addStagedItem(EnumStage.stage2, Items.STONE_PICKAXE);
		StageHelper.addStagedItem(EnumStage.stage2, Items.STONE_AXE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND_SWORD);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND_SHOVEL);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND_PICKAXE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND_AXE);
		StageHelper.addStagedItem(EnumStage.stage0, Items.STICK);
		StageHelper.addStagedItem(EnumStage.stage1, Items.BOWL);
		StageHelper.addStagedItem(EnumStage.do_later, Items.MUSHROOM_STEW);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_SWORD);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_SHOVEL);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_PICKAXE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_AXE);
		StageHelper.addStagedItem(EnumStage.stage1, Items.STRING);
		StageHelper.addStagedItem(EnumStage.stage0, Items.FEATHER);
		StageHelper.addStagedItem(EnumStage.stage0, Items.GUNPOWDER);
		StageHelper.addStagedItem(EnumStage.stage1, Items.WOODEN_HOE);
		StageHelper.addStagedItem(EnumStage.stage2, Items.STONE_HOE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_HOE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND_HOE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_HOE);
		StageHelper.addStagedItem(EnumStage.stage1, Items.WHEAT_SEEDS);
		StageHelper.addStagedItem(EnumStage.stage0, Items.WHEAT);
		StageHelper.addStagedItem(EnumStage.stage1, Items.BREAD);
		StageHelper.addStagedItem(EnumStage.stage1, Items.LEATHER_HELMET);
		StageHelper.addStagedItem(EnumStage.stage1, Items.LEATHER_CHESTPLATE);
		StageHelper.addStagedItem(EnumStage.stage1, Items.LEATHER_LEGGINGS);
		StageHelper.addStagedItem(EnumStage.stage1, Items.LEATHER_BOOTS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CHAINMAIL_HELMET);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CHAINMAIL_CHESTPLATE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CHAINMAIL_LEGGINGS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CHAINMAIL_BOOTS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_HELMET);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_CHESTPLATE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_LEGGINGS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_BOOTS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND_HELMET);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND_CHESTPLATE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND_LEGGINGS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND_BOOTS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_HELMET);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_CHESTPLATE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_LEGGINGS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_BOOTS);
		StageHelper.addStagedItem(EnumStage.stage0, Items.FLINT);
		StageHelper.addStagedItem(EnumStage.stage0, Items.PORKCHOP);
		StageHelper.addStagedItem(EnumStage.stage0, Items.COOKED_PORKCHOP);
		StageHelper.addStagedItem(EnumStage.do_later, Items.PAINTING);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_APPLE, 0);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_APPLE, 1);
		StageHelper.addStagedItem(EnumStage.do_later, Items.SIGN);
		StageHelper.addStagedItem(EnumStage.do_later, Items.OAK_DOOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.SPRUCE_DOOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.BIRCH_DOOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.JUNGLE_DOOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.ACACIA_DOOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DARK_OAK_DOOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.BUCKET);
		StageHelper.addStagedItem(EnumStage.do_later, Items.WATER_BUCKET);
		StageHelper.addStagedItem(EnumStage.do_later, Items.LAVA_BUCKET);
		StageHelper.addStagedItem(EnumStage.do_later, Items.MINECART);
		StageHelper.addStagedItem(EnumStage.do_later, Items.SADDLE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_DOOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.REDSTONE);
		StageHelper.addStagedItem(EnumStage.stage1, Items.SNOWBALL);
		StageHelper.addStagedItem(EnumStage.stage1, Items.BOAT);
		StageHelper.addStagedItem(EnumStage.stage1, Items.SPRUCE_BOAT);
		StageHelper.addStagedItem(EnumStage.stage1, Items.BIRCH_BOAT);
		StageHelper.addStagedItem(EnumStage.stage1, Items.JUNGLE_BOAT);
		StageHelper.addStagedItem(EnumStage.stage1, Items.ACACIA_BOAT);
		StageHelper.addStagedItem(EnumStage.stage1, Items.DARK_OAK_BOAT);
		StageHelper.addStagedItem(EnumStage.stage1, Items.LEATHER);
		StageHelper.addStagedItem(EnumStage.do_later, Items.MILK_BUCKET);
		StageHelper.addStagedItem(EnumStage.do_later, Items.BRICK);
		StageHelper.addStagedItem(EnumStage.stage1, Items.CLAY_BALL);
		StageHelper.addStagedItem(EnumStage.stage1, Items.REEDS);
		StageHelper.addStagedItem(EnumStage.stage2, Items.PAPER);
		StageHelper.addStagedItem(EnumStage.stage2, Items.BOOK);
		StageHelper.addStagedItem(EnumStage.stage0, Items.SLIME_BALL);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CHEST_MINECART);
		StageHelper.addStagedItem(EnumStage.do_later, Items.FURNACE_MINECART);
		StageHelper.addStagedItem(EnumStage.stage0, Items.EGG);
		StageHelper.addStagedItem(EnumStage.do_later, Items.COMPASS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.FISHING_ROD);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CLOCK);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GLOWSTONE_DUST);
		StageHelper.addStagedItem(EnumStage.stage0, Items.COOKED_FISH, 0);
		StageHelper.addStagedItem(EnumStage.stage0, Items.COOKED_FISH, 1);
		StageHelper.addStagedItem(EnumStage.stage0, Items.BONE);
		StageHelper.addStagedItem(EnumStage.stage2, Items.SUGAR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CAKE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.REPEATER);
		StageHelper.addStagedItem(EnumStage.do_later, Items.COOKIE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.FILLED_MAP);
		StageHelper.addStagedItem(EnumStage.do_later, Items.SHEARS);
		StageHelper.addStagedItem(EnumStage.stage1, Items.MELON);
		StageHelper.addStagedItem(EnumStage.stage1, Items.PUMPKIN_SEEDS);
		StageHelper.addStagedItem(EnumStage.stage1, Items.MELON_SEEDS);
		StageHelper.addStagedItem(EnumStage.stage0, Items.BEEF);
		StageHelper.addStagedItem(EnumStage.stage0, Items.COOKED_BEEF);
		StageHelper.addStagedItem(EnumStage.stage0, Items.CHICKEN);
		StageHelper.addStagedItem(EnumStage.stage0, Items.COOKED_CHICKEN);
		StageHelper.addStagedItem(EnumStage.stage0, Items.MUTTON);
		StageHelper.addStagedItem(EnumStage.stage0, Items.COOKED_MUTTON);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RABBIT);
		StageHelper.addStagedItem(EnumStage.stage0, Items.COOKED_RABBIT);
		StageHelper.addStagedItem(EnumStage.do_later, Items.RABBIT_STEW);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RABBIT_FOOT);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RABBIT_HIDE);
		StageHelper.addStagedItem(EnumStage.stage0, Items.ROTTEN_FLESH);
		StageHelper.addStagedItem(EnumStage.stage0, Items.ENDER_PEARL);
		StageHelper.addStagedItem(EnumStage.do_later, Items.BLAZE_ROD);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GHAST_TEAR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLD_NUGGET);
		StageHelper.addStagedItem(EnumStage.do_later, Items.NETHER_WART);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GLASS_BOTTLE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DRAGON_BREATH);
		StageHelper.addStagedItem(EnumStage.stage0, Items.SPIDER_EYE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.FERMENTED_SPIDER_EYE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.BLAZE_POWDER);
		StageHelper.addStagedItem(EnumStage.do_later, Items.MAGMA_CREAM);
		StageHelper.addStagedItem(EnumStage.do_later, Items.BREWING_STAND);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CAULDRON);
		StageHelper.addStagedItem(EnumStage.do_later, Items.ENDER_EYE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.SPECKLED_MELON);
		StageHelper.addStagedItem(EnumStage.do_later, Items.EXPERIENCE_BOTTLE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.FIRE_CHARGE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.WRITABLE_BOOK);
		StageHelper.addStagedItem(EnumStage.do_later, Items.WRITTEN_BOOK);
		StageHelper.addStagedItem(EnumStage.do_later, Items.EMERALD);
		StageHelper.addStagedItem(EnumStage.do_later, Items.ITEM_FRAME);
		StageHelper.addStagedItem(EnumStage.do_later, Items.FLOWER_POT);
		StageHelper.addStagedItem(EnumStage.stage0, Items.CARROT);
		StageHelper.addStagedItem(EnumStage.stage0, Items.POTATO);
		StageHelper.addStagedItem(EnumStage.stage0, Items.BAKED_POTATO);
		StageHelper.addStagedItem(EnumStage.stage0, Items.POISONOUS_POTATO);
		StageHelper.addStagedItem(EnumStage.do_later, Items.MAP);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_CARROT);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CARROT_ON_A_STICK);
		StageHelper.addStagedItem(EnumStage.do_later, Items.NETHER_STAR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.PUMPKIN_PIE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.FIREWORKS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.FIREWORK_CHARGE);
		StageHelper.addStagedItem(EnumStage.do_later, Items.ENCHANTED_BOOK);
		StageHelper.addStagedItem(EnumStage.do_later, Items.COMPARATOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.NETHERBRICK);
		StageHelper.addStagedItem(EnumStage.do_later, Items.QUARTZ);
		StageHelper.addStagedItem(EnumStage.do_later, Items.TNT_MINECART);
		StageHelper.addStagedItem(EnumStage.do_later, Items.HOPPER_MINECART);
		StageHelper.addStagedItem(EnumStage.do_later, Items.ARMOR_STAND);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_HORSE_ARMOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.GOLDEN_HORSE_ARMOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.DIAMOND_HORSE_ARMOR);
		StageHelper.addStagedItem(EnumStage.do_later, Items.LEAD);
		StageHelper.addStagedItem(EnumStage.do_later, Items.NAME_TAG);
		StageHelper.addStagedItem(EnumStage.no_show, Items.COMMAND_BLOCK_MINECART);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_13);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_CAT);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_BLOCKS);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_CHIRP);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_FAR);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_MALL);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_MELLOHI);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_STAL);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_STRAD);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_WARD);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_11);
		StageHelper.addStagedItem(EnumStage.stage0, Items.RECORD_WAIT);
		StageHelper.addStagedItem(EnumStage.do_later, Items.PRISMARINE_SHARD);
		StageHelper.addStagedItem(EnumStage.do_later, Items.PRISMARINE_CRYSTALS);
		StageHelper.addStagedItem(EnumStage.do_later, Items.END_CRYSTAL);
		StageHelper.addStagedItem(EnumStage.do_later, Items.SHIELD);
		StageHelper.addStagedItem(EnumStage.do_later, Items.ELYTRA);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CHORUS_FRUIT);
		StageHelper.addStagedItem(EnumStage.do_later, Items.CHORUS_FRUIT_POPPED);
		StageHelper.addStagedItem(EnumStage.stage1, Items.BEETROOT_SEEDS);
		StageHelper.addStagedItem(EnumStage.stage0, Items.BEETROOT);
		StageHelper.addStagedItem(EnumStage.do_later, Items.BEETROOT_SOUP);
		StageHelper.addStagedItem(EnumStage.do_later, Items.TOTEM_OF_UNDYING);
		StageHelper.addStagedItem(EnumStage.do_later, Items.SHULKER_SHELL);
		StageHelper.addStagedItem(EnumStage.do_later, Items.IRON_NUGGET);
		StageHelper.addStagedItem(EnumStage.do_later, Items.KNOWLEDGE_BOOK);
	}
}
