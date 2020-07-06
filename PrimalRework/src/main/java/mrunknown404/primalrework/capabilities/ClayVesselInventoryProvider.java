package mrunknown404.primalrework.capabilities;

import mrunknown404.primalrework.inventory.ConfigurableItemStackHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;

public class ClayVesselInventoryProvider implements ICapabilitySerializable<NBTBase> {
	private final ConfigurableItemStackHandler inventory;
	
	public ClayVesselInventoryProvider(int numSlots) {
		inventory = new ConfigurableItemStackHandler(numSlots, 1);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : null;
	}
	
	@Override
	public NBTBase serializeNBT() {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory, null);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt) {
		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory, null, nbt);
	}
}
