package mrunknown404.primalrework.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mrunknown404.primalrework.entity.EntityItemDrop;
import mrunknown404.primalrework.entity.EntityPigeon;
import mrunknown404.primalrework.entity.render.RenderBase;
import mrunknown404.primalrework.entity.render.RenderItemDrop;
import mrunknown404.primalrework.entity.render.RenderPigeon;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityRenderHandler {
	public static final Map<Class<? extends EntityLiving>, RenderBase<? extends EntityLiving>> ENTITY_RENDERS =
			new HashMap<Class<? extends EntityLiving>, RenderBase<? extends EntityLiving>>();
	
	public static final RenderBase<? extends EntityLiving> PIGEON = addRender(EntityPigeon.class, new RenderPigeon());
	
	public static void registerEntityRenderers() {
		for (Entry<Class<? extends EntityLiving>, RenderBase<? extends EntityLiving>> info : ENTITY_RENDERS.entrySet()) {
			RenderingRegistry.registerEntityRenderingHandler(info.getKey(), new EntityRenderFactory((RenderBase<? super EntityLiving>) info.getValue()));
		}
		
		RenderingRegistry.registerEntityRenderingHandler(EntityItemDrop.class, new RenderItemDrop.Factory());
	}
	
	private static RenderBase<? extends EntityLiving> addRender(Class<? extends EntityLiving> clazz, RenderBase<? extends EntityLiving> render) {
		ENTITY_RENDERS.put(clazz, render);
		return render;
	}
	
	private static class EntityRenderFactory implements IRenderFactory<EntityLiving> {
		private final RenderBase<? super EntityLiving> render;
		
		private EntityRenderFactory(RenderBase<? super EntityLiving> render) {
			this.render = render;
		}
		
		@Override
		public Render<? super EntityLiving> createRenderFor(RenderManager manager) {
			return render.createNew(manager);
		}
	}
}
