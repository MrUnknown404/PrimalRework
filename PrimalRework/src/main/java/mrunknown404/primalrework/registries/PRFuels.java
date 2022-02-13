package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.SRFuel;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.Pair;
import mrunknown404.primalrework.utils.enums.EnumFuelType;

public class PRFuels {
	private static final Map<EnumFuelType, Map<StagedItem, Integer>> FUELS = new HashMap<EnumFuelType, Map<StagedItem, Integer>>();
	private static final Map<EnumFuelType, List<SRFuel>> FUELS_AS_RECIPES = new HashMap<EnumFuelType, List<SRFuel>>();
	
	private static final Cache<StagedItem, Map<EnumFuelType, Pair<StagedItem, Integer>>> fuelsCache = new Cache<StagedItem, Map<EnumFuelType, Pair<StagedItem, Integer>>>();
	private static final Cache<EnumFuelType, List<SRFuel>> fuelsAsRecipesCache = new Cache<EnumFuelType, List<SRFuel>>();
	
	public static void load() {
		for (EnumFuelType type : EnumFuelType.values()) {
			FUELS.put(type, new HashMap<StagedItem, Integer>());
			FUELS_AS_RECIPES.put(type, new ArrayList<SRFuel>());
		}
		
		/*
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
		*/
	}
	
	public static void addFuel(EnumFuelType type, StagedItem item, int cookTime) {
		FUELS.get(type).put(item, cookTime);
		FUELS_AS_RECIPES.get(type).add(new SRFuel(item, cookTime));
	}
	
	public static boolean isFuelItem(EnumFuelType type, StagedItem item) {
		return getBurnTime(type, item) > 0;
	}
	
	public static int getBurnTime(EnumFuelType type, StagedItem item) {
		return FUELS.get(type).getOrDefault(item, 0);
	}
	
	public static Map<EnumFuelType, Pair<StagedItem, Integer>> getFuels(StagedItem item) {
		if (fuelsCache.is(item)) {
			return fuelsCache.get();
		}
		
		Map<EnumFuelType, Pair<StagedItem, Integer>> map = new HashMap<EnumFuelType, Pair<StagedItem, Integer>>();
		for (EnumFuelType fuel : EnumFuelType.values()) {
			int i = FUELS.get(fuel).getOrDefault(item, -1);
			if (i != -1) {
				map.put(fuel, new Pair<StagedItem, Integer>(item, -1));
			}
		}
		
		fuelsCache.set(item, map);
		return map;
	}
	
	public static List<SRFuel> convertToRecipes(EnumFuelType type, Pair<StagedItem, Integer> pair) {
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
