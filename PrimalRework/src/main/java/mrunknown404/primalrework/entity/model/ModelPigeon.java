package mrunknown404.primalrework.entity.model;

import mrunknown404.primalrework.entity.EntityPigeon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPigeon extends ModelBase {
	
	private final ModelRenderer LeftLeg;
	private final ModelRenderer RightLeg;
	private final ModelRenderer Body;
	private final ModelRenderer TailRight;
	private final ModelRenderer TailMiddle;
	private final ModelRenderer TailLeft;
	private final ModelRenderer RightWing;
	private final ModelRenderer LeftWing;
	private final ModelRenderer Head;
	
	public ModelPigeon() {
		textureWidth = 32;
		textureHeight = 32;
		
		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(0.0F, 21.5F, 0.0F);
		LeftLeg.cubeList.add(new ModelBox(LeftLeg, 0, 7, 0.0F, -0.5F, 0.0F, 1, 3, 0, 0.0F, false));
		LeftLeg.cubeList.add(new ModelBox(LeftLeg, 2, 2, 0.0F, 2.5F, -1.0F, 1, 0, 1, 0.0F, false));
		
		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(0.0F, 21.5F, 0.0F);
		RightLeg.cubeList.add(new ModelBox(RightLeg, 2, 7, -2.0F, -0.5F, 0.0F, 1, 3, 0, 0.0F, false));
		RightLeg.cubeList.add(new ModelBox(RightLeg, 2, 0, -2.0F, 2.5F, -1.0F, 1, 0, 1, 0.0F, false));
		
		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Body, -0.6109F, 0.0F, 0.0F);
		Body.cubeList.add(new ModelBox(Body, 0, 0, -2.0F, -4.0F, -5.0F, 3, 2, 5, 0.0F, false));
		
		TailRight = new ModelRenderer(this);
		TailRight.setRotationPoint(-0.5F, 21.5F, 2.5F);
		setRotationAngle(TailRight, -0.0873F, -0.2618F, 0.0F);
		TailRight.cubeList.add(new ModelBox(TailRight, 14, 10, -1.0F, -0.25F, -0.98F, 1, 1, 3, 0.0F, false));
		
		TailMiddle = new ModelRenderer(this);
		TailMiddle.setRotationPoint(-0.5F, 21.5F, 2.5F);
		setRotationAngle(TailMiddle, -0.0873F, 0.0F, 0.0F);
		TailMiddle.cubeList.add(new ModelBox(TailMiddle, 0, 14, -0.5F, -0.3F, -0.75F, 1, 1, 3, 0.0F, false));
		
		TailLeft = new ModelRenderer(this);
		TailLeft.setRotationPoint(-0.5F, 21.5F, 2.5F);
		setRotationAngle(TailLeft, -0.0873F, 0.2618F, 0.0F);
		TailLeft.cubeList.add(new ModelBox(TailLeft, 11, 0, 0.0F, -0.25F, -0.98F, 1, 1, 3, 0.0F, false));
		
		RightWing = new ModelRenderer(this);
		RightWing.setRotationPoint(-2.0F, 19.0F, 0.0F);
		setRotationAngle(RightWing, -0.6109F, 0.0F, 0.0F);
		RightWing.cubeList.add(new ModelBox(RightWing, 0, 7, -1.0F, -0.115F, -2.15F, 1, 2, 5, 0.0F, false));
		RightWing.cubeList.add(new ModelBox(RightWing, 0, 0, -1.0F, 0.1F, 2.85F, 1, 1, 1, 0.0F, false));
		
		LeftWing = new ModelRenderer(this);
		LeftWing.setRotationPoint(1.0F, 19.0F, 0.0F);
		setRotationAngle(LeftWing, -0.6109F, 0.0F, 0.0F);
		LeftWing.cubeList.add(new ModelBox(LeftWing, 7, 9, 0.0F, -0.115F, -2.15F, 1, 2, 5, 0.0F, false));
		LeftWing.cubeList.add(new ModelBox(LeftWing, 0, 2, 0.0F, 0.1F, 2.85F, 1, 1, 1, 0.0F, false));
		
		Head = new ModelRenderer(this);
		Head.setRotationPoint(-0.5F, 19.0F, -2.0F);
		Head.cubeList.add(new ModelBox(Head, 14, 4, -1.5F, -3.0F, -2.0F, 3, 3, 3, 0.0F, false));
		Head.cubeList.add(new ModelBox(Head, 6, 16, -0.5F, -1.5F, -3.25F, 1, 1, 2, 0.0F, false));
	}
	
	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!isChild) {
			LeftLeg.render(scale);
			RightLeg.render(scale);
			Body.render(scale);
			TailRight.render(scale);
			TailMiddle.render(scale);
			TailLeft.render(scale);
			RightWing.render(scale);
			LeftWing.render(scale);
			Head.render(scale);
		} else {
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.75F, 0.75F, 0.75F);
			GlStateManager.translate(0.0125f, 0.6F, scale);
			Head.render(scale);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
			LeftLeg.render(scale);
			RightLeg.render(scale);
			Body.render(scale);
			TailRight.render(scale);
			TailMiddle.render(scale);
			TailLeft.render(scale);
			RightWing.render(scale);
			LeftWing.render(scale);
			GlStateManager.popMatrix();
		}
	}
	
	private void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity ent) {
		if (((EntityPigeon) ent).isFlying()) {
			LeftLeg.rotateAngleX = -55;
			RightLeg.rotateAngleX = -55;
			
			RightWing.rotateAngleX = 0;
			LeftWing.rotateAngleX = 0;
			RightWing.rotateAngleZ = MathHelper.cos(limbSwing * 3f) * 1.6f * limbSwingAmount;
			LeftWing.rotateAngleZ = MathHelper.cos(limbSwing * 3f + (float) Math.PI) * 1.6f * limbSwingAmount;
		} else {
			LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 4f) * limbSwingAmount;
			RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 4f + (float) Math.PI) * limbSwingAmount;
			
			setRotationAngle(RightWing, -0.6109F, 0.0F, 0.0F);
			setRotationAngle(LeftWing, -0.6109F, 0.0F, 0.0F);
		}
		
		Head.rotateAngleY = netHeadYaw * 0.017453292f;
		Head.rotateAngleX = headPitch * 0.017453292f;
	}
}