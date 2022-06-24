package mrunknown404.primalrework.mixin;

import java.util.OptionalLong;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;

@Mixin(DimensionType.class)
public class MixinDimensionType {
	@Shadow
	public final OptionalLong fixedTime = OptionalLong.empty();
	
	@Inject(at = @At("HEAD"), method = "timeOfDay(J)F", cancellable = true)
	private void timeOfDay(long var, CallbackInfoReturnable<Float> callback) {
		double d0 = MathHelper.frac(fixedTime.orElse(var) / 72000d - 0.25d); //1 hour into ticks
		double d1 = 0.5d - Math.cos(d0 * Math.PI) / 2d;
		callback.setReturnValue((float) (d0 * 2d + d1) / 3f);
	}
}
