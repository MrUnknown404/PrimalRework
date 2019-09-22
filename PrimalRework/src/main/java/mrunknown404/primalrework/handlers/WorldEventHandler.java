package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.util.helpers.StageHelper;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEventHandler {

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
		if (!e.getWorld().isRemote) {
			StageHelper.setWorld(e.getWorld());
		}
	}
}
