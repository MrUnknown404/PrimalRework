package mrunknown404.primalrework.registries;

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

public class PRBiomes {
	public static final RegistryObject<Biome> FOREST = PRRegistry.register(
			new PRBiome("forest", () -> PRConfiguredSurfaceBuilders.GRASS, 100, BiomeType.WARM, Category.PLAINS, 0, 0.1f, 1, 1),
			withDefaultGrassyFeatures(() -> PRConfiguredFeatures.OAK_COMMON, () -> PRConfiguredFeatures.BIG_OAK_RARE, () -> PRConfiguredFeatures.BIRCH_RARE));
	public static final RegistryObject<Biome> SUNKEN_FOREST = PRRegistry.register(
			new PRBiome("sunken_forest", () -> PRConfiguredSurfaceBuilders.GRASS, 20, BiomeType.WARM, Category.SWAMP, -0.125f, 0.1f, 1, 1, ColorH.rgba2Int(60, 80, 100),
					ColorH.rgba2Int(20, 30, 40)),
			withDefaultGrassyFeatures(() -> PRConfiguredFeatures.SUNKEN_OAK_COMMON, () -> PRConfiguredFeatures.SUNKEN_BIG_OAK_RARE, () -> PRConfiguredFeatures.SUNKEN_BIRCH_RARE));
	
	@SafeVarargs
	public static List<Supplier<ConfiguredFeature<?, ?>>> withDefaultGrassyFeatures(Supplier<ConfiguredFeature<?, ?>>... features) {
		List<Supplier<ConfiguredFeature<?, ?>>> list = new ArrayList<Supplier<ConfiguredFeature<?, ?>>>();
		list.add(() -> PRConfiguredFeatures.TALL_GRASS);
		for (Supplier<ConfiguredFeature<?, ?>> f : features) {
			list.add(f);
		}
		return list;
	}
}
