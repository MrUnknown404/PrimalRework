package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.entity.EntityPigeon;
import mrunknown404.primalrework.entity.render.RenderBase;
import mrunknown404.primalrework.entity.render.RenderPigeon;
import mrunknown404.primalrework.util.EntityInfo;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModEntities {
	public static final List<EntityInfo> ENTITIES = new ArrayList<EntityInfo>();
	
	public static final EntityInfo PIGEON = new EntityInfo("pigeon", EntityPigeon.class, 100, 32, 3289650, 6579300, new RenderPigeon(null));
	
	public static void registerEntityRenderers() {
		for (EntityInfo info : ModEntities.ENTITIES) {
			RenderingRegistry.registerEntityRenderingHandler(info.clazz, new EntityRenderFactory(info.render));
		}
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
