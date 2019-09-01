package mrunknown404.primalrework.util.recipes;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TextComponentTranslation;

public class FirePitRecipeWrapper implements IRecipeWrapper {
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
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		FontRenderer r = minecraft.fontRenderer;
		String cookTime = new TextComponentTranslation("gui.fire_pit_cook").getUnformattedText() + ": " + recipe.getCookTime();
		int width = r.getStringWidth(cookTime);
		r.drawString(cookTime, recipeWidth / 2 - width / 2, 29, 16777215, true);
	}
	
	public static List<FirePitRecipeWrapper> createFromList(List<FirePitRecipe> list) {
		List<FirePitRecipeWrapper> wr = new ArrayList<FirePitRecipeWrapper>();
		
		for (FirePitRecipe r : list) {
			wr.add(new FirePitRecipeWrapper(r));
		}
		
		return wr;
	}
}
