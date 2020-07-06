package mrunknown404.primalrework.inventory.slot;

import mrunknown404.primalrework.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotClayFurnaceFuel extends Slot {
	
	public SlotClayFurnaceFuel(IInventory inventoryIn, int index, int x, int y) {
		super(inventoryIn, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if (stack.getItem() == Items.COAL || stack.getItem() == Item.getItemFromBlock(ModBlocks.CHARCOAL_BLOCK) || stack.getItem() == Item.getItemFromBlock(Blocks.COAL_BLOCK)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
}
