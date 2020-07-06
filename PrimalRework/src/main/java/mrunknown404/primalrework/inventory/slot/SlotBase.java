package mrunknown404.primalrework.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBase extends Slot {

	private final ItemStack item;
	private final int stackSize;
	
	public SlotBase(ItemStack item, int stackSize, IInventory inventoryIn, int index, int x, int y) {
		super(inventoryIn, index, x, y);
		this.item = item;
		this.stackSize = stackSize;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.isItemEqualIgnoreDurability(item);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return stackSize;
	}
}
