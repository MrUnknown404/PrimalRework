package mrunknown404.primalrework.world.biome;

import java.util.List;

import mrunknown404.primalrework.init.InitBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.storage.WorldInfo;

public class BiomeProviderPrimal extends BiomeProvider {
	
	public BiomeProviderPrimal(WorldInfo info) {
		super(info);
	}
	
	@Override
	public List<Biome> getBiomesToSpawnIn() {
		return InitBiomes.BIOMES;
	}
}
