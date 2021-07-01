package mrunknown404.primalrework.init;

import java.util.function.Supplier;

import mrunknown404.primalrework.tileentities.TECampFire;
import mrunknown404.primalrework.tileentities.TEPrimalCraftingTable;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class InitTileEntities {
	public static final RegistryObject<TileEntityType<TECampFire>> CAMPFIRE = register("campfire", TECampFire::new, InitBlocks.CAMPFIRE);
	public static final RegistryObject<TileEntityType<TEPrimalCraftingTable>> PRIMAL_CRAFTING_TABLE = register("primal_crafting_table",
			TEPrimalCraftingTable::new, InitBlocks.PRIMAL_CRAFTING_TABLE);
	
	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> entity, RegistryObject<Block> block) {
		return Registry.TILE_ENTITIES.register(name, () -> TileEntityType.Builder.of(entity, block.get()).build(null));
	}
	
	//@formatter:off
	public static void register() { }
	//@formatter:on
}
