package mrunknown404.primalrework.util.recipes.util;

import mezz.jei.api.recipe.IRecipeWrapper;

public interface IRecipeWrapperBase<T extends IRecipeBase> extends IRecipeWrapper {
	public abstract T getRecipe();
}
