package mrunknown404.primalrework.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.serialization.Lifecycle;

import net.minecraft.world.storage.ServerWorldInfo;

@Mixin(ServerWorldInfo.class)
public class MixinServerWorldInfo {
	@SuppressWarnings("static-method")
	@Inject(at = @At("HEAD"), method = "worldGenSettingsLifecycle()Lcom/mojang/serialization/Lifecycle;", cancellable = true)
	private void worldGenSettingsLifecycle(CallbackInfoReturnable<Lifecycle> callback) {
		callback.setReturnValue(Lifecycle.stable());
	}
}
