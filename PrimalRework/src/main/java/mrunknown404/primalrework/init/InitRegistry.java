package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.api.events.EventPRRegistryRegistration;
import mrunknown404.primalrework.blocks.StagedBlock;
import mrunknown404.primalrework.items.SIBlock;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.registry.Metal;
import mrunknown404.primalrework.registry.PRRegistry;
import mrunknown404.primalrework.registry.PRRegistry.State;
import mrunknown404.primalrework.registry.PRRegistryObject;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.ROISIProvider;
import mrunknown404.primalrework.world.biome.PRBiome;
import mrunknown404.primalrework.world.biome.provider.BiomeProviderPrimal;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryBuilder;

public class InitRegistry {
	private static final DeferredRegister<Stage> STAGES = DeferredRegister
			.create(new RegistryBuilder<Stage>().setName(new ResourceLocation(PrimalRework.MOD_ID, "stages")).setType(Stage.class).create(), PrimalRework.MOD_ID);
	private static final DeferredRegister<StagedTag> STAGED_TAGS = DeferredRegister
			.create(new RegistryBuilder<StagedTag>().setName(new ResourceLocation(PrimalRework.MOD_ID, "staged_tags")).setType(StagedTag.class).create(), PrimalRework.MOD_ID);
	//private static final DeferredRegister<Metal> METALS = DeferredRegister
	//		.create(new RegistryBuilder<Metal>().setName(new ResourceLocation(PrimalRework.MOD_ID, "metals")).setType(Metal.class).create(), PrimalRework.MOD_ID);
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PrimalRework.MOD_ID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PrimalRework.MOD_ID);
	private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PrimalRework.MOD_ID);
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, PrimalRework.MOD_ID);
	private static final DeferredRegister<ForgeWorldType> WORLD_TYPES = DeferredRegister.create(ForgeRegistries.WORLD_TYPES, PrimalRework.MOD_ID);
	private static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, PrimalRework.MOD_ID);
	private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, PrimalRework.MOD_ID);
	private static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, PrimalRework.MOD_ID);
	
	private static final Map<String, List<PRRegistry<?>>> PR_REGISTRIES = new LinkedHashMap<String, List<PRRegistry<?>>>();
	private static final PRRegistry<Metal> METALS = new PRRegistry<Metal>(PrimalRework.MOD_ID, Metal.class, InitMetals.class);
	
	private static final Map<String, List<Supplier<ConfiguredFeature<?, ?>>>> BIOME_FEATURE_MAP = new HashMap<String, List<Supplier<ConfiguredFeature<?, ?>>>>();
	private static final Map<String, PRBiome> PR_BIOMES = new HashMap<String, PRBiome>();
	private static State registrationState = State.TOO_EARLY;
	
	public static void register() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		//bus.addListener((EventPRRegistryRegistration e) -> e.registerRegistry(METALS)); //Example!
		
		registrationState = State.NOW;
		PrimalRework.printDivider();
		System.out.println("Adding PrimalRework's registries");
		registerRegistry(METALS);
		
		PrimalRework.printDivider();
		System.out.println("Adding Addon's registries");
		
		EventPRRegistryRegistration event = new EventPRRegistryRegistration();
		bus.post(event);
		event.getRegistries().forEach((reg) -> registerRegistry(reg));
		
		PrimalRework.printDivider();
		PR_REGISTRIES.values().stream().forEach((list) -> list.forEach((reg) -> {
			loadClass(reg.classToLoad);
			System.out.println("Loaded " + reg.getEntries().size() + " " + reg.clazzType.getSimpleName() + "s");
			reg.finish();
		}));
		registrationState = State.LATE;
		
		bus.addGenericListener(Stage.class, (RegistryEvent.Register<Stage> e) -> loadClass(InitStages.class, STAGES, "Stages"));
		bus.addGenericListener(StagedTag.class, (RegistryEvent.Register<StagedTag> e) -> loadClass(InitStagedTags.class, STAGED_TAGS, "StagedTags"));
		bus.addGenericListener(Block.class, (RegistryEvent.Register<Block> e) -> loadClass(InitBlocks.class, BLOCKS, "Blocks"));
		bus.addGenericListener(Item.class, (RegistryEvent.Register<Item> e) -> loadClass(InitItems.class, ITEMS, "Items"));
		bus.addGenericListener(TileEntityType.class, (RegistryEvent.Register<TileEntityType<?>> e) -> loadClass(InitTileEntities.class, TILE_ENTITIES, "Tile Entity Types"));
		bus.addGenericListener(ContainerType.class, (RegistryEvent.Register<ContainerType<?>> e) -> loadClass(InitContainers.class, CONTAINERS, "Container Types"));
		bus.addGenericListener(ForgeWorldType.class, (RegistryEvent.Register<ForgeWorldType> e) -> loadClass(InitWorld.class, WORLD_TYPES, "World Types"));
		bus.addGenericListener(Feature.class, (RegistryEvent.Register<Feature<?>> e) -> loadClass(InitFeatures.class, FEATURES, "Features"));
		bus.addGenericListener(Biome.class, (RegistryEvent.Register<Biome> e) -> loadClass(InitBiomes.class, BIOMES, "Biomes"));
		bus.addGenericListener(SurfaceBuilder.class, (RegistryEvent.Register<SurfaceBuilder<?>> e) -> loadClass(InitSurfaceBuilders.class, SURFACE_BUILDERS, "Surface Builders"));
		
		STAGES.register(bus);
		STAGED_TAGS.register(bus);
		BLOCKS.register(bus);
		ITEMS.register(bus);
		TILE_ENTITIES.register(bus);
		CONTAINERS.register(bus);
		WORLD_TYPES.register(bus);
		FEATURES.register(bus);
		BIOMES.register(bus);
		SURFACE_BUILDERS.register(bus);
		
		Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(PrimalRework.MOD_ID, "biome_source"), BiomeProviderPrimal.PRIMAL_CODEC);
	}
	
	private static void registerRegistry(final PRRegistry<?> registry) {
		if (InitRegistry.getRegistrationState() == State.TOO_EARLY) {
			throw new UnsupportedOperationException("Cannot register registry too early!");
		} else if (InitRegistry.getRegistrationState() == State.LATE) {
			throw new UnsupportedOperationException("Cannot register registry when registry registration is finished!");
		}
		
		Objects.requireNonNull(registry);
		
		System.out.println("New registry " + registry);
		List<PRRegistry<?>> list = PR_REGISTRIES.computeIfAbsent(registry.getModID(), (modid) -> new ArrayList<PRRegistry<?>>());
		
		if (list.stream().anyMatch((reg) -> reg.clazzType == registry.clazzType || reg.is(registry))) {
			throw new IllegalArgumentException("Duplicate registry for [modid:registryType] -> " + registry);
		}
		
		list.add(registry);
	}
	
	private static void loadClass(Class<?> clazz, DeferredRegister<?> reg, String displayName) {
		if (clazz == InitBlocks.class) {
			PrimalRework.printDivider();
		}
		
		try {
			Class.forName(clazz.getName());
			System.out.println("Loaded " + reg.getEntries().size() + " " + displayName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadClass(Class<?> clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static RegistryObject<Stage> stage(String nameID, Supplier<Stage> o) {
		return STAGES.register(nameID, o);
	}
	
	public static RegistryObject<StagedTag> stagedTag(String name, Supplier<StagedTag> o) {
		return STAGED_TAGS.register(name, o);
	}
	
	public static PRRegistryObject<Metal> metal(String name, Supplier<Metal> o) {
		return METALS.register(name, o);
	}
	
	public static <T extends StagedBlock> ROISIProvider<T> blockNoItem(String name, Supplier<T> o) {
		return new ROISIProvider<T>(BLOCKS.register(name, o));
	}
	
	public static <T extends StagedBlock> ROISIProvider<T> block(String name, Supplier<T> o) {
		RegistryObject<T> ro = BLOCKS.register(name, o);
		item(name, () -> new SIBlock(ro.get()));
		return new ROISIProvider<T>(ro);
	}
	
	public static <T extends StagedItem> ROISIProvider<T> item(String name, Supplier<T> o) {
		return new ROISIProvider<T>(ITEMS.register(name, o));
	}
	
	public static <T extends TileEntity> RegistryObject<TileEntityType<T>> tileEntity(String name, Supplier<T> entity, ROISIProvider<StagedBlock> block) {
		return TILE_ENTITIES.register(name, () -> TileEntityType.Builder.of(entity, block.get()).build(null));
	}
	
	public static <T extends Container> RegistryObject<ContainerType<T>> container(String name, Supplier<ContainerType<T>> container) {
		return CONTAINERS.register(name, container);
	}
	
	public static <T extends ForgeWorldType> RegistryObject<T> worldType(String name, Supplier<T> worldType) {
		return WORLD_TYPES.register(name, worldType);
	}
	
	public static RegistryObject<Biome> biome(PRBiome o, List<Supplier<ConfiguredFeature<?, ?>>> features) {
		BiomeManager.addBiome(o.biomeType, new BiomeEntry(RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(PrimalRework.MOD_ID, o.name)), o.weight));
		PR_BIOMES.put(o.name, o);
		BIOME_FEATURE_MAP.put(PrimalRework.MOD_ID + ":" + o.name, features);
		return BIOMES.register(o.name, () -> o.biome);
	}
	
	public static <T extends Feature<?>> RegistryObject<T> feature(String name, Supplier<T> o) {
		return FEATURES.register(name, o);
	}
	
	public static <T extends SurfaceBuilder<?>> RegistryObject<T> surfaceBuilder(String name, Supplier<T> o) {
		return SURFACE_BUILDERS.register(name, o);
	}
	
	@SubscribeEvent
	public static void biomeLoad(BiomeLoadingEvent e) {
		List<Supplier<ConfiguredFeature<?, ?>>> list = BIOME_FEATURE_MAP.getOrDefault(e.getName().toString(), new ArrayList<Supplier<ConfiguredFeature<?, ?>>>());
		for (Supplier<ConfiguredFeature<?, ?>> conf : list) {
			e.getGeneration().addFeature(Decoration.TOP_LAYER_MODIFICATION.ordinal(), () -> InitConfiguredFeatures.GROUND_SLABS);
			e.getGeneration().addFeature(Decoration.TOP_LAYER_MODIFICATION.ordinal(), () -> InitConfiguredFeatures.GROUND_ITEMS);
			e.getGeneration().addFeature(Decoration.RAW_GENERATION.ordinal(), conf); //TODO make Decoration configurable
		}
	}
	
	public static Collection<RegistryObject<Stage>> getStages() {
		return STAGES.getEntries();
	}
	
	public static Collection<RegistryObject<StagedTag>> getTags() {
		return STAGED_TAGS.getEntries();
	}
	
	public static Collection<RegistryObject<Block>> getBlocks() {
		return BLOCKS.getEntries();
	}
	
	public static Collection<RegistryObject<Item>> getItems() {
		return ITEMS.getEntries();
	}
	
	public static Collection<RegistryObject<Biome>> getBiomes() {
		return BIOMES.getEntries();
	}
	
	public static Collection<PRRegistryObject<Metal>> getMetals() {
		return METALS.getEntries();
	}
	
	public static PRBiome getBiome(String name) {
		return PR_BIOMES.get(name);
	}
	
	public static State getRegistrationState() {
		return registrationState;
	}
}
