package mrunknown404.primalrework.inventory.container;

import java.util.List;

import mrunknown404.primalrework.init.InitItems;
import mrunknown404.primalrework.inventory.slot.SlotSingleItem;
import mrunknown404.primalrework.inventory.slot.SlotClayFurnaceFuel;
import mrunknown404.primalrework.tileentity.TileEntityClayFurnace;
import mrunknown404.unknownlibs.inventory.container.IEasyTransferStack;
import mrunknown404.unknownlibs.inventory.slot.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerClayFurnace extends Container implements IEasyTransferStack {
	
	public static final int SLOT_FUEL = 0, SLOT_INPUT = 1, SLOT_OUTPUT = 2;
	
	private final TileEntityClayFurnace te;
	private int cookTime;
	
	public ContainerClayFurnace(InventoryPlayer player, TileEntityClayFurnace te) {
		this.te = te;
		
		addSlotToContainer(new SlotClayFurnaceFuel(te, SLOT_FUEL, 80, 54));
		addSlotToContainer(new SlotSingleItem(new ItemStack(InitItems.CLAY_VESSEL), 1, te, SLOT_INPUT, 59, 28));
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
	public List<Slot> getSlots() {
		return inventorySlots;
	}
	
	@Override
	public int getAmountOfInputSlots() {
		return 2;
	}
	
	@Override
	public int getAmountOfOutputSlots() {
		return 1;
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
