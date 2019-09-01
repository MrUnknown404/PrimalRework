package mrunknown404.primalrework.util.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FirePitRecipe {
	private final ItemStack input, output;
	private final int cookTime; //default 100
	
	private FirePitRecipe(ItemStack input, ItemStack output, int cookTime) {
		this.input = input;
		this.output = output;
		this.cookTime = cookTime;
	}
	
	public FirePitRecipe(Item input, Item output, int cookTime) {
		this(new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	public FirePitRecipe(Item input, Block output, int cookTime) {
		this(new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	public FirePitRecipe(Block input, Item output, int cookTime) {
		this(new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	public FirePitRecipe(Block input, Block output, int cookTime) {
		this(new ItemStack(input), new ItemStack(output), cookTime);
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
		return "(" + input.getDisplayName() + " -> " + output.getDisplayName() + " : " + cookTime + ")";
	}
}
