package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import mrunknown404.primalrework.utils.helpers.ColorH;
import mrunknown404.primalrework.world.biome.PRBiome;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.RegistryObject;

public class InitBiomes {
	public static final RegistryObject<Biome> FOREST = InitRegistry.biome("forest",
			new PRBiome(() -> InitConfiguredSurfaceBuilders.FOREST, 100, BiomeType.WARM, Category.PLAINS, 0.1f, 0.2f, 1, 1),
			() -> FeatureMap.withDefaults().trees(InitConfiguredFeatures.OAK_COMMON, InitConfiguredFeatures.BIG_OAK_RARE, InitConfiguredFeatures.BIRCH_RARE));
	public static final RegistryObject<Biome> SUNKEN_FOREST = InitRegistry.biome("sunken_forest",
			new PRBiome(() -> InitConfiguredSurfaceBuilders.FOREST, 20, BiomeType.WARM, Category.SWAMP, -0.2f, 0f, 1, 1, ColorH.rgba2Int(60, 80, 100), ColorH.rgba2Int(20, 30, 40)),
			() -> FeatureMap.withDefaults().trees(InitConfiguredFeatures.SUNKEN_OAK_COMMON, InitConfiguredFeatures.SUNKEN_BIG_OAK_RARE, InitConfiguredFeatures.SUNKEN_BIRCH_RARE));
	public static final RegistryObject<Biome> BEACH = InitRegistry.biome("beach",
			new PRBiome(() -> InitConfiguredSurfaceBuilders.DESERT, 60, BiomeType.WARM, Category.BEACH, -0.3f, 0f, 1, 1),
			() -> FeatureMap.withDefaults().trees(InitConfiguredFeatures.OAK_COMMON, InitConfiguredFeatures.BIG_OAK_RARE, InitConfiguredFeatures.BIRCH_RARE));
	public static final RegistryObject<Biome> OCEAN = InitRegistry.biome("ocean",
			new PRBiome(() -> InitConfiguredSurfaceBuilders.OCEAN, 30, BiomeType.WARM, Category.OCEAN, -1.3f, 0.3f, 1, 1), () -> FeatureMap.withDefaults());
	
	public static class FeatureMap {
		private final Map<Decoration, List<ConfiguredFeature<?, ?>>> map = new HashMap<Decoration, List<ConfiguredFeature<?, ?>>>();
		
		public static FeatureMap withDefaults() {
			//@formatter:off
			return new FeatureMap()
					.add(Decoration.TOP_LAYER_MODIFICATION, InitConfiguredFeatures.GROUND_SLABS)
					.add(Decoration.TOP_LAYER_MODIFICATION, InitConfiguredFeatures.GROUND_ITEMS)
					.add(Decoration.TOP_LAYER_MODIFICATION, InitConfiguredFeatures.SHORT_GRASS)
					.add(Decoration.TOP_LAYER_MODIFICATION, InitConfiguredFeatures.MEDIUM_GRASS)
					.add(Decoration.TOP_LAYER_MODIFICATION, InitConfiguredFeatures.TALL_GRASS);
			//@formatter:on
		}
		
		public void addFeaturesToBiome(BiConsumer<Decoration, ConfiguredFeature<?, ?>> object) {
			map.forEach((dec, list) -> list.forEach(feat -> object.accept(dec, feat)));
		}
		
		public FeatureMap add(Decoration decoration, ConfiguredFeature<?, ?> feature) {
			map.computeIfAbsent(decoration, d -> new ArrayList<ConfiguredFeature<?, ?>>()).add(feature);
			return this;
		}
		
		public FeatureMap tree(ConfiguredFeature<?, ?> feature) {
			return add(Decoration.VEGETAL_DECORATION, feature);
		}
		
		public FeatureMap trees(ConfiguredFeature<?, ?>... features) {
			for (ConfiguredFeature<?, ?> f : features) {
				add(Decoration.VEGETAL_DECORATION, f);
			}
			return this;
		}
	}
}
