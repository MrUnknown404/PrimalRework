package mrunknown404.primalrework.client.render;

import org.lwjgl.opengl.GL11;

import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class TileEntityPrimalEnchantingRenderer extends TileEntitySpecialRenderer<TileEntityPrimalEnchanting> {
	@Override
	public void render(TileEntityPrimalEnchanting te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te == null || !te.hasWorld() || te.getStackInSlot(0).isEmpty()) {
			return;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 0.814, z + 0.5);
		GlStateManager.scale(0.5f, 0.5f, 0.5f);
		GlStateManager.rotate(te.getWorld().getTotalWorldTime() * 3, 0, 1f, 0);
		
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Minecraft.getMinecraft().getRenderItem().renderItem(te.getStackInSlot(0), Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(te.getStackInSlot(0), null, null));
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
}
