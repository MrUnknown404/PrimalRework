package mrunknown404.primalrework.util.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mrunknown404.primalrework.client.gui.GuiFirePit;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.inventory.ContainerFirePit;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEICompat implements IModPlugin {

	//TODO add information pages for in-world things like knapping
	
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
}
