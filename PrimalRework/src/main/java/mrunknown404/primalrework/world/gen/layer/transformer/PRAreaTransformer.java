package mrunknown404.primalrework.world.gen.layer.transformer;

import java.util.List;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraftforge.registries.ForgeRegistry;

public class PRAreaTransformer implements IAreaTransformer0 {
	private final List<Biome> biomes;
	private final ForgeRegistry<Biome> biomeRegistry;
	
	public PRAreaTransformer(List<Biome> biomes, ForgeRegistry<Biome> biomeRegistry) {
		this.biomes = biomes;
		this.biomeRegistry = biomeRegistry;
	}
	
	@Override
	public int applyPixel(INoiseRandom rand, int x, int z) {
		return biomeRegistry.getID(biomes.get(rand.nextRandom(biomes.size())));
	}
}
