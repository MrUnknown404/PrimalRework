package mrunknown404.primalrework.init;

import java.util.function.Supplier;

import mrunknown404.primalrework.tileentities.TEICampFire;
import mrunknown404.primalrework.tileentities.TEIPrimalCraftingTable;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class InitTileEntities {
	public static final RegistryObject<TileEntityType<TEICampFire>> CAMPFIRE = register("campfire", TEICampFire::new, InitBlocks.CAMPFIRE);
	public static final RegistryObject<TileEntityType<TEIPrimalCraftingTable>> PRIMAL_CRAFTING_TABLE = register("primal_crafting_table",
			TEIPrimalCraftingTable::new, InitBlocks.PRIMAL_CRAFTING_TABLE);
	
	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> entity, RegistryObject<Block> block) {
		return Registry.TILE_ENTITIES.register(name, () -> TileEntityType.Builder.of(entity, block.get()).build(null));
	}
	
	//@formatter:off
	public static void register() { }
	//@formatter:on
}
