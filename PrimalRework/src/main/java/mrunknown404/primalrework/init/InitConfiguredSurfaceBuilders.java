package mrunknown404.primalrework.init;

import net.minecraft.block.BlockState;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class InitConfiguredSurfaceBuilders {
	private static final BlockState S_GRASS = InitBlocks.GRASS_BLOCK.get().defaultBlockState();
	private static final BlockState S_SAND = InitBlocks.SAND.get().defaultBlockState();
	private static final BlockState S_GRAVEL = InitBlocks.GRAVEL.get().defaultBlockState();
	private static final BlockState S_DIRT = InitBlocks.DIRT.get().defaultBlockState();
	
	private static final SurfaceBuilderConfig C_FOREST = new SurfaceBuilderConfig(S_GRASS, S_DIRT, S_DIRT);
	private static final SurfaceBuilderConfig C_DESERT = new SurfaceBuilderConfig(S_SAND, S_SAND, S_SAND);
	private static final SurfaceBuilderConfig C_GRAVEL = new SurfaceBuilderConfig(S_SAND, S_SAND, S_GRAVEL);
	
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> FOREST = register("forest", InitSurfaceBuilders.DEFAULT.get().configured(C_FOREST));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> DESERT = register("desert", InitSurfaceBuilders.DEFAULT.get().configured(C_DESERT));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> OCEAN = register("ocean", InitSurfaceBuilders.DEFAULT.get().configured(C_GRAVEL));
	
	private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> conf) {
		return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, "pr_" + name, conf);
	}
}
