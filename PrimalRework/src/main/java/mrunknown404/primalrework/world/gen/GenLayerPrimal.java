package mrunknown404.primalrework.world.gen;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.init.ModBiomes;
import mrunknown404.primalrework.world.biome.BiomeBase;
import net.minecraft.init.Biomes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class GenLayerPrimal extends GenLayer {
	private List<BiomeManager.BiomeEntry>[] biomes = new ArrayList[BiomeManager.BiomeType.values().length];
	
	public GenLayerPrimal(long seed, GenLayer parent, WorldType worldType) {
		super(seed);
		this.parent = parent;
		
		for (BiomeManager.BiomeType type : BiomeManager.BiomeType.values()) {
			biomes[type.ordinal()] = new ArrayList<BiomeManager.BiomeEntry>();
		}
		
		for (Biome b2 : ModBiomes.BIOMES) {
			BiomeBase b = (BiomeBase) b2;
			biomes[b.getBiomeType().ordinal()].add(new BiomeEntry(b, b.getWeight()));
		}
		
		//TODO remove later
		biomes[BiomeType.DESERT.ordinal()].add(new BiomeEntry(ModBiomes.PRIMAL_FLAT_FOREST, 10));
		//biomes[BiomeType.WARM.ordinal()].add(new BiomeEntry(ModBiomes.PRIMAL_FLAT_FOREST, 10));
		biomes[BiomeType.COOL.ordinal()].add(new BiomeEntry(ModBiomes.PRIMAL_FLAT_FOREST, 10));
		biomes[BiomeType.ICY.ordinal()].add(new BiomeEntry(ModBiomes.PRIMAL_FLAT_FOREST, 10));
	}
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] aint = parent.getInts(areaX, areaY, areaWidth, areaHeight);
		int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
		
		for (int i = 0; i < areaHeight; ++i) {
			for (int j = 0; j < areaWidth; ++j) {
				initChunkSeed((long) (j + areaX), (long) (i + areaY));
				int k = aint[j + i * areaWidth];
				k = k & -3841;
				
				if (isBiomeOceanic(k)) {
					aint1[j + i * areaWidth] = k;
				} else if (k == Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND)) {
					aint1[j + i * areaWidth] = k;
				} else if (k == 1) {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(BiomeManager.BiomeType.DESERT).biome);
				} else if (k == 2) {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(BiomeManager.BiomeType.WARM).biome);
				} else if (k == 3) {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(BiomeManager.BiomeType.COOL).biome);
				} else if (k == 4) {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(BiomeManager.BiomeType.ICY).biome);
				}
			}
		}
		
		return aint1;
	}
	
	private BiomeManager.BiomeEntry getWeightedBiomeEntry(BiomeManager.BiomeType type) {
		int totalWeight = WeightedRandom.getTotalWeight(biomes[type.ordinal()]);
		int weight = BiomeManager.isTypeListModded(type) ? nextInt(totalWeight) : nextInt(totalWeight / 10) * 10;
		return (BiomeManager.BiomeEntry) WeightedRandom.getRandomItem(biomes[type.ordinal()], weight);
	}
}
