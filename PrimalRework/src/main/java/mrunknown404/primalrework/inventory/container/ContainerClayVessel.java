package mrunknown404.primalrework.inventory.container;

import java.util.List;

import mrunknown404.primalrework.inventory.slot.SlotClayVesselOre;
import mrunknown404.unknownlibs.inventory.container.IEasyTransferStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ContainerClayVessel extends Container implements IEasyTransferStack {
	public ContainerClayVessel(InventoryPlayer player, IItemHandler item) {
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new SlotClayVesselOre(item, i, 8 + i * 18, 8));
		}
		
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
	public List<Slot> getSlots() {
		return inventorySlots;
	}
	
	@Override
	public int getAmountOfInputSlots() {
		return 9;
	}
	
	@Override
	public int getAmountOfOutputSlots() {
		return 0;
	}
	
	@Override
	public boolean IMergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
		return mergeItemStack(stack, startIndex, endIndex, reverseDirection);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return transferStack(player, index);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
