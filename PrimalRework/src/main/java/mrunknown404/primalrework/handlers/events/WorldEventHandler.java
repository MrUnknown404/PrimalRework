package mrunknown404.primalrework.handlers.events;

import mrunknown404.primalrework.handlers.StageHandler;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEventHandler {

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
		if (!e.getWorld().isRemote) {
			StageHandler.setWorld(e.getWorld());
		}
	}
}
