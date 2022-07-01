package mrunknown404.primalrework.recipes.inputs;

import mrunknown404.primalrework.utils.enums.RecipeType;

public abstract class RecipeInput<T extends RecipeInput<T>> {
	public final RecipeType recipeType;
	
	public RecipeInput(RecipeType recipeType) {
		this.recipeType = recipeType;
	}
	
	protected abstract boolean match(T input);
	
	@SuppressWarnings("unchecked")
	public boolean matches(RecipeInput<?> input) {
		return getClass().equals(input.getClass()) && match((T) input);
	}
	
	public abstract boolean isEmpty();
}
