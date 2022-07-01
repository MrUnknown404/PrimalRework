package mrunknown404.primalrework.init;

import mrunknown404.primalrework.world.gen.feature.FeatureGroundItems;
import mrunknown404.primalrework.world.gen.feature.FeatureGroundSlabs;
import mrunknown404.primalrework.world.gen.feature.FeaturePrimalTree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;

public class InitFeatures {
	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> TREE = InitRegistry.feature("tree", () -> new FeaturePrimalTree());
	public static final RegistryObject<Feature<NoFeatureConfig>> GROUND_SLABS = InitRegistry.feature("ground_slabs", () -> new FeatureGroundSlabs());
	public static final RegistryObject<Feature<NoFeatureConfig>> GROUND_ITEMS = InitRegistry.feature("ground_items", () -> new FeatureGroundItems());
}
