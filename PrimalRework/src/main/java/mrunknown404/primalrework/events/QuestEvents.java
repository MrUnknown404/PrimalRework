package mrunknown404.primalrework.events;

import mrunknown404.primalrework.helpers.StageH;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.quests.requirements.ItemRequirement;
import mrunknown404.primalrework.quests.requirements.StagedTagRequirement;
import mrunknown404.primalrework.registries.PRQuests;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class QuestEvents {
	@SubscribeEvent
	public void onTick(WorldTickEvent e) {
		if (e.world.isClientSide) {
			return;
		}
		
		for (QuestTab tab : PRQuests.getTabs()) {
			if (tab.getRoot() != null && StageH.hasAccessToStage(tab.getRoot().getStage()) && !tab.getRoot().isFinished()) {
				tab.getRoot().finishQuest(e.world, null);
			}
		}
		
		all:
		for (Quest q : PRQuests.getQuestCache()) {
			if (StageH.hasAccessToStage(q.getStage())) {
				if (q.getRequirement() instanceof ItemRequirement) {
					ItemRequirement req = (ItemRequirement) q.getRequirement();
					
					for (PlayerEntity pl : e.world.players()) {
						for (ItemStack item : pl.inventory.items) {
							if (req.check(item.getItem(), item.getCount())) {
								q.finishQuest(e.world, pl);
								break all;
							}
						}
					}
				} else if (q.getRequirement() instanceof StagedTagRequirement) {
					StagedTagRequirement req = (StagedTagRequirement) q.getRequirement();
					for (PlayerEntity pl : e.world.players()) {
						for (ItemStack item : pl.inventory.items) {
							if (req.check(item.getItem(), item.getCount())) {
								q.finishQuest(e.world, pl);
								break all;
							}
						}
					}
				}
			}
		}
	}
}
