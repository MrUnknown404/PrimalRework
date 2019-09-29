package mrunknown404.primalrework.util.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.blocks.util.IBlockBase;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo;
import mrunknown404.primalrework.util.harvest.ItemHarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class HarvestHelper {

	public static final List<BlockHarvestInfo> BLOCKS = new ArrayList<BlockHarvestInfo>();
	public static final List<ItemHarvestInfo> ITEMS = new ArrayList<ItemHarvestInfo>();
	
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
		BlockHarvestInfo info = new BlockHarvestInfo(b, harvest);
		info = info.setDrops(drops);
		
		if (hardness != -1f) {
			b.setHardness(hardness);
		}
		
		if (b instanceof IBlockBase) {
			((IBlockBase<BlockBase>) b).setHarvestInfo(info);
		}
		
		if (BLOCKS.contains(info)) {
			BLOCKS.remove(info);
		}
		BLOCKS.add(info);
	}
	
	public static void setHarvestLevel(Item i, EnumToolType type, EnumToolMaterial level) {
		ItemHarvestInfo info = new ItemHarvestInfo(i, type, level);
		
		if (ITEMS.contains(info)) {
			ITEMS.remove(info);
		}
		ITEMS.add(info);
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
		for (BlockHarvestInfo info : BLOCKS) {
			if (info.getType() == block) {
				return info;
			}
		}
		
		return null;
	}
	
	public static ItemHarvestInfo getHarvestInfo(Item item) {
		for (ItemHarvestInfo info : ITEMS) {
			if (info.getType() == item) {
				return info;
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
