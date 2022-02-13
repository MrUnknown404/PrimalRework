package mrunknown404.primalrework.registries;

import mrunknown404.primalrework.tileentities.TEICampFire;
import mrunknown404.primalrework.tileentities.TEIPrimalCraftingTable;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class PRTileEntities {
	public static final RegistryObject<TileEntityType<TEICampFire>> CAMPFIRE = PRRegistry.tileEntity("campfire", TEICampFire::new, PRBlocks.CAMPFIRE);
	public static final RegistryObject<TileEntityType<TEIPrimalCraftingTable>> PRIMAL_CRAFTING_TABLE = PRRegistry.tileEntity("primal_crafting_table", TEIPrimalCraftingTable::new,
			PRBlocks.PRIMAL_CRAFTING_TABLE);
}
