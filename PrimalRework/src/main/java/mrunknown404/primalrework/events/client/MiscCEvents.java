package mrunknown404.primalrework.events.client;

import mrunknown404.primalrework.client.gui.screen.ScreenCreatePrimalWorld;
import mrunknown404.primalrework.client.gui.screen.ScreenPrimalPause;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MiscCEvents {
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e) {
		Minecraft mc = Minecraft.getInstance();
		
		if (e.getGui() instanceof CreateWorldScreen) {
			e.setGui(new ScreenCreatePrimalWorld(mc.screen));
		} else if (e.getGui() instanceof IngameMenuScreen) {
			e.setGui(new ScreenPrimalPause(!(mc.hasSingleplayerServer() && !mc.getSingleplayerServer().isPublished())));
		}
	}
}
