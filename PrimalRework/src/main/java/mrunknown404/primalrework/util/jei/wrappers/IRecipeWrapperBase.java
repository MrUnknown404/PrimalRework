package mrunknown404.primalrework.util.jei.wrappers;

import mezz.jei.api.recipe.IRecipeWrapper;
import mrunknown404.primalrework.recipes.util.IRecipeBase;

public interface IRecipeWrapperBase<T extends IRecipeBase> extends IRecipeWrapper {
	public abstract T getRecipe();
}
