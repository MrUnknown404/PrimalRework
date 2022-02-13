package mrunknown404.primalrework.registries;

import mrunknown404.primalrework.world.gen.feature.FeatureGroundItems;
import mrunknown404.primalrework.world.gen.feature.FeatureGroundSlabs;
import mrunknown404.primalrework.world.gen.feature.FeaturePrimalTree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;

public class PRFeatures {
	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> TREE = PRRegistry.feature("tree", () -> new FeaturePrimalTree());
	public static final RegistryObject<Feature<NoFeatureConfig>> GROUND_SLABS = PRRegistry.feature("ground_slabs", () -> new FeatureGroundSlabs());
	public static final RegistryObject<Feature<NoFeatureConfig>> GROUND_ITEMS = PRRegistry.feature("ground_items", () -> new FeatureGroundItems());
}
