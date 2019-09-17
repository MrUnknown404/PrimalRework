package mrunknown404.primalrework.util.recipes;

import mrunknown404.primalrework.handlers.StageHandler.Stage;
import mrunknown404.primalrework.util.recipes.util.IRecipeBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FirePitRecipe implements IRecipeBase {
	private final Stage stage;
	
	private final int cookTime; //default 100
	
	private FirePitRecipe(Stage stage, ItemStack input, ItemStack output, int cookTime) {
		this.stage = stage;
		this.cookTime = cookTime;
		
		inputs.add(input);
		outputs.add(output);
	}
	
	public FirePitRecipe(Stage stage, Item input, Item output, int cookTime) {
		this(stage, new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	public FirePitRecipe(Stage stage, Item input, Block output, int cookTime) {
		this(stage, new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	public FirePitRecipe(Stage stage, Block input, Item output, int cookTime) {
		this(stage, new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	public FirePitRecipe(Stage stage, Block input, Block output, int cookTime) {
		this(stage, new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	@Override
	public Stage getStage() {
		return stage;
	}
	
	/** Copied */
	public ItemStack getInput() {
		return getInputs().get(0).copy();
	}
	
	/** Copied */
	public ItemStack getOutput() {
		return getOutputs().get(0).copy();
	}
	
	public int getCookTime() {
		return cookTime;
	}
	
	@Override
	public String toString() {
		return "(" + stage.getName() + ", " + getInput().getDisplayName() + " -> " + getOutput().getDisplayName() + " : " + cookTime + ")";
	}
}
