package mrunknown404.primalrework.quests;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.rewards.IQuestReward;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.world.storage.WorldSaveDataQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class Quest {
	protected final String name_key;
	protected final ItemStack itemIcon;
	protected final Quest parent;
	protected final QuestTab tab;
	protected final QuestRequirement req;
	protected final IQuestReward reward;
	protected final EnumStage stage;
	
	/** Grid pos! */
	protected float xPos, yPos, xOffset, yOffset;
	
	protected boolean isFinished;
	protected List<Quest> children = new ArrayList<Quest>();
	
	public Quest(EnumStage stage, String name_key, Quest parent, @Nullable ItemStack itemIcon, QuestRequirement req, @Nullable IQuestReward reward) {
		this.stage = stage;
		this.name_key = name_key;
		this.tab = InitQuests.QUEST_TABS.get(stage);
		this.parent = parent;
		this.itemIcon = itemIcon == null ? tab.getIcon() : itemIcon;
		this.req = req;
		this.reward = reward;
	}
	
	public Quest(String name_key, Quest parent, ItemStack itemIcon, QuestRequirement req) {
		this(parent.stage, name_key, parent, itemIcon, req, null);
	}
	
	public Quest(String name_key, Quest parent, QuestRequirement req) {
		this(parent.stage, name_key, parent, req.getItemToCollect().get(0), req, null);
	}
	
	public void addChild(Quest q) {
		children.add(q);
	}
	
	public void checkRequirements(World world, EntityPlayer player, int amount) {
		if (!isFinished && parent.isFinished) {
			if (amount >= req.getAmountNeeded()) {
				finishQuest(world, player);
			}
		}
	}
	
	public final void finishQuest(World world, @Nullable EntityPlayer player) {
		if (this instanceof QuestRoot) {
			System.out.println("Root quest '" + getName() + "' has been finished!");
		} else if (player != null) {
			System.out.println(player.getDisplayName().getUnformattedText() + " has finished the quest '" + getName() + "'!");
		} else {
			System.out.println("Someone has finished the quest '" + getName() + "'!");
		}
		
		WorldSaveDataQuest.load(world).markDirty();
		
		isFinished = true;
		InitQuests.resetQuestCache();
		if (reward != null && player != null) {
			reward.giveRewardsToPlayer(player);
		}
	}
	
	/** Used for loading only! */
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	public EnumStage getStage() {
		return stage;
	}
	
	/** @return stage + "_" + name */
	public String getName() {
		return stage.toString() + "_" + name_key;
	}
	
	public ITextComponent getFancyName() {
		return new TextComponentTranslation("quest." + stage + "." + name_key + ".name");
	}
	
	public ITextComponent getFancyDesc() {
		return new TextComponentTranslation("quest." + stage + "." + name_key + ".desc");
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
	
	public QuestRequirement getRequirement() {
		return req;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	public void setupPositions() { //TODO setup!
		Quest q = parent;
		int xx = 0;
		if (parent != null) {
			while (true) {
				xx++;
				if (!q.hasParent()) {
					break;
				}
				q = q.parent;
			}
		}
		
		this.xPos = xx;
	}
	
	public float getXPos() {
		return xPos;
	}
	
	public float getYPos() {
		return yPos;
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
		if (name_key == null) {
			if (other.name_key != null) {
				return false;
			}
		} else if (!name_key.equals(other.name_key)) {
			return false;
		}
		
		return true;
	}
}
