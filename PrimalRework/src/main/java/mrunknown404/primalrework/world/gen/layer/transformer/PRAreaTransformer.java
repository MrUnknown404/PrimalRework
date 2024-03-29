package mrunknown404.primalrework.world.gen.layer.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mrunknown404.primalrework.api.registry.PRRegistries;
import mrunknown404.primalrework.utils.Pair;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class PRAreaTransformer implements IAreaTransformer0 {
	private final BiomeList biomes = new BiomeList();
	private final ForgeRegistry<Biome> biomeRegistry = (ForgeRegistry<Biome>) ForgeRegistries.BIOMES;
	
	@Override
	public int applyPixel(INoiseRandom rand, int x, int z) {
		return biomeRegistry.getID(biomes.get(rand));
	}
	
	private static class BiomeList implements Iterable<Pair<Biome, Integer>> {
		private final List<Pair<Biome, Integer>> list = new ArrayList<Pair<Biome, Integer>>();
		private int totalWeight;
		
		private BiomeList() {
			PRRegistries.getBiomes().forEach(b -> {
				list.add(Pair.of(b.biome, b.weight));
				totalWeight += b.weight;
			});
		}
		
		private Biome get(INoiseRandom random) {
			int idx = 0;
			for (double r = random.nextRandom(totalWeight); idx < list.size() - 1; idx++) {
				if ((r -= list.get(idx).getR()) <= 0) {
					break;
				}
			}
			
			return list.get(idx).getL();
		}
		
		@Override
		public Iterator<Pair<Biome, Integer>> iterator() {
			return list.iterator();
		}
	}
}
