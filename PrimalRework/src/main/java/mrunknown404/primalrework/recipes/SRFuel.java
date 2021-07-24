package mrunknown404.primalrework.recipes;

import mrunknown404.primalrework.recipes.input.RISingle;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SRFuel implements IStagedRecipe<SRFuel, RISingle> {
	private final ItemStack item;
	private final RISingle itemRI;
	private final int time;
	
	public SRFuel(Item item, int time) {
		this.itemRI = new RISingle(item);
		this.item = new ItemStack(item);
		this.time = time;
	}
	
	@Override
	public RISingle getInput() {
		return itemRI;
	}
	
	@Override
	public ItemStack getOutput() {
		return item;
	}
	
	@Override
	public EnumStage getStage() {
		return EnumStage.stage0;
	}
	
	public int getCookTime() {
		return time;
	}
	
	@Override
	public boolean is(SRFuel recipe) {
		return recipe.item == item;
	}
	
	@Override
	public boolean has(Ingredient input) {
		return input.matches(this.itemRI.input);
	}
}
