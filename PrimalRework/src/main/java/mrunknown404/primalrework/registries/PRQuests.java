package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.Quest.QuestBuilder;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.quests.requirements.ItemRequirement;
import mrunknown404.primalrework.quests.requirements.StagedTagRequirement;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class PRQuests {
	private static final Map<Item, QuestTab> QUEST_TABS = new LinkedHashMap<Item, QuestTab>();
	private static final List<Quest> QUESTS = new ArrayList<Quest>();
	private static List<Quest> questUnfinishedCache = new ArrayList<Quest>();
	
	public static final QuestTab TAB_STAGE_0 = addTab(new QuestTab(EnumStage.stage0, PRItems.PLANT_FIBER.get()));
	public static final QuestTab TAB_STAGE_1 = addTab(new QuestTab(EnumStage.stage1, Items.OAK_LOG));
	public static final QuestTab TAB_STAGE_2 = addTab(new QuestTab(EnumStage.stage2, Items.COBBLESTONE));
	public static final QuestTab TAB_STAGE_3 = addTab(new QuestTab(EnumStage.stage3, Items.COBBLESTONE));
	public static final QuestTab TAB_STAGE_4 = addTab(new QuestTab(EnumStage.stage4, Items.COBBLESTONE));
	
	//TODO redo quests! (i deleted some and forgot them)
	
	//@formatter:off
	public static final Quest STAGE_0_ROOT =              addRoot(QuestBuilder.create(EnumStage.stage0).finish(TAB_STAGE_0));
	public static final Quest GET_PLANT_FIBER =           addQuest(QuestBuilder.create("get_plant_fiber",   STAGE_0_ROOT,      0, -1.5f, new ItemRequirement(PRItems.PLANT_FIBER.get(), 8)).finish(                TAB_STAGE_0));
	public static final Quest GET_PLANT_ROPE =            addQuest(QuestBuilder.create("get_plant_rope",    GET_PLANT_FIBER,   0, 0,     new ItemRequirement(PRItems.PLANT_ROPE.get(), 1)).finish(                 TAB_STAGE_0));
	public static final Quest GET_PLANT_MESH =            addQuest(QuestBuilder.create("get_plant_mesh",    GET_PLANT_FIBER,   0, 1f,    new ItemRequirement(PRItems.PLANT_MESH.get(), 1)).finish(                 TAB_STAGE_0));
	public static final Quest GET_STICKS =                addQuest(QuestBuilder.create("get_sticks",        STAGE_0_ROOT,      0, -0.5f, new ItemRequirement(Items.STICK, 2)).finish(                                TAB_STAGE_0));
	public static final Quest GET_FLINT =                 addQuest(QuestBuilder.create("get_flint",         STAGE_0_ROOT,      0, 0.5f,  new ItemRequirement(Items.FLINT, 4)).finish(                                TAB_STAGE_0));
	public static final Quest GET_KNAPPED_FLINT =         addQuest(QuestBuilder.create("get_knapped_flint", GET_FLINT,         0, 0,     new ItemRequirement(PRItems.KNAPPED_FLINT.get(), 2)).finish(              TAB_STAGE_0));
	public static final Quest GET_FLINT_POINT =           addQuest(QuestBuilder.create("get_flint_point",   GET_KNAPPED_FLINT, 0, 0,     new ItemRequirement(PRItems.FLINT_POINT.get(), 2)).finish(                TAB_STAGE_0));
	public static final Quest GET_CLAY =                  addQuest(QuestBuilder.create("get_clay",          STAGE_0_ROOT,      0, 1.5f,  new ItemRequirement(Items.CLAY_BALL, 8)).finish(                            TAB_STAGE_0));
	public static final Quest GET_LOG =                   addQuest(QuestBuilder.create("get_log",           GET_CLAY,          0, 0,     new StagedTagRequirement(PRStagedTags.ALL_LOGS, Items.OAK_LOG, 1)).finish(TAB_STAGE_0));
	public static final Quest GET_CAMPFIRE =              addQuest(QuestBuilder.create("get_campfire",      GET_LOG,           0, 0,     new ItemRequirement(PRBlocks.CAMPFIRE.get().asItem(), 1)).setEnd().finish(TAB_STAGE_0));
	//@formatter:on
	
	private static Quest addQuest(Quest q) {
		QUESTS.add(q);
		q.getTab().addQuestToTab(q);
		
		if (!q.isRoot()) {
			q.getParent().addChild(q);
		}
		
		return q;
	}
	
	private static Quest addRoot(Quest q) {
		addQuest(q);
		q.getTab().setRoot(q);
		return q;
	}
	
	private static QuestTab addTab(QuestTab tab) {
		QUEST_TABS.put(tab.getIcon().getItem(), tab);
		return tab;
	}
	
	public static void resetQuestCache() {
		questUnfinishedCache.clear();
	}
	
	public static List<Quest> getQuestCache() {
		if (questUnfinishedCache.isEmpty()) {
			for (Quest q : QUESTS) {
				if (!q.isFinished() && !q.isRoot() && q.getParent().isFinished()) {
					questUnfinishedCache.add(q);
				}
			}
		}
		
		return questUnfinishedCache;
	}
	
	public static Collection<QuestTab> getTabs() {
		return QUEST_TABS.values();
	}
	
	public static void load() {
		System.out.println("Loaded " + QUESTS.size() + " quests!");
	}
	
	public static Quest findQuest(String nameKey) {
		for (Quest q : QUESTS) {
			if (q.getNameKey().equals(nameKey)) {
				return q;
			}
		}
		
		return null;
	}
	
	public static List<Quest> getAllQuests() {
		return QUESTS;
	}
}
