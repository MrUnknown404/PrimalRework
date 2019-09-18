package mrunknown404.primalrework.inventory;

import mrunknown404.primalrework.tileentity.TileEntityCraftingStump;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class InventoryCraftingStump extends InventoryCrafting {
	private NonNullList<ItemStack> stackList;
	private final TileEntityCraftingStump tile;

	public InventoryCraftingStump(Container container, TileEntityCraftingStump tile) {
		super(container, 3, 3);
		this.stackList = tile.getItems();
		this.tile = tile;
	}
	
	@Override
	public int getSizeInventory() {
		return 9;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return index >= this.getSizeInventory() ? ItemStack.EMPTY : stackList.get(index);
	}
	
	public ItemStack getStackInRowAndColumn(int row, int column) {
		return row >= 0 && row < 3 && column >= 0 && column <= 3 ? getStackInSlot(row + column * 3) : ItemStack.EMPTY;
	}
	
	@Override
	public String getName() {
		return tile.getName();
	}
	
	@Override
	public boolean hasCustomName() {
		return tile.hasCustomName();
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public void markDirty() {
		tile.markDirty();
		IBlockState state = tile.getWorld().getBlockState(tile.getPos());
		tile.getWorld().notifyBlockUpdate(tile.getPos(), state, state, 3);
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
}
