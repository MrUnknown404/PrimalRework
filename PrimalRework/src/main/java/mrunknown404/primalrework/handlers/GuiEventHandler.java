package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.client.gui.GuiCreatePrimalWorld;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiEventHandler {
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e) {
		if (e.getGui() instanceof GuiCreateWorld) {
			e.setGui(new GuiCreatePrimalWorld(new GuiMainMenu()));
		}
	}
}
