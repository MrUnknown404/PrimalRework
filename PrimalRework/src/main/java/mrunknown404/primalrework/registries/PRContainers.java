package mrunknown404.primalrework.registries;

import mrunknown404.primalrework.inventory.container.ContainerCampFire;
import mrunknown404.primalrework.inventory.container.ContainerPrimalCraftingTable;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

public class PRContainers {
	public static final RegistryObject<ContainerType<ContainerCampFire>> CAMPFIRE = register("campfire", IForgeContainerType.create((windowID, inv, data) -> {
		return new ContainerCampFire(windowID, inv);
	}));
	public static final RegistryObject<ContainerType<ContainerPrimalCraftingTable>> PRIMAL_CRAFTING_TABLE = register("primal_crafting_table",
			IForgeContainerType.create((windowID, inv, data) -> {
				return new ContainerPrimalCraftingTable(windowID, inv);
			}));
	
	private static <T extends ContainerType<?>> RegistryObject<T> register(String name, T container) {
		return PRRegistry.CONTAINERS.register(name, () -> container);
	}
	
	//@formatter:off
	static void register() { }
	//@formatter:on
}
