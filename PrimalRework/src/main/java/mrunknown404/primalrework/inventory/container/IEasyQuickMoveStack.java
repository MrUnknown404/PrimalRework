package mrunknown404.primalrework.inventory.container;

import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public interface IEasyQuickMoveStack {
	default ItemStack IQuickMoveStack(PlayerEntity player, int slotID) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = getSlots().get(slotID);
		
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			
			if (slotID <= getAmountOfSlots()) {
				if (!IMoveItemStackTo(itemstack1, getAmountOfSlots(), 36 + getAmountOfSlots(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!IMoveItemStackTo(itemstack1, 0, getAmountOfSlots(), false)) {
				return ItemStack.EMPTY;
			}
			
			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTake(player, itemstack1);
		}
		
		return itemstack;
	}
	
	public abstract int getAmountOfSlots();
	
	List<Slot> getSlots();
	
	boolean IMoveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection);
}
