package mrunknown404.primalrework.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.Biome.TemperatureModifier;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeAmbience.GrassColorModifier;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class PRBiome {
	public final Biome biome;
	public final BiomeType biomeType;
	public final String name;
	public final int weight;
	
	public PRBiome(String name, int weight, BiomeType biomeType, Category category, float depth, float scale, float downfall, float temp, TemperatureModifier tempMod,
			RainType rainType) {
		this.name = name;
		this.weight = weight;
		this.biomeType = biomeType;
		
		Biome.Builder b = new Biome.Builder();
		b.biomeCategory(category);
		b.depth(depth);
		b.scale(scale);
		b.downfall(downfall);
		b.temperature(temp);
		b.temperatureAdjustment(tempMod);
		b.precipitation(rainType);
		
		BiomeGenerationSettings bgs = BiomeGenerationSettings.EMPTY;
		MobSpawnInfo msi = new MobSpawnInfo.Builder().setPlayerCanSpawn().build();
		BiomeAmbience.Builder ba = new BiomeAmbience.Builder();
		
		//ba.ambientAdditionsSound(new SoundAdditionsAmbience(SoundEvents.ANVIL_PLACE, 1));
		//ba.ambientLoopSound(SoundEvents.ANVIL_PLACE);
		//ba.ambientMoodSound(new MoodSoundAmbience(SoundEvents.ANVIL_PLACE, 1, 1, 1));
		//ba.backgroundMusic(new BackgroundMusicSelector(SoundEvents.ANVIL_PLACE, 1, 1, true));
		//ba.ambientParticle(new ParticleEffectAmbience(p_i231629_1_, p_i231629_2_));
		
		ba.foliageColorOverride(0);
		ba.grassColorModifier(GrassColorModifier.NONE);
		ba.grassColorOverride(0);
		ba.skyColor(0);
		ba.fogColor(0);
		ba.waterColor(0);
		ba.waterFogColor(0);
		
		b.generationSettings(bgs);
		b.mobSpawnSettings(msi);
		b.specialEffects(ba.build());
		this.biome = b.build();
	}
	
	public PRBiome(String name, int weight, BiomeType biomeType, Category category, float depth, float scale, float downfall, float temp) {
		this(name, weight, biomeType, category, depth, scale, downfall, temp, TemperatureModifier.NONE, RainType.RAIN);
	}
}
