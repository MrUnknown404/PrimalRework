package mrunknown404.primalrework.events.client;

import org.lwjgl.glfw.GLFW;

import mrunknown404.primalrework.client.InitClient;
import mrunknown404.primalrework.client.gui.screen.ScreenQuestMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class QuestEvents {
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent e) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.screen instanceof ChatScreen) {
			return;
		}
		
		if (e.getAction() == GLFW.GLFW_PRESS && e.getKey() == InitClient.QUESTS_OPEN.getKey().getValue()) {
			if (mc.screen == null) {
				mc.setScreen(new ScreenQuestMenu());
			} else if (mc.screen instanceof ScreenQuestMenu) {
				mc.setScreen(null);
			}
		}
	}
}
