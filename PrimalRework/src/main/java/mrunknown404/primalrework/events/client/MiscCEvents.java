package mrunknown404.primalrework.events.client;

import mrunknown404.primalrework.client.gui.screen.fixes.PRCreateWorldScreen;
import mrunknown404.primalrework.client.gui.screen.fixes.PRPauseScreen;
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
			e.setGui(new PRCreateWorldScreen(mc.screen));
		} else if (e.getGui() instanceof IngameMenuScreen) {
			e.setGui(new PRPauseScreen(!(mc.hasSingleplayerServer() && !mc.getSingleplayerServer().isPublished())));
		}
	}
}
