package mrunknown404.primalrework.inventory.container.slot;

import mrunknown404.primalrework.init.InitRecipes;
import mrunknown404.primalrework.init.InitRecipes.FuelType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotCampFireFuel extends Slot {
	public SlotCampFireFuel(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return InitRecipes.isFuelItem(FuelType.campfire, stack.getItem());
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
}
