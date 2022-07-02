package mrunknown404.primalrework.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.world.DimensionRenderInfo;

@Mixin(DimensionRenderInfo.class)
public class MixinDimensionRenderInfo {
	@Inject(at = @At("HEAD"), method = "getCloudHeight()F", cancellable = true)
	public void getCloudHeight(CallbackInfoReturnable<Float> callback) {
		callback.setReturnValue(200f);
	}
}
