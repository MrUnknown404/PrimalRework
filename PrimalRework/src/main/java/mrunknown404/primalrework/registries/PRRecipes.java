package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.IStagedRecipe;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.recipes.input.RICrafting3;
import mrunknown404.primalrework.recipes.input.RecipeInput;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.utils.DoubleCache.DoubleCachePredicate;
import mrunknown404.primalrework.utils.enums.RecipeType;
import mrunknown404.primalrework.utils.helpers.StageH;

public class PRRecipes {
	private static final Map<RecipeType, List<IStagedRecipe<?, ?>>> RECIPES = new HashMap<RecipeType, List<IStagedRecipe<?, ?>>>();
	
	//haha these names suck and won't be confusing later!
	private static final Map<RecipeType, DoubleCache<Stage, StagedItem, List<IStagedRecipe<?, ?>>>> cacheRecipesForOutput0 = new HashMap<RecipeType, DoubleCache<Stage, StagedItem, List<IStagedRecipe<?, ?>>>>();
	private static final Map<RecipeType, DoubleCache<Stage, RecipeInput<?>, IStagedRecipe<?, ?>>> cacheRecipesForInput = new HashMap<RecipeType, DoubleCache<Stage, RecipeInput<?>, IStagedRecipe<?, ?>>>();
	private static final Map<RecipeType, DoubleCache<Stage, Ingredient, List<IStagedRecipe<?, ?>>>> cacheRecipesContainingInput0 = new HashMap<RecipeType, DoubleCache<Stage, Ingredient, List<IStagedRecipe<?, ?>>>>();
	private static final DoubleCache<Stage, StagedItem, Map<RecipeType, List<IStagedRecipe<?, ?>>>> cacheRecipesForOutput1 = DoubleCache.and();
	private static final DoubleCache<Stage, Ingredient, Map<RecipeType, List<IStagedRecipe<?, ?>>>> cacheRecipesContainingInput1 = DoubleCache
			.create((o0, o1, k0, k1) -> (o0 == k0 || (o0 != null && o0.equals(k0))) && (o1 == null ? false : o1.matches(k1)));
	
	public static void load() {
		final DoubleCachePredicate<Stage, RecipeInput<?>> predicate0 = (o0, o1, k0, k1) -> (o0 == k0 || (o0 != null && o0.equals(k0))) && (o1 == null ? false : o1.matches(k1));
		final DoubleCachePredicate<Stage, Ingredient> predicate1 = (o0, o1, k0, k1) -> (o0 == k0 || (o0 != null && o0.equals(k0))) && (o1 == null ? false : o1.matches(k1));
		
		for (RecipeType type : RecipeType.values()) {
			RECIPES.put(type, new ArrayList<IStagedRecipe<?, ?>>());
			cacheRecipesForOutput0.put(type, DoubleCache.and());
			cacheRecipesForInput.put(type, DoubleCache.create(predicate0));
			cacheRecipesContainingInput0.put(type, DoubleCache.create(predicate1));
		}
		
		//@formatter:off
		Stage stage = PRStages.STAGE_0.get();
		addRecipe(new SRCrafting3(stage, PRItems.CLAY_SHOVEL.get(), 1, RICrafting3.shaped().set1x2(PRItems.CLAY_BALL, PRItems.STICK).finish()));
		addRecipe(new SRCrafting3(stage, PRItems.CLAY_AXE.get(), 1, RICrafting3.shaped().set2x2(PRItems.CLAY_BALL, PRItems.CLAY_BALL, null, PRItems.STICK).finish()));
		addRecipe(new SRCrafting3(stage, PRItems.PLANT_ROPE.get(), 1, RICrafting3.shapeless().set(PRItems.PLANT_FIBER, 3).finish()));
		addRecipe(new SRCrafting3(stage, PRItems.PLANT_MESH.get(), 1, RICrafting3.shaped().set2x2(PRItems.STICK, PRItems.PLANT_FIBER, PRItems.PLANT_FIBER, PRItems.STICK).finish()));
		addRecipe(new SRCrafting3(stage, PRBlocks.THATCH.get().asStagedItem(), 1, RICrafting3.shaped().set2x2(PRItems.PLANT_FIBER).finish()));
		//@formatter:on
	}
	
