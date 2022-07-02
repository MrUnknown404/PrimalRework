package mrunknown404.primalrework.world;

import java.util.function.BiFunction;

import com.mojang.serialization.Lifecycle;

import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.world.biome.provider.BiomeProviderPrimal;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;
import net.minecraftforge.common.world.ForgeWorldType;

public class WorldTypePrimal extends ForgeWorldType {
	public WorldTypePrimal() {
		super(null);
	}
	
	@Override
	public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry, Registry<DimensionSettings> dimensionSettingsRegistry, long seed, String generatorSettings) {
		return new NoiseChunkGenerator(new BiomeProviderPrimal(seed, biomeRegistry), seed, () -> dimensionSettingsRegistry.getOrThrow(PRIMAL));
	}
	
	@Override
	public DimensionGeneratorSettings createSettings(DynamicRegistries dynamicRegistries, long seed, boolean generateStructures, boolean generateLoot, String generatorSettings) {
		ChunkGenerator chunkGen = createChunkGenerator(dynamicRegistries.registryOrThrow(Registry.BIOME_REGISTRY),
				dynamicRegistries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY), seed, "");
		
		return new DimensionGeneratorSettings(seed, false, false, DimensionGeneratorSettings.withOverworld(dynamicRegistries.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY),
				createDimensionRegistry(seed, dynamicRegistries, chunkGen), chunkGen));
	}
	
	private static SimpleRegistry<Dimension> createDimensionRegistry(long seed, DynamicRegistries registries, ChunkGenerator generator) {
		SimpleRegistry<Dimension> registry = new SimpleRegistry<Dimension>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.stable());
		registry.register(Dimension.OVERWORLD, createDimension(DimensionType.OVERWORLD_LOCATION, registries, generator), Lifecycle.stable());
		registry.register(Dimension.NETHER,
				createDefaultDimension(seed, DimensionType.NETHER_LOCATION, DimensionSettings.NETHER, registries, NetherBiomeProvider.Preset.NETHER::biomeSource),
				Lifecycle.stable());
		registry.register(Dimension.END, createDefaultDimension(seed, DimensionType.END_LOCATION, DimensionSettings.END, registries, EndBiomeProvider::new), Lifecycle.stable());
		return registry;
	}
	
	private static Dimension createDefaultDimension(long seed, RegistryKey<DimensionType> type, RegistryKey<DimensionSettings> setting, DynamicRegistries registries,
			BiFunction<Registry<Biome>, Long, BiomeProvider> factory) {
		return createDimension(type, registries, new NoiseChunkGenerator(factory.apply(registries.registryOrThrow(Registry.BIOME_REGISTRY), seed), seed,
				() -> registries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY).getOrThrow(setting)));
	}
	
	private static Dimension createDimension(RegistryKey<DimensionType> type, DynamicRegistries registries, ChunkGenerator generator) {
		return new Dimension(() -> registries.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY).getOrThrow(type), generator);
	}
	
	public static final RegistryKey<DimensionSettings> PRIMAL = RegistryKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation("primal"));
	public static final DimensionSettings BUILTIN_PRIMAL = register(PRIMAL, primal(new DimensionStructuresSettings(true), false));
	
	private static DimensionSettings primal(DimensionStructuresSettings settings, boolean flag) {
		return new DimensionSettings(settings, new NoiseSettings(256, new ScalingSettings(0.9999999814507745d, 0.9999999814507745d, 80, 160), new SlideSettings(-10, 3, 0),
				new SlideSettings(-30, 0, 0), 1, 2, 1, -0.46875d, true, true, false, flag), InitBlocks.STONE.get().defaultBlockState(), Blocks.WATER.defaultBlockState(), -10, 0,
				80, false);
	}
	
	private static DimensionSettings register(RegistryKey<DimensionSettings> key, DimensionSettings settings) {
		WorldGenRegistries.register(WorldGenRegistries.NOISE_GENERATOR_SETTINGS, key.location(), settings);
		return settings;
	}
}
