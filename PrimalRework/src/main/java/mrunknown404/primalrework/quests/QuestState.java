package mrunknown404.primalrework.quests;

import javax.annotation.Nullable;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.network.packets.client.PQuestClaimRewards;
import mrunknown404.primalrework.utils.helpers.WordH;
import mrunknown404.primalrework.world.savedata.WSDQuests;
import mrunknown404.primalrework.world.savedata.WSDStage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class QuestState {
	public final Quest quest;
	private QuestState parentState;
	private boolean isFinished, wasClaimed;
	
	public QuestState(Quest quest, boolean isFinished, boolean wasClaimed) {
		this.quest = quest;
		this.isFinished = isFinished;
		this.wasClaimed = wasClaimed;
	}
	
	public void load(boolean isFinished, boolean wasClaimed) {
		this.isFinished = isFinished;
		this.wasClaimed = wasClaimed;
	}
	
	public void load(QuestState parentState) {
		this.parentState = parentState;
	}
	
	public boolean hasParent() {
		return quest.hasParent();
	}
	
	public QuestState getParent() {
		return parentState;
	}
	
	public boolean isFinished() {
		return isFinished;
	}
	
	public boolean wasClaimed() {
		return wasClaimed;
	}
	
	public void finishQuest(ServerWorld world, @Nullable PlayerEntity player) { //TODO send packet to clients
		if (quest.isRoot) {
			System.out.println("Root quest '" + quest.getName() + "' has been finished!");
		} else if (player != null) {
			System.out.println(player.getDisplayName().getString() + " has finished the quest '" + quest.getName() + "'!");
		} else {
			System.out.println("Someone has finished the quest '" + quest.getName() + "'!");
		}
		
		for (PlayerEntity pl : world.players()) {
			StringTextComponent space = WordH.space();
			
			if (player != null) {
				pl.sendMessage(((IFormattableTextComponent) player.getDisplayName()).append(space).append(WordH.translate("quest.finish.player")).append(space)
						.append(quest.getFancyName()), Util.NIL_UUID);
			} else {
				pl.sendMessage(
						WordH.translate("quest.finish.missing.pre").append(space).append(quest.getFancyName()).append(space).append(WordH.translate("quest.finish.missing.post")),
						Util.NIL_UUID);
			}
		}
		
		isFinished = true;
		if (quest.reward == null) {
			wasClaimed = true;
		}
		
		if (quest.isEnd) {
			if (quest.stage.get().id <= WSDStage.getStage().id) {
				WSDStage.setStage(world.getServer(), InitStages.getNextStage());
				System.out.println("The world has advanced to the next stage!");
			} else {
				System.err.println("Finished an end quest that should progress to the next stage but we're already there");
			}
		}
		
		WSDQuests.get(world.getServer()).setDirty();
	}
	
	public void forgetQuest(ServerWorld world) { //TODO send packet to clients
		System.out.println("Quest '" + quest.getName() + "' was forgotten!");
		isFinished = false;
		wasClaimed = false;
		WSDQuests.get(world.getServer()).setDirty();
	}
	
	public void claimQuest() {
		if (wasClaimed) {
			return;
		}
		
		wasClaimed = true;
		PrimalRework.NETWORK.sendPacketToServer(new PQuestClaimRewards(quest));
	}
	
	public void claimQuest(ServerPlayerEntity player) {
		wasClaimed = true;
		quest.reward.giveRewards(player);
		WSDQuests.get(player.getCommandSenderWorld().getServer()).setDirty();
	}
}
