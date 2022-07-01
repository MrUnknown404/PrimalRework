package mrunknown404.primalrework.init;

import net.minecraft.block.BlockState;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class InitConfiguredSurfaceBuilders {
	private static final BlockState GRASS_BLOCK = InitBlocks.GRASS_BLOCK.get().defaultBlockState();
	private static final BlockState DIRT = InitBlocks.DIRT.get().defaultBlockState();
	
	private static final SurfaceBuilderConfig CONFIG_GRASS = new SurfaceBuilderConfig(GRASS_BLOCK, DIRT, DIRT);
	
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> GRASS = register("grass", InitSurfaceBuilders.DEFAULT.get().configured(CONFIG_GRASS));
	
	private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> conf) {
		return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, "pr_" + name, conf);
	}
}
