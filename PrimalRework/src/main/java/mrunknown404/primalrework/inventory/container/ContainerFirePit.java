package mrunknown404.primalrework.inventory.container;

import java.util.List;

import mrunknown404.primalrework.inventory.slot.SlotFirePitFuel;
import mrunknown404.primalrework.inventory.slot.SlotFirePitItem;
import mrunknown404.primalrework.tileentity.TileEntityFirePit;
import mrunknown404.unknownlibs.inventory.container.IEasyTransferStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFirePit extends Container implements IEasyTransferStack {
	
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
	public List<Slot> getSlots() {
		return inventorySlots;
	}
	
	@Override
	public int getAmountOfInputSlots() {
		return 2;
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
}
