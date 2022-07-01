package mrunknown404.primalrework.init;

import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;

public class InitSurfaceBuilders {
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DEFAULT = InitRegistry.surfaceBuilder("pr_default",
			() -> new DefaultSurfaceBuilder(SurfaceBuilderConfig.CODEC));
}
