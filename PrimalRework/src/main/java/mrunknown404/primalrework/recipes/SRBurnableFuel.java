package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.items.raw.StagedItem;
import mrunknown404.primalrework.recipes.inputs.RIBurnableFuel;

public class SRBurnableFuel extends StagedRecipe<SRBurnableFuel, RIBurnableFuel> {
	private final int time;
	
	public SRBurnableFuel(StagedItem item, int time) {
		super(null, item.stage.get(), item, 1, new RIBurnableFuel(item));
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
