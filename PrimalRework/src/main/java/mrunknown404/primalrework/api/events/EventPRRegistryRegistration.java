package mrunknown404.primalrework.api.events;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.registry.PRRegistry;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;

public class EventPRRegistryRegistration extends Event implements IModBusEvent {
	private final List<PRRegistry<?>> registries = new ArrayList<PRRegistry<?>>();
	
	public void registerRegistry(PRRegistry<?> reg) {
		registries.add(reg);
	}
	
	public List<PRRegistry<?>> getRegistries() {
		return registries;
	}
}
