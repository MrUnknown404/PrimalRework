package mrunknown404.primalrework.world;

import mrunknown404.primalrework.world.biome.BiomeProviderPrimal;
import mrunknown404.primalrework.world.gen.ChunkGeneratorPrimal;
import mrunknown404.primalrework.world.gen.GenLayerPrimal;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.layer.GenLayer;

public class WorldTypePrimal extends WorldType {
	
	public WorldTypePrimal() {
		super("primal");
	}
	
	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new ChunkGeneratorPrimal(world, world.getSeed());
	}
	
	@Override
	public BiomeProvider getBiomeProvider(World world) {
		return new BiomeProviderPrimal(world.getWorldInfo());
	}
	
	@Override
	public GenLayer getBiomeLayer(long seed, GenLayer parent, ChunkGeneratorSettings settings) {
		return new GenLayerPrimal(seed, parent);
	}
	
	@Override
	public float getCloudHeight() {
		return 256;
	}
}
