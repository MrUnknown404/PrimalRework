package mrunknown404.primalrework.util.recipes.util;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.handlers.StageHandler.Stage;
import net.minecraft.item.ItemStack;

public interface IRecipeBase {
	public final List<ItemStack> inputs = new ArrayList<ItemStack>(), outputs = new ArrayList<ItemStack>();
	
	/** Copied */
	public default List<ItemStack> getInputs() {
		return new ArrayList<>(inputs);
	}
	
	/** Copied */
	public default List<ItemStack> getOutputs() {
		return new ArrayList<>(outputs);
	}
	
	public abstract Stage getStage();
}
