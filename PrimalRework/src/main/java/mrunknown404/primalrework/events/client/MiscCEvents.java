package mrunknown404.primalrework.events.client;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.client.gui.screen.ScreenCreateWorld;
import mrunknown404.primalrework.client.gui.screen.ScreenNonSupportedMods;
import mrunknown404.primalrework.client.gui.screen.ScreenPause;
import mrunknown404.primalrework.network.NetworkHandler;
import mrunknown404.primalrework.network.packets.POpenInventory;
import mrunknown404.primalrework.utils.PrimalMod;
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
import net.minecraftforge.fml.ModList;

public class MiscCEvents {
	private static boolean firstMainMenu;
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e) {
		Minecraft mc = Minecraft.getInstance();
		Screen gui = e.getGui();
		
		if (gui instanceof CreateWorldScreen) {
			e.setGui(new ScreenCreateWorld(mc.screen));
		} else if (gui instanceof IngameMenuScreen) {
			e.setGui(new ScreenPause(!(mc.hasSingleplayerServer() && !mc.getSingleplayerServer().isPublished())));
		} else if (!firstMainMenu && gui instanceof MainMenuScreen) {
			firstMainMenu = true;
			List<String> modids = new ArrayList<String>();
			
			ModList.get().forEachModContainer((modid, instance) -> {
				if (modid.equalsIgnoreCase("minecraft") || modid.equalsIgnoreCase("forge")) {
					return;
				}
				
				if (!instance.getMod().getClass().isAnnotationPresent(PrimalMod.class)) {
					modids.add(modid);
					System.out.println("Mod '" + modid + "' is not supported!");
				}
			});
			
			if (!modids.isEmpty()) {
				e.setGui(new ScreenNonSupportedMods(modids));
			}
		} else if (gui instanceof InventoryScreen) {
			if (!mc.player.isCreative()) {
				e.setCanceled(true);
				NetworkHandler.sendPacketToServer(new POpenInventory());
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
