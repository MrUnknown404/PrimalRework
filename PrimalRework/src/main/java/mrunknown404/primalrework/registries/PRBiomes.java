package mrunknown404.primalrework.registries;

import mrunknown404.primalrework.utils.helpers.ColorH;
import mrunknown404.primalrework.world.biome.PRBiome;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.RegistryObject;

public class PRBiomes {
	public static final RegistryObject<Biome> FOREST = PRRegistry.register(
			new PRBiome("forest", () -> PRConfiguredSurfaceBuilders.GRASS, 100, BiomeType.WARM, Category.PLAINS, 0, 0.1f, 1, 1), () -> PRConfiguredFeatures.OAK_COMMON,
			() -> PRConfiguredFeatures.BIG_OAK_RARE, () -> PRConfiguredFeatures.BIRCH_RARE);
	public static final RegistryObject<Biome> TEST1 = PRRegistry.register(
			new PRBiome("sunken_forest", () -> PRConfiguredSurfaceBuilders.GRASS, 20, BiomeType.WARM, Category.SWAMP, -0.125f, 0.1f, 1, 1, ColorH.rgba2Int(60, 80, 100),
					ColorH.rgba2Int(20, 30, 40)),
			() -> PRConfiguredFeatures.SUNKEN_OAK_COMMON, () -> PRConfiguredFeatures.SUNKEN_BIG_OAK_RARE, () -> PRConfiguredFeatures.SUNKEN_BIRCH_RARE);
}
