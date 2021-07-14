package mrunknown404.primalrework.world;

import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.common.world.ForgeWorldType;

public class WorldTypePrimal extends ForgeWorldType {
	
	public WorldTypePrimal() {
		super(null);
	}
	
	@Override
	public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry, Registry<DimensionSettings> dimensionSettingsRegistry, long seed, String generatorSettings) {
		return new NoiseChunkGenerator(new BiomeProviderPrimal(seed, biomeRegistry), seed, () -> dimensionSettingsRegistry.getOrThrow(DimensionSettings.OVERWORLD));
	}
	
	@Override
	public DimensionGeneratorSettings createSettings(DynamicRegistries dynamicRegistries, long seed, boolean generateStructures, boolean generateLoot, String generatorSettings) {
		Registry<Biome> biomeRegistry = dynamicRegistries.registryOrThrow(Registry.BIOME_REGISTRY);
		Registry<DimensionSettings> dimensionSettingsRegistry = dynamicRegistries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
		Registry<DimensionType> dimensionTypeRegistry = dynamicRegistries.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
		
		return new DimensionGeneratorSettings(seed, false, false,
				DimensionGeneratorSettings.withOverworld(dimensionTypeRegistry, DimensionTypePrimal.primalDimensions(biomeRegistry, dimensionSettingsRegistry, seed),
						createChunkGenerator(biomeRegistry, dimensionSettingsRegistry, seed, null)));
	}
}
