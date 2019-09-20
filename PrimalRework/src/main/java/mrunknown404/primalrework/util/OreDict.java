package mrunknown404.primalrework.util;

import org.apache.commons.lang3.text.WordUtils;

import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import mrunknown404.primalrework.util.harvest.HarvestInfo;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class OreDict {
	public static void register() {
		for (Item item : Item.REGISTRY) {
			if (item != Items.DIAMOND_AXE && item != Items.DIAMOND_HOE &&
					item != Items.IRON_AXE && item != Items.IRON_HOE &&
					item != Items.STONE_AXE && item != Items.STONE_HOE &&
					item != Items.GOLDEN_AXE && item != Items.GOLDEN_HOE &&
					item != Items.WOODEN_AXE && item != Items.WOODEN_HOE) {
				register(HarvestHelper.getHarvestInfo(item));
			}
		}
		
		for (Item item : ModItems.ITEMS) {
			register(HarvestHelper.getHarvestInfo(item));
		}
		
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_OAK_LOG);
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_SPRUCE_LOG);
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_BIRCH_LOG);
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_JUNGLE_LOG);
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_DARK_OAK_LOG);
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_ACACIA_LOG);
	}
	
	private static void register(HarvestInfo info) {
		for (DoubleValue<EnumToolType, EnumToolMaterial> type : info.getTypesHarvests()) {
			if (type.getL() != EnumToolType.none) {
				OreDictionary.registerOre("tool" + WordUtils.capitalizeFully(type.getL().name()), info.getItem());
			}
		}
	}
}
