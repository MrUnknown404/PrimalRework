package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.recipes.input.RISingle;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SRCampFire implements IStagedRecipe<SRCampFire, RISingle> {
	private final EnumStage stage;
	private final ItemStack output;
	private final RISingle input;
	public final int time;
	
	public SRCampFire(EnumStage stage, Item input, Item output, int time) {
		this.stage = stage;
		this.input = new RISingle(input);
		this.output = new ItemStack(output);
		this.time = time;
	}
	
	public SRCampFire(EnumStage stage, Item input, Item output) {
		this(stage, input, output, 200);
	}
	
	@Override
	public RISingle getInput() {
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
	public boolean is(SRCampFire recipe) {
		return recipe.output == output;
	}
	
	@Override
	public boolean has(Ingredient input) {
		return input.matches(this.input.input);
	}
}
