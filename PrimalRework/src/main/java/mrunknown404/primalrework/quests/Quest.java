package mrunknown404.primalrework.quests;

import javax.annotation.Nullable;

import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.rewards.IQuestReward;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.world.storage.WorldSaveDataQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class Quest {
	protected final String name_key;
	protected final Style name_style, desc_style;
	protected final ItemStack itemIcon;
	protected final Quest parent;
	protected final QuestTab tab;
	protected final QuestRequirement req;
	protected final IQuestReward reward;
	protected final EnumStage stage;
	
	protected boolean isFinished;
	
	public Quest(EnumStage stage, String name_key, @Nullable Style name_style, @Nullable Style desc_style, Quest parent, @Nullable ItemStack itemIcon, QuestRequirement req,
			@Nullable IQuestReward reward) {
		this.stage = stage;
		this.name_key = name_key;
		this.name_style = name_style == null ? new Style() : name_style;
		this.desc_style = desc_style == null ? new Style() : desc_style;
		this.tab = InitQuests.QUEST_TABS.get(stage);
		this.parent = parent;
		this.itemIcon = itemIcon == null ? tab.getIcon() : itemIcon;
		this.req = req;
		this.reward = reward;
	}
	
	public Quest(String name_key, Quest parent, ItemStack itemIcon, QuestRequirement req) {
		this(parent.stage, name_key, null, null, parent, itemIcon, req, null);
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
	
	public EnumStage getStage() {
		return stage;
	}
	
	public String getName() {
		return stage.toString() + "_" + name_key;
	}
	
	public ITextComponent getFancyName() {
		return new TextComponentTranslation("quest." + stage + "." + name_key + ".name").setStyle(name_style);
	}
	
	public ITextComponent getFancyDesc() {
		return new TextComponentTranslation("quest." + stage + "." + name_key + ".desc").setStyle(name_style);
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public ItemStack getIcon() {
		return itemIcon;
	}
	
	/** Used for loading only! */
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	public boolean isFinished() {
		return isFinished;
	}
	
	public QuestRequirement getRequirement() {
		return req;
	}
}
