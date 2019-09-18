package mrunknown404.primalrework.util.proxy;

import mrunknown404.primalrework.client.render.TileEntityCraftingStumpRenderer;
import mrunknown404.primalrework.handlers.EntityRenderHandler;
import mrunknown404.primalrework.handlers.SoundHandler;
import mrunknown404.primalrework.tileentity.TileEntityCraftingStump;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void registerRenders() {
		EntityRenderHandler.registerEntityRenderers();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraftingStump.class, new TileEntityCraftingStumpRenderer());
	}
	
	@Override
	public void registerSounds() {
		SoundHandler.registerSounds();
	}
}
