package mrunknown404.primalrework.recipes.inputs;

public abstract class RecipeInput<T extends RecipeInput<T>> {
	protected abstract boolean match(T input);
	
	public abstract boolean isEmpty();
	
	@SuppressWarnings("unchecked")
	public boolean matches(RecipeInput<?> input) {
		return getClass().equals(input.getClass()) && match((T) input);
	}
}
