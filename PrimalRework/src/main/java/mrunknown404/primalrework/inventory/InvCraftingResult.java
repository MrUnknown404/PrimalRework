package mrunknown404.primalrework.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InvCraftingResult implements IInventory {
	private ItemStack stack = ItemStack.EMPTY;
	
	@Override
	public int getContainerSize() {
		return 1;
	}
	
	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	@Override
	public ItemStack getItem(int slot) {
		return stack;
	}
	
	@Override
	public ItemStack removeItem(int slot, int amount) {
		return stack = ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return stack = ItemStack.EMPTY;
	}
	
	@Override
	public void setItem(int slot, ItemStack stack) {
		this.stack = stack;
	}
	
	@Override
	public void setChanged() {
		
	}
	
	@Override
	public boolean stillValid(PlayerEntity player) {
		return true;
	}
	
	@Override
	public void clearContent() {
		stack = ItemStack.EMPTY;
	}
}
