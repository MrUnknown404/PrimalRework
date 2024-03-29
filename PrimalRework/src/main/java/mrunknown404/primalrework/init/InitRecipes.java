package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.recipes.StagedRecipe;
import mrunknown404.primalrework.recipes.inputs.RICrafting3;
import mrunknown404.primalrework.recipes.inputs.RecipeInput;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.utils.DoubleCache.DoubleCachePredicate;
import mrunknown404.primalrework.utils.enums.RecipeType;
import mrunknown404.primalrework.world.savedata.WSDStage;

public class InitRecipes {
	private static final Map<RecipeType, List<StagedRecipe<?, ?>>> RECIPES = new HashMap<RecipeType, List<StagedRecipe<?, ?>>>();
	
	//haha these names suck and won't be confusing later!
	private static final Map<RecipeType, DoubleCache<Stage, RecipeInput<?>, StagedRecipe<?, ?>>> cacheRecipesForInput = new HashMap<RecipeType, DoubleCache<Stage, RecipeInput<?>, StagedRecipe<?, ?>>>();
	private static final Map<RecipeType, DoubleCache<Stage, Ingredient, List<StagedRecipe<?, ?>>>> cacheRecipesContainingInput = new HashMap<RecipeType, DoubleCache<Stage, Ingredient, List<StagedRecipe<?, ?>>>>();
	private static final DoubleCache<Stage, StagedItem, Map<RecipeType, List<StagedRecipe<?, ?>>>> CACHE_WAYS_OF_MAKING = DoubleCache.and();
	private static final DoubleCache<Stage, Ingredient, Map<RecipeType, List<StagedRecipe<?, ?>>>> CACHE_WHAT_CAN_BE_MADE_WITH = DoubleCache
			.create((o0, o1, k0, k1) -> (o0 == k0 || (o0 != null && o0.equals(k0))) && (o1 == null ? false : o1.matches(k1)));
	
	static void load() {
		final DoubleCachePredicate<Stage, RecipeInput<?>> predicate0 = (o0, o1, k0, k1) -> (o0 == k0 || (o0 != null && o0.equals(k0))) && (o1 == null ? false : o1.matches(k1));
		final DoubleCachePredicate<Stage, Ingredient> predicate1 = (o0, o1, k0, k1) -> (o0 == k0 || (o0 != null && o0.equals(k0))) && (o1 == null ? false : o1.matches(k1));
		
		for (RecipeType type : RecipeType.values()) {
			RECIPES.put(type, new ArrayList<StagedRecipe<?, ?>>());
			cacheRecipesForInput.put(type, DoubleCache.create(predicate0));
			cacheRecipesContainingInput.put(type, DoubleCache.create(predicate1));
		}
		
		//@formatter:off
		Stage stage = InitStages.STAGE_BEFORE.get();
		addCrafting3(new SRCrafting3(stage, InitItems.CLAY_SHOVEL,     1, RICrafting3.shaped().set1x2(InitItems.CLAY_BALL, InitItems.STICK)));
		addCrafting3(new SRCrafting3(stage, InitItems.CLAY_AXE,        1, RICrafting3.shaped().set2x2(InitItems.CLAY_BALL, InitItems.CLAY_BALL, null, InitItems.STICK)));
		addCrafting3(new SRCrafting3(stage, InitItems.PLANT_ROPE,      1, RICrafting3.shapeless().set(InitItems.PLANT_FIBER, 3)));
		addCrafting3(new SRCrafting3(stage, InitItems.PLANT_MESH,      1, RICrafting3.shaped().set2x2(InitItems.STICK, InitItems.PLANT_FIBER, InitItems.PLANT_FIBER, InitItems.STICK)));
		addCrafting3(new SRCrafting3(stage, InitBlocks.THATCH,         1, RICrafting3.shaped().set2x2(InitItems.PLANT_FIBER)));
		stage = InitStages.STAGE_STONE.get();
		addCrafting3(new SRCrafting3(stage, InitBlocks.CRAFTING_TABLE, 1, RICrafting3.shaped().set2x2(InitItems.KNAPPED_FLINT, InitItems.FLINT_POINT, InitStagedTags.LOGS.get(), InitStagedTags.LOGS.get())));
		addCrafting3(new SRCrafting3(stage, InitItems.FLINT_PICKAXE,   1, RICrafting3.shaped().set3x3(InitItems.FLINT_POINT, InitItems.KNAPPED_FLINT, null, InitItems.PLANT_ROPE, InitItems.STICK, null, null, InitItems.STICK, null)));
		addCrafting3(new SRCrafting3(stage, InitItems.FLINT_SHOVEL,    1, RICrafting3.shaped().set3x3(null, InitItems.KNAPPED_FLINT, null, InitItems.PLANT_ROPE, InitItems.STICK, null, null, InitItems.STICK, null)));
		addCrafting3(new SRCrafting3(stage, InitItems.FLINT_AXE,       1, RICrafting3.shaped().set3x3(InitItems.KNAPPED_FLINT, InitItems.KNAPPED_FLINT, null, InitItems.PLANT_ROPE, InitItems.STICK, null, null, InitItems.STICK, null)));
		addCrafting3(new SRCrafting3(stage, InitItems.FLINT_HOE,       1, RICrafting3.shaped().set3x3(InitItems.FLINT_POINT, InitItems.FLINT_POINT, null, InitItems.PLANT_ROPE, InitItems.STICK, null, null, InitItems.STICK, null)));
		addCrafting3(new SRCrafting3(stage, InitItems.FLINT_KNIFE,     1, RICrafting3.shaped().set2x2(null, InitItems.FLINT_POINT, InitItems.PLANT_ROPE, InitItems.STICK)));
		addCrafting3(new SRCrafting3(stage, InitItems.FLINT_SHEARS,    1, RICrafting3.shaped().set3x3(InitItems.FLINT_POINT, null, InitItems.FLINT_POINT, InitItems.FLINT_POINT, InitItems.PLANT_ROPE, InitItems.FLINT_POINT, null, InitItems.STICK, null)));
		addCrafting3(new SRCrafting3(stage, InitItems.BONE_SWORD,      1, RICrafting3.shaped().set2x3(null, InitItems.BONE_SHARD, null, InitItems.BONE_SHARD, InitItems.PLANT_ROPE, InitItems.STICK)));
		//@formatter:on
	}
	
