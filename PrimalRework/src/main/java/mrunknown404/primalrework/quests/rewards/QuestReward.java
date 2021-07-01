package mrunknown404.primalrework.quests.rewards;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class QuestReward {
	private final ITextComponent description;
	
	public QuestReward(String name) {
		this.description = new TranslationTextComponent("quest.reward." + name + ".desc");
	}
	
	public abstract void giveRewardsToPlayer(PlayerEntity player);
	
	public ITextComponent getDescription() {
		return description;
	}
}
