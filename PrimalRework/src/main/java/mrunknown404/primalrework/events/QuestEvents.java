package mrunknown404.primalrework.events;

import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.quests.requirements.QuestRequirement.CheckResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class QuestEvents {
	@SubscribeEvent
	public void onTick(WorldTickEvent e) {
		if (e.world.isClientSide) {
			return;
		}
		
		for (QuestTab tab : InitQuests.getTabs()) {
			if (tab.getRoot() != null && tab.getRoot().hasAccessToCurrentStage() && !tab.getRoot().isFinished()) {
				tab.getRoot().finishQuest((ServerWorld) e.world, null);
			}
		}
		
		for (Quest q : InitQuests.getQuests()) {
			if (q.isRoot() || q.isFinished() || !q.hasAccessToCurrentStage()) {
				continue;
			} else if (q.getParent() != null && !q.getParent().isFinished()) {
				continue;
			}
			
			CheckResult result = q.getRequirement().checkConditions(e.world.players());
			if (result.finished) {
				q.finishQuest((ServerWorld) e.world, result.player);
				break;
			}
		}
	}
}
