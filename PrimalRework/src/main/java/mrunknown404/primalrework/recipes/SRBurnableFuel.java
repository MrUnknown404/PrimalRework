package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.input.RIBurnableFuel;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import net.minecraft.item.ItemStack;

public class SRBurnableFuel implements IStagedRecipe<SRBurnableFuel, RIBurnableFuel> {
	private final ItemStack item;
	private final RIBurnableFuel itemRI;
	private final int time;
	
	public SRBurnableFuel(StagedItem item, int time) {
		this.itemRI = new RIBurnableFuel(item);
		this.item = new ItemStack(item);
		this.time = time;
	}
	
	@Override
	public RIBurnableFuel getInput() {
		return itemRI;
	}
	
	@Override
	public ItemStack getOutput() {
		return item;
	}
	
	@Override
	public Stage getStage() {
		return ((StagedItem) item.getItem()).stage.get();
	}
	
	public int getCookTime() {
		return time;
	}
	
	@Override
	public boolean is(SRBurnableFuel recipe) {
		return recipe.item == item;
	}
	
	@Override
	public boolean has(Ingredient input) {
		return input.matches(itemRI.input);
	}
	
	@Override
	public EnumRecipeType getRecipeType() {
		return null;
	}
}
