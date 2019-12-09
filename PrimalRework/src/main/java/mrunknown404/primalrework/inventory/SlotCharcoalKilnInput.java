package mrunknown404.primalrework.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SlotCharcoalKilnInput extends Slot {
	
	public SlotCharcoalKilnInput(IInventory inventoryIn, int index, int x, int y) {
		super(inventoryIn, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		for (ItemStack s : OreDictionary.getOres("logWood")) {
			if (OreDictionary.itemMatches(s, stack, false)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
}
