package mrunknown404.primalrework.recipes.input;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;

public class RICampfire extends RecipeInput<RICampfire> {
	public final Ingredient input;
	
	public RICampfire(StagedItem input) {
		this.input = Ingredient.createUsingItem(input);
	}
	
	@Override
	protected boolean match(RICampfire input) {
		return this.input.matches(input.input);
	}
	
	@Override
	public boolean isEmpty() {
		return input == null || input.isEmpty();
	}
	
	@Override
	public EnumRecipeType getRecipeType() {
		return EnumRecipeType.campfire;
	}
}
