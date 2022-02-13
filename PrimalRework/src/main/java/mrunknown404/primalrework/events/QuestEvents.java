package mrunknown404.primalrework.events;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.quests.requirements.ItemRequirement;
import mrunknown404.primalrework.registries.PRQuests;
import mrunknown404.primalrework.utils.helpers.StageH;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class QuestEvents {
	@SubscribeEvent
	public void onTick(WorldTickEvent e) {
		if (e.world.isClientSide) {
			return;
		}
		
		for (QuestTab tab : PRQuests.getTabs()) {
			if (tab.getRoot() != null && StageH.hasAccessToStage(tab.getRoot().getStage().get()) && !tab.getRoot().isFinished()) {
				tab.getRoot().finishQuest((ServerWorld) e.world, null);
			}
		}
		
		all:
		for (Quest q : PRQuests.getQuests()) { //TODO switch this to use unfinished quests for faster loops
			if (q.isRoot()) {
				continue;
			}
			
			if ((q.getParent() == null || q.getParent().isFinished()) && !q.isFinished() && StageH.hasAccessToStage(q.getStage().get())) {
				if (q.getRequirement() instanceof ItemRequirement) {
					ItemRequirement req = (ItemRequirement) q.getRequirement();
					
					for (PlayerEntity pl : e.world.players()) {
						for (ItemStack item : pl.inventory.items) {
							if (item.getItem() instanceof StagedItem) {
								if (req.check((StagedItem) item.getItem(), item.getCount())) {
									q.finishQuest((ServerWorld) e.world, pl);
									break all;
								}
							}
						}
					}
				}
			}
		}
	}
}
