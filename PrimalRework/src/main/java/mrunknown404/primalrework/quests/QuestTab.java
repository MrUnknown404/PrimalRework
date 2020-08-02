package mrunknown404.primalrework.quests;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.util.enums.EnumStage;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class QuestTab {
	private final List<Quest> quests = new ArrayList<Quest>();
	private QuestRoot root;
	
	private final EnumStage stage;
	private final ItemStack itemIcon;
	
	public QuestTab(EnumStage stage, ItemStack itemIcon) {
		this.stage = stage;
		this.itemIcon = itemIcon;
	}
	
	public void addQuestToTab(Quest q) {
		quests.add(q);
	}
	
	public void setRoot(QuestRoot root) {
		this.root = root;
	}
	
	public Quest get(int i) {
		return quests.get(i);
	}
	
	public List<Quest> get() {
		return quests;
	}
	
	public QuestRoot getRoot() {
		return root;
	}
	
	public String getName() {
		return stage.getName();
	}
	
	public String getDesc() {
		return I18n.format("quest_tab." + stage.toString() + ".desc");
	}
	
	public EnumStage getStage() {
		return stage;
	}
	
	public ItemStack getIcon() {
		return itemIcon;
	}
}
