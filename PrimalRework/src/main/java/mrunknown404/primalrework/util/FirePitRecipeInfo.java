package mrunknown404.primalrework.util;

import net.minecraft.item.ItemStack;

public class FirePitRecipeInfo {
	private final ItemStack input, output;
	public final int cookTime; //default 200
	
	public FirePitRecipeInfo(ItemStack input, ItemStack output, int cookTime) {
		this.input = input;
		this.output = output;
		this.cookTime = cookTime;
	}
	
	/** Copied */
	public ItemStack getInput() {
		return input.copy();
	}
	
	/** Copied */
	public ItemStack getOutput() {
		return output.copy();
	}
}
