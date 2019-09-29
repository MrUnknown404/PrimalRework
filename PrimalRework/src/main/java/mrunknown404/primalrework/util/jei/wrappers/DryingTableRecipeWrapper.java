package mrunknown404.primalrework.util.jei.wrappers;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mrunknown404.primalrework.recipes.DryingTableRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TextComponentTranslation;

public class DryingTableRecipeWrapper implements IRecipeWrapperBase<DryingTableRecipe> {
	private final DryingTableRecipe recipe;
	
	public DryingTableRecipeWrapper(DryingTableRecipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(VanillaTypes.ITEM, recipe.getInput());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}
	
	@Override
	public DryingTableRecipe getRecipe() {
		return recipe;
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		FontRenderer r = minecraft.fontRenderer;
		String cookTime = new TextComponentTranslation("gui.drying_table_time").getUnformattedText() + ": " + recipe.getDryTime();
		String stage = recipe.getStage().getName();
		
		int width1 = r.getStringWidth(cookTime), width2 = r.getStringWidth(stage);
		r.drawString(cookTime, recipeWidth / 2 - width1 / 2, 28, 16777215, true);
		r.drawString(stage, recipeWidth / 2 - width2 / 2, 37, 16777215, true);
	}
	
	public static List<DryingTableRecipeWrapper> createFromList(List<DryingTableRecipe> list) {
		List<DryingTableRecipeWrapper> wr = new ArrayList<DryingTableRecipeWrapper>();
		
		for (DryingTableRecipe r : list) {
			wr.add(new DryingTableRecipeWrapper(r));
		}
		
		return wr;
	}
	
	@Override
	public String toString() {
		return recipe.toString();
	}
}
