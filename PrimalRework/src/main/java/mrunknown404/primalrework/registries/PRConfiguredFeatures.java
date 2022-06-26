package mrunknown404.primalrework.registries;

import java.util.OptionalInt;

import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig.Builder;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features.Placements;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class PRConfiguredFeatures {
	private static final BlockState S_OAK_LOG = PRBlocks.OAK_LOG.get().defaultBlockState();
	private static final BlockState S_BIRCH_LOG = PRBlocks.BIRCH_LOG.get().defaultBlockState();
	private static final BlockState S_OAK_LEAVES = PRBlocks.OAK_LEAVES.get().defaultBlockState();
	private static final BlockState S_BIRCH_LEAVES = PRBlocks.BIRCH_LEAVES.get().defaultBlockState();
	private static final BlockState S_TALL_GRASS = PRBlocks.TALL_GRASS.get().defaultBlockState();
	
	private static final Builder B_OAK = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(S_OAK_LOG), new SimpleBlockStateProvider(S_OAK_LEAVES),
			new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines();
	private static final Builder B_BIRCH = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(S_BIRCH_LOG), new SimpleBlockStateProvider(S_BIRCH_LEAVES),
			new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(6, 2, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines();
	private static final Builder B_BIG_OAK = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(S_OAK_LOG), new SimpleBlockStateProvider(S_OAK_LEAVES),
			new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(4), 4), new FancyTrunkPlacer(5, 10, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))
					.ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING);
	
	private static final BaseTreeFeatureConfig C_OAK = B_OAK.build();
	private static final BaseTreeFeatureConfig C_BIG_OAK = B_BIG_OAK.build();
	private static final BaseTreeFeatureConfig C_BIRCH = B_BIRCH.build();
	private static final BaseTreeFeatureConfig C_SUNKEN_OAK = B_OAK.maxWaterDepth(1).build();
	private static final BaseTreeFeatureConfig C_SUNKEN_BIG_OAK = B_BIG_OAK.maxWaterDepth(2).build();
	private static final BaseTreeFeatureConfig C_SUNKEN_BIRCH = B_BIRCH.maxWaterDepth(2).build();
	private static final BlockClusterFeatureConfig C_TALL_GRASS = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(S_TALL_GRASS), SimpleBlockPlacer.INSTANCE)
			.tries(32).build();
	
	public static final ConfiguredFeature<?, ?> OAK_COMMON = register("oak_common",
			PRFeatures.TREE.get().configured(C_OAK).decorated(Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(8, 0.2f, 1))));
	public static final ConfiguredFeature<?, ?> BIG_OAK_RARE = register("big_oak_rare", PRFeatures.TREE.get().configured(C_BIG_OAK).decorated(Placements.HEIGHTMAP_SQUARE)
			.decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.2f, 1))));
	public static final ConfiguredFeature<?, ?> BIRCH_RARE = register("birch_rare",
			PRFeatures.TREE.get().configured(C_BIRCH).decorated(Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.2f, 1))));
	public static final ConfiguredFeature<?, ?> SUNKEN_OAK_COMMON = register("sunken_oak_common", PRFeatures.TREE.get().configured(C_SUNKEN_OAK)
			.decorated(Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(8, 0.2f, 1))));
	public static final ConfiguredFeature<?, ?> SUNKEN_BIG_OAK_RARE = register("sunken_big_oak_rare", PRFeatures.TREE.get().configured(C_SUNKEN_BIG_OAK)
			.decorated(Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.2f, 1))));
	public static final ConfiguredFeature<?, ?> SUNKEN_BIRCH_RARE = register("sunken_birch_rare", PRFeatures.TREE.get().configured(C_SUNKEN_BIRCH)
			.decorated(Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.2f, 1))));
	public static final ConfiguredFeature<?, ?> GROUND_SLABS = register("ground_slabs",
			PRFeatures.GROUND_SLABS.get().configured(new NoFeatureConfig()).decorated(Placements.HEIGHTMAP_WORLD_SURFACE));
	public static final ConfiguredFeature<?, ?> GROUND_ITEMS = register("ground_items", PRFeatures.GROUND_ITEMS.get().configured(new NoFeatureConfig())
			.decorated(Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(4, 0.5f, 2))));
	public static final ConfiguredFeature<?, ?> TALL_GRASS = register("tall_grass",
			Feature.RANDOM_PATCH.configured(C_TALL_GRASS).decorated(Placements.HEIGHTMAP_DOUBLE_SQUARE).count(10));
	
	private static <FC extends IFeatureConfig> ConfiguredFeature<?, ?> register(String name, ConfiguredFeature<FC, ?> feature) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "pr_" + name, feature);
	}
}
