package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.recipes.input.RecipeInput;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import net.minecraft.item.ItemStack;

public interface IStagedRecipe<T extends IStagedRecipe<T, V>, V extends RecipeInput<?>> {
	public abstract V getInput();
	public abstract ItemStack getOutput();
	public abstract Stage getStage();
	public abstract boolean is(T recipe);
	public abstract boolean has(Ingredient input);
	public abstract EnumRecipeType getRecipeType();
}
