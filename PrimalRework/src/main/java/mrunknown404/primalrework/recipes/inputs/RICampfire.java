package mrunknown404.primalrework.recipes.inputs;

import mrunknown404.primalrework.recipes.IIngredientProvider;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.utils.enums.RecipeType;

public class RICampfire extends RecipeInput<RICampfire> {
	public final Ingredient input;
	
	public RICampfire(IIngredientProvider input) {
		super(RecipeType.CAMPFIRE);
		this.input = input.getIngredient();
	}
	
	@Override
	protected boolean match(RICampfire input) {
		return this.input.matches(input.input);
	}
	
	@Override
	public boolean isEmpty() {
		return input == null || input.isEmpty();
	}
}
