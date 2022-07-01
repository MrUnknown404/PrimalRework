package mrunknown404.primalrework.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.quests.requirements.QuestRequirement;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.helpers.StageH;
import mrunknown404.primalrework.utils.helpers.WordH;
import mrunknown404.primalrework.world.savedata.WSDQuests;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class Quest {
	protected final String name_key;
	protected final ItemStack itemIcon;
	protected final Quest parent;
	protected final QuestTab tab;
	protected final QuestRequirement<?, ?> req;
	protected final QuestReward reward;
	protected final Supplier<Stage> stage;
	protected final boolean isEnd, isRoot;
	
	/** Grid pos! */
	protected final float xPos, yPos;
	protected boolean isFinished;
	protected List<Quest> children = new ArrayList<Quest>();
	
	private Quest(Supplier<Stage> stage, String name_key, Quest parent, float xPos, float yPos, ItemStack itemIcon, QuestRequirement<?, ?> req, @Nullable QuestReward reward,
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
	
	public static Quest simple(String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?, ?> req) {
		return new Quest(parent.stage, name_key, parent, xPos, yPos, new ItemStack(req.getIcon()), req, null, false, false, parent.tab);
	}
	
	public static Quest simple(String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?, ?> req, QuestReward reward) {
		return new Quest(parent.stage, name_key, parent, xPos, yPos, new ItemStack(req.getIcon()), req, reward, false, false, parent.tab);
	}
	
	public static Quest end(String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?, ?> req) {
		return new Quest(parent.stage, name_key, parent, xPos, yPos, new ItemStack(req.getIcon()), req, null, true, false, parent.tab);
	}
	
	public static Quest end(String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?, ?> req, QuestReward reward) {
		return new Quest(parent.stage, name_key, parent, xPos, yPos, new ItemStack(req.getIcon()), req, reward, true, false, parent.tab);
	}
	
	public static Quest root(Supplier<Stage> stage, QuestTab tab) {
		return new Quest(stage, "root", null, 0, 0, tab.getIcon(), null, null, false, true, tab);
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
			reward.giveRewards(player);
		}
		
		if (isEnd) {
			if (stage.get().id <= StageH.getStage().id) {
				StageH.setStage(world, InitStages.getNextStage());
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
	
	public IFormattableTextComponent getFancyName() {
		return WordH.translate("quest." + stage.get().getNameID() + "." + name_key + ".name");
	}
	
	/** @return stage + "." + name */
	public String getName() {
		return stage.get().getNameID() + "." + name_key;
	}
	
	public String getNameKey() {
		return name_key;
	}
	
	public List<IFormattableTextComponent> getDescription() {
		List<IFormattableTextComponent> desc = new ArrayList<IFormattableTextComponent>();
		for (String s : WordH.translate("quest." + getName() + ".desc").getString().split("\\n")) {
			desc.add(WordH.string(s.trim()));
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
		return Objects.hash(name_key);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this != obj || obj == null || getClass() != obj.getClass()) {
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
}
