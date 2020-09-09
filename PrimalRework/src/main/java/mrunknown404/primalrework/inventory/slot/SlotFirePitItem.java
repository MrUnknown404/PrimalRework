package mrunknown404.primalrework.inventory.slot;

import mrunknown404.primalrework.init.InitRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFirePitItem extends Slot {
	
	public SlotFirePitItem(IInventory inventoryIn, int index, int x, int y) {
		super(inventoryIn, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return InitRecipes.doesItemHaveFirePitRecipe(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
}
