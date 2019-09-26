package mrunknown404.primalrework.entity.render;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.entity.model.ModelPigeon;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderPigeon extends RenderBase<EntityLiving> {

	public RenderPigeon() {
		super(null, new ModelPigeon(), new ResourceLocation(Main.MOD_ID, "textures/entity/pigeon.png"), 0.25f);
	}
	
	@Override
	protected void applyRotations(EntityLiving entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
}
