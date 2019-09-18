package mrunknown404.primalrework.util.recipes.util;

import java.util.List;

import mrunknown404.primalrework.util.EnumStage;
import net.minecraft.item.ItemStack;

public interface IRecipeBase {
	public abstract List<ItemStack> getInputs();
	public abstract List<ItemStack> getOutputs();
	
	public abstract EnumStage getStage();
}
