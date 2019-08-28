package mrunknown404.primalrework.entity.render;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.entity.EntityPigeon;
import mrunknown404.primalrework.entity.model.ModelPigeon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPigeon extends RenderBase<EntityPigeon> {

	public RenderPigeon(RenderManager manager) {
		super(manager, new ModelPigeon(), new ResourceLocation(Main.MOD_ID, "textures/entity/pigeon.png"), 0.25f);
		
	}
	
	@Override
	protected void applyRotations(EntityPigeon entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
}
