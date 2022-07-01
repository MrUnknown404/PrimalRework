package mrunknown404.primalrework.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotCampFireFuel extends Slot {
	public SlotCampFireFuel(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) { //TODO on redo fuel
		return false;
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
}
