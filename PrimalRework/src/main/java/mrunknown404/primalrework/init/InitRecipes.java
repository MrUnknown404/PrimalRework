package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.PrimalRework;
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
import mrunknown404.primalrework.utils.helpers.StageH;

public class InitRecipes {
	private static final Map<RecipeType, List<StagedRecipe<?, ?>>> RECIPES = new HashMap<RecipeType, List<StagedRecipe<?, ?>>>();
	
	//haha these names suck and won't be confusing later!
	private static final Map<RecipeType, DoubleCache<Stage, StagedItem, List<StagedRecipe<?, ?>>>> cacheRecipesForOutput0 = new HashMap<RecipeType, DoubleCache<Stage, StagedItem, List<StagedRecipe<?, ?>>>>();
	private static final Map<RecipeType, DoubleCache<Stage, RecipeInput<?>, StagedRecipe<?, ?>>> cacheRecipesForInput = new HashMap<RecipeType, DoubleCache<Stage, RecipeInput<?>, StagedRecipe<?, ?>>>();
	private static final Map<RecipeType, DoubleCache<Stage, Ingredient, List<StagedRecipe<?, ?>>>> cacheRecipesContainingInput0 = new HashMap<RecipeType, DoubleCache<Stage, Ingredient, List<StagedRecipe<?, ?>>>>();
	private static final DoubleCache<Stage, StagedItem, Map<RecipeType, List<StagedRecipe<?, ?>>>> cacheRecipesForOutput1 = DoubleCache.and();
	private static final DoubleCache<Stage, Ingredient, Map<RecipeType, List<StagedRecipe<?, ?>>>> cacheRecipesContainingInput1 = DoubleCache
			.create((o0, o1, k0, k1) -> (o0 == k0 || (o0 != null && o0.equals(k0))) && (o1 == null ? false : o1.matches(k1)));
	
	public static void load() {
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
		addRecipe(new SRCrafting3(stage, InitItems.CLAY_SHOVEL, 1, RICrafting3.shaped().set1x2(InitItems.CLAY_BALL, InitItems.STICK).finish()));
		addRecipe(new SRCrafting3(stage, InitItems.CLAY_AXE,    1, RICrafting3.shaped().set2x2(InitItems.CLAY_BALL, InitItems.CLAY_BALL, null, InitItems.STICK).finish()));
		addRecipe(new SRCrafting3(stage, InitItems.PLANT_ROPE,  1, RICrafting3.shapeless().set(InitItems.PLANT_FIBER, 3).finish()));
		addRecipe(new SRCrafting3(stage, InitItems.PLANT_MESH,  1, RICrafting3.shaped().set2x2(InitItems.STICK, InitItems.PLANT_FIBER, InitItems.PLANT_FIBER, InitItems.STICK).finish()));
		addRecipe(new SRCrafting3(stage, InitBlocks.THATCH,     1, RICrafting3.shaped().set2x2(InitItems.PLANT_FIBER).finish()));
		//@formatter:on
		
		PrimalRework.printDivider();
		RECIPES.forEach((type, list) -> System.out.println("Loaded '" + list.size() + "' recipes for " + type));
	}
	
	public static void addRecipe(StagedRecipe<?, ?> recipe) {
		RECIPES.get(recipe.recipeType).add(recipe);
	}
	
	public static StagedRecipe<?, ?> getRecipeForInput(RecipeInput<?> input) {
		if (input.isEmpty()) {
			return null;
		}
		
		Stage stage = StageH.getStage();
		if (cacheRecipesForInput.get(input.recipeType).is(stage, input)) {
			return cacheRecipesForInput.get(input.recipeType).get();
		}
		
		for (StagedRecipe<?, ?> recipe : RECIPES.get(input.recipeType)) {
			if (StageH.hasAccessToStage(recipe.stage) && recipe.inputRecipe.matches(input)) {
				cacheRecipesForInput.get(input.recipeType).set(stage, recipe.inputRecipe, recipe);
				return recipe;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends StagedRecipe<T, ?>> List<T> getRecipesForOutput(RecipeType type, StagedItem output) {
		Stage stage = StageH.getStage();
		if (cacheRecipesForOutput0.get(type).is(stage, output)) {
			return (List<T>) cacheRecipesForOutput0.get(type).get();
		}
		
		List<T> list = new ArrayList<T>();
		for (StagedRecipe<?, ?> recipe : RECIPES.get(type)) {
			if (StageH.hasAccessToStage(recipe.stage) && recipe.getOutput() == output) {
				list.add((T) recipe);
			}
		}
		
		cacheRecipesForOutput0.get(type).set(stage, output, (List<StagedRecipe<?, ?>>) list);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends StagedRecipe<T, ?>> List<T> getRecipesContainingInput(RecipeType type, Ingredient input) {
		Stage stage = StageH.getStage();
		if (cacheRecipesContainingInput0.get(type).is(stage, input)) {
			return (List<T>) cacheRecipesContainingInput0.get(type).get();
		}
		
		List<T> list = new ArrayList<T>();
		for (StagedRecipe<?, ?> recipe : RECIPES.get(type)) {
			if (StageH.hasAccessToStage(recipe.stage) && recipe.has(input)) {
				list.add((T) recipe);
			}
		}
		
		cacheRecipesContainingInput0.get(type).set(stage, input, (List<StagedRecipe<?, ?>>) list);
		return list;
	}
	
	public static Map<RecipeType, List<StagedRecipe<?, ?>>> getRecipesContainingInput(Ingredient ingredient) {
		Stage stage = StageH.getStage();
		if (cacheRecipesContainingInput1.is(stage, ingredient)) {
			return cacheRecipesContainingInput1.get();
		}
		
		Map<RecipeType, List<StagedRecipe<?, ?>>> map = new HashMap<RecipeType, List<StagedRecipe<?, ?>>>();
		for (RecipeType type : RecipeType.values()) {
			List<StagedRecipe<?, ?>> list = new ArrayList<StagedRecipe<?, ?>>();
			for (StagedRecipe<?, ?> recipe : RECIPES.get(type)) {
				if (StageH.hasAccessToStage(recipe.stage) && recipe.has(ingredient)) {
					list.add(recipe);
				}
			}
			
			if (!list.isEmpty()) {
				map.put(type, list);
			}
		}
		
		cacheRecipesContainingInput1.set(stage, ingredient, map);
		return map;
	}
	
	public static Map<RecipeType, List<StagedRecipe<?, ?>>> getRecipesForOutput(StagedItem item) {
		Stage stage = StageH.getStage();
		if (cacheRecipesForOutput1.is(stage, item)) {
			return cacheRecipesForOutput1.get();
		}
		
		Map<RecipeType, List<StagedRecipe<?, ?>>> map = new HashMap<RecipeType, List<StagedRecipe<?, ?>>>();
		for (RecipeType type : RecipeType.values()) {
			List<StagedRecipe<?, ?>> list = new ArrayList<StagedRecipe<?, ?>>();
			for (StagedRecipe<?, ?> recipe : RECIPES.get(type)) {
				if (StageH.hasAccessToStage(recipe.stage) && recipe.getOutput() == item) {
					list.add(recipe);
				}
			}
			
			if (!list.isEmpty()) {
				map.put(type, list);
			}
		}
		
		cacheRecipesForOutput1.set(stage, item, map);
		return map;
	}
}
