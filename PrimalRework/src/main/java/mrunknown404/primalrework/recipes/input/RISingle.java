package mrunknown404.primalrework.recipes.input;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.Ingredient;

public class RISingle extends RecipeInput<RISingle> {
	public final Ingredient input;
	
	public RISingle(StagedItem input) {
		this.input = Ingredient.createUsingItem(input);
	}
	
	@Override
	protected boolean match(RISingle input) {
		return this.input.matches(input.input);
	}
	
	@Override
	public boolean isEmpty() {
		return input == null || input.isEmpty();
	}
}
