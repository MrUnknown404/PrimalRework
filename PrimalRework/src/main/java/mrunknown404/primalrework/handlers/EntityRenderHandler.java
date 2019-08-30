package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.init.ModEntities;
import mrunknown404.primalrework.util.EntityInfo;
import mrunknown404.primalrework.util.EntityRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityRenderHandler {
	public static void registerEntityRenderers() {
		for (EntityInfo info : ModEntities.ENTITIES) {
			RenderingRegistry.registerEntityRenderingHandler(info.clazz, new EntityRenderFactory(info.render));
		}
	}
}
