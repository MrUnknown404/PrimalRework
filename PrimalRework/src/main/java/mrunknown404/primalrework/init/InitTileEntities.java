package mrunknown404.primalrework.init;

import mrunknown404.primalrework.tileentities.TEICampFire;
import mrunknown404.primalrework.tileentities.TEIPrimalCraftingTable;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class InitTileEntities {
	public static final RegistryObject<TileEntityType<TEICampFire>> CAMPFIRE = InitRegistry.tileEntity("campfire", TEICampFire::new, InitBlocks.CAMPFIRE);
	public static final RegistryObject<TileEntityType<TEIPrimalCraftingTable>> PRIMAL_CRAFTING_TABLE = InitRegistry.tileEntity("primal_crafting_table", TEIPrimalCraftingTable::new,
			InitBlocks.PRIMAL_CRAFTING_TABLE);
}
