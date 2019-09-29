package mrunknown404.primalrework.inventory;

import mrunknown404.primalrework.init.ModRecipes;
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
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1;
			
			if (index == SLOT_FUEL || index == SLOT_ITEM) {
				for (int i = 29; i < 38; i++) {
					if (inventorySlots.get(i).getStack().isItemEqual(stack1) && inventorySlots.get(i).getStack().getCount() != inventorySlots.get(i).getStack().getMaxStackSize()) {
						if (!mergeItemStack(stack1, i, i + 1, true)) {
							return ItemStack.EMPTY;
						}
					}
				}
				
				for (int i = 2; i < 29; i++) {
					if (inventorySlots.get(i).getStack().isItemEqual(stack1) && inventorySlots.get(i).getStack().getCount() != inventorySlots.get(i).getStack().getMaxStackSize()) {
						if (!mergeItemStack(stack1, i, i + 1, true)) {
							return ItemStack.EMPTY;
						}
					}
				}
				
				if (!mergeItemStack(stack1, 29, 38, false)) {
					if (!mergeItemStack(stack1, 2, 29, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else {
				if (ModRecipes.isItemFirePitFuel(stack1)) {
					if (!mergeItemStack(stack1, SLOT_FUEL, SLOT_FUEL + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (ModRecipes.doesItemHaveFirePitRecipe(stack1)) {
					if (!mergeItemStack(stack1, SLOT_ITEM, SLOT_ITEM + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			
			if (stack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			
			if (stack1.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTake(playerIn, stack1);
		}
		
		return stack;
	}
}
