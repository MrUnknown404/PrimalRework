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
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mrunknown404.primalrework.client.gui.GuiFirePit;
import mrunknown404.primalrework.handlers.StageHandler;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.inventory.ContainerFirePit;
import mrunknown404.primalrework.util.recipes.util.IRecipeWrapperBase;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEICompat implements IModPlugin {

	//TODO add information pages for in-world things like knapping
	//TODO hide stage recipes
	
	public static final Map<String, List<IRecipeWrapperBase<?>>> RECIPE_MAP = new HashMap<String, List<IRecipeWrapperBase<?>>>();
	public static IRecipeRegistry rr;
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {
		final IJeiHelpers helpers = reg.getJeiHelpers();
		final IGuiHelper gui = helpers.getGuiHelper();
		
		reg.addRecipeCategories(new FirePitRecipeCategory(gui));
	}
	
	@Override
	public void register(IModRegistry reg) {
		IRecipeTransferRegistry recipeTransfer = reg.getRecipeTransferRegistry();
		
		reg.addRecipes(ModRecipes.getWrappedFirePitRecipes(), ModRecipes.CATEGORY_FIRE_PIT);
		reg.addRecipeClickArea(GuiFirePit.class, 102, 9, 3, 25, ModRecipes.CATEGORY_FIRE_PIT);
		reg.addRecipeCatalyst(new ItemStack(ModBlocks.FIRE_PIT), ModRecipes.CATEGORY_FIRE_PIT);
		recipeTransfer.addRecipeTransferHandler(ContainerFirePit.class, ModRecipes.CATEGORY_FIRE_PIT, FirePitRecipeCategory.SLOT_INPUT, 1, FirePitRecipeCategory.SLOT_INPUT, 36);
	}
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime run) {
		rr = run.getRecipeRegistry();
		
		RECIPE_MAP.put(ModRecipes.CATEGORY_FIRE_PIT, rr.getRecipeWrappers(rr.getRecipeCategory(ModRecipes.CATEGORY_FIRE_PIT)));
		
		//rr.hideRecipeCategory(VanillaRecipeCategoryUid.CRAFTING);
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
