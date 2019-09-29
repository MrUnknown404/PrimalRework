package mrunknown404.primalrework.recipes;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.recipes.util.IRecipeBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DryingTableRecipe implements IRecipeBase {
	private final EnumStage stage;
	private final ItemStack input, output;
	/** default 1200 */
	private final int dryTime;
	
	private DryingTableRecipe(EnumStage stage, ItemStack input, ItemStack output, int dryTime) {
		this.stage = stage;
		this.dryTime = dryTime;
		
		this.input = input;
		this.output = output;
	}
	
	public DryingTableRecipe(EnumStage stage, Item input, Item output, int dryTime) {
		this(stage, new ItemStack(input), new ItemStack(output), dryTime);
	}
	
	public DryingTableRecipe(EnumStage stage, Item input, Block output, int dryTime) {
		this(stage, new ItemStack(input), new ItemStack(output), dryTime);
	}
	
	public DryingTableRecipe(EnumStage stage, Block input, Item output, int dryTime) {
		this(stage, new ItemStack(input), new ItemStack(output), dryTime);
	}
	
	public DryingTableRecipe(EnumStage stage, Block input, Block output, int dryTime) {
		this(stage, new ItemStack(input), new ItemStack(output), dryTime);
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
	
	public int getDryTime() {
		return dryTime;
	}
	
	@Override
	public String toString() {
		return "(" + stage.getName() + ", " + getInput().getDisplayName() + " -> " + getOutput().getDisplayName() + " : " + dryTime + ")";
	}
}
