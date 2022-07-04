package mrunknown404.primalrework.api.registry;

import java.util.function.Supplier;

import net.minecraftforge.registries.ForgeRegistryEntry;

public class PRRegistryObject<T extends ForgeRegistryEntry<T>> implements Supplier<T> {
	private Supplier<T> supplier;
	private T value;
	
	PRRegistryObject(Supplier<T> supplier) {
		this.supplier = supplier;
	}
	
	@Override
	public T get() {
		if (value == null) {
			value = supplier.get();
			supplier = null;
		}
		
		return value;
	}
	
	@Deprecated
	@Override
	public String toString() {
		throw new UnsupportedOperationException("Cannot convert PRRegistryObject to string! You probably meant to call #get() first");
	}
}
