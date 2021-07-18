package mrunknown404.primalrework.registries;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.world.WorldTypePrimal;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.fml.RegistryObject;

public class PRWorld {
	public static final RegistryObject<ForgeWorldType> PRIMAL_WORLD = PRRegistry.WORLD_TYPES.register("primal_world", WorldTypePrimal::new);
	
	public static final RegistryKey<DimensionType> PRIMAL_WORLD_DIMENSION_TYPE = RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,
			new ResourceLocation(PrimalRework.MOD_ID, "dimension/primal_world"));
	public static final RegistryKey<Dimension> PRIMAL_WORLD_DIMENSION = RegistryKey.create(Registry.LEVEL_STEM_REGISTRY,
			new ResourceLocation(PrimalRework.MOD_ID, "primal_world"));
	
	//@formatter:off
	public static void register() { }
	//@formatter:on
}