	public static void addRecipe(IStagedRecipe<?, ?> recipe) {
		RECIPES.get(recipe.getRecipeType()).add(recipe);
	}
	
	public static IStagedRecipe<?, ?> getRecipeForInput(RecipeInput<?> input) {
		if (input.isEmpty()) {
			return null;
		}
		
		Stage stage = StageH.getStage();
		if (cacheRecipesForInput.get(input.getRecipeType()).is(stage, input)) {
			return cacheRecipesForInput.get(input.getRecipeType()).get();
		}
		
		for (IStagedRecipe<?, ?> recipe : RECIPES.get(input.getRecipeType())) {
			if (StageH.hasAccessToStage(recipe.getStage()) && recipe.getInput().matches(input)) {
				cacheRecipesForInput.get(input.getRecipeType()).set(stage, recipe.getInput(), recipe);
				return recipe;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends IStagedRecipe<T, ?>> List<T> getRecipesForOutput(RecipeType type, StagedItem output) {
		Stage stage = StageH.getStage();
		if (cacheRecipesForOutput0.get(type).is(stage, output)) {
			return (List<T>) cacheRecipesForOutput0.get(type).get();
		}
		
		List<T> list = new ArrayList<T>();
		for (IStagedRecipe<?, ?> recipe : RECIPES.get(type)) {
			if (StageH.hasAccessToStage(recipe.getStage()) && recipe.getOutput().getItem() == output) {
				list.add((T) recipe);
			}
		}
		
		cacheRecipesForOutput0.get(type).set(stage, output, (List<IStagedRecipe<?, ?>>) list);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends IStagedRecipe<T, ?>> List<T> getRecipesContainingInput(RecipeType type, Ingredient input) {
		Stage stage = StageH.getStage();
		if (cacheRecipesContainingInput0.get(type).is(stage, input)) {
			return (List<T>) cacheRecipesContainingInput0.get(type).get();
		}
		
		List<T> list = new ArrayList<T>();
		for (IStagedRecipe<?, ?> recipe : RECIPES.get(type)) {
			if (StageH.hasAccessToStage(recipe.getStage()) && recipe.has(input)) {
				list.add((T) recipe);
			}
		}
		
		cacheRecipesContainingInput0.get(type).set(stage, input, (List<IStagedRecipe<?, ?>>) list);
		return list;
	}
	
	public static Map<RecipeType, List<IStagedRecipe<?, ?>>> getRecipesContainingInput(Ingredient ingredient) {
		Stage stage = StageH.getStage();
		if (cacheRecipesContainingInput1.is(stage, ingredient)) {
			return cacheRecipesContainingInput1.get();
		}
		
		Map<RecipeType, List<IStagedRecipe<?, ?>>> map = new HashMap<RecipeType, List<IStagedRecipe<?, ?>>>();
		for (RecipeType type : RecipeType.values()) {
			List<IStagedRecipe<?, ?>> list = new ArrayList<IStagedRecipe<?, ?>>();
			for (IStagedRecipe<?, ?> recipe : RECIPES.get(type)) {
				if (StageH.hasAccessToStage(recipe.getStage()) && recipe.has(ingredient)) {
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
	
	public static Map<RecipeType, List<IStagedRecipe<?, ?>>> getRecipesForOutput(StagedItem item) {
		Stage stage = StageH.getStage();
		if (cacheRecipesForOutput1.is(stage, item)) {
			return cacheRecipesForOutput1.get();
		}
		
		Map<RecipeType, List<IStagedRecipe<?, ?>>> map = new HashMap<RecipeType, List<IStagedRecipe<?, ?>>>();
		for (RecipeType type : RecipeType.values()) {
			List<IStagedRecipe<?, ?>> list = new ArrayList<IStagedRecipe<?, ?>>();
			for (IStagedRecipe<?, ?> recipe : RECIPES.get(type)) {
				if (StageH.hasAccessToStage(recipe.getStage()) && recipe.getOutput().getItem() == item) {
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
