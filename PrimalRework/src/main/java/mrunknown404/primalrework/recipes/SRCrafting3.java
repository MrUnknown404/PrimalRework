package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.recipes.input.RICrafting3;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SRCrafting3 implements IStagedRecipe<SRCrafting3, RICrafting3> {
	private final EnumStage stage;
	private final ItemStack output;
	public final RICrafting3 input;
	
	public SRCrafting3(EnumStage stage, Item output, int count, RICrafting3 input) {
		this.stage = stage;
		this.output = new ItemStack(output, count);
		this.input = input;
	}
	
	@Override
	public RICrafting3 getInput() {
		return input;
	}
	
	@Override
	public ItemStack getOutput() {
		return output;
	}
	
	@Override
	public EnumStage getStage() {
		return stage;
	}
	
	@Override
	public boolean is(SRCrafting3 recipe) {
		return input.matches(recipe.input);
	}
	
	@Override
	public boolean has(Ingredient input) {
		return this.input.has(input);
	}
}
