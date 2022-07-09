package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.api.registry.PRRegistries;
import mrunknown404.primalrework.api.registry.PRRegistry;
import mrunknown404.primalrework.api.registry.PRRegistryObject;
import mrunknown404.primalrework.api.registry.ROISIProvider;
import mrunknown404.primalrework.blocks.StagedBlock;
import mrunknown404.primalrework.init.InitBiomes.FeatureMap;
import mrunknown404.primalrework.items.SIBlock;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.Logger;
import mrunknown404.primalrework.utils.Metal;
import mrunknown404.primalrework.utils.ToolMaterial;
import mrunknown404.primalrework.world.biome.PRBiome;
import mrunknown404.primalrework.world.biome.provider.BiomeProviderPrimal;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
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

public class InitRegistry {
	private static final DeferredRegister<Stage> STAGES = DeferredRegister.create(PRRegistries.STAGES, PrimalRework.MOD_ID);
	private static final DeferredRegister<StagedTag> STAGED_TAGS = DeferredRegister.create(PRRegistries.STAGED_TAGS, PrimalRework.MOD_ID);
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PrimalRework.MOD_ID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PrimalRework.MOD_ID);
	private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PrimalRework.MOD_ID);
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, PrimalRework.MOD_ID);
	private static final DeferredRegister<ForgeWorldType> WORLD_TYPES = DeferredRegister.create(ForgeRegistries.WORLD_TYPES, PrimalRework.MOD_ID);
	private static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, PrimalRework.MOD_ID);
	private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, PrimalRework.MOD_ID);
	private static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, PrimalRework.MOD_ID);
	
	private static final PRRegistry<Metal> METALS = new PRRegistry<Metal>(PrimalRework.MOD_ID, Metal.class, InitMetals.class);
	private static final PRRegistry<ToolMaterial> TOOL_MATERIALS = new PRRegistry<ToolMaterial>(PrimalRework.MOD_ID, ToolMaterial.class, InitToolMaterials.class);
	
	private static final Map<String, Supplier<FeatureMap>> BIOME_FEATURE_MAP = new HashMap<String, Supplier<FeatureMap>>();
	private static final Map<String, PRBiome> PR_BIOMES = new HashMap<String, PRBiome>();
	
	public static void preSetup() {
		PRRegistries.addRegistries(PrimalRework.MOD_ID, METALS, TOOL_MATERIALS);
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addGenericListener(Stage.class, (RegistryEvent.Register<Stage> e) -> loadClass(InitStages.class));
		bus.addGenericListener(StagedTag.class, (RegistryEvent.Register<StagedTag> e) -> loadClass(InitStagedTags.class));
		bus.addGenericListener(Block.class, (RegistryEvent.Register<Block> e) -> loadClass(InitBlocks.class));
		bus.addGenericListener(Item.class, (RegistryEvent.Register<Item> e) -> loadClass(InitItems.class));
		bus.addGenericListener(TileEntityType.class, (RegistryEvent.Register<TileEntityType<?>> e) -> loadClass(InitTileEntities.class));
		bus.addGenericListener(ContainerType.class, (RegistryEvent.Register<ContainerType<?>> e) -> loadClass(InitContainers.class));
		bus.addGenericListener(ForgeWorldType.class, (RegistryEvent.Register<ForgeWorldType> e) -> loadClass(InitWorld.class));
		bus.addGenericListener(Feature.class, (RegistryEvent.Register<Feature<?>> e) -> loadClass(InitFeatures.class));
		bus.addGenericListener(Biome.class, (RegistryEvent.Register<Biome> e) -> loadClass(InitBiomes.class));
		bus.addGenericListener(SurfaceBuilder.class, (RegistryEvent.Register<SurfaceBuilder<?>> e) -> loadClass(InitSurfaceBuilders.class));
		
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
	
	public static void setup() {
		Logger.multiLine("Results of adding Forge registries", loaded(PRRegistries.STAGES, "Stages"), loaded(PRRegistries.STAGED_TAGS, "StagedTags"),
				loaded(ForgeRegistries.BLOCKS, "Blocks"), loaded(ForgeRegistries.ITEMS, "Items"), loaded(ForgeRegistries.TILE_ENTITIES, "Tile Entity Types"),
				loaded(ForgeRegistries.CONTAINERS, "Container Types"), loaded(ForgeRegistries.WORLD_TYPES, "World Types"), loaded(ForgeRegistries.FEATURES, "Features"),
				loaded(ForgeRegistries.BIOMES, "Biomes"), loaded(ForgeRegistries.SURFACE_BUILDERS, "Surface Builders"));
		
		InitStages.load();
		InitRecipes.load();
		InitFuels.load();
		
		List<String> multiLine = new ArrayList<String>();
		multiLine.add("Results of adding Quests/Recipes/Fuels");
		multiLine.addAll(InitQuests.getRecipeListPrint());
		multiLine.addAll(InitRecipes.getRecipeListPrint());
		multiLine.addAll(InitFuels.getRecipeListPrint());
		Logger.multiLine(multiLine);
	}
	
	private static String loaded(IForgeRegistry<?> reg, String displayName) {
		return "Loaded " + reg.getEntries().stream().filter(e -> !e.getKey().location().getNamespace().equals("minecraft")).count() + " " + displayName;
	}
	
	private static void loadClass(Class<?> clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	static RegistryObject<Stage> stage(String nameID, Supplier<Stage> o) {
		return STAGES.register(nameID, o);
	}
	
	static RegistryObject<StagedTag> stagedTag(String name, Supplier<StagedTag> o) {
		return STAGED_TAGS.register(name, o);
	}
	
	static PRRegistryObject<Metal> metal(String name, Supplier<Metal> o) {
		return METALS.register(name, o);
	}
	
	static PRRegistryObject<ToolMaterial> toolMaterial(String name, Supplier<ToolMaterial> o) {
		return TOOL_MATERIALS.register(name, o);
	}
	
	static <T extends StagedBlock> ROISIProvider<T> blockNoItem(String name, Supplier<T> o) {
		return ROISIProvider.of(BLOCKS.register(name, o));
	}
	
	static <T extends StagedBlock> ROISIProvider<T> block(String name, Supplier<T> o) {
		RegistryObject<T> ro = BLOCKS.register(name, o);
		item(name, () -> new SIBlock(ro.get()));
		return ROISIProvider.of(ro);
	}
	
	static <T extends StagedItem> ROISIProvider<T> item(String name, Supplier<T> o) {
		return ROISIProvider.of(ITEMS.register(name, o));
	}
	
	static <T extends TileEntity> RegistryObject<TileEntityType<T>> tileEntity(String name, Supplier<T> entity, ROISIProvider<StagedBlock> block) {
		return TILE_ENTITIES.register(name, () -> TileEntityType.Builder.of(entity, block.get()).build(null));
	}
	
	static <T extends Container> RegistryObject<ContainerType<T>> container(String name, Supplier<ContainerType<T>> container) {
		return CONTAINERS.register(name, container);
	}
	
	static <T extends ForgeWorldType> RegistryObject<T> worldType(String name, Supplier<T> worldType) {
		return WORLD_TYPES.register(name, worldType);
	}
	
	/** TODO make this publicly usable */
	static RegistryObject<Biome> biome(String name, PRBiome o, Supplier<FeatureMap> features) {
		PR_BIOMES.put(PrimalRework.MOD_ID + ":" + name, o);
		BIOME_FEATURE_MAP.put(PrimalRework.MOD_ID + ":" + name, features);
		return BIOMES.register(name, () -> o.biome);
	}
	
	static <T extends Feature<?>> RegistryObject<T> feature(String name, Supplier<T> o) {
		return FEATURES.register(name, o);
	}
	
	static <T extends SurfaceBuilder<?>> RegistryObject<T> surfaceBuilder(String name, Supplier<T> o) {
		return SURFACE_BUILDERS.register(name, o);
	}
	
	@SubscribeEvent
	public static void biomeLoad(BiomeLoadingEvent e) {
		if (e.getName().getNamespace().equals("minecraft")) {
			return;
		}
		
		BIOME_FEATURE_MAP.get(e.getName().toString()).get().addFeaturesToBiome(e.getGeneration()::addFeature);
	}
	
	/** Data-gen only! */
	@Deprecated
	public static Collection<RegistryObject<Block>> getBlocks() {
		return BLOCKS.getEntries();
	}
	
	/** Data-gen only! */
	@Deprecated
	public static Collection<RegistryObject<Item>> getItems() {
		return ITEMS.getEntries();
	}
	
	public static List<Biome> getBiomes() {
		return PR_BIOMES.values().stream().map(b -> b.biome).collect(Collectors.toList());
	}
	
	public static PRBiome getBiome(String name) {
		return PR_BIOMES.get(name);
	}
}
