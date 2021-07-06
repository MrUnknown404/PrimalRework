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
	
	// Recipe Todos
	// TODO add dye recipes
	
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
		
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage0, InitItems.PLANT_ROPE.get(), 1,
				RICrafting3.shapeless().set(InitItems.PLANT_FIBER.get(), InitItems.PLANT_FIBER.get(), InitItems.PLANT_FIBER.get()).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage0, InitItems.PLANT_MESH.get(), 1,
				RICrafting3.shaped().set2x2(Items.STICK, InitItems.PLANT_FIBER.get(), InitItems.PLANT_FIBER.get(), Items.STICK).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, InitTools.CLAY_SHOVEL.get(), 1, RICrafting3.shaped().set1x2(Items.CLAY_BALL, Items.STICK).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage0, InitTools.CLAY_AXE.get(), 1,
				RICrafting3.shaped().set2x2(Items.CLAY_BALL, Items.CLAY_BALL, Items.CLAY_BALL, Items.STICK).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage1, InitTools.WOOD_SHOVEL.get(), 1, RICrafting3.shaped().set1x3(InitStagedTags.ALL_LOGS, Items.STICK, Items.STICK).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage1, InitTools.WOOD_AXE.get(), 1,
				RICrafting3.shaped().set2x3(InitStagedTags.ALL_LOGS, InitStagedTags.ALL_LOGS, InitStagedTags.ALL_LOGS, Items.STICK, null, Items.STICK).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage1, Items.COBBLESTONE, 1, RICrafting3.shaped().setRing(InitBlocks.GROUND_ROCK.get().asItem()).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, InitBlocks.THATCH.get().asItem(), 1, RICrafting3.shaped().set2x2(InitItems.PLANT_FIBER.get()).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, InitItems.PLANT_FIBER.get(), 4, RICrafting3.shapeless().set(InitBlocks.THATCH.get().asItem()).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage1, InitTools.BONE_SWORD.get(), 1, RICrafting3.shaped().set1x2(InitItems.BONE_SHARD.get(), Items.STICK).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage1, Items.OAK_PLANKS, 1, RICrafting3.shaped().setRing(InitItems.OAK_PLANK.get()).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage1, Items.BIRCH_PLANKS, 1, RICrafting3.shaped().setRing(InitItems.BIRCH_PLANK.get()).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage1, Items.SPRUCE_PLANKS, 1, RICrafting3.shaped().setRing(InitItems.SPRUCE_PLANK.get()).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage1, Items.JUNGLE_PLANKS, 1, RICrafting3.shaped().setRing(InitItems.JUNGLE_PLANK.get()).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage1, Items.ACACIA_PLANKS, 1, RICrafting3.shaped().setRing(InitItems.ACACIA_PLANK.get()).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage1, Items.DARK_OAK_PLANKS, 1, RICrafting3.shaped().setRing(InitItems.DARK_OAK_PLANK.get()).finish()));
		addRecipe(EnumRecipeType.crafting_3, new SRCrafting3(EnumStage.stage2, Items.WHITE_WOOL, 1, RICrafting3.shaped().set2x2(InitItems.CLOTH.get()).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.WHITE_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.WHITE_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.ORANGE_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.ORANGE_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.MAGENTA_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.MAGENTA_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.LIGHT_BLUE_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.LIGHT_BLUE_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.YELLOW_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.YELLOW_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.LIME_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.LIME_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.PINK_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.PINK_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.GRAY_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.GRAY_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.LIGHT_GRAY_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.LIGHT_GRAY_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.CYAN_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.CYAN_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.PURPLE_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.PURPLE_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.BLUE_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.BLUE_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.BROWN_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.BROWN_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.GREEN_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.GREEN_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.RED_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.RED_DYE).finish()));
		addRecipe(EnumRecipeType.crafting_3,
				new SRCrafting3(EnumStage.stage0, Items.BLACK_WOOL, 1, RICrafting3.shaped().setRing(InitStagedTags.ALL_WOOL).setMiddle(Items.BLACK_DYE).finish()));
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
