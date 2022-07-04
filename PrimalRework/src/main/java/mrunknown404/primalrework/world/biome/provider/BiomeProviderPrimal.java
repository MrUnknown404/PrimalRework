package mrunknown404.primalrework.world.biome.provider;

import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mrunknown404.primalrework.init.InitRegistry;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class BiomeProviderPrimal extends BiomeProvider {
	public static final Codec<BiomeProviderPrimal> PRIMAL_CODEC = RecordCodecBuilder.create((builder) -> builder
			.group(Codec.LONG.fieldOf("seed").stable().forGetter((biomeProvider) -> biomeProvider.seed),
					RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((biomeProvider) -> biomeProvider.biomes))
			.apply(builder, builder.stable(BiomeProviderPrimal::new)));
	
	private final long seed;
	private final Layer noiseBiomeLayer;
	private final Registry<Biome> biomes;
	
	public BiomeProviderPrimal(long seed, Registry<Biome> biomeRegistry) {
		super(biomeRegistry.stream().collect(Collectors.toList()));
		this.seed = seed;
		this.biomes = biomeRegistry;
		this.noiseBiomeLayer = new Layer(buildLayers(InitRegistry.getBiomes(), (ForgeRegistry<Biome>) ForgeRegistries.BIOMES, (l) -> new LazyAreaLayerContext(25, seed, l)));
	}
	
	private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> buildLayers(List<Biome> biomes, ForgeRegistry<Biome> biomeRegistry,
			LongFunction<C> seedHandler) {
		final int biomeSize = 4;
		
		IAreaFactory<T> iareafactory = new PRAreaTransformer(biomes, biomeRegistry).run(seedHandler.apply(1));
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
