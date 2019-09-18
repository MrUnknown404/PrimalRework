package mrunknown404.primalrework.client.render;

import org.lwjgl.opengl.GL11;

import mrunknown404.primalrework.tileentity.TileEntityCraftingStump;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityCraftingStumpRenderer extends TileEntitySpecialRenderer<TileEntityCraftingStump> {
	@Override
	public void render(TileEntityCraftingStump te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te == null || !te.hasWorld() || te.facing == null) {
			return;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5D, y + 0.767D, z + 0.5D);
		GlStateManager.scale(0.2125F, 0.2125F, 0.2125F);
		
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
					renderItemInSlot(te, te.getStackInSlot(i), (w * 14.2f) / 16f, 0.3F, (h * 14.2f) / 16f,
							te.getItemRotation()[i], te.getItemJump()[i], te.getItemJumpPrev()[i], partialTicks);
				}
				
				i++;
			}
		}
		
		GlStateManager.popMatrix();
	}
	
	private void renderItemInSlot(TileEntityCraftingStump te, ItemStack stack, float x, float y, float z, float rotation, float jump, float prevJump, float partialTicks) {
		if (!stack.isEmpty()) {
			float jumpUP = jump * 0.05F + (jump * 0.05F - prevJump * 0.05F) * partialTicks;
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.translate(0.115F, 0.52F + jumpUP, 0.115F);
			GlStateManager.scale(-0.5f, -0.5f, 0.5f);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(90F, 1.0F, 0.0F, 0.0F);
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, null, null));
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();
		}
	}

}
