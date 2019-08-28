package mrunknown404.primalrework.util;

import java.lang.reflect.InvocationTargetException;

import mrunknown404.primalrework.entity.render.RenderBase;
import mrunknown404.primalrework.entity.render.RenderPigeon;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityRenderFactory implements IRenderFactory<EntityLiving> {

	private final RenderBase render;
	
	public EntityRenderFactory(RenderBase render) {
		this.render = render;
	}
	
	@Override
	public Render createRenderFor(RenderManager manager) {
		return render.createNew(manager);
	}
}
