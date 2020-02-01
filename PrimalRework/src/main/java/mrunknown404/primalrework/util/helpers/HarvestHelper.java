package mrunknown404.primalrework.util.helpers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo;
import mrunknown404.primalrework.util.harvest.ItemHarvestInfo;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class HarvestHelper {
	
	public static final Map<Block, BlockHarvestInfo> BLOCKS = new HashMap<Block, BlockHarvestInfo>();
	public static final Map<Item, ItemHarvestInfo> ITEMS = new HashMap<Item, ItemHarvestInfo>();
	
	public static void setHarvestLevel(Block b, EnumToolType type, EnumToolMaterial level) {
		setHarvestLevel(b, -1, Arrays.asList(new DoubleValue<EnumToolType, EnumToolMaterial>(type, level)));
	}
	
	public static void setHarvestLevel(Block b, float hardness, EnumToolType type, EnumToolMaterial level) {
		setHarvestLevel(b, hardness, Arrays.asList(new DoubleValue<EnumToolType, EnumToolMaterial>(type, level)));
	}
	
	public static void setHarvestLevel(Block b, List<DoubleValue<EnumToolType, EnumToolMaterial>> harvest) {
		setHarvestLevel(b, -1, harvest);
	}
	
	public static void setHarvestLevel(Block b, float hardness, BlockHarvestInfo info) {
		setHarvestLevel(b, hardness, info.getHarvests(), info.getAllDrops().toArray(new HarvestDropInfo[0]));
	}
	
	public static void setHarvestLevel(Block b, BlockHarvestInfo info) {
		setHarvestLevel(b, -1, info.getHarvests(), info.getAllDrops().toArray(new HarvestDropInfo[0]));
	}
	
	public static void setHarvestLevel(Block b, float hardness, List<DoubleValue<EnumToolType, EnumToolMaterial>> harvest, HarvestDropInfo... drops) {
		BlockHarvestInfo info = new BlockHarvestInfo(harvest);
		info = info.setDrops(drops);
		
		if (hardness != -1f) {
			b.setHardness(hardness);
		}
		
		if (BLOCKS.containsKey(b)) {
			BLOCKS.remove(b);
		}
		BLOCKS.put(b, info);
	}
	
	public static void setHarvestLevel(Item i, EnumToolType type, EnumToolMaterial level) {
		ItemHarvestInfo info = new ItemHarvestInfo(type, level);
		
		if (ITEMS.containsKey(i)) {
			ITEMS.remove(i);
		}
		ITEMS.put(i, info);
	}
	
	public static boolean canBreak(Block block, @Nullable Item item) {
		BlockHarvestInfo binfo = getHarvestInfo(block);
		if (binfo == null || HarvestHelper.hasToolMaterial(block, EnumToolMaterial.unbreakable)) {
			return false;
		} else if (item == null && binfo.canBreakWithNone()) {
			return true;
		}
		
		ItemHarvestInfo iinfo = getHarvestInfo(item);
		if (iinfo == null) {
			return false;
		}
		
		if (binfo.getToolMaterials().contains(EnumToolMaterial.hand)) {
			return true;
		}
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> block_dv : binfo.getHarvests()) {
			for (EnumToolType itemType : iinfo.getToolTypes()) {
				if (block_dv.getL() == itemType) {
					if (iinfo.getMaterial().level >= block_dv.getR().level) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static boolean hasToolType(Item item, EnumToolType type) {
		return getHarvestInfo(item).getToolTypes().contains(type);
	}
	
	public static boolean hasToolType(Block block, EnumToolType type) {
		return getHarvestInfo(block).getToolTypes().contains(type);
	}
	
	public static boolean hasToolMaterial(Block block, EnumToolMaterial type) {
		return getHarvestInfo(block).getToolMaterials().contains(type);
	}
	
	public static BlockHarvestInfo getHarvestInfo(Block block) {
		Iterator<Entry<Block, BlockHarvestInfo>> it = BLOCKS.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Block, BlockHarvestInfo> pair = it.next();
			if (pair.getKey() == block) {
				return pair.getValue();
			}
		}
		
		return null;
	}
	
	public static ItemHarvestInfo getHarvestInfo(Item item) {
		Iterator<Entry<Item, ItemHarvestInfo>> it = ITEMS.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Item, ItemHarvestInfo> pair = it.next();
			if (pair.getKey() == item) {
				return pair.getValue();
			}
		}
		
		return null;
	}
	
	public static Item getItem(ItemHarvestInfo info) {
		Iterator<Entry<Item, ItemHarvestInfo>> it = ITEMS.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Item, ItemHarvestInfo> pair = it.next();
			if (pair.getValue() == info) {
				return pair.getKey();
			}
		}
		
		return null;
	}
	
	public static Block getBlock(BlockHarvestInfo info) {
		Iterator<Entry<Block, BlockHarvestInfo>> it = BLOCKS.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Block, BlockHarvestInfo> pair = it.next();
			if (pair.getValue() == info) {
				return pair.getKey();
			}
		}
		
		return null;
	}
	
	public static float getItemsHarvestSpeed(Block block, @Nullable Item item) {
		BlockHarvestInfo binfo = getHarvestInfo(block);
		ItemHarvestInfo iinfo = getHarvestInfo(item);
		if (item == null || binfo == null || iinfo == null) {
			return EnumToolMaterial.hand.speed;
		}
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> block_dv : binfo.getHarvests()) {
			for (EnumToolType toolType : iinfo.getToolTypes()) {
				if (block_dv.getL() == toolType) {
					return iinfo.getMaterial().speed;
				}
			}
		}
		
		return EnumToolMaterial.hand.speed;
	}
	
	public static EnumToolType getItemsToolType(Block block, Item item) {
		BlockHarvestInfo binfo = getHarvestInfo(block);
		ItemHarvestInfo iinfo = getHarvestInfo(item);
		if (binfo == null || iinfo == null) {
			return null;
		}
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> block_dv : binfo.getHarvests()) {
			for (EnumToolType toolType : iinfo.getToolTypes()) {
				if (block_dv.getL() == toolType) {
					return toolType;
				}
			}
		}
		
		return EnumToolType.none;
	}
}
