package mrunknown404.primalrework.util.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.blocks.util.BlockSlabBase;
import mrunknown404.primalrework.blocks.util.IBlockBase;
import mrunknown404.primalrework.blocks.util.ISlabBase;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.items.util.IItemBase;
import mrunknown404.primalrework.items.util.ItemBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.jei.JEICompat;
import mrunknown404.primalrework.world.StageWorldSaveData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StageHelper {
	
	public static final Map<EnumStage, List<ItemStack>> ITEM_STAGE_MAP = new HashMap<EnumStage, List<ItemStack>>();
	
	private static EnumStage stage = EnumStage.stage0;
	private static World world;
	
	static {
		for (EnumStage stage : EnumStage.values()) {
			ITEM_STAGE_MAP.put(stage, new ArrayList<ItemStack>());
		}
	}
	
	public static void setStage(EnumStage stage) {
		StageHelper.stage = stage;
		StageWorldSaveData.load(world).markDirty();
		JEICompat.setupRecipesAndItems();
	}
	
	public static EnumStage getStage() {
		return stage;
	}
	
	public static void setStageFromNBT(EnumStage stage) {
		StageHelper.stage = stage;
		JEICompat.setupRecipesAndItems();
	}
	
	public static boolean hasAccessToStage(EnumStage stage) {
		return stage.id <= StageHelper.stage.id;
	}
	
	public static void setWorld(World world) {
		StageHelper.world = world;
		StageWorldSaveData.load(world);
	}
	
	private static void addStagedItem(EnumStage stage, Item item, int meta) {
		if (new ItemStack(item, 1, meta).isEmpty()) {
			System.out.println("EMPTY: " + item.getUnlocalizedName());
			return;
		}
		
		ITEM_STAGE_MAP.get(stage).add(new ItemStack(item, 1, meta));
	}
	
	private static void addStagedItem(EnumStage stage, Item item) {
		addStagedItem(stage, item, 0);
	}
	
	private static void addStagedItem(EnumStage stage, Block block, int meta) {
		if (new ItemStack(block, 1, meta).isEmpty()) {
			System.out.println("EMPTY: " + block.getUnlocalizedName());
			return;
		}
		
		ITEM_STAGE_MAP.get(stage).add(new ItemStack(block, 1, meta));
	}
	
	private static void addStagedItem(EnumStage stage, Block block) {
		addStagedItem(stage, block, 0);
	}
	
	public static void setupItemStages() {
		for (Item item : ModItems.ITEMS) {
			if (item instanceof ItemSlab) {
				addStagedItem(((ISlabBase<BlockSlabBase>) ((ItemBlock) item).getBlock()).getStage(), item);
			} else if (item instanceof ItemBlock) {
				addStagedItem(((IBlockBase<BlockBase>) ((ItemBlock) item).getBlock()).getStage(), item);
			} else {
				addStagedItem(((IItemBase<ItemBase>) item).getStage(), item);
			}
		}
		
		for (int i = 0; i < 3; i++) {
			addStagedItem(EnumStage.stage0, Blocks.DIRT, i);
			addStagedItem(EnumStage.stage0, Blocks.TALLGRASS, i);
		}
		for (int i = 0; i < 4; i++) {
			if (i <= 1) {
				addStagedItem(EnumStage.stage0, Blocks.LOG2, i);
				addStagedItem(EnumStage.stage0, Blocks.LEAVES2, i);
			}
			addStagedItem(EnumStage.stage0, Blocks.LOG, i);
			addStagedItem(EnumStage.stage0, Blocks.LEAVES, i);
			addStagedItem(EnumStage.stage0, Items.FISH, i);
		}
		for (int i = 0; i < 6; i++) {
			addStagedItem(EnumStage.stage1, Blocks.PLANKS, i);
			addStagedItem(EnumStage.stage1, Blocks.WOODEN_SLAB, i);
			addStagedItem(EnumStage.stage0, Blocks.SAPLING, i);
			addStagedItem(EnumStage.stage0, Blocks.DOUBLE_PLANT, i);
			addStagedItem(EnumStage.do_later, Items.SKULL, i);
			addStagedItem(EnumStage.no_show, Blocks.MONSTER_EGG, i);
		}
		for (int i = 0; i < 7; i++) {
			addStagedItem(EnumStage.stage0, Blocks.STONE, i);
		}
		for (int i = 0; i < 9; i++) {
			addStagedItem(EnumStage.stage0, Blocks.RED_FLOWER, i);
		}
		for (int i = 0; i < 16; i++) {
			addStagedItem(EnumStage.do_later, Items.DYE, i);
			addStagedItem(EnumStage.stage1, Blocks.WOOL, i);
			addStagedItem(EnumStage.stage1, Blocks.CARPET, i);
			addStagedItem(EnumStage.do_later, Blocks.STAINED_HARDENED_CLAY, i);
			addStagedItem(EnumStage.do_later, Blocks.STAINED_GLASS, i);
			addStagedItem(EnumStage.do_later, Blocks.STAINED_GLASS_PANE, i);
			addStagedItem(EnumStage.stage1, Items.BED, i);
			addStagedItem(EnumStage.do_later, Items.BANNER, i);
			addStagedItem(EnumStage.do_later, Blocks.CONCRETE, i);
			addStagedItem(EnumStage.do_later, Blocks.CONCRETE_POWDER, i);
		}
		
		addStagedItem(EnumStage.stage0, Blocks.GRASS);
		addStagedItem(EnumStage.stage0, Blocks.COBBLESTONE);
		addStagedItem(EnumStage.no_show, Blocks.BEDROCK);
		addStagedItem(EnumStage.stage0, Blocks.SAND, 0);
		addStagedItem(EnumStage.stage0, Blocks.SAND, 1);
		addStagedItem(EnumStage.stage0, Blocks.GRAVEL);
		addStagedItem(EnumStage.do_later, Blocks.GOLD_ORE);
		addStagedItem(EnumStage.do_later, Blocks.IRON_ORE);
		addStagedItem(EnumStage.do_later, Blocks.COAL_ORE);
		addStagedItem(EnumStage.do_later, Blocks.SPONGE, 0);
		addStagedItem(EnumStage.do_later, Blocks.SPONGE, 1);
		addStagedItem(EnumStage.do_later, Blocks.GLASS);
		addStagedItem(EnumStage.do_later, Blocks.LAPIS_ORE);
		addStagedItem(EnumStage.do_later, Blocks.LAPIS_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.DISPENSER);
		addStagedItem(EnumStage.do_later, Blocks.SANDSTONE, 0);
		addStagedItem(EnumStage.do_later, Blocks.SANDSTONE, 1);
		addStagedItem(EnumStage.do_later, Blocks.SANDSTONE, 2);
		addStagedItem(EnumStage.do_later, Blocks.NOTEBLOCK);
		addStagedItem(EnumStage.do_later, Blocks.GOLDEN_RAIL);
		addStagedItem(EnumStage.do_later, Blocks.DETECTOR_RAIL);
		addStagedItem(EnumStage.do_later, Blocks.STICKY_PISTON);
		addStagedItem(EnumStage.do_later, Blocks.WEB);
		addStagedItem(EnumStage.stage0, Blocks.DEADBUSH);
		addStagedItem(EnumStage.do_later, Blocks.PISTON);
		addStagedItem(EnumStage.stage0, Blocks.YELLOW_FLOWER);
		addStagedItem(EnumStage.stage0, Blocks.BROWN_MUSHROOM);
		addStagedItem(EnumStage.stage0, Blocks.RED_MUSHROOM);
		addStagedItem(EnumStage.do_later, Blocks.GOLD_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.IRON_BLOCK);
		addStagedItem(EnumStage.stage1, Blocks.STONE_SLAB, 0);
		addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 1);
		addStagedItem(EnumStage.no_show, Blocks.STONE_SLAB, 2);
		addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 3);
		addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 4);
		addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 5);
		addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 6);
		addStagedItem(EnumStage.do_later, Blocks.STONE_SLAB, 7);
		addStagedItem(EnumStage.do_later, Blocks.BRICK_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.TNT);
		addStagedItem(EnumStage.do_later, Blocks.BOOKSHELF);
		addStagedItem(EnumStage.stage0, Blocks.MOSSY_COBBLESTONE);
		addStagedItem(EnumStage.do_later, Blocks.OBSIDIAN);
		addStagedItem(EnumStage.do_later, Blocks.TORCH);
		addStagedItem(EnumStage.no_show, Blocks.MOB_SPAWNER);
		addStagedItem(EnumStage.do_later, Blocks.OAK_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.CHEST);
		addStagedItem(EnumStage.do_later, Blocks.DIAMOND_ORE);
		addStagedItem(EnumStage.do_later, Blocks.DIAMOND_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.CRAFTING_TABLE);
		addStagedItem(EnumStage.stage1, Blocks.FARMLAND);
		addStagedItem(EnumStage.do_later, Blocks.FURNACE);
		addStagedItem(EnumStage.do_later, Blocks.LADDER);
		addStagedItem(EnumStage.do_later, Blocks.RAIL);
		addStagedItem(EnumStage.do_later, Blocks.STONE_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.LEVER);
		addStagedItem(EnumStage.do_later, Blocks.STONE_PRESSURE_PLATE);
		addStagedItem(EnumStage.do_later, Blocks.WOODEN_PRESSURE_PLATE);
		addStagedItem(EnumStage.do_later, Blocks.REDSTONE_ORE);
		addStagedItem(EnumStage.do_later, Blocks.REDSTONE_TORCH);
		addStagedItem(EnumStage.do_later, Blocks.STONE_BUTTON);
		addStagedItem(EnumStage.stage0, Blocks.SNOW_LAYER);
		addStagedItem(EnumStage.stage0, Blocks.ICE);
		addStagedItem(EnumStage.stage0, Blocks.SNOW);
		addStagedItem(EnumStage.stage1, Blocks.CACTUS);
		addStagedItem(EnumStage.stage1, Blocks.CLAY);
		addStagedItem(EnumStage.do_later, Blocks.JUKEBOX);
		addStagedItem(EnumStage.do_later, Blocks.OAK_FENCE);
		addStagedItem(EnumStage.do_later, Blocks.SPRUCE_FENCE);
		addStagedItem(EnumStage.do_later, Blocks.BIRCH_FENCE);
		addStagedItem(EnumStage.do_later, Blocks.JUNGLE_FENCE);
		addStagedItem(EnumStage.do_later, Blocks.DARK_OAK_FENCE);
		addStagedItem(EnumStage.do_later, Blocks.ACACIA_FENCE);
		addStagedItem(EnumStage.stage1, Blocks.PUMPKIN);
		addStagedItem(EnumStage.do_later, Blocks.NETHERRACK);
		addStagedItem(EnumStage.do_later, Blocks.SOUL_SAND);
		addStagedItem(EnumStage.do_later, Blocks.GLOWSTONE);
		addStagedItem(EnumStage.stage1, Blocks.LIT_PUMPKIN);
		addStagedItem(EnumStage.do_later, Blocks.TRAPDOOR);
		addStagedItem(EnumStage.do_later, Blocks.STONEBRICK, 0);
		addStagedItem(EnumStage.do_later, Blocks.STONEBRICK, 1);
		addStagedItem(EnumStage.do_later, Blocks.STONEBRICK, 2);
		addStagedItem(EnumStage.do_later, Blocks.STONEBRICK, 3);
		addStagedItem(EnumStage.stage0, Blocks.BROWN_MUSHROOM_BLOCK);
		addStagedItem(EnumStage.stage0, Blocks.RED_MUSHROOM_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.IRON_BARS);
		addStagedItem(EnumStage.do_later, Blocks.GLASS_PANE);
		addStagedItem(EnumStage.stage1, Blocks.MELON_BLOCK);
		addStagedItem(EnumStage.stage1, Blocks.VINE);
		addStagedItem(EnumStage.do_later, Blocks.OAK_FENCE_GATE);
		addStagedItem(EnumStage.do_later, Blocks.SPRUCE_FENCE_GATE);
		addStagedItem(EnumStage.do_later, Blocks.BIRCH_FENCE_GATE);
		addStagedItem(EnumStage.do_later, Blocks.JUNGLE_FENCE_GATE);
		addStagedItem(EnumStage.do_later, Blocks.DARK_OAK_FENCE_GATE);
		addStagedItem(EnumStage.do_later, Blocks.ACACIA_FENCE_GATE);
		addStagedItem(EnumStage.do_later, Blocks.BRICK_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.STONE_BRICK_STAIRS);
		addStagedItem(EnumStage.stage0, Blocks.MYCELIUM);
		addStagedItem(EnumStage.stage0, Blocks.WATERLILY);
		addStagedItem(EnumStage.do_later, Blocks.NETHER_BRICK);
		addStagedItem(EnumStage.do_later, Blocks.NETHER_BRICK_FENCE);
		addStagedItem(EnumStage.do_later, Blocks.NETHER_BRICK_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.ENCHANTING_TABLE);
		addStagedItem(EnumStage.no_show, Blocks.END_PORTAL_FRAME);
		addStagedItem(EnumStage.do_later, Blocks.END_STONE);
		addStagedItem(EnumStage.do_later, Blocks.DRAGON_EGG);
		addStagedItem(EnumStage.do_later, Blocks.REDSTONE_LAMP);
		addStagedItem(EnumStage.do_later, Blocks.SANDSTONE_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.EMERALD_ORE);
		addStagedItem(EnumStage.do_later, Blocks.ENDER_CHEST);
		addStagedItem(EnumStage.do_later, Blocks.TRIPWIRE_HOOK);
		addStagedItem(EnumStage.do_later, Blocks.EMERALD_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.SPRUCE_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.BIRCH_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.JUNGLE_STAIRS);
		addStagedItem(EnumStage.no_show, Blocks.COMMAND_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.BEACON);
		addStagedItem(EnumStage.do_later, Blocks.COBBLESTONE_WALL, 0);
		addStagedItem(EnumStage.do_later, Blocks.COBBLESTONE_WALL, 1);
		addStagedItem(EnumStage.do_later, Blocks.WOODEN_BUTTON);
		addStagedItem(EnumStage.do_later, Blocks.ANVIL, 0);
		addStagedItem(EnumStage.do_later, Blocks.ANVIL, 1);
		addStagedItem(EnumStage.do_later, Blocks.ANVIL, 2);
		addStagedItem(EnumStage.do_later, Blocks.TRAPPED_CHEST);
		addStagedItem(EnumStage.do_later, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
		addStagedItem(EnumStage.do_later, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
		addStagedItem(EnumStage.do_later, Blocks.DAYLIGHT_DETECTOR);
		addStagedItem(EnumStage.do_later, Blocks.REDSTONE_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.QUARTZ_ORE);
		addStagedItem(EnumStage.do_later, Blocks.HOPPER);
		addStagedItem(EnumStage.do_later, Blocks.QUARTZ_BLOCK, 0);
		addStagedItem(EnumStage.do_later, Blocks.QUARTZ_BLOCK, 1);
		addStagedItem(EnumStage.do_later, Blocks.QUARTZ_BLOCK, 2);
		addStagedItem(EnumStage.do_later, Blocks.QUARTZ_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.ACTIVATOR_RAIL);
		addStagedItem(EnumStage.do_later, Blocks.DROPPER);
		addStagedItem(EnumStage.do_later, Blocks.BARRIER);
		addStagedItem(EnumStage.do_later, Blocks.IRON_TRAPDOOR);
		addStagedItem(EnumStage.do_later, Blocks.HAY_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.HARDENED_CLAY);
		addStagedItem(EnumStage.do_later, Blocks.COAL_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.PACKED_ICE);
		addStagedItem(EnumStage.do_later, Blocks.ACACIA_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.DARK_OAK_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.SLIME_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.PRISMARINE, 0);
		addStagedItem(EnumStage.do_later, Blocks.PRISMARINE, 1);
		addStagedItem(EnumStage.do_later, Blocks.PRISMARINE, 2);
		addStagedItem(EnumStage.do_later, Blocks.SEA_LANTERN);
		addStagedItem(EnumStage.do_later, Blocks.RED_SANDSTONE, 0);
		addStagedItem(EnumStage.do_later, Blocks.RED_SANDSTONE, 1);
		addStagedItem(EnumStage.do_later, Blocks.RED_SANDSTONE, 2);
		addStagedItem(EnumStage.do_later, Blocks.RED_SANDSTONE_STAIRS);
		addStagedItem(EnumStage.stage1, Blocks.DOUBLE_STONE_SLAB2);
		addStagedItem(EnumStage.stage1, Blocks.STONE_SLAB2);
		addStagedItem(EnumStage.do_later, Blocks.END_ROD);
		addStagedItem(EnumStage.do_later, Blocks.CHORUS_PLANT);
		addStagedItem(EnumStage.do_later, Blocks.CHORUS_FLOWER);
		addStagedItem(EnumStage.do_later, Blocks.PURPUR_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.PURPUR_PILLAR);
		addStagedItem(EnumStage.do_later, Blocks.PURPUR_STAIRS);
		addStagedItem(EnumStage.do_later, Blocks.PURPUR_DOUBLE_SLAB);
		addStagedItem(EnumStage.do_later, Blocks.PURPUR_SLAB);
		addStagedItem(EnumStage.do_later, Blocks.END_BRICKS);
		addStagedItem(EnumStage.do_later, Blocks.GRASS_PATH);
		addStagedItem(EnumStage.no_show, Blocks.REPEATING_COMMAND_BLOCK);
		addStagedItem(EnumStage.no_show, Blocks.CHAIN_COMMAND_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.MAGMA);
		addStagedItem(EnumStage.do_later, Blocks.NETHER_WART_BLOCK);
		addStagedItem(EnumStage.do_later, Blocks.RED_NETHER_BRICK);
		addStagedItem(EnumStage.do_later, Blocks.BONE_BLOCK);
		addStagedItem(EnumStage.no_show, Blocks.STRUCTURE_VOID);
		addStagedItem(EnumStage.do_later, Blocks.OBSERVER);
		addStagedItem(EnumStage.do_later, Blocks.WHITE_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.ORANGE_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.MAGENTA_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.LIGHT_BLUE_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.YELLOW_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.LIME_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.PINK_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.GRAY_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.SILVER_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.CYAN_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.PURPLE_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.BLUE_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.BROWN_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.GREEN_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.RED_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.BLACK_SHULKER_BOX);
		addStagedItem(EnumStage.do_later, Blocks.WHITE_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.ORANGE_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.MAGENTA_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.YELLOW_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.LIME_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.PINK_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.GRAY_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.SILVER_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.CYAN_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.PURPLE_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.BLUE_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.BROWN_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.GREEN_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.RED_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.BLACK_GLAZED_TERRACOTTA);
		addStagedItem(EnumStage.do_later, Blocks.STRUCTURE_BLOCK);
		
		addStagedItem(EnumStage.do_later, Items.IRON_SHOVEL);
		addStagedItem(EnumStage.do_later, Items.IRON_PICKAXE);
		addStagedItem(EnumStage.do_later, Items.IRON_AXE);
		addStagedItem(EnumStage.do_later, Items.FLINT_AND_STEEL);
		addStagedItem(EnumStage.stage0, Items.APPLE);
		addStagedItem(EnumStage.do_later, Items.BOW);
		addStagedItem(EnumStage.do_later, Items.ARROW);
		addStagedItem(EnumStage.do_later, Items.SPECTRAL_ARROW);
		addStagedItem(EnumStage.do_later, Items.COAL, 0);
		addStagedItem(EnumStage.do_later, Items.COAL, 1);
		addStagedItem(EnumStage.do_later, Items.DIAMOND);
		addStagedItem(EnumStage.do_later, Items.IRON_INGOT);
		addStagedItem(EnumStage.do_later, Items.GOLD_INGOT);
		addStagedItem(EnumStage.do_later, Items.IRON_SWORD);
		addStagedItem(EnumStage.stage1, Items.WOODEN_SWORD);
		addStagedItem(EnumStage.stage1, Items.WOODEN_SHOVEL);
		addStagedItem(EnumStage.stage1, Items.WOODEN_PICKAXE);
		addStagedItem(EnumStage.stage1, Items.WOODEN_AXE);
		addStagedItem(EnumStage.stage2, Items.STONE_SWORD);
		addStagedItem(EnumStage.stage2, Items.STONE_SHOVEL);
		addStagedItem(EnumStage.stage2, Items.STONE_PICKAXE);
		addStagedItem(EnumStage.stage2, Items.STONE_AXE);
		addStagedItem(EnumStage.do_later, Items.DIAMOND_SWORD);
		addStagedItem(EnumStage.do_later, Items.DIAMOND_SHOVEL);
		addStagedItem(EnumStage.do_later, Items.DIAMOND_PICKAXE);
		addStagedItem(EnumStage.do_later, Items.DIAMOND_AXE);
		addStagedItem(EnumStage.stage0, Items.STICK);
		addStagedItem(EnumStage.stage1, Items.BOWL);
		addStagedItem(EnumStage.do_later, Items.MUSHROOM_STEW);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_SWORD);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_SHOVEL);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_PICKAXE);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_AXE);
		addStagedItem(EnumStage.stage1, Items.STRING);
		addStagedItem(EnumStage.stage0, Items.FEATHER);
		addStagedItem(EnumStage.stage0, Items.GUNPOWDER);
		addStagedItem(EnumStage.stage1, Items.WOODEN_HOE);
		addStagedItem(EnumStage.stage2, Items.STONE_HOE);
		addStagedItem(EnumStage.do_later, Items.IRON_HOE);
		addStagedItem(EnumStage.do_later, Items.DIAMOND_HOE);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_HOE);
		addStagedItem(EnumStage.stage1, Items.WHEAT_SEEDS);
		addStagedItem(EnumStage.stage1, Items.WHEAT);
		addStagedItem(EnumStage.stage1, Items.BREAD);
		addStagedItem(EnumStage.stage1, Items.LEATHER_HELMET);
		addStagedItem(EnumStage.stage1, Items.LEATHER_CHESTPLATE);
		addStagedItem(EnumStage.stage1, Items.LEATHER_LEGGINGS);
		addStagedItem(EnumStage.stage1, Items.LEATHER_BOOTS);
		addStagedItem(EnumStage.do_later, Items.CHAINMAIL_HELMET);
		addStagedItem(EnumStage.do_later, Items.CHAINMAIL_CHESTPLATE);
		addStagedItem(EnumStage.do_later, Items.CHAINMAIL_LEGGINGS);
		addStagedItem(EnumStage.do_later, Items.CHAINMAIL_BOOTS);
		addStagedItem(EnumStage.do_later, Items.IRON_HELMET);
		addStagedItem(EnumStage.do_later, Items.IRON_CHESTPLATE);
		addStagedItem(EnumStage.do_later, Items.IRON_LEGGINGS);
		addStagedItem(EnumStage.do_later, Items.IRON_BOOTS);
		addStagedItem(EnumStage.do_later, Items.DIAMOND_HELMET);
		addStagedItem(EnumStage.do_later, Items.DIAMOND_CHESTPLATE);
		addStagedItem(EnumStage.do_later, Items.DIAMOND_LEGGINGS);
		addStagedItem(EnumStage.do_later, Items.DIAMOND_BOOTS);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_HELMET);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_CHESTPLATE);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_LEGGINGS);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_BOOTS);
		addStagedItem(EnumStage.stage0, Items.FLINT);
		addStagedItem(EnumStage.stage0, Items.PORKCHOP);
		addStagedItem(EnumStage.stage0, Items.COOKED_PORKCHOP);
		addStagedItem(EnumStage.do_later, Items.PAINTING);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_APPLE, 0);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_APPLE, 1);
		addStagedItem(EnumStage.do_later, Items.SIGN);
		addStagedItem(EnumStage.do_later, Items.OAK_DOOR);
		addStagedItem(EnumStage.do_later, Items.SPRUCE_DOOR);
		addStagedItem(EnumStage.do_later, Items.BIRCH_DOOR);
		addStagedItem(EnumStage.do_later, Items.JUNGLE_DOOR);
		addStagedItem(EnumStage.do_later, Items.ACACIA_DOOR);
		addStagedItem(EnumStage.do_later, Items.DARK_OAK_DOOR);
		addStagedItem(EnumStage.do_later, Items.BUCKET);
		addStagedItem(EnumStage.do_later, Items.WATER_BUCKET);
		addStagedItem(EnumStage.do_later, Items.LAVA_BUCKET);
		addStagedItem(EnumStage.do_later, Items.MINECART);
		addStagedItem(EnumStage.do_later, Items.SADDLE);
		addStagedItem(EnumStage.do_later, Items.IRON_DOOR);
		addStagedItem(EnumStage.do_later, Items.REDSTONE);
		addStagedItem(EnumStage.stage1, Items.SNOWBALL);
		addStagedItem(EnumStage.do_later, Items.BOAT);
		addStagedItem(EnumStage.do_later, Items.SPRUCE_BOAT);
		addStagedItem(EnumStage.do_later, Items.BIRCH_BOAT);
		addStagedItem(EnumStage.do_later, Items.JUNGLE_BOAT);
		addStagedItem(EnumStage.do_later, Items.ACACIA_BOAT);
		addStagedItem(EnumStage.do_later, Items.DARK_OAK_BOAT);
		addStagedItem(EnumStage.stage1, Items.LEATHER);
		addStagedItem(EnumStage.do_later, Items.MILK_BUCKET);
		addStagedItem(EnumStage.do_later, Items.BRICK);
		addStagedItem(EnumStage.stage1, Items.CLAY_BALL);
		addStagedItem(EnumStage.stage1, Items.REEDS);
		addStagedItem(EnumStage.do_later, Items.PAPER);
		addStagedItem(EnumStage.do_later, Items.BOOK);
		addStagedItem(EnumStage.stage0, Items.SLIME_BALL);
		addStagedItem(EnumStage.do_later, Items.CHEST_MINECART);
		addStagedItem(EnumStage.do_later, Items.FURNACE_MINECART);
		addStagedItem(EnumStage.stage0, Items.EGG);
		addStagedItem(EnumStage.do_later, Items.COMPASS);
		addStagedItem(EnumStage.do_later, Items.FISHING_ROD);
		addStagedItem(EnumStage.do_later, Items.CLOCK);
		addStagedItem(EnumStage.do_later, Items.GLOWSTONE_DUST);
		addStagedItem(EnumStage.stage0, Items.COOKED_FISH, 0);
		addStagedItem(EnumStage.stage0, Items.COOKED_FISH, 1);
		addStagedItem(EnumStage.stage0, Items.BONE);
		addStagedItem(EnumStage.do_later, Items.SUGAR);
		addStagedItem(EnumStage.do_later, Items.CAKE);
		addStagedItem(EnumStage.do_later, Items.REPEATER);
		addStagedItem(EnumStage.do_later, Items.COOKIE);
		addStagedItem(EnumStage.do_later, Items.FILLED_MAP);
		addStagedItem(EnumStage.do_later, Items.SHEARS);
		addStagedItem(EnumStage.stage1, Items.MELON);
		addStagedItem(EnumStage.stage1, Items.PUMPKIN_SEEDS);
		addStagedItem(EnumStage.stage1, Items.MELON_SEEDS);
		addStagedItem(EnumStage.stage0, Items.BEEF);
		addStagedItem(EnumStage.stage0, Items.COOKED_BEEF);
		addStagedItem(EnumStage.stage0, Items.CHICKEN);
		addStagedItem(EnumStage.stage0, Items.COOKED_CHICKEN);
		addStagedItem(EnumStage.stage0, Items.MUTTON);
		addStagedItem(EnumStage.stage0, Items.COOKED_MUTTON);
		addStagedItem(EnumStage.stage0, Items.RABBIT);
		addStagedItem(EnumStage.stage0, Items.COOKED_RABBIT);
		addStagedItem(EnumStage.do_later, Items.RABBIT_STEW);
		addStagedItem(EnumStage.stage0, Items.RABBIT_FOOT);
		addStagedItem(EnumStage.stage0, Items.RABBIT_HIDE);
		addStagedItem(EnumStage.stage0, Items.ROTTEN_FLESH);
		addStagedItem(EnumStage.stage0, Items.ENDER_PEARL);
		addStagedItem(EnumStage.do_later, Items.BLAZE_ROD);
		addStagedItem(EnumStage.do_later, Items.GHAST_TEAR);
		addStagedItem(EnumStage.do_later, Items.GOLD_NUGGET);
		addStagedItem(EnumStage.do_later, Items.NETHER_WART);
		addStagedItem(EnumStage.do_later, Items.GLASS_BOTTLE);
		addStagedItem(EnumStage.do_later, Items.DRAGON_BREATH);
		addStagedItem(EnumStage.stage0, Items.SPIDER_EYE);
		addStagedItem(EnumStage.do_later, Items.FERMENTED_SPIDER_EYE);
		addStagedItem(EnumStage.do_later, Items.BLAZE_POWDER);
		addStagedItem(EnumStage.do_later, Items.MAGMA_CREAM);
		addStagedItem(EnumStage.do_later, Items.BREWING_STAND);
		addStagedItem(EnumStage.do_later, Items.CAULDRON);
		addStagedItem(EnumStage.do_later, Items.ENDER_EYE);
		addStagedItem(EnumStage.do_later, Items.SPECKLED_MELON);
		addStagedItem(EnumStage.do_later, Items.EXPERIENCE_BOTTLE);
		addStagedItem(EnumStage.do_later, Items.FIRE_CHARGE);
		addStagedItem(EnumStage.do_later, Items.WRITABLE_BOOK);
		addStagedItem(EnumStage.do_later, Items.WRITTEN_BOOK);
		addStagedItem(EnumStage.do_later, Items.EMERALD);
		addStagedItem(EnumStage.do_later, Items.ITEM_FRAME);
		addStagedItem(EnumStage.do_later, Items.FLOWER_POT);
		addStagedItem(EnumStage.stage1, Items.CARROT);
		addStagedItem(EnumStage.stage1, Items.POTATO);
		addStagedItem(EnumStage.stage1, Items.BAKED_POTATO);
		addStagedItem(EnumStage.stage1, Items.POISONOUS_POTATO);
		addStagedItem(EnumStage.do_later, Items.MAP);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_CARROT);
		addStagedItem(EnumStage.do_later, Items.CARROT_ON_A_STICK);
		addStagedItem(EnumStage.do_later, Items.NETHER_STAR);
		addStagedItem(EnumStage.do_later, Items.PUMPKIN_PIE);
		addStagedItem(EnumStage.do_later, Items.FIREWORKS);
		addStagedItem(EnumStage.do_later, Items.FIREWORK_CHARGE);
		addStagedItem(EnumStage.do_later, Items.ENCHANTED_BOOK);
		addStagedItem(EnumStage.do_later, Items.COMPARATOR);
		addStagedItem(EnumStage.do_later, Items.NETHERBRICK);
		addStagedItem(EnumStage.do_later, Items.QUARTZ);
		addStagedItem(EnumStage.do_later, Items.TNT_MINECART);
		addStagedItem(EnumStage.do_later, Items.HOPPER_MINECART);
		addStagedItem(EnumStage.do_later, Items.ARMOR_STAND);
		addStagedItem(EnumStage.do_later, Items.IRON_HORSE_ARMOR);
		addStagedItem(EnumStage.do_later, Items.GOLDEN_HORSE_ARMOR);
		addStagedItem(EnumStage.do_later, Items.DIAMOND_HORSE_ARMOR);
		addStagedItem(EnumStage.do_later, Items.LEAD);
		addStagedItem(EnumStage.do_later, Items.NAME_TAG);
		addStagedItem(EnumStage.no_show, Items.COMMAND_BLOCK_MINECART);
		addStagedItem(EnumStage.stage0, Items.RECORD_13);
		addStagedItem(EnumStage.stage0, Items.RECORD_CAT);
		addStagedItem(EnumStage.stage0, Items.RECORD_BLOCKS);
		addStagedItem(EnumStage.stage0, Items.RECORD_CHIRP);
		addStagedItem(EnumStage.stage0, Items.RECORD_FAR);
		addStagedItem(EnumStage.stage0, Items.RECORD_MALL);
		addStagedItem(EnumStage.stage0, Items.RECORD_MELLOHI);
		addStagedItem(EnumStage.stage0, Items.RECORD_STAL);
		addStagedItem(EnumStage.stage0, Items.RECORD_STRAD);
		addStagedItem(EnumStage.stage0, Items.RECORD_WARD);
		addStagedItem(EnumStage.stage0, Items.RECORD_11);
		addStagedItem(EnumStage.stage0, Items.RECORD_WAIT);
		addStagedItem(EnumStage.do_later, Items.PRISMARINE_SHARD);
		addStagedItem(EnumStage.do_later, Items.PRISMARINE_CRYSTALS);
		addStagedItem(EnumStage.do_later, Items.END_CRYSTAL);
		addStagedItem(EnumStage.do_later, Items.SHIELD);
		addStagedItem(EnumStage.do_later, Items.ELYTRA);
		addStagedItem(EnumStage.do_later, Items.CHORUS_FRUIT);
		addStagedItem(EnumStage.do_later, Items.CHORUS_FRUIT_POPPED);
		addStagedItem(EnumStage.stage1, Items.BEETROOT_SEEDS);
		addStagedItem(EnumStage.stage1, Items.BEETROOT);
		addStagedItem(EnumStage.do_later, Items.BEETROOT_SOUP);
		addStagedItem(EnumStage.do_later, Items.TOTEM_OF_UNDYING);
		addStagedItem(EnumStage.do_later, Items.SHULKER_SHELL);
		addStagedItem(EnumStage.do_later, Items.IRON_NUGGET);
		addStagedItem(EnumStage.do_later, Items.KNOWLEDGE_BOOK);
	}
}
