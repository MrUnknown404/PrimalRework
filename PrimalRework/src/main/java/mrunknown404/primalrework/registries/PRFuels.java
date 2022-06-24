package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.SRBurnableFuel;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.Pair;
import mrunknown404.primalrework.utils.enums.EnumFuelType;

public class PRFuels {
	private static final Map<EnumFuelType, Map<StagedItem, Integer>> FUELS = new HashMap<EnumFuelType, Map<StagedItem, Integer>>();
	private static final Map<EnumFuelType, List<SRBurnableFuel>> FUELS_AS_RECIPES = new HashMap<EnumFuelType, List<SRBurnableFuel>>();
	
	private static final Cache<StagedItem, Map<EnumFuelType, Pair<StagedItem, Integer>>> fuelsCache = new Cache<StagedItem, Map<EnumFuelType, Pair<StagedItem, Integer>>>();
	private static final Cache<EnumFuelType, List<SRBurnableFuel>> fuelsAsRecipesCache = new Cache<EnumFuelType, List<SRBurnableFuel>>();
	
	public static void load() {
		for (EnumFuelType type : EnumFuelType.values()) {
			FUELS.put(type, new HashMap<StagedItem, Integer>());
			FUELS_AS_RECIPES.put(type, new ArrayList<SRBurnableFuel>());
		}
		
		int oneBurnableItem = 200;
		addFuel(EnumFuelType.burnable_fuel, PRItems.STICK.get(), oneBurnableItem / 4);
	}
	
	public static void addFuel(EnumFuelType type, StagedItem item, int cookTime) {
		FUELS.get(type).put(item, cookTime);
		FUELS_AS_RECIPES.get(type).add(new SRBurnableFuel(item, cookTime));
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
	
	public static List<SRBurnableFuel> convertToRecipes(EnumFuelType type, Pair<StagedItem, Integer> pair) {
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
