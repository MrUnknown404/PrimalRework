package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
	private static final Map<RecipeType, DoubleCache<Stage, StagedItem, List<StagedRecipe<?, ?>>>> cacheRecipesForOutput0 = new HashMap<RecipeType, DoubleCache<Stage, StagedItem, List<StagedRecipe<?, ?>>>>();
	private static final Map<RecipeType, DoubleCache<Stage, RecipeInput<?>, StagedRecipe<?, ?>>> cacheRecipesForInput = new HashMap<RecipeType, DoubleCache<Stage, RecipeInput<?>, StagedRecipe<?, ?>>>();
	private static final Map<RecipeType, DoubleCache<Stage, Ingredient, List<StagedRecipe<?, ?>>>> cacheRecipesContainingInput0 = new HashMap<RecipeType, DoubleCache<Stage, Ingredient, List<StagedRecipe<?, ?>>>>();
	private static final DoubleCache<Stage, StagedItem, Map<RecipeType, List<StagedRecipe<?, ?>>>> CACHE_WAYS_OF_MAKING = DoubleCache.and();
	private static final DoubleCache<Stage, Ingredient, Map<RecipeType, List<StagedRecipe<?, ?>>>> CACHE_WHAT_CAN_BE_MADE_WITH = DoubleCache
			.create((o0, o1, k0, k1) -> (o0 == k0 || (o0 != null && o0.equals(k0))) && (o1 == null ? false : o1.matches(k1)));
	
	static void load() {
		final DoubleCachePredicate<Stage, RecipeInput<?>> predicate0 = (o0, o1, k0, k1) -> (o0 == k0 || (o0 != null && o0.equals(k0))) && (o1 == null ? false : o1.matches(k1));
		final DoubleCachePredicate<Stage, Ingredient> predicate1 = (o0, o1, k0, k1) -> (o0 == k0 || (o0 != null && o0.equals(k0))) && (o1 == null ? false : o1.matches(k1));
		
		for (RecipeType type : RecipeType.values()) {
			RECIPES.put(type, new ArrayList<StagedRecipe<?, ?>>());
			cacheRecipesForOutput0.put(type, DoubleCache.and());
			cacheRecipesForInput.put(type, DoubleCache.create(predicate0));
			cacheRecipesContainingInput0.put(type, DoubleCache.create(predicate1));
		}
		
		//@formatter:off
		Stage stage = InitStages.STAGE_0.get();
		addRecipe(new SRCrafting3(stage, InitItems.CLAY_SHOVEL,     1, RICrafting3.shaped().set1x2(InitItems.CLAY_BALL, InitItems.STICK)));
		addRecipe(new SRCrafting3(stage, InitItems.CLAY_AXE,        1, RICrafting3.shaped().set2x2(InitItems.CLAY_BALL, InitItems.CLAY_BALL, null, InitItems.STICK)));
		addRecipe(new SRCrafting3(stage, InitItems.PLANT_ROPE,      1, RICrafting3.shapeless().set(InitItems.PLANT_FIBER, 3)));
		addRecipe(new SRCrafting3(stage, InitItems.PLANT_MESH,      1, RICrafting3.shaped().set2x2(InitItems.STICK, InitItems.PLANT_FIBER, InitItems.PLANT_FIBER, InitItems.STICK)));
		addRecipe(new SRCrafting3(stage, InitBlocks.THATCH,         1, RICrafting3.shaped().set2x2(InitItems.PLANT_FIBER)));
		stage = InitStages.STAGE_1.get();
		addRecipe(new SRCrafting3(stage, InitBlocks.CRAFTING_TABLE, 1, RICrafting3.shaped().set2x2(InitItems.KNAPPED_FLINT, InitItems.FLINT_POINT, InitStagedTags.LOGS.get(), InitStagedTags.LOGS.get())));
		addRecipe(new SRCrafting3(stage, InitItems.FLINT_PICKAXE,   1, RICrafting3.shaped().set3x3(InitItems.FLINT_POINT, InitItems.KNAPPED_FLINT, null, InitItems.PLANT_ROPE, InitItems.STICK, null, null, InitItems.STICK, null)));
		addRecipe(new SRCrafting3(stage, InitItems.FLINT_SHOVEL,    1, RICrafting3.shaped().set3x3(null, InitItems.KNAPPED_FLINT, null, InitItems.PLANT_ROPE, InitItems.STICK, null, null, InitItems.STICK, null)));
		addRecipe(new SRCrafting3(stage, InitItems.FLINT_AXE,       1, RICrafting3.shaped().set3x3(InitItems.KNAPPED_FLINT, InitItems.KNAPPED_FLINT, null, InitItems.PLANT_ROPE, InitItems.STICK, null, null, InitItems.STICK, null)));
		addRecipe(new SRCrafting3(stage, InitItems.FLINT_HOE,       1, RICrafting3.shaped().set3x3(InitItems.FLINT_POINT, InitItems.FLINT_POINT, null, InitItems.PLANT_ROPE, InitItems.STICK, null, null, InitItems.STICK, null)));
		addRecipe(new SRCrafting3(stage, InitItems.FLINT_KNIFE,     1, RICrafting3.shaped().set2x2(null, InitItems.FLINT_POINT, InitItems.PLANT_ROPE, InitItems.STICK)));
		addRecipe(new SRCrafting3(stage, InitItems.FLINT_SHEARS,    1, RICrafting3.shaped().set3x3(InitItems.FLINT_POINT, null, InitItems.FLINT_POINT, InitItems.FLINT_POINT, InitItems.PLANT_ROPE, InitItems.FLINT_POINT, null, InitItems.STICK, null)));
		addRecipe(new SRCrafting3(stage, InitItems.BONE_SWORD,      1, RICrafting3.shaped().set2x3(null, InitItems.BONE_SHARD, null, InitItems.BONE_SHARD, InitItems.PLANT_ROPE, InitItems.STICK)));
		//@formatter:on
	}
	
	public static void addRecipe(StagedRecipe<?, ?> recipe) {
		RECIPES.get(recipe.recipeType).add(recipe);
	}
	
	public static StagedRecipe<?, ?> getRecipeForInput(RecipeInput<?> input) {
		if (input.isEmpty()) {
			return null;
		}
		
		Stage stage = WSDStage.getStage();
		if (cacheRecipesForInput.get(input.recipeType).is(stage, input)) {
			return cacheRecipesForInput.get(input.recipeType).get();
		}
		
		Optional<StagedRecipe<?, ?>> recipe = RECIPES.get(input.recipeType).stream().filter(r -> r.hasAccessToCurrentStage() && r.inputRecipe.matches(input)).findFirst();
		return recipe.isPresent() ? cacheRecipesForInput.get(input.recipeType).set(stage, recipe.get().inputRecipe, recipe.get()) : null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends StagedRecipe<T, ?>> List<T> getRecipesForOutput(RecipeType type, StagedItem output) {
		Stage stage = WSDStage.getStage();
		if (cacheRecipesForOutput0.get(type).is(stage, output)) {
			return (List<T>) cacheRecipesForOutput0.get(type).get();
		}
		
		return (List<T>) cacheRecipesForOutput0.get(type).set(stage, output,
				RECIPES.get(type).stream().filter(r -> r.hasAccessToCurrentStage() && r.getOutput() == output).collect(Collectors.toList()));
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends StagedRecipe<T, ?>> List<T> getRecipesContainingInput(RecipeType type, Ingredient input) {
		Stage stage = WSDStage.getStage();
		if (cacheRecipesContainingInput0.get(type).is(stage, input)) {
			return (List<T>) cacheRecipesContainingInput0.get(type).get();
		}
		
		return (List<T>) cacheRecipesContainingInput0.get(type).set(stage, input,
				RECIPES.get(type).stream().filter(r -> r.hasAccessToCurrentStage() && r.has(input)).collect(Collectors.toList()));
	}
	
	public static Map<RecipeType, List<StagedRecipe<?, ?>>> getWhatCanBeMadeWith(Ingredient ingredient) {
		Stage stage = WSDStage.getStage();
		if (CACHE_WHAT_CAN_BE_MADE_WITH.is(stage, ingredient)) {
			return CACHE_WHAT_CAN_BE_MADE_WITH.get();
		}
		
		Map<RecipeType, List<StagedRecipe<?, ?>>> map = new HashMap<RecipeType, List<StagedRecipe<?, ?>>>();
		RECIPES.forEach((type, list) -> {
			List<StagedRecipe<?, ?>> newList = list.stream().filter(r -> r.hasAccessToCurrentStage() && r.has(ingredient)).collect(Collectors.toList());
			if (!newList.isEmpty()) {
				map.put(type, newList);
			}
		});
		
		return CACHE_WHAT_CAN_BE_MADE_WITH.set(stage, ingredient, map);
	}
	
	public static Map<RecipeType, List<StagedRecipe<?, ?>>> getWaysOfMaking(StagedItem item) {
		Stage stage = WSDStage.getStage();
		if (CACHE_WAYS_OF_MAKING.is(stage, item)) {
			return CACHE_WAYS_OF_MAKING.get();
		}
		
		Map<RecipeType, List<StagedRecipe<?, ?>>> map = new HashMap<RecipeType, List<StagedRecipe<?, ?>>>();
		RECIPES.forEach((type, list) -> {
			List<StagedRecipe<?, ?>> newList = list.stream().filter(r -> r.hasAccessToCurrentStage() && r.getOutput() == item).collect(Collectors.toList());
			if (!newList.isEmpty()) {
				map.put(type, newList);
			}
		});
		
		return CACHE_WAYS_OF_MAKING.set(stage, item, map);
	}
	
	public static List<String> getRecipeListPrint() {
		return RECIPES.entrySet().stream().map(e -> "Loaded " + e.getValue().size() + " recipes for " + e.getKey()).collect(Collectors.toList());
	}
}
