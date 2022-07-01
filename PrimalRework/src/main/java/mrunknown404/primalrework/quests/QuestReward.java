package mrunknown404.primalrework.quests;

import java.util.function.Consumer;

import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

public class QuestReward {
	private final ITextComponent description;
	private final Consumer<PlayerEntity> giveReward;
	
	public QuestReward(String name, Consumer<PlayerEntity> giveReward) {
		this.description = WordH.translate("quest.reward." + name + ".desc");
		this.giveReward = giveReward;
	}
	
	public void giveRewards(PlayerEntity player) {
		giveReward.accept(player);
	}
	
	public ITextComponent getDescription() {
		return description;
	}
}
