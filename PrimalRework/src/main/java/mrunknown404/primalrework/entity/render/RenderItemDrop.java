package mrunknown404.primalrework.entity.render;

import java.util.Random;

import mrunknown404.primalrework.entity.EntityItemDrop;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderItemDrop extends Render<EntityItemDrop> {
	
	private final RenderItem itemRenderer;
	private Random r = new Random();
	
	public RenderItemDrop(RenderManager manager, RenderItem itemRenderer) {
		super(manager);
		this.itemRenderer = itemRenderer;
		this.shadowSize = 0.15f;
		this.shadowOpaque = 0.75f;
	}
	
	private int transformModelCount(EntityItemDrop ent, double x, double y, double z, IBakedModel model) {
		ItemStack itemstack = ent.getItem();
		
		if (itemstack.isEmpty()) {
			return 0;
		} else {
			GlStateManager.translate(x, y, z);
			
			if (model.isGui3d() || renderManager.options != null) {
				IBlockState stateDown = ent.world.getBlockState(new BlockPos(ent.posX, ent.posY - 0.25D, ent.posZ));
				
				if (!(ent.onGround || (ent.isInWater() || stateDown.getBlock() instanceof BlockLiquid || stateDown.getBlock() instanceof IFluidBlock))) {
					GlStateManager.rotate(ent.hoverStart * 360f, 0, 1, 0);
				} else {
					GlStateManager.translate(0, 0.05f, 0);
					GlStateManager.rotate(90f, 1, 0, 0);
					GlStateManager.rotate(ent.hoverStart * 360f, 0, 0, 1);
					GlStateManager.translate(0f, itemstack.getItem() instanceof ItemBlock ? -0.175f : -0.1f, 0.0f);
				}
			}
			
			GlStateManager.color(1, 1, 1, 1);
			return getModelCount(itemstack);
		}
	}
	
	@Override
	public void doRender(EntityItemDrop entity, double x, double y, double z, float entityYaw, float partialTicks) {
		r.setSeed((long) (!entity.getItem().isEmpty() ? Item.getIdFromItem(entity.getItem().getItem()) + entity.getItem().getMetadata() : 187));
		boolean flag = bindEntityTexture(entity);
		
		if (flag) {
			renderManager.renderEngine.getTexture(getEntityTexture(entity)).setBlurMipmap(false, false);
		}
		
		GlStateManager.enableRescaleNormal();
		GlStateManager.alphaFunc(516, 0.1f);
		GlStateManager.enableBlend();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.pushMatrix();
		IBakedModel model = itemRenderer.getItemModelWithOverrides(entity.getItem(), entity.world, null);
		int j = transformModelCount(entity, x, y, z, model);
		
		if (!model.isGui3d()) {
			float f3 = -0f * (float) (j - 1) * 0.5f;
			float f4 = -0f * (float) (j - 1) * 0.5f;
			float f5 = -0.09375f * (float) (j - 1) * 0.5f;
			GlStateManager.translate(f3, f4, f5);
		}
		
		if (renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(getTeamColor(entity));
		}
		
		for (int k = 0; k < j; ++k) {
			if (model.isGui3d()) {
				GlStateManager.pushMatrix();
				
				if (k > 0) {
					float f7 = (r.nextFloat() * 2f - 1f) * 0.15f;
					float f9 = (r.nextFloat() * 2f - 1f) * 0.15f;
					float f6 = (r.nextFloat() * 2f - 1f) * 0.15f;
					GlStateManager.translate(f7, f9, f6);
				}
				
				model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);
				itemRenderer.renderItem(entity.getItem(), model);
				GlStateManager.popMatrix();
			} else {
				GlStateManager.pushMatrix();
				
				if (k > 0) {
					float f8 = (r.nextFloat() * 2f - 1f) * 0.15f * 0.5f;
					float f10 = (r.nextFloat() * 2f - 1f) * 0.15f * 0.5f;
					GlStateManager.translate(f8, f10, 0);
				}
				
				model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);
				itemRenderer.renderItem(entity.getItem(), model);
				GlStateManager.popMatrix();
				GlStateManager.translate(0, 0, 0.09375f);
			}
		}
		
		if (renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}
		
		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
		bindEntityTexture(entity);
		
		if (flag) {
			renderManager.renderEngine.getTexture(getEntityTexture(entity)).restoreLastBlurMipmap();
		}
		
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	private int getModelCount(ItemStack stack) {
		int i = 1;
		
		if (stack.getCount() > 48) {
			i = 5;
		} else if (stack.getCount() > 32) {
			i = 4;
		} else if (stack.getCount() > 16) {
			i = 3;
		} else if (stack.getCount() > 1) {
			i = 2;
		}
		
		return i;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityItemDrop entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
	
	public static class Factory implements IRenderFactory<EntityItemDrop> {
		@Override
		public Render<? super EntityItemDrop> createRenderFor(RenderManager manager) {
			return new RenderItemDrop(manager, Minecraft.getMinecraft().getRenderItem());
		}
	}
}
