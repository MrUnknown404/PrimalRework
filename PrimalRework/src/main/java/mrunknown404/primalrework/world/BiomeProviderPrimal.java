package mrunknown404.primalrework.world;

import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;

public class BiomeProviderPrimal extends BiomeProvider {
	public static final Codec<BiomeProviderPrimal> CODEC0 = RecordCodecBuilder.create((builder) -> {
		return builder
				.group(Codec.LONG.fieldOf("seed").stable().forGetter((biomeProvider) -> biomeProvider.seed),
						RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((biomeProvider) -> biomeProvider.biomes))
				.apply(builder, builder.stable(BiomeProviderPrimal::new));
	});
	
	private final long seed;
	private final Layer noiseBiomeLayer;
	private final Registry<Biome> biomes;
	
	public BiomeProviderPrimal(long seed, Registry<Biome> biomes) {
		super(biomes.stream().collect(Collectors.toList()));
		this.seed = seed;
		this.noiseBiomeLayer = LayerUtil.getDefaultLayer(seed, false, 4, 4);
		this.biomes = biomes;
	}
	
	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
		return noiseBiomeLayer.get(biomes, x, z);
	}
	
	@Override
	protected Codec<? extends BiomeProvider> codec() {
		return CODEC0;
	}
	
	@Override
	public BiomeProvider withSeed(long seed) {
		return new BiomeProviderPrimal(seed, this.biomes);
	}
}
