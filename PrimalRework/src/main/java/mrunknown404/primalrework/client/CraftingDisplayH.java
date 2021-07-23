package mrunknown404.primalrework.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mrunknown404.primalrework.client.gui.screen.ScreenRecipeList;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.IStagedRecipe;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.registries.PRRecipes;
import mrunknown404.primalrework.registries.PRStagedTags;
import mrunknown404.primalrework.stage.StageH;
import mrunknown404.primalrework.stage.VanillaRegistry;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingDisplayH {
	private static final Map<Item, Data> ITEM_DATA = new HashMap<Item, Data>();
	private static final List<ItemStack> ALL_ITEMS = new ArrayList<ItemStack>();
	private static final Cache<EnumStage, List<ItemStack>> ITEM_CACHE = new Cache<EnumStage, List<ItemStack>>();
	
	public static void addItem(Item item) {
		if (item.getItemCategory() == null) {
			return;
		}
		
		ITEM_DATA.put(item, new Data(ITEM_DATA.size(), new ItemStack(item)));
	}
	
	public static void finish() {
		for (Data map : ITEM_DATA.values()) {
			ALL_ITEMS.add(map.stack);
		}
		
		ALL_ITEMS.sort(new Comparator<ItemStack>() {
			@Override
			public int compare(ItemStack o1, ItemStack o2) {
				Item i1 = o1.getItem(), i2 = o2.getItem();
				return (i1.getItemCategory().getId() * 100000) - (i2.getItemCategory().getId() * 100000) + ITEM_DATA.get(i1).order - ITEM_DATA.get(i2).order;
			}
		});
	}
	
	public static List<ItemStack> getItemList() {
		if (!ITEM_CACHE.is(StageH.getStage())) {
			List<ItemStack> list = new ArrayList<ItemStack>();
			for (ItemStack stack : ALL_ITEMS) {
				EnumStage stage = stack.getItem() instanceof StagedItem ? ((StagedItem) stack.getItem()).stage : VanillaRegistry.getStage(stack.getItem());
				if (StageH.hasAccessToStage(stage)) {
					list.add(stack);
				}
			}
			
			ITEM_CACHE.set(StageH.getStage(), list);
		}
		
		//TODO handle search here
		
		return ITEM_CACHE.get();
	}
	
	private static boolean lateRun = false; //i don't like this but doesn't want to work in #finish
	
	public static boolean isItem3D(ItemStack item) {
		if (!lateRun) {
			lateRun = true;
			ItemRenderer ir = Minecraft.getInstance().getItemRenderer();
			for (Entry<Item, Data> data : ITEM_DATA.entrySet()) {
				IBakedModel model = ir.getModel(data.getValue().stack, null, null);
				if (model.usesBlockLight()) {
					data.getValue().is3D();
				}
			}
		}
		
		return ITEM_DATA.get(item.getItem()).is3D;
	}
	
	public static void showHowToCraft(Minecraft minecraft, Item item, ContainerScreen<?> lastScreen) {
		Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> recipes = PRRecipes.getRecipesForOutput(item);
		if (!recipes.isEmpty()) {
			minecraft.setScreen(new ScreenRecipeList(lastScreen, recipes, item));
		}
	}
	
	public static void showWhatCanBeMade(Minecraft minecraft, Item item, ContainerScreen<?> lastScreen) {
		Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> recipes = PRRecipes.getRecipesContainingInput(new Ingredient(item, PRStagedTags.getItemsTags(item)));
		if (!recipes.isEmpty()) {
			minecraft.setScreen(new ScreenRecipeList(lastScreen, recipes, item));
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
