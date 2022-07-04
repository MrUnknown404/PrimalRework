package mrunknown404.primalrework.world.biome;

import java.util.function.Supplier;

import mrunknown404.primalrework.utils.helpers.ColorH;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.Biome.TemperatureModifier;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeAmbience.GrassColorModifier;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class PRBiome {
	private static final int FOG_COLOR = ColorH.rgba2Int(192, 216, 255), SKY_COLOR = ColorH.rgba2Int(121, 166, 255);
	
	public final Biome biome;
	public final BiomeType biomeType;
	public final int weight;
	
	public PRBiome(Supplier<ConfiguredSurfaceBuilder<?>> surface, int weight, BiomeType biomeType, Category category, float depth, float scale, float downfall, float temp,
			int waterColor, int waterFogColor, TemperatureModifier tempMod, RainType rainType) {
		this.weight = weight;
		this.biomeType = biomeType;
		
		Biome.Builder b = new Biome.Builder();
		b.biomeCategory(category);
		b.depth(1 + depth);
		b.scale(scale);
		b.downfall(downfall);
		b.temperature(temp);
		b.temperatureAdjustment(tempMod);
		b.precipitation(rainType);
		
		BiomeGenerationSettings.Builder bgs = new BiomeGenerationSettings.Builder();
		MobSpawnInfo msi = new MobSpawnInfo.Builder().setPlayerCanSpawn().build();
		BiomeAmbience.Builder ba = new BiomeAmbience.Builder();
		
		bgs.surfaceBuilder(surface);
		
		//ba.ambientAdditionsSound(new SoundAdditionsAmbience(SoundEvents.ANVIL_PLACE, 1));
		//ba.ambientLoopSound(SoundEvents.ANVIL_PLACE);
		//ba.ambientMoodSound(new MoodSoundAmbience(SoundEvents.ANVIL_PLACE, 1, 1, 1));
		//ba.backgroundMusic(new BackgroundMusicSelector(SoundEvents.ANVIL_PLACE, 1, 1, true));
		//ba.ambientParticle(new ParticleEffectAmbience(p_i231629_1_, p_i231629_2_));
		
		int grassColor = ColorH.rgba2Int(121, 192, 90);
		ba.foliageColorOverride(grassColor);
		ba.grassColorModifier(GrassColorModifier.NONE);
		ba.grassColorOverride(grassColor);
		ba.skyColor(SKY_COLOR);
		ba.fogColor(FOG_COLOR);
		ba.waterColor(waterColor);
		ba.waterFogColor(waterFogColor);
		
		b.generationSettings(bgs.build());
		b.mobSpawnSettings(msi);
		b.specialEffects(ba.build());
		this.biome = b.build();
	}
	
	public PRBiome(Supplier<ConfiguredSurfaceBuilder<?>> surface, int weight, BiomeType biomeType, Category category, float depth, float scale, float downfall, float temp,
			int waterColor, int waterFogColor) {
		this(surface, weight, biomeType, category, depth, scale, downfall, temp, waterColor, waterFogColor, TemperatureModifier.NONE, RainType.RAIN);
	}
	
	public PRBiome(Supplier<ConfiguredSurfaceBuilder<?>> surface, int weight, BiomeType biomeType, Category category, float depth, float scale, float downfall, float temp) {
		this(surface, weight, biomeType, category, depth, scale, downfall, temp, ColorH.rgba2Int(63, 118, 228), ColorH.rgba2Int(5, 5, 51), TemperatureModifier.NONE, RainType.RAIN);
	}
}
