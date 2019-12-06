package mrunknown404.primalrework.world.biome;

import mrunknown404.primalrework.init.ModBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class BiomeBase extends Biome {
	
	protected final BiomeType biomeType;
	protected final BiomeDictionary.Type[] types;
	protected final int weight;
	protected final boolean isStrongholdBiome, isVillageBiome;
	
	public BiomeBase(String name, BiomeType biomeType, BiomeDictionary.Type[] types, int weight, boolean isStrongholdBiome, boolean isVillageBiome) {
		super(new BiomeProperties(name));
		setRegistryName(name);
		
		this.biomeType = biomeType;
		this.types = types;
		this.weight = weight;
		this.isStrongholdBiome = isStrongholdBiome;
		this.isVillageBiome = isVillageBiome;
		
		ModBiomes.BIOMES.add(this);
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
}
