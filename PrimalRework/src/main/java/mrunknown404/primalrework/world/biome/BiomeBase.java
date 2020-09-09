package mrunknown404.primalrework.world.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import mrunknown404.primalrework.init.InitBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class BiomeBase extends Biome {
	
	protected final BiomeType biomeType;
	protected final BiomeModifier biomeMod;
	protected final BiomeDictionary.Type[] types;
	protected final int weight;
	protected final boolean isStrongholdBiome, isVillageBiome;
	
	/** @param name primal_ + biomeMod + _ + name */
	public BiomeBase(String name, BiomeType biomeType, BiomeModifier biomeMod, BiomeDictionary.Type[] types, int weight, boolean isStrongholdBiome, boolean isVillageBiome) {
		super(new BiomeProperties(WordUtils.capitalizeFully("primal_" + biomeMod.toString() + "_" + name, '_')).setBaseHeight(getBaseHeight(biomeMod))
				.setHeightVariation(getHeightVariation(biomeMod)));
		setRegistryName("primal_" + biomeMod + "_" + name);
		
		this.biomeType = biomeType;
		this.biomeMod = biomeMod;
		this.types = getBiomeTypes(types, biomeMod);
		this.weight = weight;
		this.isStrongholdBiome = isStrongholdBiome;
		this.isVillageBiome = isVillageBiome;
		
		InitBiomes.BIOMES.add(this);
	}
	
	protected static BiomeDictionary.Type[] getBiomeTypes(BiomeDictionary.Type[] types, BiomeModifier biomeMod) {
		List<BiomeDictionary.Type> t = new ArrayList<BiomeDictionary.Type>();
		t.addAll(Arrays.asList(types));
		
		switch (biomeMod) {
			case FLAT:
				t.add(BiomeDictionary.Type.PLAINS);
				break;
			case HILLY:
				t.add(BiomeDictionary.Type.HILLS);
				break;
			case MOUNTAINOUS:
				t.add(BiomeDictionary.Type.MOUNTAIN);
				break;
			default: break;
		}
		
		return t.toArray(new BiomeDictionary.Type[0]);
	}
	
	protected static float getBaseHeight(BiomeModifier biomeMod) {
		switch (biomeMod) {
			case FLAT:
				return 0.005f;
			case MODERATE:
				return 0.1f;
			case HILLY:
				return 1;
			case MOUNTAINOUS:
				return 1.5f;
		}
		
		return 0f;
	}
	
	protected static float getHeightVariation(BiomeModifier biomeMod) {
		switch (biomeMod) {
			case FLAT:
				return 0.05f;
			case MODERATE:
				return 0.2f;
			case HILLY:
				return 0.3f;
			case MOUNTAINOUS:
				return 0.5f;
		}
		
		return 0f;
	}
	
	public BiomeType getBiomeType() {
		return biomeType;
	}
	
	public Type[] getBiomeTypes() {
		return types;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public boolean isStrongholdBiome() {
		return isStrongholdBiome;
	}
	
	public boolean isVillageBiome() {
		return isVillageBiome;
	}
	
	public enum BiomeModifier {
		FLAT,
		MODERATE,
		HILLY,
		MOUNTAINOUS;
		
		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}
}
