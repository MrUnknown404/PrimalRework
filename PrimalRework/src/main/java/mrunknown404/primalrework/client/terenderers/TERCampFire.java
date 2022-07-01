package mrunknown404.primalrework.client.terenderers;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.tileentities.TEICampFire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class TERCampFire extends TileEntityRenderer<TEICampFire> {
	private final ItemRenderer ir;
	
	public TERCampFire(TileEntityRendererDispatcher d) {
		super(d);
		ir = Minecraft.getInstance().getItemRenderer();
	}
	
	@Override
	public void render(TEICampFire te, float ticks, MatrixStack stack, IRenderTypeBuffer buf, int light, int overlay) {
		ItemStack item = te.getItem(1);
		if (item.isEmpty()) {
			return;
		}
		
		IBakedModel model = ir.getModel(item, null, null);
		stack.pushPose();
		stack.translate(0.5, 0.3, 0.5);
		stack.mulPose(new Quaternion(new Vector3f(0, 1, 0), 3, true));
		Minecraft.getInstance().getItemRenderer().render(item, TransformType.GROUND, false, stack, buf, light, overlay, model);
		stack.popPose();
	}
}
