package mrunknown404.primalrework.api.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

/** Register using {@link PRRegistries#addRegistries(String, PRRegistry...)} */
public class PRRegistry<T extends ForgeRegistryEntry<T>> {
	private final ResourceLocation name;
	private final Map<String, PRRegistryObject<T>> map = new HashMap<String, PRRegistryObject<T>>();
	
	private final Class<T> classType;
	public final Class<?> classToLoad;
	
	public PRRegistry(String modid, Class<T> clazzType, Class<?> classToLoad) {
		this.name = new ResourceLocation(modid, clazzType.getSimpleName().toLowerCase());
		this.classType = clazzType;
		this.classToLoad = classToLoad;
	}
	
	public PRRegistryObject<T> register(String name, Supplier<T> obj) {
		if (PRRegistries.getRegistrationState() == State.TOO_EARLY) {
			throw new UnsupportedOperationException("Cannot register new objects too early!");
		} else if (PRRegistries.getRegistrationState() == State.LATE) {
			throw new UnsupportedOperationException("Cannot register new objects when registration is finished!");
		}
		
		Objects.requireNonNull(name);
		Objects.requireNonNull(obj);
		
		PRRegistryObject<T> ro = new PRRegistryObject<T>(obj);
		if (map.keySet().stream().anyMatch((o) -> o.equals(name))) {
			throw new IllegalArgumentException("Duplicate registry entry for [modid:name] -> [" + this.name.getNamespace() + ":" + name + "]");
		}
		
		map.put(name, ro);
		return ro;
	}
	
	void finish() {
		String modid = getModID();
		map.forEach((name, obj) -> obj.get().setRegistryName(new ResourceLocation(modid, name)));
	}
	
	public String getModID() {
		return name.getNamespace();
	}
	
	public String getTypeName() {
		return name.getPath();
	}
	
	public void forEach(Consumer<PRRegistryObject<T>> con) {
		map.values().forEach(con);
	}
	
	public Set<String> getKeys() {
		return map.keySet();
	}
	
	public Collection<PRRegistryObject<T>> getEntries() {
		return map.values();
	}
	
	public boolean is(PRRegistry<?> registry) {
		return name.equals(registry.name) || classType == registry.classType;
	}
	
	public Class<T> getType() {
		return classType;
	}
	
	@Override
	public String toString() {
		return "[" + name + "]";
	}
	
	public static enum State {
		TOO_EARLY,
		NOW,
		LATE;
	}
}
