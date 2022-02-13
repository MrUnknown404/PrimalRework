package mrunknown404.primalrework.registries;

import mrunknown404.primalrework.world.WorldTypePrimal;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.fml.RegistryObject;

public class PRWorld {
	public static final RegistryObject<ForgeWorldType> PRIMAL_WORLD = PRRegistry.worldType("primal_world", WorldTypePrimal::new);
}
