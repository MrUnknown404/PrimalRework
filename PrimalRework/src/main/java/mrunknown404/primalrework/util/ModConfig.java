package mrunknown404.primalrework.util;

import mrunknown404.primalrework.Main;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Main.MOD_ID)
@Config.LangKey(Main.MOD_ID + ".config.title")
public class ModConfig {
	
	@Config.Comment("The maximum number of quests to check at once")
	@Config.RangeInt(min = 1, max = 100)
	public static int maxQuestsToCheck = 10;
	
	@EventBusSubscriber(modid = Main.MOD_ID)
	private static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
			if (e.getModID().equals(Main.MOD_ID)) {
				ConfigManager.sync(Main.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}
