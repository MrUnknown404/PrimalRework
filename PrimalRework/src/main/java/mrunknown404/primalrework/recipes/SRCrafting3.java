package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.items.raw.StagedItem;
import mrunknown404.primalrework.recipes.inputs.RICrafting3;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.RecipeType;

public class SRCrafting3 extends StagedRecipe<SRCrafting3, RICrafting3> {
	public SRCrafting3(Stage stage, StagedItem output, int count, RICrafting3 input) {
		super(RecipeType.CRAFTING_3, stage, output, count, input);
	}
	
	@Override
	public boolean is(SRCrafting3 recipe) {
		return inputRecipe.matches(recipe.inputRecipe);
	}
	
	@Override
	public boolean has(Ingredient ingredient) {
		return inputRecipe.has(ingredient);
	}
}
