package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.recipes.IIngredientProvider;
import mrunknown404.primalrework.recipes.SRBurnableFuel;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.utils.enums.FuelType;

public class InitFuels {
	private static final Map<FuelType, Map<StagedItem, Integer>> FUELS = new HashMap<FuelType, Map<StagedItem, Integer>>();
	private static final Map<FuelType, List<SRBurnableFuel>> FUELS_AS_RECIPES = new HashMap<FuelType, List<SRBurnableFuel>>();
	
	private static final Cache<StagedItem, Map<FuelType, StagedItem>> fuelsCache = Cache.create();
	private static final DoubleCache<FuelType, StagedItem, List<SRBurnableFuel>> fuelsAsRecipesCache = DoubleCache.and();
	
	static void load() {
		for (FuelType type : FuelType.values()) {
			FUELS.put(type, new HashMap<StagedItem, Integer>());
			FUELS_AS_RECIPES.put(type, new ArrayList<SRBurnableFuel>());
		}
		
		addFuel(FuelType.BURNABLE_FUEL, InitItems.PLANT_FIBER, 200, 8);
		addFuel(FuelType.BURNABLE_FUEL, InitItems.PLANT_ROPE, 200, 8f / 3f);
		addFuel(FuelType.BURNABLE_FUEL, InitItems.STICK, 200, 8);
		addFuel(FuelType.BURNABLE_FUEL, InitItems.BARK, 200, 8);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.OAK_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.BIRCH_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.SPRUCE_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.JUNGLE_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.ACACIA_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.DARK_OAK_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.STRIPPED_OAK_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.STRIPPED_BIRCH_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.STRIPPED_SPRUCE_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.STRIPPED_JUNGLE_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.STRIPPED_ACACIA_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.STRIPPED_DARK_OAK_LOG, 200, 1);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.OAK_LEAVES, 200, 16);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.BIRCH_LEAVES, 200, 16);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.SPRUCE_LEAVES, 200, 16);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.JUNGLE_LEAVES, 200, 16);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.ACACIA_LEAVES, 200, 16);
		addFuel(FuelType.BURNABLE_FUEL, InitBlocks.DARK_OAK_LEAVES, 200, 16);
		addFuel(FuelType.BURNABLE_FUEL, InitItems.OAK_PLANK, 200, 8);
		addFuel(FuelType.BURNABLE_FUEL, InitItems.BIRCH_PLANK, 200, 8);
		addFuel(FuelType.BURNABLE_FUEL, InitItems.SPRUCE_PLANK, 200, 8);
		addFuel(FuelType.BURNABLE_FUEL, InitItems.JUNGLE_PLANK, 200, 8);
		addFuel(FuelType.BURNABLE_FUEL, InitItems.ACACIA_PLANK, 200, 8);
		addFuel(FuelType.BURNABLE_FUEL, InitItems.DARK_OAK_PLANK, 200, 8);
	}
	
	public static <T extends IIngredientProvider & ISIProvider> void addFuel(FuelType type, T item, int maxTime, float amountOfItemsNeeded) {
		int time = Math.round(maxTime / amountOfItemsNeeded);
		FUELS.get(type).put(item.getStagedItem(), time);
		FUELS_AS_RECIPES.get(type).add(new SRBurnableFuel(item, time));
	}
	
	public static boolean isFuelItem(FuelType type, ISIProvider item) {
		return getBurnTime(type, item) > 0;
	}
	
	public static int getBurnTime(FuelType type, ISIProvider item) {
		return FUELS.get(type).getOrDefault(item.getStagedItem(), 0);
	}
	
	public static Map<FuelType, StagedItem> getFuels(ISIProvider item) {
		return fuelsCache.computeIfAbsent(item.getStagedItem(),
				() -> FUELS.entrySet().stream().filter(e -> e.getValue().containsKey(item.getStagedItem())).collect(Collectors.toMap(Entry::getKey, r -> item.getStagedItem())));
	}
	
	public static List<SRBurnableFuel> convertToRecipes(FuelType type, StagedItem item) {
		return fuelsAsRecipesCache.computeIfAbsent(type, item,
				() -> FUELS_AS_RECIPES.get(type).stream().filter(sr -> sr.getOutput().getItem() == item).collect(Collectors.toList()));
	}
	
	public static List<String> getRecipeListPrint() {
		return FUELS.entrySet().stream().map(e -> "Loaded " + e.getValue().size() + " fuels for " + e.getKey()).collect(Collectors.toList());
	}
}
