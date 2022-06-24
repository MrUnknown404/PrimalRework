package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.SIBlock;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.stage.StagedTag;
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
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class PRRegistry {
	private static final IForgeRegistry<Stage> REG_STAGES = new RegistryBuilder<Stage>().setName(new ResourceLocation(PrimalRework.MOD_ID, "stages")).setType(Stage.class).create();
	private static final IForgeRegistry<StagedTag> REG_STAGED_TAGS = new RegistryBuilder<StagedTag>().setName(new ResourceLocation(PrimalRework.MOD_ID, "staged_tags"))
			.setType(StagedTag.class).create();
	
	private static final DeferredRegister<Stage> STAGES = DeferredRegister.create(REG_STAGES, PrimalRework.MOD_ID);
	private static final DeferredRegister<StagedTag> STAGED_TAGS = DeferredRegister.create(REG_STAGED_TAGS, PrimalRework.MOD_ID);
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PrimalRework.MOD_ID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PrimalRework.MOD_ID);
	private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PrimalRework.MOD_ID);
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, PrimalRework.MOD_ID);
	private static final DeferredRegister<ForgeWorldType> WORLD_TYPES = DeferredRegister.create(ForgeRegistries.WORLD_TYPES, PrimalRework.MOD_ID);
	private static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, PrimalRework.MOD_ID);
	private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, PrimalRework.MOD_ID);
	private static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, PrimalRework.MOD_ID);
	
	public static void register() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addGenericListener(Stage.class, PRRegistry::registerStages);
		bus.addGenericListener(StagedTag.class, PRRegistry::registerStagedTags);
		bus.addGenericListener(Block.class, PRRegistry::registerBlocks);
		bus.addGenericListener(Item.class, PRRegistry::registerItems);
		bus.addGenericListener(TileEntityType.class, PRRegistry::registerTileEntities);
		bus.addGenericListener(ContainerType.class, PRRegistry::registerContainers);
		bus.addGenericListener(ForgeWorldType.class, PRRegistry::registerWorldTypes);
		bus.addGenericListener(Feature.class, PRRegistry::registerFeatures);
		bus.addGenericListener(Biome.class, PRRegistry::registerBiomes);
		bus.addGenericListener(SurfaceBuilder.class, PRRegistry::registerSurfaceBuilders);
		
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
	
	@SubscribeEvent
	public static void registerStages(@SuppressWarnings("unused") RegistryEvent.Register<Stage> e) {
		loadClass(PRStages.class);
	}
	
	@SubscribeEvent
	public static void registerStagedTags(@SuppressWarnings("unused") RegistryEvent.Register<StagedTag> e) {
		loadClass(PRStagedTags.class);
	}
	
	@SubscribeEvent
	public static void registerBlocks(@SuppressWarnings("unused") RegistryEvent.Register<Block> e) {
		loadClass(PRBlocks.class);
	}
	
	@SubscribeEvent
	public static void registerItems(@SuppressWarnings("unused") RegistryEvent.Register<Item> e) {
		loadClass(PRItems.class);
	}
	
	@SubscribeEvent
	public static void registerTileEntities(@SuppressWarnings("unused") RegistryEvent.Register<TileEntityType<?>> e) {
		loadClass(PRTileEntities.class);
	}
	
	@SubscribeEvent
	public static void registerContainers(@SuppressWarnings("unused") RegistryEvent.Register<ContainerType<?>> e) {
		loadClass(PRContainers.class);
	}
	
	@SubscribeEvent
	public static void registerWorldTypes(@SuppressWarnings("unused") RegistryEvent.Register<ForgeWorldType> e) {
		loadClass(PRWorld.class);
	}
	
	@SubscribeEvent
	public static void registerFeatures(@SuppressWarnings("unused") RegistryEvent.Register<Feature<?>> e) {
		loadClass(PRFeatures.class);
	}
	
	@SubscribeEvent
	public static void registerBiomes(@SuppressWarnings("unused") RegistryEvent.Register<Biome> e) {
		loadClass(PRBiomes.class);
	}
	
	@SubscribeEvent
	public static void registerSurfaceBuilders(@SuppressWarnings("unused") RegistryEvent.Register<SurfaceBuilder<?>> e) {
		loadClass(PRSurfaceBuilders.class);
	}
	
	private static final Map<String, List<Supplier<ConfiguredFeature<?, ?>>>> BIOME_FEATURE_MAP = new HashMap<String, List<Supplier<ConfiguredFeature<?, ?>>>>();
	
	@SubscribeEvent
	public static void biomeLoad(BiomeLoadingEvent e) {
		List<Supplier<ConfiguredFeature<?, ?>>> list = BIOME_FEATURE_MAP.getOrDefault(e.getName().toString(), new ArrayList<Supplier<ConfiguredFeature<?, ?>>>());
		for (Supplier<ConfiguredFeature<?, ?>> conf : list) {
			e.getGeneration().addFeature(Decoration.TOP_LAYER_MODIFICATION.ordinal(), () -> PRConfiguredFeatures.GROUND_SLABS);
			e.getGeneration().addFeature(Decoration.TOP_LAYER_MODIFICATION.ordinal(), () -> PRConfiguredFeatures.GROUND_ITEMS);
			e.getGeneration().addFeature(Decoration.RAW_GENERATION.ordinal(), conf); //TODO make Decoration configurable
		}
	}
	
	private static void loadClass(Class<?> clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static RegistryObject<Stage> stage(Stage o) {
		return STAGES.register(o.nameID, () -> o);
	}
	
	public static RegistryObject<StagedTag> stagedTag(StagedTag o) {
		return STAGED_TAGS.register(o.name, () -> o);
	}
	
	public static <T extends StagedBlock> RegistryObject<T> block(T o) {
		return block(o, true);
	}
	
	public static <T extends StagedBlock> RegistryObject<T> block(T o, boolean registerItem) {
		if (registerItem) {
			item((StagedItem) new SIBlock(o));
		}
		return BLOCKS.register(o.getRegName(), () -> o);
	}
	
	public static <T extends StagedItem> RegistryObject<T> item(T o) {
		return ITEMS.register(o.getRegName(), () -> o);
	}
	
	public static <T extends TileEntity> RegistryObject<TileEntityType<T>> tileEntity(String name, Supplier<T> entity, RegistryObject<StagedBlock> block) {
		return TILE_ENTITIES.register(name, () -> TileEntityType.Builder.of(entity, block.get()).build(null));
	}
	
	public static <T extends Container> RegistryObject<ContainerType<T>> container(String name, Supplier<ContainerType<T>> container) {
		return CONTAINERS.register(name, container);
	}
	
	public static <T extends ForgeWorldType> RegistryObject<T> worldType(String name, Supplier<T> worldType) {
		return WORLD_TYPES.register(name, worldType);
	}
	
	@SafeVarargs
	public static RegistryObject<Biome> register(PRBiome o, Supplier<ConfiguredFeature<?, ?>>... features) {
		ResourceLocation loc = new ResourceLocation(PrimalRework.MOD_ID, o.name);
		BiomeManager.addBiome(o.biomeType, new BiomeEntry(RegistryKey.create(Registry.BIOME_REGISTRY, loc), o.weight));
		BIOME_FEATURE_MAP.put(PrimalRework.MOD_ID + ":" + o.name, Arrays.asList(features));
		return BIOMES.register(o.name, () -> o.biome);
	}
	
	public static <T extends Feature<?>> RegistryObject<T> feature(String name, Supplier<T> o) {
		return FEATURES.register(name, o);
	}
	
	public static <T extends SurfaceBuilder<?>> RegistryObject<T> surfaceBuilder(String name, Supplier<T> o) {
		return SURFACE_BUILDERS.register(name, o);
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
}
