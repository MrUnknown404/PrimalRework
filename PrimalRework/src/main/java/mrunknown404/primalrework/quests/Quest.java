package mrunknown404.primalrework.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.quests.requirements.QuestRequirement;
import mrunknown404.primalrework.quests.rewards.QuestReward;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.IDescription;
import mrunknown404.primalrework.utils.IName;
import mrunknown404.primalrework.utils.helpers.StageH;
import mrunknown404.primalrework.utils.helpers.WordH;
import mrunknown404.primalrework.world.savedata.WSDQuests;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class Quest implements IName, IDescription {
	protected final String name_key;
	protected final ItemStack itemIcon;
	protected final Quest parent;
	protected final QuestTab tab;
	protected final QuestRequirement<?, ?> req;
	protected final QuestReward reward;
	protected final Supplier<Stage> stage;
	protected final boolean isEnd, isRoot;
	
	/** Grid pos! */
	protected float xPos, yPos;
	protected boolean isFinished;
	protected List<Quest> children = new ArrayList<Quest>();
	
	protected Quest(Supplier<Stage> stage, String name_key, Quest parent, float xPos, float yPos, ItemStack itemIcon, QuestRequirement<?, ?> req, @Nullable QuestReward reward,
			boolean isEnd, boolean isRoot, QuestTab tab) {
		this.stage = stage;
		this.name_key = name_key;
		this.tab = tab;
		this.parent = parent;
		this.xPos = xPos;
		this.yPos = yPos;
		this.itemIcon = itemIcon;
		this.req = req;
		this.reward = reward;
		this.isEnd = isEnd;
		this.isRoot = isRoot;
	}
	
	public void addChild(Quest q) {
		children.add(q);
	}
	
	public void finishQuest(ServerWorld world, @Nullable PlayerEntity player) {
		if (world.isClientSide) {
			System.out.println("Tried to finish quest on client side!");
			return;
		}
		
		if (isRoot) {
			System.out.println("Root quest '" + getName() + "' has been finished!");
		} else if (player != null) {
			System.out.println(player.getDisplayName().getString() + " has finished the quest '" + getName() + "'!");
		} else {
			System.out.println("Someone has finished the quest '" + getName() + "'!");
		}
		
		for (PlayerEntity pl : world.players()) {
			StringTextComponent space = WordH.space();
			
			if (player != null) {
				pl.sendMessage(
						((IFormattableTextComponent) player.getDisplayName()).append(space).append(WordH.translate("quest.finish.player")).append(space).append(getFancyName()),
						Util.NIL_UUID);
			} else {
				pl.sendMessage(WordH.translate("quest.finish.missing.pre").append(space).append(getFancyName()).append(space).append(WordH.translate("quest.finish.missing.post")),
						Util.NIL_UUID);
			}
		}
		
		isFinished = true;
		if (reward != null && player != null) { //TODO make this not auto claimed. add a button or something
			reward.giveRewardsToPlayer(player);
		}
		
		if (isEnd) {
			if (stage.get().id <= StageH.getStage().id) {
				StageH.setStage(world, PRStages.getNextStage());
				System.out.println("The world has advanced to the next stage!");
			} else {
				System.err.println("Finished an end quest that should progress to the next stage but we're already there");
			}
		}
		
		WSDQuests.get(world).setDirty();
	}
	
	public void forgetQuest(ServerWorld world) {
		System.out.println("Quest '" + getName() + "' was forgotten!");
		isFinished = false;
		WSDQuests.get(world).setDirty();
	}
	
	public void loadFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	public Supplier<Stage> getStage() {
		return stage;
	}
	
	@Override
	public IFormattableTextComponent getFancyName() {
		return WordH.translate("quest." + stage.get().nameID + "." + name_key + ".name");
	}
	
	/** @return stage + "." + name */
	@Override
	public String getName() {
		return stage.get().nameID + "." + name_key;
	}
	
	public String getNameKey() {
		return name_key;
	}
	
	@Override
	public List<IFormattableTextComponent> getFancyDescription() {
		List<IFormattableTextComponent> desc = new ArrayList<IFormattableTextComponent>();
		for (String s : WordH.translate("quest." + getName() + ".desc").getString().split("\\n")) {
			desc.add(WordH.string(s.trim()));
		}
		return desc;
	}
	
	@Override
	public List<String> getDescription() {
		List<String> desc = new ArrayList<String>();
		for (ITextComponent text : getFancyDescription()) {
			desc.add(text.getString());
		}
		return desc;
	}
	
	public QuestTab getTab() {
		return tab;
	}
	
	public QuestReward getReward() {
		return reward;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public ItemStack getIcon() {
		return itemIcon;
	}
	
	public boolean isFinished() {
		return isFinished;
	}
	
	public boolean isEnd() {
		return isEnd;
	}
	
	public boolean isRoot() {
		return isRoot;
	}
	
	public QuestRequirement<?, ?> getRequirement() {
		return req;
	}
	
	public boolean hasReward() {
		return reward != null;
	}
	
	public float getXPos() {
		float xx = !isRoot() ? parent.getXPos() : 0;
		return xPos + xx + (!isRoot() ? 1 : 0);
	}
	
	public float getYPos() {
		float yy = !isRoot() ? parent.getYPos() : 0;
		return yPos + yy;
	}
	
	public Quest getParent() {
		return parent;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name_key == null) ? 0 : name_key.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		Quest other = (Quest) obj;
		if (name_key == null || other.name_key != null) {
			return false;
		} else if (!name_key.equals(other.name_key)) {
			return false;
		}
		
		return true;
	}
	
	public static class QuestBuilder {
		//@Nullable QuestReward reward
		
		private final Supplier<Stage> stage;
		private final String name_key;
		private final Quest parent;
		private final float xPos, yPos;
		private final QuestRequirement<?, ?> req;
		private ItemStack itemIcon;
		private QuestReward reward;
		private boolean isRoot;
		
		private QuestBuilder(Supplier<Stage> stage, String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?, ?> req) {
			this.stage = stage;
			this.name_key = name_key;
			this.parent = parent;
			this.xPos = xPos;
			this.yPos = yPos;
			this.req = req;
		}
		
		public static Quest root(Supplier<Stage> stage, QuestTab tab) {
			return new Quest(stage, "root", null, 0, 0, tab.getIcon(), null, null, false, true, tab);
		}
		
		public static QuestBuilder create(String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?, ?> req) {
			return new QuestBuilder(parent.stage, name_key, parent, xPos, yPos, req).setIcon(req.getIcon());
		}
		
		public QuestBuilder setIcon(StagedItem itemIcon) {
			this.itemIcon = new ItemStack(itemIcon);
			return this;
		}
		
		public QuestBuilder setRoot() {
			this.isRoot = true;
			return this;
		}
		
		public QuestBuilder setReward(QuestReward reward) {
			this.reward = reward;
			return this;
		}
		
		public Quest finish() {
			return new Quest(stage, name_key, parent, xPos, yPos, itemIcon, req, reward, false, isRoot, parent.tab);
		}
		
		public Quest finishAsEnd() {
			return new Quest(stage, name_key, parent, xPos, yPos, itemIcon, req, reward, true, isRoot, parent.tab);
		}
	}
}
