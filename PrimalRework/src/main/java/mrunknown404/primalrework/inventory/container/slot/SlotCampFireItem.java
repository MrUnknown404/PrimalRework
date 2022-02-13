package mrunknown404.primalrework.inventory.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotCampFireItem extends Slot {
	public SlotCampFireItem(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) { //TODO on redo recipes
		return false;
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
}
