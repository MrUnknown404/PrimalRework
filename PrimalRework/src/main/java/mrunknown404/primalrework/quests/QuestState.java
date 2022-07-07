package mrunknown404.primalrework.quests;

import java.util.List;

import javax.annotation.Nullable;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.api.utils.IStageProvider;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.network.packets.toclient.PSyncQuestState;
import mrunknown404.primalrework.network.packets.toserver.PQuestClaimRewards;
import mrunknown404.primalrework.quests.requirements.QuestRequirement;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.helpers.WordH;
import mrunknown404.primalrework.world.savedata.WSDQuestStates;
import mrunknown404.primalrework.world.savedata.WSDStage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class QuestState implements IStageProvider {
	private final Quest quest;
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
	
	public void finishQuest(ServerWorld world, @Nullable PlayerEntity player) {
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
		if (!hasReward()) {
			wasClaimed = true;
		}
		
		if (quest.isEnd) {
			if (quest.getStage().id <= WSDStage.getStage().id) {
				WSDStage.setStage(world.getServer(), InitStages.getNextStage());
				System.out.println("The world has advanced to the next stage!");
			} else {
				System.err.println("Finished an end quest that should progress to the next stage but we're already there");
			}
		}
		
		WSDQuestStates.get(world.getServer()).setDirty();
		PrimalRework.NETWORK.sendPacketToAll(new PSyncQuestState(this));
	}
	
	public void forgetQuest(ServerWorld world) {
		System.out.println("Quest '" + quest.getName() + "' was forgotten!");
		isFinished = false;
		wasClaimed = false;
		
		WSDQuestStates.get(world.getServer()).setDirty();
		PrimalRework.NETWORK.sendPacketToAll(new PSyncQuestState(this));
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
		WSDQuestStates.get(player.getCommandSenderWorld().getServer()).setDirty();
		PrimalRework.NETWORK.sendPacketToAll(new PSyncQuestState(this));
	}
	
	public ItemStack getIcon() {
		return quest.itemIcon;
	}
	
	public String getName() {
		return quest.getName();
	}
	
	public ITextComponent getFancyName() {
		return quest.getFancyName();
	}
	
	public List<IFormattableTextComponent> getDescription() {
		return quest.getDescription();
	}
	
	public boolean hasParent() {
		return quest.hasParent();
	}
	
	public String getParentName() {
		return quest.getParent().getName();
	}
	
	public QuestState getParent() {
		return parentState;
	}
	
	public boolean hasReward() {
		return quest.reward != null;
	}
	
	public QuestReward getReward() {
		return quest.reward;
	}
	
	public QuestRequirement<?> getRequirement() {
		return quest.getRequirement();
	}
	
	public boolean isFinished() {
		return isFinished;
	}
	
	public boolean wasClaimed() {
		return wasClaimed;
	}
	
	public boolean isEnd() {
		return quest.isEnd;
	}
	
	public boolean isRoot() {
		return quest.isRoot;
	}
	
	public float getXPos() {
		return quest.getXPos();
	}
	
	public float getYPos() {
		return quest.getYPos();
	}
	
	@Override
	public Stage getStage() {
		return quest.getStage();
	}
	
	public boolean is(QuestState quest) {
		return this.quest == quest.quest;
	}
}
