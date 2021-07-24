package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.recipes.SRFuel;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.Pair;
import mrunknown404.primalrework.utils.enums.EnumFuelType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class PRFuels {
	private static final Map<EnumFuelType, Map<Item, Integer>> FUELS = new HashMap<EnumFuelType, Map<Item, Integer>>();
	private static final Map<EnumFuelType, List<SRFuel>> FUELS_AS_RECIPES = new HashMap<EnumFuelType, List<SRFuel>>();
	
	private static final Cache<Item, Map<EnumFuelType, Pair<Item, Integer>>> fuelsCache = new Cache<Item, Map<EnumFuelType, Pair<Item, Integer>>>();
	private static final Cache<EnumFuelType, List<SRFuel>> fuelsAsRecipesCache = new Cache<EnumFuelType, List<SRFuel>>();
	
	public static void load() {
		for (EnumFuelType type : EnumFuelType.values()) {
			FUELS.put(type, new HashMap<Item, Integer>());
			FUELS_AS_RECIPES.put(type, new ArrayList<SRFuel>());
		}
		
		int oneCampfireItem = 200;
		addFuel(EnumFuelType.campfire, Items.STICK, oneCampfireItem / 4);
		addFuel(EnumFuelType.campfire, Items.COAL, oneCampfireItem * 8);
		addFuel(EnumFuelType.campfire, Items.CHARCOAL, oneCampfireItem * 8);
		for (Item item : PRStagedTags.ALL_LOGS.getItemsFromAllStages()) {
			addFuel(EnumFuelType.campfire, item, oneCampfireItem * 4);
		}
		for (Item item : PRStagedTags.ALL_PLANK_BLOCKS.getItemsFromAllStages()) {
			addFuel(EnumFuelType.campfire, item, oneCampfireItem);
		}
		for (Item item : PRStagedTags.ALL_PLANKS.getItemsFromAllStages()) {
			addFuel(EnumFuelType.campfire, item, oneCampfireItem / 8);
		}
	}
	
	public static void addFuel(EnumFuelType type, Item item, int cookTime) {
		FUELS.get(type).put(item, cookTime);
		FUELS_AS_RECIPES.get(type).add(new SRFuel(item, cookTime));
	}
	
	public static boolean isFuelItem(EnumFuelType type, Item item) {
		return getBurnTime(type, item) > 0;
	}
	
	public static int getBurnTime(EnumFuelType type, Item item) {
		return FUELS.get(type).getOrDefault(item, 0);
	}
	
	public static Map<EnumFuelType, Pair<Item, Integer>> getFuels(Item item) {
		if (fuelsCache.is(item)) {
			return fuelsCache.get();
		}
		
		Map<EnumFuelType, Pair<Item, Integer>> map = new HashMap<EnumFuelType, Pair<Item, Integer>>();
		for (EnumFuelType fuel : EnumFuelType.values()) {
			int i = FUELS.get(fuel).getOrDefault(item, -1);
			if (i != -1) {
				map.put(fuel, new Pair<Item, Integer>(item, -1));
			}
		}
		
		fuelsCache.set(item, map);
		return map;
	}
	
	public static List<SRFuel> convertToRecipes(EnumFuelType type, Pair<Item, Integer> pair) {
		if (fuelsAsRecipesCache.is(type)) {
			fuelsAsRecipesCache.get();
		}
		
		List<SRFuel> srFuels = new ArrayList<SRFuel>();
		for (SRFuel sr : FUELS_AS_RECIPES.get(type)) {
			if (sr.getOutput().getItem() == pair.getL()) {
				srFuels.add(sr);
			}
		}
		
		fuelsAsRecipesCache.set(type, srFuels);
		return srFuels;
	}
}
