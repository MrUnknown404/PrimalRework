package mrunknown404.primalrework.inventory.container;

import mrunknown404.primalrework.inventory.slot.SlotCharcoalKilnInput;
import mrunknown404.primalrework.tileentity.TileEntityCharcoalKiln;
import mrunknown404.unknownlibs.inventory.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCharcoalKiln extends Container {
	
	public static final int[] SLOT_INPUT = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	public static final int SLOT_OUTPUT = 9;
	
	private final TileEntityCharcoalKiln te;
	private int cookTime;
	
	public ContainerCharcoalKiln(InventoryPlayer player, TileEntityCharcoalKiln te) {
		this.te = te;
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				addSlotToContainer(new SlotCharcoalKilnInput(te, x + y * 3, 39 + x * 18, 17 + y * 18));
			}
		}
		
		addSlotToContainer(new SlotOutput(te, SLOT_OUTPUT, 117, 35));
		
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
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if (index <= SLOT_OUTPUT) {
				if (!mergeItemStack(stack1, 9, 36 + 10, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(stack1, 0, 10, false)) {
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
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < listeners.size(); ++i) {
			IContainerListener listen = listeners.get(i);
			
			if (cookTime != te.getCookTime()) {
				listen.sendWindowProperty(this, 2, te.getCookTime());
			}
		}
		
		cookTime = te.getCookTime();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		te.setField(id, data);
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, te);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return te.isUsableByPlayer(player);
	}
}
