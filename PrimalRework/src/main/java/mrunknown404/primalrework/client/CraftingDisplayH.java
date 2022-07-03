package mrunknown404.primalrework.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mrunknown404.primalrework.client.gui.screen.ScreenRecipeList;
import mrunknown404.primalrework.init.InitPRFuels;
import mrunknown404.primalrework.init.InitRecipes;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.StagedRecipe;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.Pair;
import mrunknown404.primalrework.utils.enums.FuelType;
import mrunknown404.primalrework.utils.enums.RecipeType;
import mrunknown404.primalrework.utils.helpers.StageH;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingDisplayH {
	private static final Map<StagedItem, Data> ITEM_DATA = new HashMap<StagedItem, Data>();
	private static final List<ItemStack> ALL_ITEMS = new ArrayList<ItemStack>();
	private static final Cache<Stage, List<ItemStack>> ITEM_CACHE = new Cache<Stage, List<ItemStack>>();
	
	public static void addItem(StagedItem item) {
		if (item.getItemCategory() == null) {
			return;
		}
		
		ITEM_DATA.put(item, new Data(ITEM_DATA.size(), new ItemStack(item)));
	}
	
	public static void finish() {
		ITEM_DATA.values().forEach((d) -> ALL_ITEMS.add(d.stack));
		
		ALL_ITEMS.sort((o1, o2) -> {
			Item i1 = o1.getItem(), i2 = o2.getItem();
			return (i1.getItemCategory().getId() * 100000) - (i2.getItemCategory().getId() * 100000) + ITEM_DATA.get(i1).order - ITEM_DATA.get(i2).order;
		});
	}
	
	public static List<ItemStack> getItemList() {
		if (!ITEM_CACHE.is(StageH.getStage())) {
			ITEM_CACHE.set(StageH.getStage(), ALL_ITEMS.stream().filter((s) -> StageH.hasAccessToStage(((StagedItem) s.getItem()).stage.get())).collect(Collectors.toList()));
		}
		
		//TODO handle search here
		
		return ITEM_CACHE.get();
	}
	
	private static boolean lateRun = false; //i don't like this but doesn't want to work in #finish
	
	public static boolean isItem3D(ItemStack item) {
		if (!lateRun) {
			lateRun = true;
			ItemRenderer ir = Minecraft.getInstance().getItemRenderer();
			ITEM_DATA.values().stream().filter((data) -> ir.getModel(data.stack, null, null).usesBlockLight()).forEach((data) -> data.is3D());
		}
		
		return ITEM_DATA.get(item.getItem()).is3D;
	}
	
	public static void showHowToCraft(Minecraft minecraft, StagedItem item, ContainerScreen<?> lastScreen) {
		Map<RecipeType, List<StagedRecipe<?, ?>>> recipes = InitRecipes.getRecipesForOutput(item);
		if (!recipes.isEmpty()) {
			minecraft.setScreen(new ScreenRecipeList(lastScreen, recipes, null, item));
		}
	}
	
	public static void showWhatCanBeMade(Minecraft minecraft, StagedItem item, ContainerScreen<?> lastScreen) {
		Map<RecipeType, List<StagedRecipe<?, ?>>> recipes = InitRecipes.getRecipesContainingInput(Ingredient.createUsingTags(item));
		Map<FuelType, Pair<StagedItem, Integer>> fuels = InitPRFuels.getFuels(item);
		if (!recipes.isEmpty() || !fuels.isEmpty()) {
			minecraft.setScreen(new ScreenRecipeList(lastScreen, recipes, fuels, item));
		}
	}
	
	private static class Data {
		private final int order;
		private boolean is3D;
		private ItemStack stack;
		
		private Data(int order, ItemStack stack) {
			this.order = order;
			this.stack = stack;
		}
		
		private void is3D() {
			is3D = true;
		}
	}
}
