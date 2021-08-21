package mrunknown404.primalrework.quests.rewards;

import mrunknown404.primalrework.helpers.WordH;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

public abstract class QuestReward {
	private final ITextComponent description;
	
	public QuestReward(String name) {
		this.description = WordH.translate("quest.reward." + name + ".desc");
	}
	
	public abstract void giveRewardsToPlayer(PlayerEntity player);
	
	public ITextComponent getDescription() {
		return description;
	}
}
