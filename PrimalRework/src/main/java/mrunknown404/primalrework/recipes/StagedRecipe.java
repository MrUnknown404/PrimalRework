package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.items.ISIProvider;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.recipes.inputs.RecipeInput;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.RecipeType;
import net.minecraft.item.ItemStack;

public abstract class StagedRecipe<T extends StagedRecipe<T, V>, V extends RecipeInput<?>> {
	public final Stage stage;
	public final V inputRecipe;
	public final RecipeType recipeType;
	private final ItemStack output;
	
	public StagedRecipe(RecipeType recipeType, Stage stage, ISIProvider output, int count, V inputRecipe) {
		this.recipeType = recipeType;
		this.stage = stage;
		this.inputRecipe = inputRecipe;
		this.output = new ItemStack(output.getStagedItem(), count);
	}
	
	public abstract boolean is(T recipe);
	
	public abstract boolean has(Ingredient ingredient);
	
	public ItemStack getOutputCopy() {
		return output.copy();
	}
	
	public StagedItem getOutput() {
		return (StagedItem) output.getItem();
	}
	
	/** You probably want to use {@link StagedRecipe#getOutput()} */
	@Deprecated
	public ItemStack getOutputNoCopy() {
		return output;
	}
}