package mrunknown404.primalrework.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.items.ItemBase;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.ToolHarvestLevel;
import mrunknown404.primalrework.util.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class HarvestHandler {

	public static Map<Block, List<DoubleValue<ToolType, ToolHarvestLevel>>> blocks = new HashMap<Block, List<DoubleValue<ToolType, ToolHarvestLevel>>>();
	public static Map<Item, List<DoubleValue<ToolType, ToolHarvestLevel>>> items = new HashMap<Item, List<DoubleValue<ToolType, ToolHarvestLevel>>>();
	
	public static void changeHarvestLevels() {
		for (Block block : Block.REGISTRY) {
			setHarvestLevel(block, ToolType.none, ToolHarvestLevel.unbreakable);
		}
		
		for (Item item : Item.REGISTRY) {
			setHarvestLevel(item, ToolType.none, ToolHarvestLevel.hand);
		}
		
		for (Block block : ModBlocks.BLOCKS) {
			setHarvestLevel(block, ((BlockBase) block).getToolType(), ((BlockBase) block).getHarvestLevel());
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
	
	public static void setHarvestLevel(Block b, ToolType type, ToolHarvestLevel level) {
		blocks.put(b, Arrays.asList(new DoubleValue<ToolType, ToolHarvestLevel>(type, level)));
	}
	
	public static void setHarvestLevel(Item i, ToolType type, ToolHarvestLevel level) {
		items.put(i, Arrays.asList(new DoubleValue<ToolType, ToolHarvestLevel>(type, level)));
	}
	
	public static void setHarvestLevel(Block b, List<DoubleValue<ToolType, ToolHarvestLevel>> list) {
		blocks.put(b, list);
	}
	
	public static void setHarvestLevel(Item i, List<DoubleValue<ToolType, ToolHarvestLevel>> list) {
		items.put(i, list);
	}
	
	public static void setHarvestLevel(Block b, DoubleValue<ToolType, ToolHarvestLevel>... list) {
		blocks.put(b, Arrays.asList(list));
	}
	
	public static void setHarvestLevel(Item i, DoubleValue<ToolType, ToolHarvestLevel>... list) {
		items.put(i, Arrays.asList(list));
	}
	
	public static boolean canBreak(Block block, Item item) {
		if (!blocks.containsKey(block) || !items.containsKey(item)) {
			return false;
		}
		
		List<DoubleValue<ToolType, ToolHarvestLevel>> dvb = blocks.get(block);
		List<DoubleValue<ToolType, ToolHarvestLevel>> dvi = items.get(item);
		
		//System.out.println(dvb);
		//System.out.println(dvi);
		//System.out.println("---");
		
		for (DoubleValue<ToolType, ToolHarvestLevel> block_dv : dvb) {
			if (block_dv.getR() == ToolHarvestLevel.unbreakable) {
				return false;
			} else if (block_dv.getL() == ToolType.none) {
				return true;
			}
			
			for (DoubleValue<ToolType, ToolHarvestLevel> item_dv : dvi) {
				if (block_dv.getL() == item_dv.getL()) {
					if (item_dv.getR().level >= block_dv.getR().level) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	public static ToolHarvestLevel getCurrentItemHarvestLevel(Block block, Item item) {
		List<DoubleValue<ToolType, ToolHarvestLevel>> dvb = blocks.get(block);
		List<DoubleValue<ToolType, ToolHarvestLevel>> dvi = items.get(item);
		
		for (DoubleValue<ToolType, ToolHarvestLevel> block_dv : dvb) {
			for (DoubleValue<ToolType, ToolHarvestLevel> item_dv : dvi) {
				if (block_dv.getL() == item_dv.getL()) {
					return item_dv.getR();
				}
			}
		}
		
		return null;
	}
	
	private static void registerBlocks() {
		setHarvestLevel(Blocks.TALLGRASS, ToolType.knife, ToolHarvestLevel.flint);
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
