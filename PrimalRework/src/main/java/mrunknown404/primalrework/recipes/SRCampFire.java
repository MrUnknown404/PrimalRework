package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.items.raw.StagedItem;
import mrunknown404.primalrework.recipes.inputs.RICampfire;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.RecipeType;

public class SRCampFire extends StagedRecipe<SRCampFire, RICampfire> {
	public final int time;
	
	public SRCampFire(Stage stage, StagedItem input, StagedItem output, int time) {
		super(RecipeType.CAMPFIRE, stage, output, 1, new RICampfire(input));
		this.time = time;
	}
	
	public SRCampFire(Stage stage, StagedItem input, StagedItem output) {
		this(stage, input, output, 200);
	}
	
	@Override
	public boolean is(SRCampFire recipe) {
		return recipe.getOutput() == getOutput();
	}
	
	@Override
	public boolean has(Ingredient ingredient) {
		return inputRecipe.input.matches(ingredient);
	}
}
