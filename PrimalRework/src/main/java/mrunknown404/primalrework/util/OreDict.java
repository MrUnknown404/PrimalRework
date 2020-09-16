package mrunknown404.primalrework.util;

import org.apache.commons.lang3.text.WordUtils;

import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitItems;
import mrunknown404.primalrework.items.ItemOreNugget;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.ItemHarvestInfo;
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
			ItemHarvestInfo info = HarvestHelper.getHarvestInfo(item);
			if (!(item instanceof ItemHoe) && !(item instanceof ItemAxe)) {
				register(info, item.isDamageable() ? OreDictionary.WILDCARD_VALUE : 0);
			}
			
			if (info.getMaterial() != EnumToolMaterial.hand) {
				for (EnumToolType type : info.getToolTypes()) {
					if (type != EnumToolType.none) {
						OreDictionary.registerOre("toolMat" + WordUtils.capitalizeFully(info.getMaterial().toString()), new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
					}
				}
			}
		}
		
		for (int i = 0; i < 16; i++) {
			OreDictionary.registerOre("wool", new ItemStack(Blocks.WOOL, 1, i));
			OreDictionary.registerOre("bed", new ItemStack(Items.BED, 1, i));
		}
		
		for (Item item : InitItems.ITEMS) {
			if (item instanceof ItemOreNugget) {
				OreDictionary.registerOre("nuggetOre", item);
			}
		}
		
		OreDictionary.registerOre("prDyeWhite", InitItems.WHITE_DYE);
		OreDictionary.registerOre("prDyeBlue", InitItems.BLUE_DYE);
		OreDictionary.registerOre("prDyeBlack", InitItems.BLACK_DYE);
		OreDictionary.registerOre("prDyeBrown", InitItems.BROWN_DYE);
		
		OreDictionary.registerOre("logStripped", InitBlocks.STRIPPED_OAK_LOG);
		OreDictionary.registerOre("logStripped", InitBlocks.STRIPPED_BIRCH_LOG);
		OreDictionary.registerOre("logStripped", InitBlocks.STRIPPED_SPRUCE_LOG);
		OreDictionary.registerOre("logStripped", InitBlocks.STRIPPED_JUNGLE_LOG);
		OreDictionary.registerOre("logStripped", InitBlocks.STRIPPED_DARK_OAK_LOG);
		OreDictionary.registerOre("logStripped", InitBlocks.STRIPPED_ACACIA_LOG);
		
		OreDictionary.registerOre("logWood", InitBlocks.STRIPPED_OAK_LOG);
		OreDictionary.registerOre("logWood", InitBlocks.STRIPPED_BIRCH_LOG);
		OreDictionary.registerOre("logWood", InitBlocks.STRIPPED_SPRUCE_LOG);
		OreDictionary.registerOre("logWood", InitBlocks.STRIPPED_JUNGLE_LOG);
		OreDictionary.registerOre("logWood", InitBlocks.STRIPPED_DARK_OAK_LOG);
		OreDictionary.registerOre("logWood", InitBlocks.STRIPPED_ACACIA_LOG);
		
		OreDictionary.registerOre("plankItem", InitItems.OAK_PLANK);
		OreDictionary.registerOre("plankItem", InitItems.SPRUCE_PLANK);
		OreDictionary.registerOre("plankItem", InitItems.BIRCH_PLANK);
		OreDictionary.registerOre("plankItem", InitItems.JUNGLE_PLANK);
		OreDictionary.registerOre("plankItem", InitItems.DARK_OAK_PLANK);
		OreDictionary.registerOre("plankItem", InitItems.ACACIA_PLANK);
		
		OreDictionary.registerOre("logOak", InitBlocks.STRIPPED_OAK_LOG);
		OreDictionary.registerOre("logOak", new ItemStack(Blocks.LOG, 1, 0));
		OreDictionary.registerOre("logSpruce", InitBlocks.STRIPPED_SPRUCE_LOG);
		OreDictionary.registerOre("logSpruce", new ItemStack(Blocks.LOG, 1, 1));
		OreDictionary.registerOre("logBirch", InitBlocks.STRIPPED_BIRCH_LOG);
		OreDictionary.registerOre("logBirch", new ItemStack(Blocks.LOG, 1, 2));
		OreDictionary.registerOre("logJungle", InitBlocks.STRIPPED_JUNGLE_LOG);
		OreDictionary.registerOre("logJungle", new ItemStack(Blocks.LOG, 1, 3));
		OreDictionary.registerOre("logAcacia", InitBlocks.STRIPPED_ACACIA_LOG);
		OreDictionary.registerOre("logAcacia", new ItemStack(Blocks.LOG2, 1, 0));
		OreDictionary.registerOre("logDarkOak", InitBlocks.STRIPPED_DARK_OAK_LOG);
		OreDictionary.registerOre("logDarkOak", new ItemStack(Blocks.LOG2, 1, 1));
	}
	
	private static void register(ItemHarvestInfo info, int meta) {
		for (EnumToolType type : info.getToolTypes()) {
			if (type != EnumToolType.none) {
				OreDictionary.registerOre("tool" + WordUtils.capitalizeFully(type.name()), new ItemStack(HarvestHelper.getItem(info), 1, meta));
			}
		}
	}
}
