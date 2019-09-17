package mrunknown404.primalrework.util.harvest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.items.IItemBase;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo.ItemDropInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class HarvestHelper {

	public static final List<BlockHarvestInfo> BLOCKS = new ArrayList<BlockHarvestInfo>();
	public static final List<HarvestInfo> ITEMS = new ArrayList<HarvestInfo>();
	
	public static void changeHarvestLevels() {
		for (Block block : Block.REGISTRY) {
			setHarvestLevel(block, EnumToolType.none, EnumToolMaterial.unbreakable);
		}
		
		for (Item item : Item.REGISTRY) {
			setHarvestLevel(item, EnumToolType.none, EnumToolMaterial.hand);
		}
		
		for (Block block : ModBlocks.BLOCKS) {
			setHarvestLevel(block, ((BlockBase) block).getHarvestInfo().getTypesHarvests());
		}
		
		for (Item item : ModItems.ITEMS) {
			if (item instanceof IItemBase) {
				setHarvestLevel(item, ((IItemBase) item).getToolType(), ((IItemBase) item).getHarvestLevel());
			} else {
				setHarvestLevel(item, EnumToolType.none, EnumToolMaterial.hand);
			}
		}
		
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
		
		registerBlocks();
		registerItems();
		
		setupVanillaHoe((ItemHoe) Items.DIAMOND_HOE, EnumToolMaterial.diamond);
		setupVanillaHoe((ItemHoe) Items.IRON_HOE, EnumToolMaterial.iron);
		setupVanillaHoe((ItemHoe) Items.STONE_HOE, EnumToolMaterial.stone);
		setupVanillaHoe((ItemHoe) Items.WOODEN_HOE, EnumToolMaterial.flint);
		setupVanillaHoe((ItemHoe) Items.GOLDEN_HOE, EnumToolMaterial.gold);
		
		setupVanillaSword((ItemSword) Items.DIAMOND_SWORD, EnumToolMaterial.diamond);
		setupVanillaSword((ItemSword) Items.IRON_SWORD, EnumToolMaterial.iron);
		setupVanillaSword((ItemSword) Items.STONE_SWORD, EnumToolMaterial.stone);
		setupVanillaSword((ItemSword) Items.WOODEN_SWORD, EnumToolMaterial.flint);
		setupVanillaSword((ItemSword) Items.GOLDEN_SWORD, EnumToolMaterial.gold);
		
		setupVanillaTool((ItemTool) Items.DIAMOND_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.diamond);
		setupVanillaTool((ItemTool) Items.IRON_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.iron);
		setupVanillaTool((ItemTool) Items.STONE_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.stone);
		setupVanillaTool((ItemTool) Items.WOODEN_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.flint);
		setupVanillaTool((ItemTool) Items.GOLDEN_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.gold);
		
		setupVanillaTool((ItemTool) Items.DIAMOND_SHOVEL, EnumToolType.shovel, EnumToolMaterial.diamond);
		setupVanillaTool((ItemTool) Items.IRON_SHOVEL, EnumToolType.shovel, EnumToolMaterial.iron);
		setupVanillaTool((ItemTool) Items.STONE_SHOVEL, EnumToolType.shovel, EnumToolMaterial.stone);
		setupVanillaTool((ItemTool) Items.WOODEN_SHOVEL, EnumToolType.shovel, EnumToolMaterial.flint);
		setupVanillaTool((ItemTool) Items.GOLDEN_SHOVEL, EnumToolType.shovel, EnumToolMaterial.gold);
		
		setupVanillaTool((ItemTool) Items.DIAMOND_AXE, EnumToolType.axe, EnumToolMaterial.diamond);
		setupVanillaTool((ItemTool) Items.IRON_AXE, EnumToolType.axe, EnumToolMaterial.iron);
		setupVanillaTool((ItemTool) Items.STONE_AXE, EnumToolType.axe, EnumToolMaterial.stone);
		setupVanillaTool((ItemTool) Items.WOODEN_AXE, EnumToolType.axe, EnumToolMaterial.flint);
		setupVanillaTool((ItemTool) Items.GOLDEN_AXE, EnumToolType.axe, EnumToolMaterial.gold);
	}
	
	private static void setupVanillaHoe(ItemHoe item, EnumToolMaterial level) {
		ReflectionHelper.setPrivateValue(ItemHoe.class, item, EnumToolType.hoe.swingSpeed + 4, "speed", "field_185072_b");
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
	}
	
	public static void setHarvestLevel(Block b, EnumToolType type, EnumToolMaterial level) {
		setHarvestLevel(b, -1, Arrays.asList(new DoubleValue<EnumToolType, EnumToolMaterial>(type, level)));
	}
	
	public static void setHarvestLevel(Block b, float hardness, EnumToolType type, EnumToolMaterial level) {
		setHarvestLevel(b, hardness, Arrays.asList(new DoubleValue<EnumToolType, EnumToolMaterial>(type, level)));
	}
	
	public static void setHarvestLevel(Block b, List<DoubleValue<EnumToolType, EnumToolMaterial>> harvest) {
		setHarvestLevel(b, -1, harvest);
	}
	
	public static void setHarvestLevel(Block b, float hardness, List<DoubleValue<EnumToolType, EnumToolMaterial>> harvest, HarvestDropInfo... drops) {
		BlockHarvestInfo info = new BlockHarvestInfo(b, harvest);
		
		if (drops != null) {
			info = info.setDrops(drops);
		}
		
		if (hardness != -1f) {
			b.setHardness(hardness);
		}
		
		if (BLOCKS.contains(info)) {
			BLOCKS.remove(info);
		}
		BLOCKS.add(info);
	}
	
	public static void setHarvestLevel(Item i, EnumToolType type, EnumToolMaterial level) {
		HarvestInfo info = new HarvestInfo(i, new DoubleValue<EnumToolType, EnumToolMaterial>(type, level));
		
		if (ITEMS.contains(info)) {
			ITEMS.remove(info);
		}
		ITEMS.add(info);
	}
	
	public static boolean canBreak(Block block, Item item) {
		BlockHarvestInfo binfo = getHarvestInfo(block);
		HarvestInfo iinfo = getHarvestInfo(item);
		if (binfo == null || iinfo == null || binfo.isUnbreakable()) {
			return false;
		}
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> block_dv : binfo.getTypesHarvests()) {
			for (DoubleValue<EnumToolType, EnumToolMaterial> item_dv : iinfo.getTypesHarvests()) {
				if (block_dv.getL() == item_dv.getL()) {
					if (item_dv.getR().level >= block_dv.getR().level) {
						return true;
					}
				}
			}
		}
		
		return binfo.canBreakWithNone();
	}
	
	public static BlockHarvestInfo getHarvestInfo(Block block) {
		for (BlockHarvestInfo info : BLOCKS) {
			if (info.getItem() == Item.getItemFromBlock(block)) {
				return info;
			}
		}
		
		return null;
	}
	
	public static HarvestInfo getHarvestInfo(Item item) {
		for (HarvestInfo info : ITEMS) {
			if (info.getItem() == item) {
				return info;
			}
		}
		
		return null;
	}
	
	public static EnumToolMaterial getItemsHarvestLevel(Block block, Item item) {
		BlockHarvestInfo binfo = getHarvestInfo(block);
		HarvestInfo iinfo = getHarvestInfo(item);
		if (binfo == null || iinfo == null) {
			return null;
		}
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> block_dv : binfo.getTypesHarvests()) {
			for (DoubleValue<EnumToolType, EnumToolMaterial> item_dv : iinfo.getTypesHarvests()) {
				if (block_dv.getL() == item_dv.getL()) {
					return item_dv.getR();
				}
			}
		}
		
		return binfo.canBreakWithNone() ? EnumToolMaterial.hand : null;
	}
	
	public static EnumToolMaterial getBlocksHarvestLevel(Block block, Item item) {
		BlockHarvestInfo binfo = getHarvestInfo(block);
		HarvestInfo iinfo = getHarvestInfo(item);
		if (binfo == null || iinfo == null) {
			return null;
		}
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> block_dv : binfo.getTypesHarvests()) {
			for (DoubleValue<EnumToolType, EnumToolMaterial> item_dv : iinfo.getTypesHarvests()) {
				if (block_dv.getL() == item_dv.getL()) {
					return block_dv.getR();
				}
			}
		}
		
		return binfo.canBreakWithNone() ? EnumToolMaterial.hand : null;
	}
	
	public static EnumToolType getItemsToolType(Block block, Item item) {
		BlockHarvestInfo binfo = getHarvestInfo(block);
		HarvestInfo iinfo = getHarvestInfo(item);
		if (binfo == null || iinfo == null) {
			return null;
		}
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> block_dv : binfo.getTypesHarvests()) {
			for (DoubleValue<EnumToolType, EnumToolMaterial> item_dv : iinfo.getTypesHarvests()) {
				if (block_dv.getL() == item_dv.getL()) {
					return item_dv.getL();
				}
			}
		}
		
		return EnumToolType.none;
	}
	
	private static void registerBlocks() {
		setHarvestLevel(Blocks.STONE, -1, Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.flint)),
				new HarvestDropInfo(EnumToolType.pickaxe, true,
						new ItemDropInfo(ModBlocks.ROCK, false, 100, 4, 0, 5, 1.2f, 0f),
						new ItemDropInfo(Blocks.COBBLESTONE, false, 10, 1, 0, 0, 0f, 0.1f)));
		setHarvestLevel(Blocks.GRASS,                         EnumToolType.shovel,  EnumToolMaterial.flint);
		setHarvestLevel(Blocks.DIRT,                          EnumToolType.shovel,  EnumToolMaterial.flint);
		setHarvestLevel(Blocks.COBBLESTONE,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.PLANKS,                        EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.SAPLING,                       EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.BEDROCK,                       EnumToolType.none,    EnumToolMaterial.unbreakable);
		setHarvestLevel(Blocks.SAND,                          EnumToolType.shovel,  EnumToolMaterial.flint);
		setHarvestLevel(Blocks.GRAVEL, -1, Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.shovel, EnumToolMaterial.flint)),
				new HarvestDropInfo(EnumToolType.shovel, true,
						new ItemDropInfo(ModItems.GRAVEL, false, 100, 2, 1, 2, 0.2f, 0f)));
		setHarvestLevel(Blocks.GOLD_ORE,                      EnumToolType.pickaxe, EnumToolMaterial.bronze);
		setHarvestLevel(Blocks.IRON_ORE,                      EnumToolType.pickaxe, EnumToolMaterial.bronze);
		setHarvestLevel(Blocks.COAL_ORE,                      EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.LOG,                           EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.LOG2,                          EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.LEAVES, 0.3f, Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand),
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.knife, EnumToolMaterial.flint)),
				new HarvestDropInfo(EnumToolType.none, false,
						new ItemDropInfo(Items.STICK, false, 10, 1, 0, 0, 0.2f, 0.1f)),
				new HarvestDropInfo(EnumToolType.knife, false,
						new ItemDropInfo(Items.STICK, false, 70, 1, 0, 1, 0.2f, 0.1f)));
		setHarvestLevel(Blocks.LEAVES2, 0.3f, Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand),
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.knife, EnumToolMaterial.flint)),
				new HarvestDropInfo(EnumToolType.none, false,
						new ItemDropInfo(Items.STICK, false, 10, 1, 0, 0, 0.2f, 0.1f)),
				new HarvestDropInfo(EnumToolType.knife, false,
						new ItemDropInfo(Items.STICK, false, 70, 1, 0, 1, 0.2f, 0.1f)));
		setHarvestLevel(Blocks.SPONGE,                        EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.GLASS,                         EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.LAPIS_ORE,                     EnumToolType.pickaxe, EnumToolMaterial.copper);
		setHarvestLevel(Blocks.LAPIS_BLOCK,                   EnumToolType.pickaxe, EnumToolMaterial.copper);
		setHarvestLevel(Blocks.DISPENSER,                     EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.SANDSTONE,                     EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.NOTEBLOCK,                     EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.BED,                           EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.GOLDEN_RAIL,                   EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.DETECTOR_RAIL,                 EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.STICKY_PISTON,                 EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.WEB,                           EnumToolType.sword,   EnumToolMaterial.flint);
		setHarvestLevel(Blocks.TALLGRASS, -1, Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand),
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.knife, EnumToolMaterial.flint)),
				new HarvestDropInfo(EnumToolType.none, false,
						new ItemDropInfo(ModItems.PLANT_FIBER, false, 50, 1, 0, 0, 0f, 0f)),
				new HarvestDropInfo(EnumToolType.knife, false,
						new ItemDropInfo(ModItems.PLANT_FIBER, false, 100, 1, 1, 2, 0f, 0f)));
		setHarvestLevel(Blocks.DEADBUSH,                      EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.PISTON,                        EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.PISTON_HEAD,                   EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.WOOL, Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand),
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.shears, EnumToolMaterial.flint),
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.knife, EnumToolMaterial.flint)));
		//setHarvestLevel(Blocks.PISTON_EXTENSION, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.YELLOW_FLOWER,                 EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.RED_FLOWER,                    EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.BROWN_MUSHROOM,                EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.RED_MUSHROOM,                  EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.GOLD_BLOCK,                    EnumToolType.pickaxe, EnumToolMaterial.bronze);
		setHarvestLevel(Blocks.IRON_BLOCK,                    EnumToolType.pickaxe, EnumToolMaterial.bronze);
		setHarvestLevel(Blocks.DOUBLE_STONE_SLAB,             EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.STONE_SLAB,                    EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.BRICK_BLOCK,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.TNT,                           EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.BOOKSHELF,                     EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.MOSSY_COBBLESTONE,             EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.OBSIDIAN,                      EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.TORCH,                         EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.MOB_SPAWNER,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.OAK_STAIRS,                    EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.CHEST,                         EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.REDSTONE_WIRE,                 EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.DIAMOND_ORE,                   EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.DIAMOND_BLOCK,                 EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.CRAFTING_TABLE,                EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.WHEAT, 0.3f,                   EnumToolType.hoe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.FARMLAND,                      EnumToolType.shovel,  EnumToolMaterial.flint);
		setHarvestLevel(Blocks.FURNACE,                       EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.LIT_FURNACE,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.STANDING_SIGN,                 EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.OAK_DOOR,                      EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.SPRUCE_DOOR,                   EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.BIRCH_DOOR,                    EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.JUNGLE_DOOR,                   EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.ACACIA_DOOR,                   EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.DARK_OAK_DOOR,                 EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.LADDER,                        EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.RAIL,                          EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.STONE_STAIRS,                  EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.WALL_SIGN,                     EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.LEVER,                         EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.STONE_PRESSURE_PLATE,          EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.IRON_DOOR,                     EnumToolType.pickaxe, EnumToolMaterial.bronze);
		setHarvestLevel(Blocks.WOODEN_PRESSURE_PLATE,         EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.REDSTONE_ORE,                  EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Blocks.LIT_REDSTONE_ORE,              EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Blocks.UNLIT_REDSTONE_TORCH,          EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.REDSTONE_TORCH,                EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.STONE_BUTTON,                  EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.SNOW_LAYER,                    EnumToolType.shovel,  EnumToolMaterial.hand);
		setHarvestLevel(Blocks.ICE,                           EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.SNOW,                          EnumToolType.shovel,  EnumToolMaterial.flint);
		setHarvestLevel(Blocks.CACTUS,                        EnumToolType.shears,  EnumToolMaterial.hand);
		setHarvestLevel(Blocks.CLAY,                          EnumToolType.shovel,  EnumToolMaterial.flint);
		setHarvestLevel(Blocks.REEDS, 0.3f,                   EnumToolType.hoe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.JUKEBOX,                       EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.OAK_FENCE,                     EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.SPRUCE_FENCE,                  EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.BIRCH_FENCE,                   EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.JUNGLE_FENCE,                  EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.DARK_OAK_FENCE,                EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.ACACIA_FENCE,                  EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.PUMPKIN,                       EnumToolType.hoe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.NETHERRACK,                    EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.SOUL_SAND,                     EnumToolType.shovel,  EnumToolMaterial.stone);
		setHarvestLevel(Blocks.GLOWSTONE,                     EnumToolType.pickaxe, EnumToolMaterial.stone);
		//setHarvestLevel(Blocks.PORTAL, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.LIT_PUMPKIN,                   EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.CAKE,                          EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.UNPOWERED_REPEATER,            EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.POWERED_REPEATER,              EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.TRAPDOOR,                      EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.MONSTER_EGG, -1, Arrays.asList(
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.flint)),
				new HarvestDropInfo(EnumToolType.pickaxe, true,
						new ItemDropInfo(ModBlocks.ROCK, false, 100, 4, 0, 5, 1.2f, 0f),
						new ItemDropInfo(Blocks.COBBLESTONE, false, 10, 1, 0, 0, 0f, 0.1f)));
		setHarvestLevel(Blocks.STONEBRICK,                    EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.BROWN_MUSHROOM_BLOCK,          EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.RED_MUSHROOM_BLOCK,            EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.IRON_BARS,                     EnumToolType.pickaxe, EnumToolMaterial.bronze);
		setHarvestLevel(Blocks.GLASS_PANE,                    EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.MELON_BLOCK,                   EnumToolType.hoe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.PUMPKIN_STEM,                  EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.MELON_STEM,                    EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.VINE,                          EnumToolType.axe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.OAK_FENCE_GATE,                EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.SPRUCE_FENCE_GATE,             EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.BIRCH_FENCE_GATE,              EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.JUNGLE_FENCE_GATE,             EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.DARK_OAK_FENCE_GATE,           EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.ACACIA_FENCE_GATE,             EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.BRICK_STAIRS,                  EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.STONE_BRICK_STAIRS,            EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.MYCELIUM,                      EnumToolType.shovel,  EnumToolMaterial.flint);
		setHarvestLevel(Blocks.WATERLILY,                     EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.NETHER_BRICK,                  EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.NETHER_BRICK_FENCE,            EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.NETHER_BRICK_STAIRS,           EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.NETHER_WART, 0.3f,             EnumToolType.hoe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.ENCHANTING_TABLE,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.BREWING_STAND,                 EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.CAULDRON,                      EnumToolType.pickaxe, EnumToolMaterial.bronze);
		//setHarvestLevel(Blocks.END_PORTAL, ToolType., ToolHarvestLevel.);
		//setHarvestLevel(Blocks.END_PORTAL_FRAME, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.END_STONE,                     EnumToolType.pickaxe, EnumToolMaterial.iron);
		//setHarvestLevel(Blocks.DRAGON_EGG, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.REDSTONE_LAMP,                 EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.LIT_REDSTONE_LAMP,             EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.DOUBLE_WOODEN_SLAB,            EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.WOODEN_SLAB,                   EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.COCOA, 0.3f,                   EnumToolType.hoe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.SANDSTONE_STAIRS,              EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.EMERALD_ORE,                   EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.ENDER_CHEST,                   EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.TRIPWIRE_HOOK,                 EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.TRIPWIRE,                      EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.EMERALD_BLOCK,                 EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.SPRUCE_STAIRS,                 EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.BIRCH_STAIRS,                  EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.JUNGLE_STAIRS,                 EnumToolType.axe,     EnumToolMaterial.flint);
		//setHarvestLevel(Blocks.COMMAND_BLOCK, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.BEACON,                        EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.COBBLESTONE_WALL,              EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.FLOWER_POT,                    EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.CARROTS, 0.3f,                 EnumToolType.hoe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.POTATOES, 0.3f,                EnumToolType.hoe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.WOODEN_BUTTON,                 EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.SKULL,                         EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.ANVIL,                         EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.TRAPPED_CHEST,                 EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.UNPOWERED_COMPARATOR,          EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.POWERED_COMPARATOR,            EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.DAYLIGHT_DETECTOR,             EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.DAYLIGHT_DETECTOR_INVERTED,    EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.REDSTONE_BLOCK,                EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Blocks.QUARTZ_ORE,                    EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.HOPPER,                        EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.QUARTZ_BLOCK,                  EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.QUARTZ_STAIRS,                 EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.ACTIVATOR_RAIL,                EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.DROPPER,                       EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.STAINED_HARDENED_CLAY,         EnumToolType.pickaxe, EnumToolMaterial.flint);
		//setHarvestLevel(Blocks.BARRIER, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.IRON_TRAPDOOR,                 EnumToolType.pickaxe, EnumToolMaterial.bronze);
		setHarvestLevel(Blocks.HAY_BLOCK,                     EnumToolType.axe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.CARPET,                        EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.HARDENED_CLAY,                 EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.COAL_BLOCK,                    EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.PACKED_ICE,                    EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.ACACIA_STAIRS,                 EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.DARK_OAK_STAIRS,               EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.SLIME_BLOCK,                   EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.DOUBLE_PLANT,                  EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.STAINED_GLASS,                 EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.STAINED_GLASS_PANE,            EnumToolType.none,    EnumToolMaterial.hand);
		setHarvestLevel(Blocks.PRISMARINE,                    EnumToolType.pickaxe, EnumToolMaterial.copper);
		setHarvestLevel(Blocks.SEA_LANTERN,                   EnumToolType.pickaxe, EnumToolMaterial.copper);
		setHarvestLevel(Blocks.STANDING_BANNER,               EnumToolType.axe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.WALL_BANNER,                   EnumToolType.axe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.RED_SANDSTONE,                 EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.RED_SANDSTONE_STAIRS,          EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.DOUBLE_STONE_SLAB2,            EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.STONE_SLAB2,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.END_ROD,                       EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Blocks.CHORUS_PLANT,                  EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.CHORUS_FLOWER,                 EnumToolType.axe,     EnumToolMaterial.flint);
		setHarvestLevel(Blocks.PURPUR_BLOCK,                  EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Blocks.PURPUR_PILLAR,                 EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Blocks.PURPUR_STAIRS,                 EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Blocks.PURPUR_DOUBLE_SLAB,            EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Blocks.PURPUR_SLAB,                   EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Blocks.END_BRICKS,                    EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Blocks.BEETROOTS, 0.3f,               EnumToolType.hoe,     EnumToolMaterial.hand);
		setHarvestLevel(Blocks.GRASS_PATH,                    EnumToolType.shovel,  EnumToolMaterial.flint);
		//setHarvestLevel(Blocks.END_GATEWAY, ToolType., ToolHarvestLevel.);
		//setHarvestLevel(Blocks.REPEATING_COMMAND_BLOCK, ToolType., ToolHarvestLevel.);
		//setHarvestLevel(Blocks.CHAIN_COMMAND_BLOCK, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.FROSTED_ICE,                   EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.MAGMA,                         EnumToolType.pickaxe, EnumToolMaterial.bronze);
		setHarvestLevel(Blocks.NETHER_WART_BLOCK,             EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.RED_NETHER_BRICK,              EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.BONE_BLOCK,                    EnumToolType.pickaxe, EnumToolMaterial.flint);
		//setHarvestLevel(Blocks.STRUCTURE_VOID, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.OBSERVER,                      EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.WHITE_SHULKER_BOX,             EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.ORANGE_SHULKER_BOX,            EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.MAGENTA_SHULKER_BOX,           EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.LIGHT_BLUE_SHULKER_BOX,        EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.YELLOW_SHULKER_BOX,            EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.LIME_SHULKER_BOX,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.PINK_SHULKER_BOX,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.GRAY_SHULKER_BOX,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.SILVER_SHULKER_BOX,            EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.CYAN_SHULKER_BOX,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.PURPLE_SHULKER_BOX,            EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.BLUE_SHULKER_BOX,              EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.BROWN_SHULKER_BOX,             EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.GREEN_SHULKER_BOX,             EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.RED_SHULKER_BOX,               EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.BLACK_SHULKER_BOX,             EnumToolType.pickaxe, EnumToolMaterial.steel);
		setHarvestLevel(Blocks.WHITE_GLAZED_TERRACOTTA,       EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.ORANGE_GLAZED_TERRACOTTA,      EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.MAGENTA_GLAZED_TERRACOTTA,     EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA,  EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.YELLOW_GLAZED_TERRACOTTA,      EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.LIME_GLAZED_TERRACOTTA,        EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.PINK_GLAZED_TERRACOTTA,        EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.GRAY_GLAZED_TERRACOTTA,        EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.SILVER_GLAZED_TERRACOTTA,      EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.CYAN_GLAZED_TERRACOTTA,        EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.PURPLE_GLAZED_TERRACOTTA,      EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.BLUE_GLAZED_TERRACOTTA,        EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.BROWN_GLAZED_TERRACOTTA,       EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.GREEN_GLAZED_TERRACOTTA,       EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.RED_GLAZED_TERRACOTTA,         EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.BLACK_GLAZED_TERRACOTTA,       EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Blocks.CONCRETE,                      EnumToolType.pickaxe, EnumToolMaterial.flint);
		setHarvestLevel(Blocks.CONCRETE_POWDER,               EnumToolType.shovel,  EnumToolMaterial.flint);
		//setHarvestLevel(Blocks.STRUCTURE_BLOCK, ToolType., ToolHarvestLevel.);
	}
	
	private static void registerItems() {
		setHarvestLevel(Items.SHEARS, EnumToolType.shears, EnumToolMaterial.iron);
		
		setHarvestLevel(Items.DIAMOND_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.diamond);
		setHarvestLevel(Items.GOLDEN_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.gold);
		setHarvestLevel(Items.IRON_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.iron);
		setHarvestLevel(Items.STONE_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.stone);
		setHarvestLevel(Items.WOODEN_PICKAXE, EnumToolType.pickaxe, EnumToolMaterial.flint);
		
		setHarvestLevel(Items.DIAMOND_SHOVEL, EnumToolType.shovel, EnumToolMaterial.diamond);
		setHarvestLevel(Items.GOLDEN_SHOVEL, EnumToolType.shovel, EnumToolMaterial.gold);
		setHarvestLevel(Items.IRON_SHOVEL, EnumToolType.shovel, EnumToolMaterial.iron);
		setHarvestLevel(Items.STONE_SHOVEL, EnumToolType.shovel, EnumToolMaterial.stone);
		setHarvestLevel(Items.WOODEN_SHOVEL, EnumToolType.shovel, EnumToolMaterial.flint);
		
		setHarvestLevel(Items.DIAMOND_AXE, EnumToolType.axe, EnumToolMaterial.diamond);
		setHarvestLevel(Items.GOLDEN_AXE, EnumToolType.axe, EnumToolMaterial.gold);
		setHarvestLevel(Items.IRON_AXE, EnumToolType.axe, EnumToolMaterial.iron);
		setHarvestLevel(Items.STONE_AXE, EnumToolType.axe, EnumToolMaterial.stone);
		setHarvestLevel(Items.WOODEN_AXE, EnumToolType.axe, EnumToolMaterial.flint);
		
		setHarvestLevel(Items.DIAMOND_SWORD, EnumToolType.sword, EnumToolMaterial.diamond);
		setHarvestLevel(Items.GOLDEN_SWORD, EnumToolType.sword, EnumToolMaterial.gold);
		setHarvestLevel(Items.IRON_SWORD, EnumToolType.sword, EnumToolMaterial.iron);
		setHarvestLevel(Items.STONE_SWORD, EnumToolType.sword, EnumToolMaterial.stone);
		setHarvestLevel(Items.WOODEN_SWORD, EnumToolType.sword, EnumToolMaterial.flint);
		
		setHarvestLevel(Items.DIAMOND_HOE, EnumToolType.hoe, EnumToolMaterial.diamond);
		setHarvestLevel(Items.GOLDEN_HOE, EnumToolType.hoe, EnumToolMaterial.gold);
		setHarvestLevel(Items.IRON_HOE, EnumToolType.hoe, EnumToolMaterial.iron);
		setHarvestLevel(Items.STONE_HOE, EnumToolType.hoe, EnumToolMaterial.stone);
		setHarvestLevel(Items.WOODEN_HOE, EnumToolType.hoe, EnumToolMaterial.flint);
	}
}
