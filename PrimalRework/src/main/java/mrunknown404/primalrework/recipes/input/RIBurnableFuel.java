package mrunknown404.primalrework.recipes.input;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;

public class RIBurnableFuel extends RecipeInput<RIBurnableFuel> {
	public final Ingredient input;
	
	public RIBurnableFuel(StagedItem input) {
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
	
	@Override
	public EnumRecipeType getRecipeType() {
		return null;
	}
}
