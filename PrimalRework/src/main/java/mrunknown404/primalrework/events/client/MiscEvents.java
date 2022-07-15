package mrunknown404.primalrework.events.client;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.client.gui.screen.ScreenCreateWorld;
import mrunknown404.primalrework.client.gui.screen.ScreenMainMenu;
import mrunknown404.primalrework.client.gui.screen.ScreenPause;
import mrunknown404.primalrework.network.packets.toserver.POpenInventory;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MiscEvents {
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e) {
		Minecraft mc = Minecraft.getInstance();
		Screen gui = e.getGui();
		
		if (gui instanceof CreateWorldScreen) {
			try {
				e.setGui(new ScreenCreateWorld(mc.getLevelSource().getLevelList().isEmpty() ? null : mc.screen));
			} catch (AnvilConverterException e1) {
				e1.printStackTrace();
			}
		} else if (gui instanceof IngameMenuScreen) {
			e.setGui(new ScreenPause(!(mc.hasSingleplayerServer() && !mc.getSingleplayerServer().isPublished())));
		} else if (gui instanceof MainMenuScreen) {
			e.setGui(new ScreenMainMenu());
		} else if (gui instanceof InventoryScreen) {
			if (!mc.player.isCreative()) {
				e.setCanceled(true);
				PrimalRework.NETWORK.sendPacketToServer(new POpenInventory());
			} else {
				e.setGui(new CreativeScreen(mc.player));
			}
		}
	}
	
	@SubscribeEvent
	public void onOverlayRenderPre(RenderGameOverlayEvent.Pre e) {
		switch (e.getType()) {
			case AIR:
			case ARMOR:
			case FOOD:
			case HEALTH:
			case HEALTHMOUNT:
			case JUMPBAR:
				e.getMatrixStack().translate(0, 6, 0);
				break;
			case EXPERIENCE:
				e.setCanceled(true);
				break;
			default:
				break;
		}
	}
	
	@SubscribeEvent
	public void onOverlayRenderPost(RenderGameOverlayEvent.Post e) {
		switch (e.getType()) {
			case AIR:
			case ARMOR:
			case FOOD:
			case HEALTH:
			case HEALTHMOUNT:
			case JUMPBAR:
				e.getMatrixStack().translate(0, -6, 0);
				break;
			default:
				break;
		}
	}
}
