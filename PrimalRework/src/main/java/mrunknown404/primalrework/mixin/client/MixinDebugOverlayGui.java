package mrunknown404.primalrework.mixin.client;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.PlatformDescriptors;

import mrunknown404.primalrework.PrimalRework;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import net.minecraft.util.SharedConstants;
import net.minecraftforge.fml.ModList;

@Mixin(DebugOverlayGui.class)
public class MixinDebugOverlayGui {
	@SuppressWarnings("static-method")
	@Inject(at = @At("HEAD"), method = "getGameInformation()Ljava/util/List;", cancellable = true)
	private void getGameInformation(CallbackInfoReturnable<List<String>> callback) {
		List<String> list = new ArrayList<String>();
		Minecraft mc = Minecraft.getInstance();
		
		list.add("Minecraft " + SharedConstants.getCurrentVersion().getName() + " - PrimalRework " +
				ModList.get().getModContainerById(PrimalRework.MOD_ID).get().getModInfo().getVersion());
		list.add("FPS: " + mc.fpsString.substring(0, mc.fpsString.indexOf(' ')));
		
		callback.setReturnValue(list);
	}
	
	@SuppressWarnings("static-method")
	@Inject(at = @At("HEAD"), method = "getSystemInformation()Ljava/util/List;", cancellable = true)
	private void getSystemInformation(CallbackInfoReturnable<List<String>> callback) {
		Minecraft mc = Minecraft.getInstance();
		long i = Runtime.getRuntime().maxMemory();
		long j = Runtime.getRuntime().totalMemory();
		long l = j - Runtime.getRuntime().freeMemory();
		
		List<String> list = Lists.newArrayList(String.format("Java: %s %dbit", System.getProperty("java.version"), mc.is64Bit() ? 64 : 32),
				String.format("Mem: % 2d%% %03d/%03dMB", l * 100L / i, bytesToMegabytes(l), bytesToMegabytes(i)),
				String.format("Allocated: % 2d%% %03dMB", j * 100L / i, bytesToMegabytes(j)), "", String.format("CPU: %s", PlatformDescriptors.getCpuInfo()), "",
				String.format("Display: %dx%d (%s)", Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight(),
						PlatformDescriptors.getVendor()),
				PlatformDescriptors.getRenderer(), PlatformDescriptors.getOpenGLVersion());
		callback.setReturnValue(list);
	}
	
	private static long bytesToMegabytes(long l) {
		return l / 1024L / 1024L;
	}
	
	@SuppressWarnings("static-method")
	@Shadow
	private List<String> getGameInformation() {
		try {
			throw new Exception("This should not be run!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
