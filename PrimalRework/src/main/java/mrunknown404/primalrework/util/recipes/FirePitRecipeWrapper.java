package mrunknown404.primalrework.util.recipes;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mrunknown404.primalrework.handlers.StageHandler;
import mrunknown404.primalrework.util.recipes.util.IRecipeWrapperBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TextComponentTranslation;

public class FirePitRecipeWrapper implements IRecipeWrapperBase<FirePitRecipe> {
	private final FirePitRecipe recipe;
	
	public FirePitRecipeWrapper(FirePitRecipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(VanillaTypes.ITEM, recipe.getInput());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}
	
	@Override
	public FirePitRecipe getRecipe() {
		return recipe;
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		FontRenderer r = minecraft.fontRenderer;
		String cookTime = new TextComponentTranslation("gui.fire_pit_cook").getUnformattedText() + ": " + recipe.getCookTime();
		
		if (!StageHandler.hasAccessToStage(recipe.getStage())) {
			cookTime = new TextComponentTranslation("gui.cant_craft").getUnformattedText();
		}
		
		int width = r.getStringWidth(cookTime);
		r.drawString(cookTime, recipeWidth / 2 - width / 2, 28, 16777215, true);
	}
	
	public static List<FirePitRecipeWrapper> createFromList(List<FirePitRecipe> list) {
		List<FirePitRecipeWrapper> wr = new ArrayList<FirePitRecipeWrapper>();
		
		for (FirePitRecipe r : list) {
			wr.add(new FirePitRecipeWrapper(r));
		}
		
		return wr;
	}
	
	@Override
	public String toString() {
		return recipe.toString();
	}
}
