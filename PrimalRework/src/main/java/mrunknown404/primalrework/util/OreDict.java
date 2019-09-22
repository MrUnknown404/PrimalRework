package mrunknown404.primalrework.util;

import org.apache.commons.lang3.text.WordUtils;

import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.HarvestInfo;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDict {
	public static void register() {
		for (Item item : Item.REGISTRY) {
			if (!(item instanceof ItemHoe) && !(item instanceof ItemAxe)) {
				register(HarvestHelper.getHarvestInfo(item), item.isDamageable() ? OreDictionary.WILDCARD_VALUE : 0);
			}
		}
		
		for (int i = 0; i < 16; i++) {
			OreDictionary.registerOre("wool", new ItemStack(Blocks.WOOL, 1, i));
			OreDictionary.registerOre("bed", new ItemStack(Items.BED, 1, i));
		}
		
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_OAK_LOG);
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_BIRCH_LOG);
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_SPRUCE_LOG);
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_JUNGLE_LOG);
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_DARK_OAK_LOG);
		OreDictionary.registerOre("logStripped", ModBlocks.STRIPPED_ACACIA_LOG);
		
		OreDictionary.registerOre("logWood", ModBlocks.STRIPPED_OAK_LOG);
		OreDictionary.registerOre("logWood", ModBlocks.STRIPPED_BIRCH_LOG);
		OreDictionary.registerOre("logWood", ModBlocks.STRIPPED_SPRUCE_LOG);
		OreDictionary.registerOre("logWood", ModBlocks.STRIPPED_JUNGLE_LOG);
		OreDictionary.registerOre("logWood", ModBlocks.STRIPPED_DARK_OAK_LOG);
		OreDictionary.registerOre("logWood", ModBlocks.STRIPPED_ACACIA_LOG);
		
		OreDictionary.registerOre("plankItem", ModItems.OAK_PLANK);
		OreDictionary.registerOre("plankItem", ModItems.SPRUCE_PLANK);
		OreDictionary.registerOre("plankItem", ModItems.BIRCH_PLANK);
		OreDictionary.registerOre("plankItem", ModItems.JUNGLE_PLANK);
		OreDictionary.registerOre("plankItem", ModItems.DARK_OAK_PLANK);
		OreDictionary.registerOre("plankItem", ModItems.ACACIA_PLANK);
		
		OreDictionary.registerOre("logOak", ModBlocks.STRIPPED_OAK_LOG);
		OreDictionary.registerOre("logOak", new ItemStack(Blocks.LOG, 1, 0));
		OreDictionary.registerOre("logBirch", ModBlocks.STRIPPED_BIRCH_LOG);
		OreDictionary.registerOre("logBirch", new ItemStack(Blocks.LOG, 1, 1));
		OreDictionary.registerOre("logSpruce", ModBlocks.STRIPPED_SPRUCE_LOG);
		OreDictionary.registerOre("logSpruce", new ItemStack(Blocks.LOG, 1, 2));
		OreDictionary.registerOre("logJungle", ModBlocks.STRIPPED_JUNGLE_LOG);
		OreDictionary.registerOre("logJungle", new ItemStack(Blocks.LOG, 1, 3));
		OreDictionary.registerOre("logDarkOak", ModBlocks.STRIPPED_DARK_OAK_LOG);
		OreDictionary.registerOre("logDarkOak", new ItemStack(Blocks.LOG2, 1, 0));
		OreDictionary.registerOre("logAcacia", ModBlocks.STRIPPED_ACACIA_LOG);
		OreDictionary.registerOre("logAcacia", new ItemStack(Blocks.LOG2, 1, 1));
	}
	
	private static void register(HarvestInfo info, int meta) {
		for (DoubleValue<EnumToolType, EnumToolMaterial> type : info.getTypesHarvests()) {
			if (type.getL() != EnumToolType.none) {
				OreDictionary.registerOre("tool" + WordUtils.capitalizeFully(type.getL().name()), new ItemStack(info.getItem(), 1, meta));
			}
		}
	}
}
