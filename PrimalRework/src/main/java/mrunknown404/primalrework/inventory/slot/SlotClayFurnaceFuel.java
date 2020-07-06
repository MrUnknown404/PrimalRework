package mrunknown404.primalrework.inventory.slot;

import mrunknown404.primalrework.init.ModRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotClayFurnaceFuel extends Slot {
	
	public SlotClayFurnaceFuel(IInventory inventoryIn, int index, int x, int y) {
		super(inventoryIn, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return ModRecipes.isItemClayFurnaceFuel(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
}
