package mrunknown404.primalrework.recipes.inputs;

import mrunknown404.primalrework.recipes.IIngredientProvider;
import mrunknown404.primalrework.recipes.Ingredient;

public class RIBurnableFuel extends RecipeInput<RIBurnableFuel> {
	public final Ingredient input;
	
	public RIBurnableFuel(IIngredientProvider input) {
		this.input = input.getIngredient();
	}
	
	@Override
	protected boolean match(RIBurnableFuel input) {
		return this.input.matches(input.input);
	}
	
	@Override
	public boolean isEmpty() {
		return input == null || input.isEmpty();
	}
}
