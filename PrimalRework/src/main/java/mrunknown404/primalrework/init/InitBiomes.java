package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.world.biome.BiomeBase.BiomeModifier;
import mrunknown404.primalrework.world.biome.BiomePrimalForest;
import net.minecraft.world.biome.Biome;

public class InitBiomes {
	public static final List<Biome> BIOMES = new ArrayList<Biome>();
	
	public static final Biome PRIMAL_FLAT_FOREST =        new BiomePrimalForest(BiomeModifier.FLAT,        BiomePrimalForest.ForestType.NORMAL, 8, 10);
	public static final Biome PRIMAL_MODERATE_FOREST =    new BiomePrimalForest(BiomeModifier.MODERATE,    BiomePrimalForest.ForestType.NORMAL, 14, 10);
	public static final Biome PRIMAL_HILLY_FOREST =       new BiomePrimalForest(BiomeModifier.HILLY,       BiomePrimalForest.ForestType.NORMAL, 12, 8);
	public static final Biome PRIMAL_MOUNTAINOUS_FOREST = new BiomePrimalForest(BiomeModifier.MOUNTAINOUS, BiomePrimalForest.ForestType.NORMAL, 10, 6);
	
	public static final Biome PRIMAL_FLAT_BIRCH_FOREST        = new BiomePrimalForest(BiomeModifier.FLAT,        BiomePrimalForest.ForestType.BIRCH, 6, 8);
	public static final Biome PRIMAL_MODERATE_BIRCH_FOREST    = new BiomePrimalForest(BiomeModifier.MODERATE,    BiomePrimalForest.ForestType.BIRCH, 10, 8);
	public static final Biome PRIMAL_HILLY_BIRCH_FOREST       = new BiomePrimalForest(BiomeModifier.HILLY,       BiomePrimalForest.ForestType.BIRCH, 8, 6);
	public static final Biome PRIMAL_MOUNTAINOUS_BIRCH_FOREST = new BiomePrimalForest(BiomeModifier.MOUNTAINOUS, BiomePrimalForest.ForestType.BIRCH, 6, 4);
	
	public static final Biome PRIMAL_FLAT_MUSHROOM_FOREST =        new BiomePrimalForest(BiomeModifier.FLAT,        BiomePrimalForest.ForestType.MUSHROOM, 4, 14);
	public static final Biome PRIMAL_MODERATE_MUSHROOM_FOREST =    new BiomePrimalForest(BiomeModifier.MODERATE,    BiomePrimalForest.ForestType.MUSHROOM, 8, 14);
	public static final Biome PRIMAL_HILLY_MUSHROOM_FOREST =       new BiomePrimalForest(BiomeModifier.HILLY,       BiomePrimalForest.ForestType.MUSHROOM, 6, 12);
	public static final Biome PRIMAL_MOUNTAINOUS_MUSHROOM_FOREST = new BiomePrimalForest(BiomeModifier.MOUNTAINOUS, BiomePrimalForest.ForestType.MUSHROOM, 4, 10);
}
