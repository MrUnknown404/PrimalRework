package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import mrunknown404.primalrework.utils.helpers.ColorH;
import mrunknown404.primalrework.world.biome.PRBiome;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.RegistryObject;

public class InitBiomes {
	public static final RegistryObject<Biome> FOREST = InitRegistry.biome(
			new PRBiome("forest", () -> InitConfiguredSurfaceBuilders.GRASS, 100, BiomeType.WARM, Category.PLAINS, 0, 0.1f, 1, 1),
			withDefaultGrassyFeatures(() -> InitConfiguredFeatures.OAK_COMMON, () -> InitConfiguredFeatures.BIG_OAK_RARE, () -> InitConfiguredFeatures.BIRCH_RARE));
	public static final RegistryObject<Biome> SUNKEN_FOREST = InitRegistry.biome(
			new PRBiome("sunken_forest", () -> InitConfiguredSurfaceBuilders.GRASS, 20, BiomeType.WARM, Category.SWAMP, -0.2f, 0f, 1, 1, ColorH.rgba2Int(60, 80, 100),
					ColorH.rgba2Int(20, 30, 40)),
			withDefaultGrassyFeatures(() -> InitConfiguredFeatures.SUNKEN_OAK_COMMON, () -> InitConfiguredFeatures.SUNKEN_BIG_OAK_RARE,
					() -> InitConfiguredFeatures.SUNKEN_BIRCH_RARE));
	public static final RegistryObject<Biome> BEACH = InitRegistry.biome(
			new PRBiome("beach", () -> InitConfiguredSurfaceBuilders.SAND, 60, BiomeType.WARM, Category.BEACH, -0.3f, 0f, 1, 1),
			withDefaultGrassyFeatures(() -> InitConfiguredFeatures.OAK_COMMON, () -> InitConfiguredFeatures.BIG_OAK_RARE, () -> InitConfiguredFeatures.BIRCH_RARE));
	
	@SafeVarargs
	public static List<Supplier<ConfiguredFeature<?, ?>>> withDefaultGrassyFeatures(Supplier<ConfiguredFeature<?, ?>>... features) {
		List<Supplier<ConfiguredFeature<?, ?>>> list = new ArrayList<Supplier<ConfiguredFeature<?, ?>>>();
		list.add(() -> InitConfiguredFeatures.SHORT_GRASS);
		list.add(() -> InitConfiguredFeatures.MEDIUM_GRASS);
		list.add(() -> InitConfiguredFeatures.TALL_GRASS);
		for (Supplier<ConfiguredFeature<?, ?>> f : features) {
			list.add(f);
		}
		return list;
	}
}
