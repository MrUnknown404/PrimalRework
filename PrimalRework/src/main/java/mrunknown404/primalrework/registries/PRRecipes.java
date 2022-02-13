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
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import mrunknown404.primalrework.utils.helpers.StageH;

public class PRRecipes {
	private static final Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> RECIPES = new HashMap<EnumRecipeType, List<IStagedRecipe<?, ?>>>();
	
	//haha these names suck and won't be confusing later!
	private static final Map<EnumRecipeType, Cache<StagedItem, List<IStagedRecipe<?, ?>>>> cacheRecipesForOutput0 = new HashMap<EnumRecipeType, Cache<StagedItem, List<IStagedRecipe<?, ?>>>>();
	private static final Map<EnumRecipeType, Cache<RecipeInput<?>, IStagedRecipe<?, ?>>> cacheRecipesForInput = new HashMap<EnumRecipeType, Cache<RecipeInput<?>, IStagedRecipe<?, ?>>>();
	private static final Map<EnumRecipeType, Cache<Ingredient, List<IStagedRecipe<?, ?>>>> cacheRecipesContainingInput0 = new HashMap<EnumRecipeType, Cache<Ingredient, List<IStagedRecipe<?, ?>>>>();
	private static final Cache<StagedItem, Map<EnumRecipeType, List<IStagedRecipe<?, ?>>>> cacheRecipesForOutput1 = new Cache<StagedItem, Map<EnumRecipeType, List<IStagedRecipe<?, ?>>>>();
	private static final Cache<Ingredient, Map<EnumRecipeType, List<IStagedRecipe<?, ?>>>> cacheRecipesContainingInput1 = new Cache<Ingredient, Map<EnumRecipeType, List<IStagedRecipe<?, ?>>>>() {
		@Override
		public boolean is(Ingredient key) {
			return this.key == null ? false : this.key.matches(key);
		}
	};
	
	public static void load() {
		for (EnumRecipeType type : EnumRecipeType.values()) {
			RECIPES.put(type, new ArrayList<IStagedRecipe<?, ?>>());
			cacheRecipesForOutput0.put(type, new Cache<StagedItem, List<IStagedRecipe<?, ?>>>());
			cacheRecipesForInput.put(type, new Cache<RecipeInput<?>, IStagedRecipe<?, ?>>() {
				@Override
				public boolean is(RecipeInput<?> key) {
					return this.key == null ? false : this.key.matches(key);
				}
			});
			cacheRecipesContainingInput0.put(type, new Cache<Ingredient, List<IStagedRecipe<?, ?>>>() {
				@Override
				public boolean is(Ingredient key) {
					return this.key == null ? false : this.key.matches(key);
				}
			});
		}
		
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(PRStages.STAGE_0.get(), PRBlocks.DIRT.get().asStagedItem(), 4, RICrafting3.shaped().set1x2(PRStagedTags.LOGS).finish()));
	}
	
	public static void addRecipe(EnumRecipeType type, IStagedRecipe<?, ?> recipe) {
		RECIPES.get(type).add(recipe);
	}
	
	public static IStagedRecipe<?, ?> getRecipeForInput(EnumRecipeType type, RecipeInput<?> input) {
		if (input.isEmpty()) {
			return null;
		}
		
		if (cacheRecipesForInput.get(type).is(input)) {
			return cacheRecipesForInput.get(type).get();
		}
		
		for (IStagedRecipe<?, ?> recipe : RECIPES.get(type)) {
			if (StageH.hasAccessToStage(recipe.getStage()) && recipe.getInput().matches(input)) {
				cacheRecipesForInput.get(type).set(recipe.getInput(), recipe);
				return recipe;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends IStagedRecipe<T, ?>> List<T> getRecipesForOutput(EnumRecipeType type, StagedItem output) {
		if (cacheRecipesForOutput0.get(type).is(output)) {
			return (List<T>) cacheRecipesForOutput0.get(type).get();
		}
		
		List<T> list = new ArrayList<T>();
		for (IStagedRecipe<?, ?> recipe : RECIPES.get(type)) {
			if (StageH.hasAccessToStage(recipe.getStage()) && recipe.getOutput().getItem() == output) {
				list.add((T) recipe);
			}
		}
		
		cacheRecipesForOutput0.get(type).set(output, (List<IStagedRecipe<?, ?>>) list);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends IStagedRecipe<T, ?>> List<T> getRecipesContainingInput(EnumRecipeType type, Ingredient input) {
		if (cacheRecipesContainingInput0.get(type).is(input)) {
			return (List<T>) cacheRecipesContainingInput0.get(type).get();
		}
		
		List<T> list = new ArrayList<T>();
		for (IStagedRecipe<?, ?> recipe : RECIPES.get(type)) {
			if (StageH.hasAccessToStage(recipe.getStage()) && recipe.has(input)) {
				list.add((T) recipe);
			}
		}
		
		cacheRecipesContainingInput0.get(type).set(input, (List<IStagedRecipe<?, ?>>) list);
		return list;
	}
	
	public static Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> getRecipesContainingInput(Ingredient ingredient) {
		if (cacheRecipesContainingInput1.is(ingredient)) {
			return cacheRecipesContainingInput1.get();
		}
		
		Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> map = new HashMap<EnumRecipeType, List<IStagedRecipe<?, ?>>>();
		for (EnumRecipeType type : EnumRecipeType.values()) {
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
		
		cacheRecipesContainingInput1.set(ingredient, map);
		return map;
	}
	
	public static Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> getRecipesForOutput(StagedItem item) {
		if (cacheRecipesForOutput1.is(item)) {
			return cacheRecipesForOutput1.get();
		}
		
		Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> map = new HashMap<EnumRecipeType, List<IStagedRecipe<?, ?>>>();
		for (EnumRecipeType type : EnumRecipeType.values()) {
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
		
		cacheRecipesForOutput1.set(item, map);
		return map;
	}
}
