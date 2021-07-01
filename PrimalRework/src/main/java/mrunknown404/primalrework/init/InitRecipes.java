package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.recipes.IStagedRecipe;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.SRCampFire;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.recipes.input.RICrafting3;
import mrunknown404.primalrework.recipes.input.RecipeInput;
import mrunknown404.primalrework.stage.StageH;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class InitRecipes {
	private static final Map<FuelType, Map<Item, Integer>> FUELS = new HashMap<FuelType, Map<Item, Integer>>();
	private static final Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> RECIPES = new HashMap<EnumRecipeType, List<IStagedRecipe<?, ?>>>();
	
	//haha these names suck and won't be confusing later!
	private static Map<EnumRecipeType, Cache<Item, List<IStagedRecipe<?, ?>>>> cacheRecipesForOutput0 = new HashMap<EnumRecipeType, Cache<Item, List<IStagedRecipe<?, ?>>>>();
	private static Map<EnumRecipeType, Cache<RecipeInput<?>, IStagedRecipe<?, ?>>> cacheRecipesForInput = new HashMap<EnumRecipeType, Cache<RecipeInput<?>, IStagedRecipe<?, ?>>>();
	private static Map<EnumRecipeType, Cache<Ingredient, List<IStagedRecipe<?, ?>>>> cacheRecipesContainingInput0 = new HashMap<EnumRecipeType, Cache<Ingredient, List<IStagedRecipe<?, ?>>>>();
	private static Cache<Item, Map<EnumRecipeType, List<IStagedRecipe<?, ?>>>> cacheRecipesForOutput1 = new Cache<Item, Map<EnumRecipeType, List<IStagedRecipe<?, ?>>>>();
	private static Cache<Ingredient, Map<EnumRecipeType, List<IStagedRecipe<?, ?>>>> cacheRecipesContainingInput1 = new Cache<Ingredient, Map<EnumRecipeType, List<IStagedRecipe<?, ?>>>>() {
		@Override
		public boolean is(Ingredient key) {
			return this.key == null ? false : this.key.matches(key);
		}
	};
	
	public static void load() {
		for (FuelType type : FuelType.values()) {
			FUELS.put(type, new HashMap<Item, Integer>());
		}
		for (EnumRecipeType type : EnumRecipeType.values()) {
			RECIPES.put(type, new ArrayList<IStagedRecipe<?, ?>>());
			cacheRecipesForOutput0.put(type, new Cache<Item, List<IStagedRecipe<?, ?>>>());
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
		
		int oneCampfireItem = 200;
		addFuel(FuelType.campfire, Items.STICK, oneCampfireItem / 4);
		addFuel(FuelType.campfire, Items.COAL, oneCampfireItem * 8);
		addFuel(FuelType.campfire, Items.CHARCOAL, oneCampfireItem * 8);
		for (Item item : InitStagedTags.ALL_LOGS.getItemsFromAllStages()) {
			addFuel(FuelType.campfire, item, oneCampfireItem * 4);
		}
		for (Item item : InitStagedTags.ALL_PLANK_BLOCKS.getItemsFromAllStages()) {
			addFuel(FuelType.campfire, item, oneCampfireItem);
		}
		for (Item item : InitStagedTags.ALL_PLANKS.getItemsFromAllStages()) {
			addFuel(FuelType.campfire, item, oneCampfireItem / 8);
		}
		
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, Items.PORKCHOP, Items.COOKED_PORKCHOP));
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, Items.BEEF, Items.COOKED_BEEF));
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, Items.CHICKEN, Items.COOKED_CHICKEN));
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, Items.MUTTON, Items.COOKED_MUTTON));
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, Items.RABBIT, Items.COOKED_RABBIT));
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, Items.COD, Items.COOKED_COD));
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, Items.SALMON, Items.COOKED_SALMON));
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, Items.POTATO, Items.BAKED_POTATO));
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, Items.CARROT, InitItems.COOKED_CARROT.get()));
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, Items.BEETROOT, InitItems.COOKED_BEETROOT.get()));
		addRecipe(EnumRecipeType.campfire, new SRCampFire(EnumStage.stage1, InitBlocks.UNLIT_PRIMAL_TORCH.get().asItem(), InitBlocks.LIT_PRIMAL_TORCH.get().asItem(), 100));
		
		//TODO add more recipes
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage0, Items.OAK_PLANKS, 4, RICrafting3.create(true).set(InitStagedTags.OAK_LOGS).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage0, Items.SPRUCE_PLANKS, 4, RICrafting3.create(true).set(InitStagedTags.SPRUCE_LOGS).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage0, Items.BIRCH_PLANKS, 4, RICrafting3.create(true).set(InitStagedTags.BIRCH_LOGS).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage0, Items.JUNGLE_PLANKS, 4, RICrafting3.create(true).set(InitStagedTags.JUNGLE_LOGS).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage0, Items.DARK_OAK_PLANKS, 4, RICrafting3.create(true).set(InitStagedTags.DARK_OAK_LOGS).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage0, Items.ACACIA_PLANKS, 4, RICrafting3.create(true).set(InitStagedTags.ACACIA_LOGS).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage0, Items.STICK, 4, RICrafting3.create().set1x2(InitStagedTags.ALL_PLANK_BLOCKS).finish()));
	}
	
	public static void addFuel(FuelType type, Item item, int cookTime) {
		FUELS.get(type).put(item, cookTime);
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
	public static <T extends IStagedRecipe<T, ?>> List<T> getRecipesForOutput(EnumRecipeType type, Item output) {
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
	
	public static Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> getRecipesForOutput(Item item) {
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
	
	public static boolean isFuelItem(FuelType type, Item item) {
		return getBurnTime(type, item) > 0;
	}
	
	public static int getBurnTime(FuelType type, Item item) {
		return FUELS.get(type).getOrDefault(item, 0);
	}
	
	public enum FuelType {
		campfire;
	}
}
