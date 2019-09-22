package mrunknown404.primalrework.util.proxy;

import mrunknown404.primalrework.client.render.TileEntityCraftingStumpRenderer;
import mrunknown404.primalrework.client.render.TileEntityPrimalEnchantingRenderer;
import mrunknown404.primalrework.init.ModEntities;
import mrunknown404.primalrework.init.ModSoundEvents;
import mrunknown404.primalrework.tileentity.TileEntityCraftingStump;
import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void registerRenders() {
		ModEntities.registerEntityRenderers();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraftingStump.class, new TileEntityCraftingStumpRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPrimalEnchanting.class, new TileEntityPrimalEnchantingRenderer());
	}
	
	@Override
	public void registerSounds() {
		ForgeRegistries.SOUND_EVENTS.registerAll(ModSoundEvents.SOUNDS.toArray(new SoundEvent[0]));
	}
}
