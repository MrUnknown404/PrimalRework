package mrunknown404.primalrework.events.client;

import mrunknown404.primalrework.client.gui.screen.ScreenCraftingDisplayItems;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CraftingDisplayCEvents {
	@OnlyIn(Dist.CLIENT)
	private ScreenCraftingDisplayItems screen;
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onScreenToggle(GuiOpenEvent e) {
		if (e.getGui() == null) {
			screen = null;
		} else if (e.getGui() instanceof ContainerScreen) {
			screen = new ScreenCraftingDisplayItems((ContainerScreen<?>) e.getGui());
		} else {
			screen = null;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onGuiInit(InitGuiEvent.Post e) {
		if (e.getGui() instanceof ContainerScreen) {
			Minecraft mc = Minecraft.getInstance();
			MainWindow w = mc.getWindow();
			screen.init(mc, w.getGuiScaledWidth(), w.getGuiScaledHeight());
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onDrawScreen(DrawScreenEvent.Post e) {
		if (e.getGui() instanceof ContainerScreen) {
			if (screen == null) {
				return;
			}
			
			screen.render(e.getMatrixStack(), e.getMouseX(), e.getMouseY(), e.getRenderPartialTicks());
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onMouseClicked(GuiScreenEvent.MouseClickedEvent.Pre e) {
		if (screen == null) {
			return;
		}
		
		e.setCanceled(screen.click(e.getMouseX(), e.getButton()));
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onMouseScrolled(GuiScreenEvent.MouseScrollEvent.Pre e) {
		if (screen == null) {
			return;
		}
		
		e.setCanceled(screen.scroll(e.getMouseX(), e.getScrollDelta()));
	}
}
