package mrunknown404.primalrework.inventory;

import mrunknown404.primalrework.util.helpers.EnchantHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMagicDust extends Slot {

	public SlotMagicDust(IInventory inventoryIn, int index, int x, int y) {
		super(inventoryIn, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return EnchantHelper.isMagicDust(stack.getItem());
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 64;
	}
}
