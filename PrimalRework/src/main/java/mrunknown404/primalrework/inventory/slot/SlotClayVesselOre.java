package mrunknown404.primalrework.inventory.slot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.oredict.OreDictionary;

public class SlotClayVesselOre extends SlotItemHandler {
	public SlotClayVesselOre(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		for (ItemStack s : OreDictionary.getOres("nuggetOre")) {
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
