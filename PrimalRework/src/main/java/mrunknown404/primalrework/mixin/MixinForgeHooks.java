package mrunknown404.primalrework.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import mrunknown404.primalrework.init.InitWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.world.ForgeWorldType;

@Mixin(ForgeHooks.class) //ForgeWorldTypeScreens also exists but is client side so i don't care
public class MixinForgeHooks {
	@Redirect(method = "getDefaultWorldType", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/world/ForgeWorldType;getDefaultWorldType()Lnet/minecraftforge/common/world/ForgeWorldType;"), remap = false)
	private static ForgeWorldType getDefaultWorldType() {
		return InitWorld.PRIMAL_WORLD.get();
	}
}
