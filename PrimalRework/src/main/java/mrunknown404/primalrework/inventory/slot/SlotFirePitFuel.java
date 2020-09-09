package mrunknown404.primalrework.inventory.slot;

import mrunknown404.primalrework.init.InitRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFirePitFuel extends Slot {
	
	public SlotFirePitFuel(IInventory inventoryIn, int index, int x, int y) {
		super(inventoryIn, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return InitRecipes.isItemFirePitFuel(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
}
