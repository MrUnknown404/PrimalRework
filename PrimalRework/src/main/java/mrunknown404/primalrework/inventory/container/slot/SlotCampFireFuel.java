package mrunknown404.primalrework.inventory.container.slot;

import mrunknown404.primalrework.registries.PRFuels;
import mrunknown404.primalrework.utils.enums.EnumFuelType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotCampFireFuel extends Slot {
	public SlotCampFireFuel(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return PRFuels.isFuelItem(EnumFuelType.campfire, stack.getItem());
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
}
