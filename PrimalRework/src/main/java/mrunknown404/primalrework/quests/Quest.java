package mrunknown404.primalrework.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import mrunknown404.primalrework.api.utils.IStageProvider;
import mrunknown404.primalrework.quests.requirements.QuestRequirement;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;

public class Quest implements IStageProvider {
	protected final String name_key;
	protected final ItemStack itemIcon;
	protected final Quest parent;
	protected final QuestTab tab;
	protected final QuestRequirement<?> req;
	protected final QuestReward reward;
	protected final Supplier<Stage> stage;
	protected final boolean isEnd, isRoot;
	
	/** Grid pos! */
	protected final float xPos, yPos;
	protected List<Quest> children = new ArrayList<Quest>();
	
	private Quest(Supplier<Stage> stage, String name_key, Quest parent, float xPos, float yPos, ItemStack itemIcon, QuestRequirement<?> req, @Nullable QuestReward reward,
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
	
	public static Quest simple(String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?> req) {
		return new Quest(parent.stage, name_key, parent, xPos, yPos, new ItemStack(req.getIcon()), req, null, false, false, parent.tab);
	}
	
	public static Quest simple(String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?> req, QuestReward reward) {
		return new Quest(parent.stage, name_key, parent, xPos, yPos, new ItemStack(req.getIcon()), req, reward, false, false, parent.tab);
	}
	
	public static Quest end(String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?> req) {
		return new Quest(parent.stage, name_key, parent, xPos, yPos, new ItemStack(req.getIcon()), req, null, true, false, parent.tab);
	}
	
	public static Quest end(String name_key, Quest parent, float xPos, float yPos, QuestRequirement<?> req, QuestReward reward) {
		return new Quest(parent.stage, name_key, parent, xPos, yPos, new ItemStack(req.getIcon()), req, reward, true, false, parent.tab);
	}
	
	public static Quest root(Supplier<Stage> stage, QuestTab tab) {
		return new Quest(stage, "root", null, 0, 0, tab.getIcon(), null, null, false, true, tab);
	}
	
	public void addChild(Quest q) {
		children.add(q);
	}
	
	@Override
	public Stage getStage() {
		return stage.get();
	}
	
	public IFormattableTextComponent getFancyName() {
		return WordH.translate("quest." + stage.get().getNameID() + "." + name_key + ".name");
	}
	
	/** @return stage + "." + name */
	public String getName() {
		return stage.get().getNameID() + "." + name_key;
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
	
	public boolean isEnd() {
		return isEnd;
	}
	
	public boolean isRoot() {
		return isRoot;
	}
	
	public QuestRequirement<?> getRequirement() {
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
	
	public boolean hasParent() {
		return parent != null;
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
