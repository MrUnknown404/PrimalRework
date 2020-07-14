package mrunknown404.primalrework.inventory.slot;

import mrunknown404.primalrework.items.ItemCastable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotCastable extends SlotItemHandler {
	public SlotCastable(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemCastable;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
}
