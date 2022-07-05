package mrunknown404.primalrework.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.api.utils.IStageProvider;
import mrunknown404.primalrework.stage.Stage;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class QuestTab implements IStageProvider {
	private final List<Quest> quests = new ArrayList<Quest>();
	private Quest root;
	
	private final Supplier<Stage> stage;
	private final ItemStack itemIcon;
	
	public QuestTab(Supplier<Stage> stage, ISIProvider itemIcon) {
		this.stage = stage;
		this.itemIcon = new ItemStack(itemIcon.getStagedItem());
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
	
	@Override
	public Stage getStage() {
		return stage.get();
	}
	
	public ItemStack getIcon() {
		return itemIcon;
	}
}
