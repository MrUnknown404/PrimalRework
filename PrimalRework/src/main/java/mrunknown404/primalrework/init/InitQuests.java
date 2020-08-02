package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestRequirement;
import mrunknown404.primalrework.quests.QuestRoot;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.StageHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class InitQuests {
	public static final Map<EnumStage, QuestTab> QUEST_TABS = new HashMap<EnumStage, QuestTab>();
	public static final List<Quest> QUESTS = new ArrayList<Quest>();
	private static List<Quest> questUnfinishedCache = new ArrayList<Quest>();
	
	public static final QuestTab TAB_STAGE_0 = addTab(new QuestTab(EnumStage.stage0, new ItemStack(Items.STICK)));
	public static final QuestTab TAB_STAGE_1 = addTab(new QuestTab(EnumStage.stage1, new ItemStack(Blocks.COBBLESTONE)));
	public static final QuestTab TAB_STAGE_2 = addTab(new QuestTab(EnumStage.stage2, new ItemStack(ModBlocks.FIRE_PIT)));
	public static final QuestTab TAB_STAGE_3 = addTab(new QuestTab(EnumStage.stage3, new ItemStack(ModBlocks.CLAY_FURNACE)));
	
	public static final QuestRoot STAGE_0_ROOT = addRoot(new QuestRoot(EnumStage.stage0));
	public static final QuestRoot STAGE_1_ROOT = addRoot(new QuestRoot(EnumStage.stage1));
	public static final QuestRoot STAGE_2_ROOT = addRoot(new QuestRoot(EnumStage.stage2));
	public static final QuestRoot STAGE_3_ROOT = addRoot(new QuestRoot(EnumStage.stage3));
	
	public static final Quest STAGE_0_GET_STICKS = addQuest(
			new Quest("get_sticks", STAGE_0_ROOT, new ItemStack(Items.STICK), new QuestRequirement(new ItemStack(Items.STICK, 1))));
	
	private static Quest addQuest(Quest q) {
		QUESTS.add(q);
		QUEST_TABS.get(q.getStage()).addQuestToTab(q);
		return q;
	}
	
	private static QuestRoot addRoot(QuestRoot q) {
		addQuest(q);
		QUEST_TABS.get(q.getStage()).setRoot(q);
		return q;
	}
	
	private static QuestTab addTab(QuestTab tab) {
		QUEST_TABS.put(tab.getStage(), tab);
		return tab;
	}
	
	public static void load() {
		System.out.println("Loaded " + QUESTS.size() + " quests!");
	}
	
	public static List<Quest> getQuestCache() {
		if (questUnfinishedCache.isEmpty()) {
			for (EnumStage stage : StageHelper.getAllPrevStages()) {
				if (InitQuests.QUEST_TABS.get(stage) != null) {
					for (Quest q : QUEST_TABS.get(stage).get()) {
						if (!q.isFinished()) {
							questUnfinishedCache.add(q);
						}
					}
				}
			}
		}
		
		return new ArrayList<Quest>(questUnfinishedCache);
	}
	
	public static void resetQuestCache() {
		questUnfinishedCache.clear();
	}
	
	public static void loadQuestFromMap(Map<String, Boolean> quests) {
		for (Quest q : QUESTS) {
			if (quests.containsKey(q.getName())) {
				q.setFinished(quests.get(q.getName()));
			}
		}
	}
}
