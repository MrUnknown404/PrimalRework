package mrunknown404.primalrework.recipes.input;

import mrunknown404.primalrework.recipes.Ingredient;
import net.minecraft.item.Item;

public class RISingle extends RecipeInput<RISingle> {
	public final Ingredient input;
	
	public RISingle(Item input) {
		this.input = new Ingredient(input);
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
