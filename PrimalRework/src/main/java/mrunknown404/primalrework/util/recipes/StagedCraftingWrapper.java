package mrunknown404.primalrework.util.recipes;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import mrunknown404.primalrework.util.recipes.util.IRecipeWrapperBase;
import mrunknown404.primalrework.util.recipes.util.IStagedRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class StagedCraftingWrapper implements IRecipeWrapperBase<IStagedRecipeBase>, ICustomCraftingRecipeWrapper {

	private final IStagedRecipeBase recipe;
	
	public StagedCraftingWrapper(IStagedRecipeBase recipe) {
		this.recipe = recipe;
	}
	
	public boolean isShapeless() {
		return recipe.isShapeless();
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ItemStack recipeOutput = recipe.getOutputs().get(0);
		
		ingredients.setInputLists(VanillaTypes.ITEM, recipe.getListInputs());
		ingredients.setOutput(VanillaTypes.ITEM, recipeOutput);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		FontRenderer r = minecraft.fontRenderer;
		
		int width = r.getStringWidth(recipe.getStage().getName());
		r.drawString(recipe.getStage().getName(), recipeWidth / 2 - width / 2, 56, 16777215, true);
	}
	
	public static List<StagedCraftingWrapper> createFromList(List<IStagedRecipeBase> list) {
		List<StagedCraftingWrapper> wr = new ArrayList<StagedCraftingWrapper>();
		
		for (IStagedRecipeBase r : list) {
			wr.add(new StagedCraftingWrapper(r));
		}
		
		return wr;
	}
	
	@Override
	public IStagedRecipeBase getRecipe() {
		return recipe;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients) {
		
	}
}
