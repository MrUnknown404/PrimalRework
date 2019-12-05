package mrunknown404.primalrework.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBase<T extends EntityLiving> extends RenderLiving<T> {
	
	private final ModelBase model;
	private final ResourceLocation resourceLoc;
	private final float shadow;
	
	protected RenderBase(RenderManager manager, ModelBase model, ResourceLocation resourceLoc, float shadow) {
		super(manager, model, shadow);
		this.model = model;
		this.shadow = shadow;
		this.resourceLoc = resourceLoc;
	}
	
	public RenderBase<T> createNew(RenderManager manager) {
		return new RenderBase<T>(manager, model, resourceLoc, shadow);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return resourceLoc;
	}
}
