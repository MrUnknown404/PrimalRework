package mrunknown404.primalrework.quests;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.network.NetworkHandler;
import mrunknown404.primalrework.network.packets.PacketSyncQuestFinished;
import mrunknown404.primalrework.quests.requirements.QuestRequirement;
import mrunknown404.primalrework.quests.rewards.QuestReward;
import mrunknown404.primalrework.stage.StageH;
import mrunknown404.primalrework.utils.IDescription;
import mrunknown404.primalrework.utils.IName;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

//TODO save quests!

public class Quest implements IName, IDescription {
	protected final String name_key;
	protected final ItemStack itemIcon;
	protected final Quest parent;
	protected final QuestTab tab;
	protected final QuestRequirement<?, ?> req;
	protected final QuestReward reward;
	protected final EnumStage stage;
	protected final boolean isEnd, isRoot;
	
	/** Grid pos! */
	protected float xPos, yPos;
	protected boolean isFinished;
	protected List<Quest> children = new ArrayList<Quest>();
	
	protected Quest(EnumStage stage, String name_key, Quest parent, float xPos, float yPos, ItemStack itemIcon, QuestRequirement<?, ?> req, @Nullable QuestReward reward,
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
	
	public void finishQuest(World world, @Nullable PlayerEntity player) {
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
			StringTextComponent space = new StringTextComponent(" ");
			
			if (player != null) {
				pl.sendMessage(((IFormattableTextComponent) player.getDisplayName()).append(space).append(new TranslationTextComponent("quest.finish.player")).append(space)
						.append(getFancyName()), Util.NIL_UUID);
			} else {
				pl.sendMessage(new TranslationTextComponent("quest.finish.missing.pre").append(space).append(getFancyName()).append(space)
						.append(new TranslationTextComponent("quest.finish.missing.post")), Util.NIL_UUID);
			}
		}
		
		isFinished = true;
		InitQuests.resetQuestCache();
		if (reward != null && player != null) {
			reward.giveRewardsToPlayer(player);
		}
		
		if (isEnd) {
			StageH.setStage(StageH.getNextStage());
			System.out.println("The world has advanced to the next stage!");
		}
		
		NetworkHandler.sendPacketToAll(new PacketSyncQuestFinished(this));
	}
	
	public void forgetQuest(@SuppressWarnings("unused") World world) {
		System.out.println("Quest '" + getName() + "' was forgotten!");
		
		isFinished = false;
		InitQuests.resetQuestCache();
	}
	
	public void loadFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	public EnumStage getStage() {
		return stage;
	}
	
	@Override
	public IFormattableTextComponent getFancyName() {
		return new TranslationTextComponent("quest." + stage + "." + name_key + ".name");
	}
	
	/** @return stage + "_" + name */
	@Override
	public String getName() {
		return stage.toString() + "_" + name_key;
	}
	
	public String getNameKey() {
		return name_key;
	}
	
	@Override
	public List<IFormattableTextComponent> getFancyDescription() {
		List<IFormattableTextComponent> desc = new ArrayList<IFormattableTextComponent>();
		for (String s : new TranslationTextComponent("quest." + stage + "." + name_key + ".desc").getString().split("\\n")) {
			desc.add(new StringTextComponent(s.trim()));
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
		
		private final EnumStage stage;
		private final String name_key;
		private final Quest parent;
		private final float xPos, yPos;
		private final QuestRequirement<?, ?> req;
		private ItemStack itemIcon;
		private QuestReward reward;
		private boolean isEnd, isRoot;
		
		private QuestBuilder(EnumStage stage, String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?, ?> req) {
			this.stage = stage;
			this.name_key = name_key;
			this.parent = parent;
			this.xPos = xPos;
			this.yPos = yPos;
			this.req = req;
		}
		
		public static QuestBuilder create(EnumStage stage) {
			return new QuestBuilder(stage, "root", null, 0, 0, null).setRoot();
		}
		
		public static QuestBuilder create(String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?, ?> req) {
			return new QuestBuilder(parent.stage, name_key, parent, xPos, yPos, req).setIcon(req.getIcon());
		}
		
		public QuestBuilder setIcon(Item itemIcon) {
			this.itemIcon = new ItemStack(itemIcon);
			return this;
		}
		
		public QuestBuilder setEnd() {
			this.isEnd = true;
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
		
		public Quest finish(QuestTab tab) {
			if (itemIcon == null) {
				itemIcon = tab.getIcon();
			}
			
			return new Quest(stage, name_key, parent, xPos, yPos, itemIcon, req, reward, isEnd, isRoot, tab);
		}
	}
}
