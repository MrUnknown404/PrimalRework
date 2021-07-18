package mrunknown404.primalrework.inventory.container.slot;

import mrunknown404.primalrework.recipes.input.RISingle;
import mrunknown404.primalrework.registries.PRRecipes;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotCampFireItem extends Slot {
	public SlotCampFireItem(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return PRRecipes.getRecipeForInput(EnumRecipeType.campfire, new RISingle(stack.getItem())) != null;
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
}
