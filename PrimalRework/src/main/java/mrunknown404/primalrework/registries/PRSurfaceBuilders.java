package mrunknown404.primalrework.registries;

import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;

public class PRSurfaceBuilders {
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DEFAULT = PRRegistry.surfaceBuilder("pr_default",
			() -> new DefaultSurfaceBuilder(SurfaceBuilderConfig.CODEC));
}
