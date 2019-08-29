package mrunknown404.primalrework.proxy;

import mrunknown404.primalrework.handlers.EntityRenderHandler;
import mrunknown404.primalrework.items.ItemBase;
import net.minecraft.item.Item;

public abstract class CommonProxy {
	public abstract void registerItemRenderer(Item item, int meta, String id);
	public abstract void registerEntityRenders();
	public abstract void registerSounds();
}
