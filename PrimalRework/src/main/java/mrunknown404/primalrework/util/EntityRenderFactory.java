package mrunknown404.primalrework.util;

import mrunknown404.primalrework.entity.render.RenderBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityRenderFactory implements IRenderFactory<EntityLiving> {

	private final RenderBase<? super EntityLiving> render;
	
	public EntityRenderFactory(RenderBase<? super EntityLiving> render) {
		this.render = render;
	}
	
	@Override
	public Render<? super EntityLiving> createRenderFor(RenderManager manager) {
		return render.createNew(manager);
	}
}
