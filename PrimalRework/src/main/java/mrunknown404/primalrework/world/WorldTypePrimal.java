package mrunknown404.primalrework.world;

import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.world.biome.provider.BiomeProviderPrimal;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
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
	public static final RegistryKey<DimensionSettings> PRIMAL = RegistryKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation("primal"));
	public static final DimensionSettings BUILTIN_PRIMAL = WorldGenRegistries.register(WorldGenRegistries.NOISE_GENERATOR_SETTINGS, PRIMAL.location(),
			new DimensionSettings(
					new DimensionStructuresSettings(true), new NoiseSettings(256, new ScalingSettings(0.9999999814507745d, 0.9999999814507745d, 80, 160),
							new SlideSettings(-10, 3, 0), new SlideSettings(-30, 0, 0), 1, 2, 1, -0.46875d, true, true, false, false),
					InitBlocks.STONE.get().defaultBlockState(), Blocks.WATER.defaultBlockState(), -10, 0, 80, false));
	
	public WorldTypePrimal() {
		super(null);
	}
	
	@Override
	public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry, Registry<DimensionSettings> dimensionSettingsRegistry, long seed, String generatorSettings) {
		return new NoiseChunkGenerator(new BiomeProviderPrimal(seed, biomeRegistry), seed, () -> dimensionSettingsRegistry.getOrThrow(PRIMAL));
	}
	
	@Override
	public DimensionGeneratorSettings createSettings(DynamicRegistries dynamicRegistries, long seed, boolean generateStructures, boolean generateLoot, String generatorSettings) {
		MutableRegistry<Biome> biome = dynamicRegistries.registryOrThrow(Registry.BIOME_REGISTRY);
		MutableRegistry<DimensionType> dimType = dynamicRegistries.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
		MutableRegistry<DimensionSettings> noise = dynamicRegistries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
		
		return new DimensionGeneratorSettings(seed, false, false,
				DimensionGeneratorSettings.withOverworld(dimType, DimensionType.defaultDimensions(dimType, biome, noise, seed), createChunkGenerator(biome, noise, seed, "")));
	}
}
