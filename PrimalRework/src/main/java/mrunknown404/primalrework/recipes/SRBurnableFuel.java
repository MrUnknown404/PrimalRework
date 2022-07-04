package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.recipes.inputs.RIBurnableFuel;

public class SRBurnableFuel extends StagedRecipe<SRBurnableFuel, RIBurnableFuel> {
	private final int time;
	
	public <T extends IIngredientProvider & ISIProvider> SRBurnableFuel(T item, int time) {
		super(null, item.getStagedItem().stage.get(), item, 1, new RIBurnableFuel(item));
		this.time = time;
	}
	
	public int getCookTime() {
		return time;
	}
	
	@Override
	public boolean is(SRBurnableFuel recipe) {
		return recipe.getOutput() == getOutput();
	}
	
	@Override
	public boolean has(Ingredient ingredient) {
		return inputRecipe.input.matches(ingredient);
	}
}
