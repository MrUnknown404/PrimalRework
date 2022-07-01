package mrunknown404.primalrework.init;

import mrunknown404.primalrework.world.WorldTypePrimal;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.fml.RegistryObject;

public class InitWorld {
	public static final RegistryObject<ForgeWorldType> PRIMAL_WORLD = InitRegistry.worldType("primal_world", WorldTypePrimal::new);
}
