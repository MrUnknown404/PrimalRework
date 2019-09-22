package mrunknown404.primalrework.recipes;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.recipes.util.IRecipeBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FirePitRecipe implements IRecipeBase {
	private final EnumStage stage;
	private final ItemStack input, output;
	/** default 100 */
	private final int cookTime;
	
	private FirePitRecipe(EnumStage stage, ItemStack input, ItemStack output, int cookTime) {
		this.stage = stage;
		this.cookTime = cookTime;
		
		this.input = input;
		this.output = output;
	}
	
	public FirePitRecipe(EnumStage stage, Item input, Item output, int cookTime) {
		this(stage, new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	public FirePitRecipe(EnumStage stage, Item input, Block output, int cookTime) {
		this(stage, new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	public FirePitRecipe(EnumStage stage, Block input, Item output, int cookTime) {
		this(stage, new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	public FirePitRecipe(EnumStage stage, Block input, Block output, int cookTime) {
		this(stage, new ItemStack(input), new ItemStack(output), cookTime);
	}
	
	@Override
	public EnumStage getStage() {
		return stage;
	}
	
	@Override
	public List<ItemStack> getInputs() {
		return Arrays.asList(input);
	}
	
	@Override
	public List<ItemStack> getOutputs() {
		return Arrays.asList(output);
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
