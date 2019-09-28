package mrunknown404.primalrework.util.proxy;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.handlers.RegistryHandler;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.tileentity.TileEntityCraftingStump;
import mrunknown404.primalrework.tileentity.TileEntityDryingTable;
import mrunknown404.primalrework.tileentity.TileEntityFirePit;
import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) {}
	public void registerRenders() {}
	public void registerColors() {}
	public void registerSounds() {}
	
	public final void setupRecipes() {
		ModRecipes.removeRecipes();
		ModRecipes.addRecipes();
	}
	
	public final void registerEntities() {
		RegistryHandler.registerEntities();
		
		GameRegistry.registerTileEntity(TileEntityFirePit.class, new ResourceLocation(Main.MOD_ID, "fire_pit"));
		GameRegistry.registerTileEntity(TileEntityCraftingStump.class, new ResourceLocation(Main.MOD_ID, "crafting_stump"));
		GameRegistry.registerTileEntity(TileEntityDryingTable.class, new ResourceLocation(Main.MOD_ID, "drying_table"));
		GameRegistry.registerTileEntity(TileEntityPrimalEnchanting.class, new ResourceLocation(Main.MOD_ID, "primal_enchanting_table"));
	}
}
