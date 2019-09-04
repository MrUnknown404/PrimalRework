package mrunknown404.primalrework.util.recipes;

import mrunknown404.primalrework.handlers.StageHandler.Stage;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FirePitRecipe {
	private final Stage stage;
	
	private final ItemStack input, output;
	private final int cookTime; //default 100
	
	private FirePitRecipe(Stage stage, ItemStack input, ItemStack output, int cookTime) {
		this.stage = stage;
		this.input = input;
		this.output = output;
		this.cookTime = cookTime;
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
	
	public Stage getStage() {
		return stage;
	}
	
	/** Copied */
	public ItemStack getInput() {
		return input.copy();
	}
	
	/** Copied */
	public ItemStack getOutput() {
		return output.copy();
	}
	
	public int getCookTime() {
		return cookTime;
	}
	
	@Override
	public String toString() {
		return "(" + stage.getName() + ", " + input.getDisplayName() + " -> " + output.getDisplayName() + " : " + cookTime + ")";
	}
}
