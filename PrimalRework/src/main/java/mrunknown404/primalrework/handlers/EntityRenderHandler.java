package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.entity.render.RenderBase;
import mrunknown404.primalrework.init.ModEntities;
import mrunknown404.primalrework.util.EntityInfo;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityRenderHandler {
	public static void registerEntityRenderers() {
		for (EntityInfo info : ModEntities.ENTITIES) {
			RenderingRegistry.registerEntityRenderingHandler(info.clazz, new EntityRenderFactory(info.render));
		}
	}
	
	public static class EntityRenderFactory implements IRenderFactory<EntityLiving> {
		private final RenderBase<? super EntityLiving> render;
		
		public EntityRenderFactory(RenderBase<? super EntityLiving> render) {
			this.render = render;
		}
		
		@Override
		public Render<? super EntityLiving> createRenderFor(RenderManager manager) {
			return render.createNew(manager);
		}
	}
}
