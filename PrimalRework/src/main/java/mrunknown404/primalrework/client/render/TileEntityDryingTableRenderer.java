package mrunknown404.primalrework.client.render;

import org.lwjgl.opengl.GL11;

import mrunknown404.primalrework.tileentity.TileEntityDryingTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityDryingTableRenderer extends TileEntitySpecialRenderer<TileEntityDryingTable> {
	@Override
	public void render(TileEntityDryingTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te == null || !te.hasWorld() || te.facing == null) {
			return;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5f, y + (12.1f / 16f), z + 0.5f);
		GlStateManager.scale(0.25F, 0.25F, 0.25F);
		
		if (te.facing == EnumFacing.NORTH) {
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		} else if (te.facing == EnumFacing.EAST) {
			GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
		} else if (te.facing == EnumFacing.WEST) {
			GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
		}
		
		GlStateManager.translate(-1F, 0, -1F);
		
		int i = 0;
		for (int h = 0; h < 3; h++) {
			for (int w = 0; w < 3; w++) {
				if (!te.getStackInSlot(i).isEmpty()) {
					renderItemInSlot(te, te.getStackInSlot(i), w, 0.25F, h, partialTicks);
				}
				
				i++;
			}
		}
		
		GlStateManager.popMatrix();
	}
	
	private void renderItemInSlot(TileEntityDryingTable te, ItemStack stack, float x, float y, float z, float partialTicks) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, null, null));
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
}
