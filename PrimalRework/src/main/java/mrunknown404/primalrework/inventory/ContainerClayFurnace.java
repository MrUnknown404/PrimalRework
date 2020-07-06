package mrunknown404.primalrework.inventory;

import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.inventory.slot.SlotBase;
import mrunknown404.primalrework.inventory.slot.SlotClayFurnaceFuel;
import mrunknown404.primalrework.tileentity.TileEntityClayFurnace;
import mrunknown404.unknownlibs.inventory.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerClayFurnace extends Container {
	
	public static final int SLOT_FUEL = 0, SLOT_INPUT = 1, SLOT_OUTPUT = 2;
	
	private final TileEntityClayFurnace te;
	private int cookTime;
	
	public ContainerClayFurnace(InventoryPlayer player, TileEntityClayFurnace te) {
		this.te = te;
		
		addSlotToContainer(new SlotClayFurnaceFuel(te, SLOT_FUEL, 80, 54));
		addSlotToContainer(new SlotBase(new ItemStack(ModItems.CLAY_VESSEL), 1, te, SLOT_INPUT, 59, 28));
		addSlotToContainer(new SlotOutput(te, SLOT_OUTPUT, 105, 28));
		
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
				if (!mergeItemStack(stack1, 2, 36 + 3, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(stack1, 0, 3, false)) {
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
