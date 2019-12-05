package mrunknown404.primalrework.client.render;

import java.util.List;

import org.lwjgl.opengl.GL11;

import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.tileentity.TileEntityDryingTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.pipeline.LightUtil;

public class TileEntityDryingTableRenderer extends TileEntitySpecialRenderer<TileEntityDryingTable> {
	private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	
	@Override
	public void render(TileEntityDryingTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te == null || !te.hasWorld() || te.facing == null) {
			return;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5f, y + (12.1f / 16f), z + 0.5f);
		GlStateManager.scale(0.25F, 0.25F, 0.25F);
		
		if (te.facing == EnumFacing.NORTH) {
			GlStateManager.rotate(180f, 0f, 1f, 0f);
		} else if (te.facing == EnumFacing.EAST) {
			GlStateManager.rotate(90f, 0f, 1f, 0f);
		} else if (te.facing == EnumFacing.WEST) {
			GlStateManager.rotate(270f, 0f, 1f, 0f);
		}
		
		GlStateManager.translate(-1F, 0, -1F);
		
		int i = 0;
		for (int h = 0; h < 3; h++) {
			for (int w = 0; w < 3; w++) {
				ItemStack item = te.getStackInSlot(i);
				
				if (!item.isEmpty()) {
					int cur = te.getDryProgress()[i];
					int max = ModRecipes.doesItemHaveDryingTableRecipe(item) ? ModRecipes.getDryingTableRecipeFromInput(item).getDryTime() : 1;
					
					renderItemInSlot(te, item, w, 0.25F, h, cur, max, partialTicks);
				}
				
				i++;
			}
		}
		
		GlStateManager.popMatrix();
	}
	
	private void renderItemInSlot(TileEntityDryingTable te, ItemStack stack, float x, float y, float z, int curDryTime, int dryTime, float partialTicks) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.rotate(-90f, 1f, 0f, 0f);
			
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			if (curDryTime != 0) {
				int newDarkness = 255 + (int) ((255f / 2f) - ((1 - ((float) curDryTime / (float) dryTime / 2f)) * 255f));
				renderItem(stack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, null, null), newDarkness);
			} else {
				renderItem(stack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, null, null), 255);
			}
			
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
	
	private void renderItem(ItemStack stack, IBakedModel model, int darkness) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(-0.5F, -0.5F, -0.5F);
			
			if (model.isBuiltInRenderer()) {
				GlStateManager.color(1f, 1f, 1f, 1f);
				GlStateManager.enableRescaleNormal();
				stack.getItem().getTileEntityItemStackRenderer().renderByItem(stack);
			} else {
				renderModel(model, darkness, stack);
				
				if (stack.hasEffect()) {
					renderEffect(model);
				}
			}
			
			GlStateManager.popMatrix();
		}
	}
	
	private void renderEffect(IBakedModel model) {
		GlStateManager.depthMask(false);
		GlStateManager.depthFunc(514);
		GlStateManager.disableLighting();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
		Minecraft.getMinecraft().getTextureManager().bindTexture(RES_ITEM_GLINT);
		GlStateManager.matrixMode(5890);
		GlStateManager.pushMatrix();
		GlStateManager.scale(8f, 8f, 8f);
		float f = (float) (Minecraft.getSystemTime() % 3000l) / 3000f / 8f;
		GlStateManager.translate(f, 0f, 0f);
		GlStateManager.rotate(-50f, 0f, 0f, 1f);
		renderModel(model, -8372020);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.scale(8f, 8f, 8f);
		float f1 = (float) (Minecraft.getSystemTime() % 4873l) / 4873f / 8f;
		GlStateManager.translate(-f1, 0f, 0f);
		GlStateManager.rotate(10f, 0f, 0f, 1f);
		renderModel(model, -8372020);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableLighting();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	}
	
	private void renderModel(IBakedModel model, int darkness, ItemStack stack) {
		renderModel(model, -1, darkness, stack);
	}
	
	private void renderModel(IBakedModel model, int color) {
		renderModel(model, color, 255, ItemStack.EMPTY);
	}
	
	private void renderModel(IBakedModel model, int color, int darkness, ItemStack stack) {
		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();
		buf.begin(7, DefaultVertexFormats.ITEM);
		
		for (EnumFacing enumfacing : EnumFacing.values()) {
			renderQuads(buf, model.getQuads(null, enumfacing, 0L), color, darkness, stack);
		}
		
		renderQuads(buf, model.getQuads(null, null, 0L), color, darkness, stack);
		tes.draw();
	}
	
	private void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color, int darkness, ItemStack stack) {
		boolean flag = color == -1 && !stack.isEmpty();
		int i = 0;
		
		for (int j = quads.size(); i < j; ++i) {
			BakedQuad bakedquad = quads.get(i);
			int k = color;
			
			if (flag && bakedquad.hasTintIndex()) {
				k = Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, bakedquad.getTintIndex());
				
				if (EntityRenderer.anaglyphEnable) {
					k = TextureUtil.anaglyphColor(k);
				} else {
					k = (darkness << 16) + (darkness << 8) + (darkness);
				}
				
				k = k | -16777216;
			}
			
			LightUtil.renderQuadColor(renderer, bakedquad, k);
		}
	}
}
