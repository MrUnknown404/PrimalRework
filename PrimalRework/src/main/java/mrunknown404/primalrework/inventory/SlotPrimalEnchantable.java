package mrunknown404.primalrework.inventory;

import mrunknown404.primalrework.util.helpers.EnchantHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPrimalEnchantable extends Slot {

	public SlotPrimalEnchantable(IInventory inventoryIn, int index, int x, int y) {
		super(inventoryIn, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return EnchantHelper.isEnchantable(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
}
