package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.input.RISingle;
import mrunknown404.primalrework.stage.Stage;
import net.minecraft.item.ItemStack;

public class SRCampFire implements IStagedRecipe<SRCampFire, RISingle> {
	private final Stage stage;
	private final ItemStack output;
	private final RISingle input;
	public final int time;
	
	public SRCampFire(Stage stage, StagedItem input, StagedItem output, int time) {
		this.stage = stage;
		this.input = new RISingle(input);
		this.output = new ItemStack(output);
		this.time = time;
	}
	
	public SRCampFire(Stage stage, StagedItem input, StagedItem output) {
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
	public Stage getStage() {
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
