package mrunknown404.primalrework.inventory;

import mrunknown404.primalrework.tileentity.TileEntityLoom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLoom extends Container {

	public static final int SLOT_INPUT_AMOUNT = 8, SLOT_OUTPUT = 8;
	
	private final TileEntityLoom te;
	
	public ContainerLoom(InventoryPlayer player, TileEntityLoom te) {
		this.te = te;
		
		for (int i = 0; i < SLOT_INPUT_AMOUNT; i++) {
			addSlotToContainer(new SlotLoom(te, i, 17 + i * 18, 13));
		}
		
		addSlotToContainer(new SlotOutput(te, SLOT_OUTPUT, 80, 48));
		
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
	public boolean canInteractWith(EntityPlayer player) {
		return te.isUsableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1;
			
			for (int sl = 0; sl < SLOT_INPUT_AMOUNT + 1; sl++) {
				if (index == sl) {
					for (int i = 36; i < 45; i++) {
						if (inventorySlots.get(i).getStack().isItemEqual(stack1) && inventorySlots.get(i).getStack().getCount() != inventorySlots.get(i).getStack().getMaxStackSize()) {
							if (!mergeItemStack(stack1, i, i + 1, true)) {
								return ItemStack.EMPTY;
							}
						}
					}
					
					for (int i = 9; i < 36; i++) {
						if (inventorySlots.get(i).getStack().isItemEqual(stack1) && inventorySlots.get(i).getStack().getCount() != inventorySlots.get(i).getStack().getMaxStackSize()) {
							if (!mergeItemStack(stack1, i, i + 1, true)) {
								return ItemStack.EMPTY;
							}
						}
					}
					
					if (!mergeItemStack(stack1, 36, 45, false)) {
						if (!mergeItemStack(stack1, 9, 36, false)) {
							return ItemStack.EMPTY;
						}
					}
				}
			}
			
			if (stack.getItem() == Items.STRING && index >= 9) {
				boolean found = true;
				for (int sl = 0; sl < SLOT_INPUT_AMOUNT; sl++) {
					if (!mergeItemStack(stack1, sl, sl + 1, false)) {
						found = false;
					}
				}
				
				if (!found) {
					return ItemStack.EMPTY;
				}
			}
			
			if (stack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			
			if (stack1.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTake(playerIn, stack1);
		}
		
		return stack;
	}
}
