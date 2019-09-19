package mrunknown404.primalrework.util;

import org.apache.commons.lang3.text.WordUtils;

import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import mrunknown404.primalrework.util.harvest.HarvestHelper;
import mrunknown404.primalrework.util.harvest.HarvestInfo;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class OreDict {
	public static void register() {
		for (Item item : Item.REGISTRY) {
			register(HarvestHelper.getHarvestInfo(item));
		}
		
		for (Item item : ModItems.ITEMS) {
			register(HarvestHelper.getHarvestInfo(item));
		}
	}
	
	private static void register(HarvestInfo info) {
		for (DoubleValue<EnumToolType, EnumToolMaterial> type : info.getTypesHarvests()) {
			if (type.getL() != EnumToolType.none) {
				OreDictionary.registerOre("tool" + WordUtils.capitalizeFully(type.getL().name()), info.getItem());
			}
		}
	}
}
