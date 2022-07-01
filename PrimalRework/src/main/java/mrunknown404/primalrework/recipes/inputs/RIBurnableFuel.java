package mrunknown404.primalrework.recipes.inputs;

import mrunknown404.primalrework.items.raw.StagedItem;
import mrunknown404.primalrework.recipes.Ingredient;

public class RIBurnableFuel extends RecipeInput<RIBurnableFuel> {
	public final Ingredient input;
	
	public RIBurnableFuel(StagedItem input) {
		super(null);
		this.input = Ingredient.createUsingItem(input);
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
