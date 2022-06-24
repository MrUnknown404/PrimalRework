package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.SRBurnableFuel;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.Pair;
import mrunknown404.primalrework.utils.enums.FuelType;

public class PRFuels {
	private static final Map<FuelType, Map<StagedItem, Integer>> FUELS = new HashMap<FuelType, Map<StagedItem, Integer>>();
	private static final Map<FuelType, List<SRBurnableFuel>> FUELS_AS_RECIPES = new HashMap<FuelType, List<SRBurnableFuel>>();
	
	private static final Cache<StagedItem, Map<FuelType, Pair<StagedItem, Integer>>> fuelsCache = new Cache<StagedItem, Map<FuelType, Pair<StagedItem, Integer>>>();
	private static final Cache<FuelType, List<SRBurnableFuel>> fuelsAsRecipesCache = new Cache<FuelType, List<SRBurnableFuel>>();
	
	public static void load() {
		for (FuelType type : FuelType.values()) {
			FUELS.put(type, new HashMap<StagedItem, Integer>());
			FUELS_AS_RECIPES.put(type, new ArrayList<SRBurnableFuel>());
		}
		
		int oneBurnableItem = 200;
		addFuel(FuelType.BURNABLE_FUEL, PRItems.STICK.get(), oneBurnableItem / 4);
	}
	
	public static void addFuel(FuelType type, StagedItem item, int cookTime) {
		FUELS.get(type).put(item, cookTime);
		FUELS_AS_RECIPES.get(type).add(new SRBurnableFuel(item, cookTime));
	}
	
	public static boolean isFuelItem(FuelType type, StagedItem item) {
		return getBurnTime(type, item) > 0;
	}
	
	public static int getBurnTime(FuelType type, StagedItem item) {
		return FUELS.get(type).getOrDefault(item, 0);
	}
	
	public static Map<FuelType, Pair<StagedItem, Integer>> getFuels(StagedItem item) {
		if (fuelsCache.is(item)) {
			return fuelsCache.get();
		}
		
		Map<FuelType, Pair<StagedItem, Integer>> map = new HashMap<FuelType, Pair<StagedItem, Integer>>();
		for (FuelType fuel : FuelType.values()) {
			int i = FUELS.get(fuel).getOrDefault(item, -1);
			if (i != -1) {
				map.put(fuel, new Pair<StagedItem, Integer>(item, -1));
			}
		}
		
		fuelsCache.set(item, map);
		return map;
	}
	
	public static List<SRBurnableFuel> convertToRecipes(FuelType type, Pair<StagedItem, Integer> pair) {
		if (fuelsAsRecipesCache.is(type)) {
			fuelsAsRecipesCache.get();
		}
		
		List<SRBurnableFuel> srFuels = new ArrayList<SRBurnableFuel>();
		for (SRBurnableFuel sr : FUELS_AS_RECIPES.get(type)) {
			if (sr.getOutput().getItem() == pair.getL()) {
				srFuels.add(sr);
			}
		}
		
		fuelsAsRecipesCache.set(type, srFuels);
		return srFuels;
	}
}
