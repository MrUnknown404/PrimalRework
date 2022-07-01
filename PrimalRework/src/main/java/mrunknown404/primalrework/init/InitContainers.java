package mrunknown404.primalrework.init;

import mrunknown404.primalrework.inventory.container.ContainerCampFire;
import mrunknown404.primalrework.inventory.container.ContainerInventory;
import mrunknown404.primalrework.inventory.container.ContainerPrimalCraftingTable;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

public class InitContainers {
	public static final RegistryObject<ContainerType<ContainerCampFire>> CAMPFIRE = InitRegistry.container("campfire",
			() -> IForgeContainerType.create((windowID, inv, data) -> new ContainerCampFire(windowID, inv)));
	public static final RegistryObject<ContainerType<ContainerPrimalCraftingTable>> PRIMAL_CRAFTING_TABLE = InitRegistry.container("primal_crafting_table",
			() -> IForgeContainerType.create((windowID, inv, data) -> new ContainerPrimalCraftingTable(windowID, inv)));
	public static final RegistryObject<ContainerType<ContainerInventory>> INVENTORY = InitRegistry.container("inventory",
			() -> IForgeContainerType.create((windowID, inv, data) -> new ContainerInventory(windowID, inv)));
}
