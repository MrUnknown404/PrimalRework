package mrunknown404.primalrework.inventory;

import net.minecraftforge.items.ItemStackHandler;

public class ConfigurableItemStackHandler extends ItemStackHandler {
	private final int stackSize;
	
	public ConfigurableItemStackHandler(int size, int stackSize) {
		super(size);
		this.stackSize = stackSize;
	}
	
	@Override
	public int getSlotLimit(int slot) {
		return stackSize;
	}
}
