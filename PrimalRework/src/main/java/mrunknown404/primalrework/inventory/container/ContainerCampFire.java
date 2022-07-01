package mrunknown404.primalrework.inventory.container;

import java.util.List;

import mrunknown404.primalrework.init.InitContainers;
import mrunknown404.primalrework.inventory.slot.SlotCampFireFuel;
import mrunknown404.primalrework.inventory.slot.SlotCampFireItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerCampFire extends Container implements IEasyQuickMoveStack {
	private final IInventory container;
	private final IIntArray dataAccess;
	
	/** This is only used for registration! */
	public ContainerCampFire(int windowID, PlayerInventory inv) {
		this(windowID, inv, new Inventory(2), new IntArray(4));
	}
	
	public ContainerCampFire(int windowID, PlayerInventory inv, IInventory container, IIntArray dataAccess) {
		super(InitContainers.CAMPFIRE.get(), windowID);
		this.container = container;
		this.dataAccess = dataAccess;
		
		addSlot(new SlotCampFireFuel(container, 0, 80, 51));
		addSlot(new SlotCampFireItem(container, 1, 80, 14));
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlot(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(inv, i, 8 + i * 18, 142));
		}
		
		addDataSlots(dataAccess);
	}
	
	@Override
	public boolean stillValid(PlayerEntity player) {
		return container.stillValid(player);
	}
	
	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int slotID) {
		return IQuickMoveStack(player, slotID);
	}
	
	@Override
	public int getAmountOfSlots() {
		return 2;
	}
	
	@Override
	public List<Slot> getSlots() {
		return slots;
	}
	
	@Override
	public boolean IMoveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
		return moveItemStackTo(stack, startIndex, endIndex, reverseDirection);
	}
	
	public boolean isBurning() {
		return getMaxBurnTime() > 0;
	}
	
	public boolean isCooking() {
		return getMaxCookTime() > 0;
	}
	
	public int getBurnTimeLeft() {
		return dataAccess.get(0);
	}
	
	public int getMaxBurnTime() {
		return dataAccess.get(1);
	}
	
	public int getCookTimeLeft() {
		return dataAccess.get(2);
	}
	
	public int getMaxCookTime() {
		return dataAccess.get(3);
	}
}
