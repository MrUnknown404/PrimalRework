package mrunknown404.primalrework.world.gen.layer.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import mrunknown404.primalrework.init.InitRegistry;
import mrunknown404.primalrework.utils.Pair;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraftforge.registries.ForgeRegistry;

public class PRAreaTransformer implements IAreaTransformer0 {
	private final BiomeList biomes;
	private final ForgeRegistry<Biome> biomeRegistry;
	
	public PRAreaTransformer(List<Biome> biomes, ForgeRegistry<Biome> biomeRegistry) {
		this.biomes = new BiomeList(biomes.stream().map((b) -> Pair.of(b, InitRegistry.getBiome(b.getRegistryName().getPath()).weight)).collect(Collectors.toList()));
		this.biomeRegistry = biomeRegistry;
	}
	
	@Override
	public int applyPixel(INoiseRandom rand, int x, int z) {
		return biomeRegistry.getID(biomes.get(rand));
	}
	
	public static class BiomeList implements Iterable<Pair<Biome, Integer>> {
		protected final List<Pair<Biome, Integer>> list = new ArrayList<Pair<Biome, Integer>>();
		protected int totalWeight;
		
		public BiomeList() {
			
		}
		
		public BiomeList(List<Pair<Biome, Integer>> list) {
			for (Pair<Biome, Integer> entry : list) {
				add(entry.getL(), entry.getR());
			}
		}
		
		private void add(Biome t, int weight) {
			list.add(Pair.of(t, weight));
			totalWeight += weight;
		}
		
		private Biome get(INoiseRandom random) {
			int idx = 0;
			for (double r = random.nextRandom(totalWeight); idx < list.size() - 1; idx++) {
				r -= list.get(idx).getR();
				if (r <= 0) {
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
