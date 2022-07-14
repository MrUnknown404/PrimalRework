package mrunknown404.primalrework.world.biome.provider;

import java.util.function.LongFunction;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mrunknown404.primalrework.world.gen.layer.transformer.PRAreaTransformer;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.ZoomLayer;

public class BiomeProviderPrimal extends BiomeProvider {
	public static final Codec<BiomeProviderPrimal> PRIMAL_CODEC = RecordCodecBuilder
			.create(b -> b.group(Codec.LONG.fieldOf("seed").stable().forGetter(p -> p.seed), RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter(p -> p.biomes)).apply(b,
					b.stable(BiomeProviderPrimal::new)));
	
	private final long seed;
	private final Layer noiseBiomeLayer;
	private final Registry<Biome> biomes;
	
	public BiomeProviderPrimal(long seed, Registry<Biome> biomes) {
		super(biomes.stream().collect(Collectors.toList()));
		this.seed = seed;
		this.biomes = biomes;
		this.noiseBiomeLayer = new Layer(buildLayers(l -> new LazyAreaLayerContext(25, seed, l)));
	}
	
	private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> buildLayers(LongFunction<C> seedHandler) {
		final int biomeSize = 4;
		
		IAreaFactory<T> iareafactory = new PRAreaTransformer().run(seedHandler.apply(1)); //TODO this needs to be re-done!
		iareafactory = LayerUtil.zoom(2001, ZoomLayer.NORMAL, iareafactory, biomeSize, seedHandler);
		
		return iareafactory;
	}
	
	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
		return noiseBiomeLayer.get(biomes, x, z);
	}
	
	@Override
	protected Codec<? extends BiomeProvider> codec() {
		return PRIMAL_CODEC;
	}
	
	@Override
	public BiomeProvider withSeed(long seed) {
		return new BiomeProviderPrimal(seed, biomes);
	}
}
