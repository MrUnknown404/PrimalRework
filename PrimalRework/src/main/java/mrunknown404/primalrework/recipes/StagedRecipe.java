package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.api.utils.IStageProvider;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.recipes.inputs.RecipeInput;
import mrunknown404.primalrework.stage.Stage;
import net.minecraft.item.ItemStack;

public abstract class StagedRecipe<T extends StagedRecipe<T, V>, V extends RecipeInput<?>> implements IStageProvider {
	public final Stage stage;
	public final V inputRecipe;
	private final ItemStack output;
	
	public StagedRecipe(Stage stage, ISIProvider output, int count, V inputRecipe) {
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
	
	@Override
	public Stage getStage() {
		return stage;
	}
}