package mrunknown404.primalrework.util.jei;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mezz.jei.transfer.PlayerRecipeTransferHandler;
import mrunknown404.primalrework.client.gui.GuiFirePit;
import mrunknown404.primalrework.handlers.StageHandler;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.inventory.ContainerFirePit;
import mrunknown404.primalrework.util.recipes.util.IRecipeWrapperBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEICompat implements IModPlugin {

	//TODO add information pages for in-world things like knapping
	//TODO hide stage recipes
	
	public static final Map<String, List<IRecipeWrapperBase<?>>> RECIPE_MAP = new HashMap<String, List<IRecipeWrapperBase<?>>>();
	public static IRecipeRegistry rr;
	public static IJeiHelpers helper;
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {
		helper = reg.getJeiHelpers();
		IGuiHelper gui = helper.getGuiHelper();
		
		reg.addRecipeCategories(new FirePitRecipeCategory(gui));
		reg.addRecipeCategories(new StagedCraftingRecipeCategory(gui));
	}
	
	@Override
	public void register(IModRegistry reg) {
		IRecipeTransferRegistry recipeTransfer = reg.getRecipeTransferRegistry();
		
		reg.addRecipes(ModRecipes.getWrappedFirePitRecipes(), ModRecipes.CATEGORY_FIRE_PIT);
		reg.addRecipes(ModRecipes.getWrappedStageRecipes(), ModRecipes.CATEGORY_STAGED_CRAFTING);
		
		reg.addRecipeClickArea(GuiFirePit.class, 102, 9, 3, 25, ModRecipes.CATEGORY_FIRE_PIT);
		reg.addRecipeClickArea(GuiInventory.class, 137, 29, 10, 13, ModRecipes.CATEGORY_STAGED_CRAFTING);
		reg.addRecipeClickArea(GuiCrafting.class, 88, 32, 28, 23, ModRecipes.CATEGORY_STAGED_CRAFTING); //TODO replace vanilla crafting GUI with custom GUI
		
		reg.addRecipeCatalyst(new ItemStack(ModBlocks.FIRE_PIT), ModRecipes.CATEGORY_FIRE_PIT);
		reg.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), ModRecipes.CATEGORY_STAGED_CRAFTING);
		
		recipeTransfer.addRecipeTransferHandler(ContainerFirePit.class, ModRecipes.CATEGORY_FIRE_PIT, FirePitRecipeCategory.SLOT_INPUT, 1, FirePitRecipeCategory.SLOT_INPUT, 36);
		recipeTransfer.addRecipeTransferHandler(ContainerWorkbench.class, ModRecipes.CATEGORY_STAGED_CRAFTING, 1, 9, 10, 36);
		recipeTransfer.addRecipeTransferHandler(new PlayerRecipeTransferHandler(reg.getJeiHelpers().recipeTransferHandlerHelper()), ModRecipes.CATEGORY_STAGED_CRAFTING);
	}
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime run) {
		rr = run.getRecipeRegistry();
		
		RECIPE_MAP.put(ModRecipes.CATEGORY_FIRE_PIT, rr.getRecipeWrappers(rr.getRecipeCategory(ModRecipes.CATEGORY_FIRE_PIT)));
		RECIPE_MAP.put(ModRecipes.CATEGORY_STAGED_CRAFTING, rr.getRecipeWrappers(rr.getRecipeCategory(ModRecipes.CATEGORY_STAGED_CRAFTING)));
		
		rr.hideRecipeCategory(VanillaRecipeCategoryUid.CRAFTING);
		rr.hideRecipeCategory(VanillaRecipeCategoryUid.FUEL);
		rr.hideRecipeCategory(VanillaRecipeCategoryUid.ANVIL);
	}
	
	public static void setupRecipes() {
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			@Override
			public void run() {
				Iterator<Entry<String, List<IRecipeWrapperBase<?>>>> it = RECIPE_MAP.entrySet().iterator();
				
				while (it.hasNext()) {
					Entry<String, List<IRecipeWrapperBase<?>>> pair = it.next();
					
					for (IRecipeWrapperBase<?> wrap : pair.getValue()) {
						if (StageHandler.hasAccessToStage(wrap.getRecipe().getStage())) {
							rr.unhideRecipe(wrap, pair.getKey());
						} else {
							rr.hideRecipe(wrap, pair.getKey());
						}
					}
				}
			}
		});
	}
}
