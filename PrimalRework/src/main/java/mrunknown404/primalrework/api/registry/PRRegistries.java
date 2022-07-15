package mrunknown404.primalrework.api.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.api.registry.PRRegistry.State;
import mrunknown404.primalrework.init.InitBiomes.FeatureMap;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.Logger;
import mrunknown404.primalrework.utils.Metal;
import mrunknown404.primalrework.utils.ToolMaterial;
import mrunknown404.primalrework.world.biome.PRBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public final class PRRegistries {
	private static final Map<String, Supplier<FeatureMap>> BIOME_FEATURE_MAP = new HashMap<String, Supplier<FeatureMap>>();
	private static final Map<String, PRBiome> PR_BIOMES = new HashMap<String, PRBiome>();
	
	private static final Map<Class<?>, List<PRRegistryObject<?>>> MASTER_REGISTRIES = new HashMap<Class<?>, List<PRRegistryObject<?>>>();
	private static final Map<String, List<PRRegistry<?>>> EVENT_MAP = new HashMap<String, List<PRRegistry<?>>>();
	
	public static final List<PRRegistryObject<Stage>> STAGES = addMasterRegistry(Stage.class);
	public static final List<PRRegistryObject<Metal>> METALS = addMasterRegistry(Metal.class);
	public static final List<PRRegistryObject<ToolMaterial>> TOOL_MATERIALS = addMasterRegistry(ToolMaterial.class);
	
	public static final IForgeRegistry<StagedTag> STAGED_TAGS = forgeRegistry("staged_tags", StagedTag.class);
	
	private static State registrationState = State.TOO_EARLY;
	
	private PRRegistries() {}
	
	/** Do not use! */
	@Deprecated
	public static void setup() {
		registrationState = State.NOW;
		
		List<String> multiLine = new ArrayList<String>();
		multiLine.add("Results of adding PRRegistries");
		
		EVENT_MAP.values().forEach(c -> c.forEach(reg -> {
			loadClass(reg.classToLoad);
			PRRegistries.addToMasterRegistery(reg);
			multiLine.add("Loaded " + reg.getEntries().size() + " " + reg.getType().getSimpleName() + "s");
		}));
		
		registrationState = State.LATE;
		Logger.multiLine(multiLine);
	}
	
	private static <T extends ForgeRegistryEntry<T>> void addToMasterRegistery(PRRegistry<T> reg) {
		if (!MASTER_REGISTRIES.containsKey(reg.getType())) {
			throw new UnsupportedOperationException("Unknown registry type '" + reg.getType().getSimpleName() + "'");
		}
		
		reg.finish();
		reg.forEach(MASTER_REGISTRIES.get(reg.getType())::add);
	}
	
	@SuppressWarnings("unchecked")
	private static <L extends PRRegistryObject<?>, T extends IForgeRegistryEntry<T>> List<L> addMasterRegistry(Class<T> clazz) {
		List<PRRegistryObject<?>> l = new ArrayList<PRRegistryObject<?>>();
		MASTER_REGISTRIES.put(clazz, l);
		return (List<L>) l;
	}
	
	public static void addRegistries(String modid, PRRegistry<?>... registries) {
		if (EVENT_MAP.containsKey(modid)) {
			throw new UnsupportedOperationException("There is already someone listening with that modid!");
		}
		
		List<PRRegistry<?>> list = EVENT_MAP.computeIfAbsent(modid, m -> new ArrayList<PRRegistry<?>>());
		for (PRRegistry<?> reg : registries) {
			if (list.stream().anyMatch(reg::is)) {
				throw new IllegalArgumentException("Duplicate registry for [modid:registryType] -> " + reg);
			}
			
			list.add(reg);
		}
	}
	
	public static RegistryObject<Biome> biome(String modid, DeferredRegister<Biome> biomes, String name, PRBiome o, Supplier<FeatureMap> features) {
		PR_BIOMES.put(modid + ":" + name, o);
		BIOME_FEATURE_MAP.put(modid + ":" + name, features);
		return biomes.register(name, () -> o.biome);
	}
	
	public static State getRegistrationState() {
		return registrationState;
	}
	
	public static Collection<PRBiome> getBiomes() {
		return PR_BIOMES.values();
	}
	
	public static void biomeLoad(BiomeLoadingEvent e) {
		if (e.getName().getNamespace().equals("minecraft")) {
			return;
		}
		BIOME_FEATURE_MAP.get(e.getName().toString()).get().addFeaturesToBiome(e.getGeneration()::addFeature);
	}
	
	private static <T extends IForgeRegistryEntry<T>> IForgeRegistry<T> forgeRegistry(String name, Class<T> clazz) {
		return new RegistryBuilder<T>().setName(new ResourceLocation(PrimalRework.MOD_ID, name)).setType(clazz).create();
	}
	
	private static void loadClass(Class<?> clazz) {
		if (clazz == null) {
			return;
		}
		
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
