package mrunknown404.primalrework.init;

import net.minecraft.block.BlockState;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class InitConfiguredSurfaceBuilders {
	private static final BlockState S_GRASS = InitBlocks.GRASS_BLOCK.get().defaultBlockState();
	private static final BlockState S_SAND = InitBlocks.SAND.get().defaultBlockState();
	private static final BlockState S_DIRT = InitBlocks.DIRT.get().defaultBlockState();
	
	private static final SurfaceBuilderConfig C_GRASS = new SurfaceBuilderConfig(S_GRASS, S_DIRT, S_DIRT);
	private static final SurfaceBuilderConfig C_SAND = new SurfaceBuilderConfig(S_SAND, S_SAND, S_SAND);
	
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> GRASS = register("grass", InitSurfaceBuilders.DEFAULT.get().configured(C_GRASS));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> SAND = register("sand", InitSurfaceBuilders.DEFAULT.get().configured(C_SAND));
	
	private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> conf) {
		return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, "pr_" + name, conf);
	}
}
