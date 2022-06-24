package mrunknown404.primalrework.recipes.input;

import mrunknown404.primalrework.utils.enums.EnumRecipeType;

public abstract class RecipeInput<T extends RecipeInput<T>> {
	protected abstract boolean match(T input);
	
	@SuppressWarnings("unchecked")
	public boolean matches(RecipeInput<?> input) {
		return getClass().equals(input.getClass()) && match((T) input);
	}
	
	public abstract boolean isEmpty();
	
	public abstract EnumRecipeType getRecipeType();
}
