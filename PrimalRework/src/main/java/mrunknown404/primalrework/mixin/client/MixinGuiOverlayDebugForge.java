package mrunknown404.primalrework.mixin.client;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.overlay.DebugOverlayGui;

@Mixin(targets = "net/minecraftforge/client/gui/ForgeIngameGui$GuiOverlayDebugForge")
public class MixinGuiOverlayDebugForge extends DebugOverlayGui {
	public MixinGuiOverlayDebugForge() {
		super(null);
	}
	
	@Inject(at = @At("HEAD"), method = "getLeft()Ljava/util/List;", cancellable = true)
	private void getLeft(CallbackInfoReturnable<List<String>> callback) {
		callback.setReturnValue(getGameInformation());
	}
}
