package mrunknown404.primalrework.quests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import mrunknown404.primalrework.items.raw.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class QuestTab {
	private final List<Quest> quests = new ArrayList<Quest>();
	private Quest root;
	
	private final Supplier<Stage> stage;
	private final ItemStack itemIcon;
	
	public QuestTab(Supplier<Stage> stage, StagedItem itemIcon) {
		this.stage = stage;
		this.itemIcon = new ItemStack(itemIcon);
	}
	
	public void addQuestToTab(Quest q) {
		quests.add(q);
	}
	
	public void setRoot(Quest root) {
		this.root = root;
	}
	
	public Quest get(int i) {
		return quests.get(i);
	}
	
	public List<Quest> get() {
		return quests;
	}
	
	public Quest getRoot() {
		return root;
	}
	
	public String getName() {
		return stage.get().getName();
	}
	
	public String getDesc() {
		return I18n.get("quest_tab." + stage.toString() + ".desc");
	}
	
	public Supplier<Stage> getStage() {
		return stage;
	}
	
	public ItemStack getIcon() {
		return itemIcon;
	}
	
	public void sort() {
		quests.sort(COMPARE);
	}
	
	private static final CompareQuest COMPARE = new CompareQuest();
	
	private static class CompareQuest implements Comparator<Quest> {
		@Override
		public int compare(Quest o1, Quest o2) {
			return o1.name_key.compareTo(o2.name_key);
		}
	}
}
