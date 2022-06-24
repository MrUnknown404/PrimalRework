package mrunknown404.primalrework.inventory.container.slot;

import mrunknown404.primalrework.inventory.InventoryCraftingGrid;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotInventoryResult extends SlotOutput {
	private final PlayerInventory pinv;
	private final InventoryCraftingGrid craftSlots;
	private boolean quick, drop;
	
	public SlotInventoryResult(IInventory inv, int slot, int x, int y, PlayerInventory pinv, InventoryCraftingGrid craftSlots) {
		super(inv, slot, x, y);
		this.pinv = pinv;
		this.craftSlots = craftSlots;
	}
	
	@Override
	protected void onQuickCraft(ItemStack stack, int slot) {
		quick = true;
	}
	
	public void drop() {
		drop = true;
	}
	
	@Override
	public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
		for (int i = 0; i < 4; i++) {
			ItemStack item = craftSlots.getItem(i);
			
			if (item.isDamageableItem()) {
				if (item.getDamageValue() + 1 >= item.getMaxDamage()) {
					craftSlots.setItem(i, ItemStack.EMPTY);
				} else {
					item.setDamageValue(item.getDamageValue() + 1);
					craftSlots.setItem(i, item);
				}
			} else {
				craftSlots.removeItem(i, 1);
			}
		}
		
		if (drop) {
			pinv.player.drop(craftSlots.getRecipe().getOutput().copy(), true);
		} else if (!quick && pinv.getCarried().isEmpty()) {
			pinv.setCarried(craftSlots.getRecipe().getOutput().copy());
		}
		
		quick = false;
		drop = false;
		craftSlots.setChanged();
		return super.onTake(entity, stack);
	}
}
