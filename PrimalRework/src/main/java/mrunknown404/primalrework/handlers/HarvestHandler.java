package mrunknown404.primalrework.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.items.ItemBase;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo;
import mrunknown404.primalrework.util.harvest.HarvestInfo;
import mrunknown404.primalrework.util.harvest.ItemDropInfo;
import mrunknown404.primalrework.util.harvest.ToolHarvestLevel;
import mrunknown404.primalrework.util.harvest.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class HarvestHandler {

	public static final List<HarvestInfo> BLOCKS = new ArrayList<HarvestInfo>();
	public static final List<HarvestInfo> ITEMS = new ArrayList<HarvestInfo>();
	
	public static void changeHarvestLevels() {
		for (Block block : Block.REGISTRY) {
			setHarvestLevel(block, ToolType.none, ToolHarvestLevel.unbreakable);
		}
		
		for (Item item : Item.REGISTRY) {
			setHarvestLevel(item, ToolType.none, ToolHarvestLevel.hand);
		}
		
		for (Block block : ModBlocks.BLOCKS) {
			setHarvestLevel(block, ((BlockBase) block).getHarvestInfo().getTypesHarvests());
		}
		
		for (Item item : ModItems.ITEMS) {
			if (item instanceof ItemBase) {
				setHarvestLevel(item, ((ItemBase) item).getToolType(), ((ItemBase) item).getHarvestLevel());
			} else {
				setHarvestLevel(item, ToolType.none, ToolHarvestLevel.hand);
			}
		}
		
		ReflectionHelper.setPrivateValue(Material.class, Material.GRASS, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.GROUND, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.WOOD, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.ROCK, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.IRON, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.ANVIL, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.LEAVES, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.PLANTS, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.VINE, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.SPONGE, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.CLOTH, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.SAND, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.CIRCUITS, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.CARPET, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.GLASS, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.REDSTONE_LIGHT, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.TNT, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.CORAL, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.ICE, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.PACKED_ICE, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.SNOW, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.CRAFTED_SNOW, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.CACTUS, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.CLAY, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.GOURD, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.DRAGON_EGG, true, "requiresNoTool");
		ReflectionHelper.setPrivateValue(Material.class, Material.WEB, true, "requiresNoTool");
		
		Items.DIAMOND_PICKAXE.setHarvestLevel("pickaxe", ToolHarvestLevel.unbreakable.level);
		Items.IRON_PICKAXE.setHarvestLevel("pickaxe", ToolHarvestLevel.unbreakable.level);
		Items.STONE_PICKAXE.setHarvestLevel("pickaxe", ToolHarvestLevel.unbreakable.level);
		Items.WOODEN_PICKAXE.setHarvestLevel("pickaxe", ToolHarvestLevel.unbreakable.level);
		Items.GOLDEN_PICKAXE.setHarvestLevel("pickaxe", ToolHarvestLevel.unbreakable.level);
		
		Items.DIAMOND_SHOVEL.setHarvestLevel("shovel", ToolHarvestLevel.unbreakable.level);
		Items.IRON_SHOVEL.setHarvestLevel("shovel", ToolHarvestLevel.unbreakable.level);
		Items.STONE_SHOVEL.setHarvestLevel("shovel", ToolHarvestLevel.unbreakable.level);
		Items.WOODEN_SHOVEL.setHarvestLevel("shovel", ToolHarvestLevel.unbreakable.level);
		Items.GOLDEN_SHOVEL.setHarvestLevel("shovel", ToolHarvestLevel.unbreakable.level);
		
		Items.DIAMOND_AXE.setHarvestLevel("axe", ToolHarvestLevel.unbreakable.level);
		Items.IRON_AXE.setHarvestLevel("axe", ToolHarvestLevel.unbreakable.level);
		Items.STONE_AXE.setHarvestLevel("axe", ToolHarvestLevel.unbreakable.level);
		Items.WOODEN_AXE.setHarvestLevel("axe", ToolHarvestLevel.unbreakable.level);
		Items.GOLDEN_AXE.setHarvestLevel("axe", ToolHarvestLevel.unbreakable.level);
		
		registerBlocks();
		registerItems();
	}
	
	public static void setHarvestLevel(Block b, List<DoubleValue<ToolType, ToolHarvestLevel>> list) {
		setHarvestLevel(b, -1f, null, list.toArray(new DoubleValue[0]));
	}
	
	public static void setHarvestLevel(Block b, DoubleValue<ToolType, ToolHarvestLevel>... list) {
		setHarvestLevel(b, -1f, null, list);
	}
	
	public static void setHarvestLevel(Block b, ToolType type, ToolHarvestLevel level) {
		setHarvestLevel(b, -1f, null, new DoubleValue<ToolType, ToolHarvestLevel>(type, level));
	}
	
	public static void setHarvestLevel(Block b, float hardness, ToolType type, ToolHarvestLevel level) {
		setHarvestLevel(b, hardness, null, new DoubleValue<ToolType, ToolHarvestLevel>(type, level));
	}
	
	public static void setHarvestLevel(Block b, float hardness, @Nullable List<HarvestDropInfo> drops, DoubleValue<ToolType, ToolHarvestLevel>... list) {
		HarvestInfo info = new HarvestInfo(b, list);
		
		if (drops != null && !drops.isEmpty()) {
			info.setDrops(drops);
		}
		
		if (hardness != -1f) {
			b.setHardness(hardness);
		}
		
		if (BLOCKS.contains(info)) {
			BLOCKS.remove(info);
		}
		BLOCKS.add(info);
	}
	
	public static void setHarvestLevel(Item i, List<DoubleValue<ToolType, ToolHarvestLevel>> list) {
		setHarvestLevel(i, list);
	}
	
	public static void setHarvestLevel(Item i, ToolType type, ToolHarvestLevel level) {
		setHarvestLevel(i, null, new DoubleValue<ToolType, ToolHarvestLevel>(type, level));
	}
	
	public static void setHarvestLevel(Item i, @Nullable List<HarvestDropInfo> drops, DoubleValue<ToolType, ToolHarvestLevel>... list) {
		HarvestInfo info = new HarvestInfo(i, list);
		
		if (drops != null && !drops.isEmpty()) {
			info.setDrops(drops);
		}
		
		if (ITEMS.contains(info)) {
			ITEMS.remove(info);
		}
		ITEMS.add(info);
	}
	
	public static boolean canBreak(Block block, Item item) {
		HarvestInfo binfo = getHarvestInfo(block), iinfo = getHarvestInfo(item);
		if (binfo == null || iinfo == null) {
			return false;
		}
		
		for (DoubleValue<ToolType, ToolHarvestLevel> block_dv : binfo.getTypesHarvests()) {
			if (block_dv.getR() == ToolHarvestLevel.unbreakable) {
				return false;
			} else if (block_dv.getR() == ToolHarvestLevel.hand) {
				return true;
			} else if (block_dv.getL() == ToolType.none) {
				return true;
			}
			
			for (DoubleValue<ToolType, ToolHarvestLevel> item_dv : iinfo.getTypesHarvests()) {
				if (block_dv.getL() == item_dv.getL()) {
					if (item_dv.getR().level >= block_dv.getR().level) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static HarvestInfo getHarvestInfo(Block block) {
		for (HarvestInfo info : BLOCKS) {
			if (info.getBlock() == block) {
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
	
	public static ToolHarvestLevel getCurrentItemHarvestLevel(Block block, Item item) {
		HarvestInfo binfo = getHarvestInfo(block), iinfo = getHarvestInfo(item);
		if (binfo == null || iinfo == null) {
			return null;
		}
		
		for (DoubleValue<ToolType, ToolHarvestLevel> block_dv : binfo.getTypesHarvests()) {
			for (DoubleValue<ToolType, ToolHarvestLevel> item_dv : iinfo.getTypesHarvests()) {
				if (block_dv.getL() == item_dv.getL()) {
					return item_dv.getR();
				} else if (block_dv.getR() == ToolHarvestLevel.hand) {
					return block_dv.getR();
				}
			}
		}
		
		return null;
	}
	
	public static ToolHarvestLevel getCurrentBlockHarvestLevel(Block block, Item item) {
		HarvestInfo binfo = getHarvestInfo(block), iinfo = getHarvestInfo(item);
		if (binfo == null || iinfo == null) {
			return null;
		}
		
		for (DoubleValue<ToolType, ToolHarvestLevel> block_dv : binfo.getTypesHarvests()) {
			for (DoubleValue<ToolType, ToolHarvestLevel> item_dv : iinfo.getTypesHarvests()) {
				if (block_dv.getL() == item_dv.getL()) {
					return block_dv.getR();
				} else if (block_dv.getR() == ToolHarvestLevel.hand) {
					return block_dv.getR();
				}
			}
		}
		
		return null;
	}
	
	public static ToolType getCurrentItemToolType(Block block, Item item) {
		HarvestInfo binfo = getHarvestInfo(block), iinfo = getHarvestInfo(item);
		if (binfo == null || iinfo == null) {
			return null;
		}
		
		for (DoubleValue<ToolType, ToolHarvestLevel> block_dv : binfo.getTypesHarvests()) {
			for (DoubleValue<ToolType, ToolHarvestLevel> item_dv : iinfo.getTypesHarvests()) {
				if (block_dv.getL() == item_dv.getL()) {
					return item_dv.getL();
				}
			}
		}
		
		return null;
	}
	
	private static void registerBlocks() {
		setHarvestLevel(Blocks.STONE, -1, Arrays.asList(new HarvestDropInfo(ToolType.pickaxe, true,
				new ItemDropInfo(ModBlocks.ROCK, false, 100, 4, 0, 5, 1.2f, 0f),
				new ItemDropInfo(Item.getItemFromBlock(Blocks.COBBLESTONE), false, 10, 1, 0, 0, 0f, 0.1f))),
				new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.pickaxe, ToolHarvestLevel.flint));
		setHarvestLevel(Blocks.GRASS,                         ToolType.shovel,  ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.DIRT,                          ToolType.shovel,  ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.COBBLESTONE,                   ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.PLANKS,                        ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.SAPLING,                       ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.BEDROCK,                       ToolType.none,    ToolHarvestLevel.unbreakable);
		setHarvestLevel(Blocks.SAND,                          ToolType.shovel,  ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.GRAVEL, -1, Arrays.asList(new HarvestDropInfo(ToolType.shovel, true,
				new ItemDropInfo(ModItems.GRAVEL, false, 100, 2, 1, 2, 0.2f, 0f))),
				new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.shovel, ToolHarvestLevel.flint));
		setHarvestLevel(Blocks.GOLD_ORE,                      ToolType.pickaxe, ToolHarvestLevel.bronze);
		setHarvestLevel(Blocks.IRON_ORE,                      ToolType.pickaxe, ToolHarvestLevel.bronze);
		setHarvestLevel(Blocks.COAL_ORE,                      ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.LOG,                           ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.LOG2,                          ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.LEAVES, 0.5f, Arrays.asList(new HarvestDropInfo(ToolType.none, false,
				new ItemDropInfo(Items.STICK, false, 10, 1, 0, 0, 0.2f, 0.1f)),
				new HarvestDropInfo(ToolType.knife, false, new ItemDropInfo(Items.STICK, false, 70, 1, 0, 1, 0.2f, 0.1f))),
				new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.knife, ToolHarvestLevel.hand));
		setHarvestLevel(Blocks.LEAVES2, 0.5f, Arrays.asList(new HarvestDropInfo(ToolType.none, false,
				new ItemDropInfo(Items.STICK, false, 10, 1, 0, 0, 0.2f, 0.1f)),
				new HarvestDropInfo(ToolType.knife, false, new ItemDropInfo(Items.STICK, false, 70, 1, 0, 1, 0.2f, 0.1f))),
				new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.knife, ToolHarvestLevel.hand));
		setHarvestLevel(Blocks.SPONGE,                        ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.GLASS,                         ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.LAPIS_ORE,                     ToolType.pickaxe, ToolHarvestLevel.copper);
		setHarvestLevel(Blocks.LAPIS_BLOCK,                   ToolType.pickaxe, ToolHarvestLevel.copper);
		setHarvestLevel(Blocks.DISPENSER,                     ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.SANDSTONE,                     ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.NOTEBLOCK,                     ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.BED,                           ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.GOLDEN_RAIL,                   ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.DETECTOR_RAIL,                 ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.STICKY_PISTON,                 ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.WEB,                           ToolType.sword,   ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.TALLGRASS, -1, Arrays.asList(new HarvestDropInfo(ToolType.knife, true,
				new ItemDropInfo(ModBlocks.ROCK, false, 100, 1, 0, 0, 0f, 0f))),
				new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.knife, ToolHarvestLevel.flint));
		setHarvestLevel(Blocks.DEADBUSH,                      ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.PISTON,                        ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.PISTON_HEAD,                   ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.WOOL, new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.none, ToolHarvestLevel.hand));
		//setHarvestLevel(Blocks.PISTON_EXTENSION, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.YELLOW_FLOWER,                 ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.RED_FLOWER,                    ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.BROWN_MUSHROOM,                ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.RED_MUSHROOM,                  ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.GOLD_BLOCK,                    ToolType.pickaxe, ToolHarvestLevel.bronze);
		setHarvestLevel(Blocks.IRON_BLOCK,                    ToolType.pickaxe, ToolHarvestLevel.bronze);
		setHarvestLevel(Blocks.DOUBLE_STONE_SLAB,             ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.STONE_SLAB,                    ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.BRICK_BLOCK,                   ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.TNT,                           ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.BOOKSHELF,                     ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.MOSSY_COBBLESTONE,             ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.OBSIDIAN,                      ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.TORCH,                         ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.MOB_SPAWNER,                   ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.OAK_STAIRS,                    ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.CHEST,                         ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.REDSTONE_WIRE,                 ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.DIAMOND_ORE,                   ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.DIAMOND_BLOCK,                 ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.CRAFTING_TABLE,                ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.WHEAT, 0.3f,                   ToolType.hoe,     ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.FARMLAND,                      ToolType.shovel,  ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.FURNACE,                       ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.LIT_FURNACE,                   ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.STANDING_SIGN,                 ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.OAK_DOOR,                      ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.SPRUCE_DOOR,                   ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.BIRCH_DOOR,                    ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.JUNGLE_DOOR,                   ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.ACACIA_DOOR,                   ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.DARK_OAK_DOOR,                 ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.LADDER,                        ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.RAIL,                          ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.STONE_STAIRS,                  ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.WALL_SIGN,                     ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.LEVER,                         ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.STONE_PRESSURE_PLATE,          ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.IRON_DOOR,                     ToolType.pickaxe, ToolHarvestLevel.bronze);
		setHarvestLevel(Blocks.WOODEN_PRESSURE_PLATE,         ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.REDSTONE_ORE,                  ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Blocks.LIT_REDSTONE_ORE,              ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Blocks.UNLIT_REDSTONE_TORCH,          ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.REDSTONE_TORCH,                ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.STONE_BUTTON,                  ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.SNOW_LAYER,                    ToolType.shovel,  ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.ICE,                           ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.SNOW,                          ToolType.shovel,  ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.CACTUS,                        ToolType.shears,  ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.CLAY,                          ToolType.shovel,  ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.REEDS, 0.3f,                   ToolType.hoe,     ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.JUKEBOX,                       ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.OAK_FENCE,                     ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.SPRUCE_FENCE,                  ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.BIRCH_FENCE,                   ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.JUNGLE_FENCE,                  ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.DARK_OAK_FENCE,                ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.ACACIA_FENCE,                  ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.PUMPKIN,                       ToolType.hoe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.NETHERRACK,                    ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.SOUL_SAND,                     ToolType.shovel,  ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.GLOWSTONE,                     ToolType.pickaxe, ToolHarvestLevel.stone);
		//setHarvestLevel(Blocks.PORTAL, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.LIT_PUMPKIN,                   ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.CAKE,                          ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.UNPOWERED_REPEATER,            ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.POWERED_REPEATER,              ToolType.none,    ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.TRAPDOOR,                      ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.MONSTER_EGG, -1, Arrays.asList(new HarvestDropInfo(ToolType.pickaxe, true,
				new ItemDropInfo(ModBlocks.ROCK, false, 100, 4, 0, 5, 1.2f, 0f),
				new ItemDropInfo(Item.getItemFromBlock(Blocks.COBBLESTONE), false, 10, 1, 0, 0, 0f, 0.1f))),
				new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.pickaxe, ToolHarvestLevel.flint));
		setHarvestLevel(Blocks.STONEBRICK,                    ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.BROWN_MUSHROOM_BLOCK,          ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.RED_MUSHROOM_BLOCK,            ToolType.axe,     ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.IRON_BARS,                     ToolType.pickaxe, ToolHarvestLevel.bronze);
		setHarvestLevel(Blocks.GLASS_PANE,                    ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.MELON_BLOCK,                   ToolType.hoe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.PUMPKIN_STEM,                  ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.MELON_STEM,                    ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.VINE,                          ToolType.axe, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.OAK_FENCE_GATE,                ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.SPRUCE_FENCE_GATE,             ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.BIRCH_FENCE_GATE,              ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.JUNGLE_FENCE_GATE,             ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.DARK_OAK_FENCE_GATE,           ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.ACACIA_FENCE_GATE,             ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.BRICK_STAIRS,                  ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.STONE_BRICK_STAIRS,            ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.MYCELIUM,                      ToolType.shovel, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.WATERLILY,                     ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.NETHER_BRICK,                  ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.NETHER_BRICK_FENCE,            ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.NETHER_BRICK_STAIRS,           ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.NETHER_WART, 0.3f,             ToolType.hoe, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.ENCHANTING_TABLE,              ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.BREWING_STAND,                 ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.CAULDRON,                      ToolType.pickaxe, ToolHarvestLevel.bronze);
		//setHarvestLevel(Blocks.END_PORTAL, ToolType., ToolHarvestLevel.);
		//setHarvestLevel(Blocks.END_PORTAL_FRAME, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.END_STONE,                     ToolType.pickaxe, ToolHarvestLevel.iron);
		//setHarvestLevel(Blocks.DRAGON_EGG, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.REDSTONE_LAMP,                 ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.LIT_REDSTONE_LAMP,             ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.DOUBLE_WOODEN_SLAB,            ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.WOODEN_SLAB,                   ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.COCOA, 0.3f,                   ToolType.hoe, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.SANDSTONE_STAIRS,              ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.EMERALD_ORE,                   ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.ENDER_CHEST,                   ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.TRIPWIRE_HOOK,                 ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.TRIPWIRE,                      ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.EMERALD_BLOCK,                 ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.SPRUCE_STAIRS,                 ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.BIRCH_STAIRS,                  ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.JUNGLE_STAIRS,                 ToolType.axe, ToolHarvestLevel.flint);
		//setHarvestLevel(Blocks.COMMAND_BLOCK, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.BEACON,                        ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.COBBLESTONE_WALL,              ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.FLOWER_POT,                    ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.CARROTS, 0.3f,                 ToolType.hoe, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.POTATOES, 0.3f,                ToolType.hoe, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.WOODEN_BUTTON,                 ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.SKULL,                         ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.ANVIL,                         ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.TRAPPED_CHEST,                 ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.UNPOWERED_COMPARATOR,          ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.POWERED_COMPARATOR,            ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.DAYLIGHT_DETECTOR,             ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.DAYLIGHT_DETECTOR_INVERTED,    ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.REDSTONE_BLOCK,                ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Blocks.QUARTZ_ORE,                    ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.HOPPER,                        ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.QUARTZ_BLOCK,                  ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.QUARTZ_STAIRS,                 ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.ACTIVATOR_RAIL,                ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.DROPPER,                       ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.STAINED_HARDENED_CLAY,         ToolType.pickaxe, ToolHarvestLevel.flint);
		//setHarvestLevel(Blocks.BARRIER, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.IRON_TRAPDOOR,                 ToolType.pickaxe, ToolHarvestLevel.bronze);
		setHarvestLevel(Blocks.HAY_BLOCK,                     ToolType.axe, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.CARPET,                        ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.HARDENED_CLAY,                 ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.COAL_BLOCK,                    ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.PACKED_ICE,                    ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.ACACIA_STAIRS,                 ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.DARK_OAK_STAIRS,               ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.SLIME_BLOCK,                   ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.DOUBLE_PLANT,                  ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.STAINED_GLASS,                 ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.STAINED_GLASS_PANE,            ToolType.none, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.PRISMARINE,                    ToolType.pickaxe, ToolHarvestLevel.copper);
		setHarvestLevel(Blocks.SEA_LANTERN,                   ToolType.pickaxe, ToolHarvestLevel.copper);
		setHarvestLevel(Blocks.STANDING_BANNER,               ToolType.axe, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.WALL_BANNER,                   ToolType.axe, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.RED_SANDSTONE,                 ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.RED_SANDSTONE_STAIRS,          ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.DOUBLE_STONE_SLAB2,            ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.STONE_SLAB2,                   ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.END_ROD,                       ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Blocks.CHORUS_PLANT,                  ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.CHORUS_FLOWER,                 ToolType.axe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.PURPUR_BLOCK,                  ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Blocks.PURPUR_PILLAR,                 ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Blocks.PURPUR_STAIRS,                 ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Blocks.PURPUR_DOUBLE_SLAB,            ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Blocks.PURPUR_SLAB,                   ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Blocks.END_BRICKS,                    ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Blocks.BEETROOTS, 0.3f,               ToolType.hoe, ToolHarvestLevel.hand);
		setHarvestLevel(Blocks.GRASS_PATH,                    ToolType.shovel, ToolHarvestLevel.flint);
		//setHarvestLevel(Blocks.END_GATEWAY, ToolType., ToolHarvestLevel.);
		//setHarvestLevel(Blocks.REPEATING_COMMAND_BLOCK, ToolType., ToolHarvestLevel.);
		//setHarvestLevel(Blocks.CHAIN_COMMAND_BLOCK, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.FROSTED_ICE,                   ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.MAGMA,                         ToolType.pickaxe, ToolHarvestLevel.bronze);
		setHarvestLevel(Blocks.NETHER_WART_BLOCK,             ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.RED_NETHER_BRICK,              ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.BONE_BLOCK,                    ToolType.pickaxe, ToolHarvestLevel.flint);
		//setHarvestLevel(Blocks.STRUCTURE_VOID, ToolType., ToolHarvestLevel.);
		setHarvestLevel(Blocks.OBSERVER,                      ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.WHITE_SHULKER_BOX,             ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.ORANGE_SHULKER_BOX,            ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.MAGENTA_SHULKER_BOX,           ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.LIGHT_BLUE_SHULKER_BOX,        ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.YELLOW_SHULKER_BOX,            ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.LIME_SHULKER_BOX,              ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.PINK_SHULKER_BOX,              ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.GRAY_SHULKER_BOX,              ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.SILVER_SHULKER_BOX,            ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.CYAN_SHULKER_BOX,              ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.PURPLE_SHULKER_BOX,            ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.BLUE_SHULKER_BOX,              ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.BROWN_SHULKER_BOX,             ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.GREEN_SHULKER_BOX,             ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.RED_SHULKER_BOX,               ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.BLACK_SHULKER_BOX,             ToolType.pickaxe, ToolHarvestLevel.steel);
		setHarvestLevel(Blocks.WHITE_GLAZED_TERRACOTTA,       ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.ORANGE_GLAZED_TERRACOTTA,      ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.MAGENTA_GLAZED_TERRACOTTA,     ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA,  ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.YELLOW_GLAZED_TERRACOTTA,      ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.LIME_GLAZED_TERRACOTTA,        ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.PINK_GLAZED_TERRACOTTA,        ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.GRAY_GLAZED_TERRACOTTA,        ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.SILVER_GLAZED_TERRACOTTA,      ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.CYAN_GLAZED_TERRACOTTA,        ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.PURPLE_GLAZED_TERRACOTTA,      ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.BLUE_GLAZED_TERRACOTTA,        ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.BROWN_GLAZED_TERRACOTTA,       ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.GREEN_GLAZED_TERRACOTTA,       ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.RED_GLAZED_TERRACOTTA,         ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.BLACK_GLAZED_TERRACOTTA,       ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Blocks.CONCRETE,                      ToolType.pickaxe, ToolHarvestLevel.flint);
		setHarvestLevel(Blocks.CONCRETE_POWDER,               ToolType.shovel, ToolHarvestLevel.flint);
		//setHarvestLevel(Blocks.STRUCTURE_BLOCK, ToolType., ToolHarvestLevel.);
	}
	
	private static void registerItems() {
		setHarvestLevel(Items.SHEARS, ToolType.shears, ToolHarvestLevel.iron);
		
		setHarvestLevel(Items.DIAMOND_PICKAXE, ToolType.pickaxe, ToolHarvestLevel.diamond);
		setHarvestLevel(Items.GOLDEN_PICKAXE, ToolType.pickaxe, ToolHarvestLevel.copper);
		setHarvestLevel(Items.IRON_PICKAXE, ToolType.pickaxe, ToolHarvestLevel.iron);
		setHarvestLevel(Items.STONE_PICKAXE, ToolType.pickaxe, ToolHarvestLevel.stone);
		setHarvestLevel(Items.WOODEN_PICKAXE, ToolType.pickaxe, ToolHarvestLevel.flint);
		
		setHarvestLevel(Items.DIAMOND_SHOVEL, ToolType.shovel, ToolHarvestLevel.diamond);
		setHarvestLevel(Items.GOLDEN_SHOVEL, ToolType.shovel, ToolHarvestLevel.copper);
		setHarvestLevel(Items.IRON_SHOVEL, ToolType.shovel, ToolHarvestLevel.iron);
		setHarvestLevel(Items.STONE_SHOVEL, ToolType.shovel, ToolHarvestLevel.stone);
		setHarvestLevel(Items.WOODEN_SHOVEL, ToolType.shovel, ToolHarvestLevel.flint);
		
		setHarvestLevel(Items.DIAMOND_AXE, ToolType.axe, ToolHarvestLevel.diamond);
		setHarvestLevel(Items.GOLDEN_AXE, ToolType.axe, ToolHarvestLevel.copper);
		setHarvestLevel(Items.IRON_AXE, ToolType.axe, ToolHarvestLevel.iron);
		setHarvestLevel(Items.STONE_AXE, ToolType.axe, ToolHarvestLevel.stone);
		setHarvestLevel(Items.WOODEN_AXE, ToolType.axe, ToolHarvestLevel.flint);
		
		setHarvestLevel(Items.DIAMOND_SWORD, ToolType.sword, ToolHarvestLevel.diamond);
		setHarvestLevel(Items.GOLDEN_SWORD, ToolType.sword, ToolHarvestLevel.copper);
		setHarvestLevel(Items.IRON_SWORD, ToolType.sword, ToolHarvestLevel.iron);
		setHarvestLevel(Items.STONE_SWORD, ToolType.sword, ToolHarvestLevel.stone);
		setHarvestLevel(Items.WOODEN_SWORD, ToolType.sword, ToolHarvestLevel.flint);
		
		setHarvestLevel(Items.DIAMOND_HOE, ToolType.hoe, ToolHarvestLevel.diamond);
		setHarvestLevel(Items.GOLDEN_HOE, ToolType.hoe, ToolHarvestLevel.copper);
		setHarvestLevel(Items.IRON_HOE, ToolType.hoe, ToolHarvestLevel.iron);
		setHarvestLevel(Items.STONE_HOE, ToolType.hoe, ToolHarvestLevel.stone);
		setHarvestLevel(Items.WOODEN_HOE, ToolType.hoe, ToolHarvestLevel.flint);
	}
}
