package mrunknown404.primalrework.inventory.container;

import mrunknown404.primalrework.inventory.slot.SlotFirePitFuel;
import mrunknown404.primalrework.inventory.slot.SlotFirePitItem;
import mrunknown404.primalrework.tileentity.TileEntityFirePit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFirePit extends Container {
	
	public static final int SLOT_FUEL = 0, SLOT_ITEM = 1;
	
	private final TileEntityFirePit te;
	private int cookTime, totalCookTime, burnTime, totalBurnTime;
	
	public ContainerFirePit(InventoryPlayer player, TileEntityFirePit te) {
		this.te = te;
		
		addSlotToContainer(new SlotFirePitFuel(te, SLOT_FUEL, 80, 51));
		addSlotToContainer(new SlotFirePitItem(te, SLOT_ITEM, 80, 14));
		
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
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, te);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = this.listeners.get(i);
			
			if (cookTime != te.getCookTime()) {
				icontainerlistener.sendWindowProperty(this, 2, te.getCookTime());
			}
			
			if (burnTime != te.getBurnTime()) {
				icontainerlistener.sendWindowProperty(this, 0, te.getBurnTime());
			}
			
			if (totalBurnTime != te.getTotalBurnTime()) {
				icontainerlistener.sendWindowProperty(this, 1, te.getTotalBurnTime());
			}
			
			if (totalCookTime != te.getTotalCookTime()) {
				icontainerlistener.sendWindowProperty(this, 3, te.getTotalCookTime());
			}
		}
		
		cookTime = te.getCookTime();
		burnTime = te.getBurnTime();
		totalBurnTime = te.getTotalBurnTime();
		totalCookTime = te.getTotalCookTime();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		te.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.isUsableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if (index == SLOT_FUEL || index == SLOT_ITEM) {
				if (!mergeItemStack(stack1, 2, 36 + 2, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(stack1, 0, 2, false)) {
				return ItemStack.EMPTY;
			}
			
			if (stack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			
			if (stack1.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTake(player, stack1);
		}
		
		return stack;
	}
}
