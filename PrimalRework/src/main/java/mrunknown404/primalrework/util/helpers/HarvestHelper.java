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
import mrunknown404.primalrework.util.harvest.HarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class HarvestHelper {

	public static final List<BlockHarvestInfo> BLOCKS = new ArrayList<BlockHarvestInfo>();
	public static final List<HarvestInfo> ITEMS = new ArrayList<HarvestInfo>();
	
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
		setHarvestLevel(b, hardness, info.getTypesHarvests(), info.getAllDrops().toArray(new HarvestDropInfo[0]));
	}
	
	public static void setHarvestLevel(Block b, BlockHarvestInfo info) {
		setHarvestLevel(b, -1, info.getTypesHarvests(), info.getAllDrops().toArray(new HarvestDropInfo[0]));
	}
	
	public static void setHarvestLevel(Block b, float hardness, List<DoubleValue<EnumToolType, EnumToolMaterial>> harvest, HarvestDropInfo... drops) {
		BlockHarvestInfo info = new BlockHarvestInfo(b, harvest);
		
		if (drops != null) {
			info = info.setDrops(drops);
		}
		
		if (hardness != -1f) {
			b.setHardness(hardness);
		}
		
		if (b instanceof IBlockBase<?>) {
			((IBlockBase<BlockBase>) b).setHarvestInfo(info);
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
	
	public static boolean canBreak(Block block, @Nullable Item item) {
		BlockHarvestInfo binfo = getHarvestInfo(block);
		if (binfo == null || HarvestHelper.hasToolMaterial(block, EnumToolMaterial.unbreakable)) {
			return false;
		} else if (item == null && binfo.canBreakWithNone()) {
			return true;
		}
		
		HarvestInfo iinfo = getHarvestInfo(item);
		if (iinfo == null) {
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
		
		return false;
	}
	
	public static boolean hasToolType(Item item, EnumToolType type) {
		for (DoubleValue<EnumToolType, EnumToolMaterial> dv : getHarvestInfo(item).getTypesHarvests()) {
			if (dv.getL() == type) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean hasToolMaterial(Block block, EnumToolMaterial type) {
		for (DoubleValue<EnumToolType, EnumToolMaterial> dv : getHarvestInfo(block).getTypesHarvests()) {
			if (dv.getR() == type) {
				return true;
			}
		}
		
		return false;
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
	
	public static float getItemsHarvestSpeed(Block block, @Nullable Item item) {
		BlockHarvestInfo binfo = getHarvestInfo(block);
		HarvestInfo iinfo = getHarvestInfo(item);
		if (item == null || binfo == null || iinfo == null) {
			return EnumToolMaterial.hand.speed;
		}
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> block_dv : binfo.getTypesHarvests()) {
			for (DoubleValue<EnumToolType, EnumToolMaterial> item_dv : iinfo.getTypesHarvests()) {
				if (block_dv.getL() == item_dv.getL()) {
					return item_dv.getR().speed;
				}
			}
		}
		
		return EnumToolMaterial.hand.speed;
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
}
