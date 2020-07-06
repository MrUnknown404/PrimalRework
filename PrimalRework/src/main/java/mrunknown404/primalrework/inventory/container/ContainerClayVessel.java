package mrunknown404.primalrework.inventory.container;

import mrunknown404.primalrework.inventory.slot.SlotClayVesselOre;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ContainerClayVessel extends Container {
	public ContainerClayVessel(InventoryPlayer player, IItemHandler item) {
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new SlotClayVesselOre(item, i, 8 + i * 18, 8));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if (index <= 9) {
				if (!mergeItemStack(stack1, 9, 36 + 9, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(stack1, 0, 9, false)) {
				return ItemStack.EMPTY;
			}
			
			if (stack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			
			if (stack1.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTake(player, stack1);
		}
		
		return stack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