	public static void addRecipe(RecipeType type, StagedRecipe<?, ?> recipe) {
		if (type.supportsClass != recipe.getClass()) {
			throw new IllegalArgumentException("RecipeType: "+type+" does not support Class: " + recipe.getClass());
		}
		
		RECIPES.get(type).add(recipe);
	}
	
	public static void addCrafting3(SRCrafting3 recipe) {
		addRecipe(RecipeType.CRAFTING_3, recipe);
	}
	
	public static StagedRecipe<?, ?> getRecipeForInput(RecipeType recipeType, RecipeInput<?> input) {
		return input.isEmpty() ? null :
				cacheRecipesForInput.get(recipeType).computeIfAbsent(WSDStage.getStage(), input,
						() -> RECIPES.get(recipeType).stream().filter(r -> r.hasAccessToCurrentStage() && r.inputRecipe.matches(input)).findFirst().orElse(null));
	}
	
	public static <T extends StagedRecipe<T, ?>> List<StagedRecipe<?, ?>> getRecipesContainingInput(RecipeType type, Ingredient input) {
		return cacheRecipesContainingInput.get(type).computeIfAbsent(WSDStage.getStage(), input,
				() -> RECIPES.get(type).stream().filter(r -> r.hasAccessToCurrentStage() && r.has(input)).collect(Collectors.toList()));
	}
	
	public static Map<RecipeType, List<StagedRecipe<?, ?>>> getWhatCanBeMadeWith(Ingredient ingredient) {
		return CACHE_WHAT_CAN_BE_MADE_WITH.computeIfAbsent(WSDStage.getStage(), ingredient, () -> {
			Map<RecipeType, List<StagedRecipe<?, ?>>> map = new HashMap<RecipeType, List<StagedRecipe<?, ?>>>();
			RECIPES.forEach((type, list) -> {
				List<StagedRecipe<?, ?>> newList = list.stream().filter(r -> r.hasAccessToCurrentStage() && r.has(ingredient)).collect(Collectors.toList());
				if (!newList.isEmpty()) {
					map.put(type, newList);
				}
			});
			return map;
		});
	}
	
	public static Map<RecipeType, List<StagedRecipe<?, ?>>> getWaysOfMaking(StagedItem item) {
		return CACHE_WAYS_OF_MAKING.computeIfAbsent(WSDStage.getStage(), item, () -> {
			Map<RecipeType, List<StagedRecipe<?, ?>>> map = new HashMap<RecipeType, List<StagedRecipe<?, ?>>>();
			RECIPES.forEach((type, list) -> {
				List<StagedRecipe<?, ?>> newList = list.stream().filter(r -> r.hasAccessToCurrentStage() && r.getOutput() == item).collect(Collectors.toList());
				if (!newList.isEmpty()) {
					map.put(type, newList);
				}
			});
			return map;
		});
	}
	
	public static List<String> getRecipeListPrint() {
		return RECIPES.entrySet().stream().map(e -> "Loaded " + e.getValue().size() + " recipes for " + e.getKey()).collect(Collectors.toList());
	}
}
