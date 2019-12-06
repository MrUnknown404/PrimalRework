package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.world.biome.BiomePrimalForest;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class ModBiomes {
	public static final List<Biome> BIOMES = new ArrayList<Biome>();
	
	public static final Biome PRIMAL_FOREST = new BiomePrimalForest("primal_forest", BiomeType.WARM, BiomePrimalForest.ForestType.NORMAL, 12, 10);
	public static final Biome PRIMAL_BIRCH_FOREST = new BiomePrimalForest("primal_birch_forest", BiomeType.WARM, BiomePrimalForest.ForestType.BIRCH, 8, 8);
	public static final Biome PRIMAL_MUSHROOM_FOREST = new BiomePrimalForest("primal_mushroom_forest", BiomeType.WARM, BiomePrimalForest.ForestType.MUSHROOM, 6, 6);
}
