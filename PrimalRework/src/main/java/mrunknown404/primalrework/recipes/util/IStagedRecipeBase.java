package mrunknown404.primalrework.recipes.util;

import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public interface IStagedRecipeBase extends IRecipeBase {
	public abstract boolean match(InventoryCrafting inv, World world);
	public abstract boolean isShapeless();
	public abstract IRecipe getRecipe();
	public abstract List<List<ItemStack>> getListInputs();
	
	public default int getWidth() { return 0; }
	public default int getHeight() { return 0; }
	
	/** Don't use this */
	@Override
	@Deprecated
	public default List<ItemStack> getInputs() {
		return null;
	}
}
