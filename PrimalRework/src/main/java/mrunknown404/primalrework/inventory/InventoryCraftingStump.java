package mrunknown404.primalrework.inventory;

import mrunknown404.primalrework.tileentity.TileEntityCraftingStump;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryCraftingStump extends InventoryCrafting {
	private final TileEntityCraftingStump te;

	public InventoryCraftingStump(Container container, TileEntityCraftingStump te) {
		super(container, 3, 3);
		this.te = te;
	}
	
	@Override
	public int getSizeInventory() {
		return 9;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return index >= getSizeInventory() ? ItemStack.EMPTY : te.getStackInSlot(index);
	}
	
	@Override
	public ItemStack getStackInRowAndColumn(int row, int column) {
		return row >= 0 && row < 3 && column >= 0 && column <= 3 ? getStackInSlot(row + column * 3) : ItemStack.EMPTY;
	}
	
	@Override
	public String getName() {
		return te.getName();
	}
	
	@Override
	public boolean hasCustomName() {
		return te.hasCustomName();
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public void markDirty() {
		te.markDirty();
		IBlockState state = te.getWorld().getBlockState(te.getPos());
		te.getWorld().notifyBlockUpdate(te.getPos(), state, state, 3);
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
}
