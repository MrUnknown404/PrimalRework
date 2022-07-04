package mrunknown404.primalrework.events;

import mrunknown404.primalrework.quests.requirements.QuestRequirement.CheckResult;
import mrunknown404.primalrework.world.savedata.WSDQuests;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class QuestEvents {
	@SubscribeEvent
	public void onTick(WorldTickEvent e) {
		if (e.world.isClientSide) {
			return;
		}
		
		WSDQuests.get(e.world.getServer()).forEach(q -> {
			if (!q.quest.hasAccessToCurrentStage() || q.isFinished()) {
				return;
			} else if (q.quest.isRoot()) {
				if (!q.isFinished()) {
					q.finishQuest((ServerWorld) e.world, null);
				}
				return;
			}
			
			if (q.hasParent() && !q.getParent().isFinished()) {
				return;
			}
			
			CheckResult result = q.quest.getRequirement().checkConditions(e.world.players());
			if (result.finished) {
				q.finishQuest((ServerWorld) e.world, result.player);
			}
		});
	}
}
