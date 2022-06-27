package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.Quest.QuestBuilder;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.quests.requirements.ItemRequirement;

public class PRQuests {
	private static final Map<String, Integer> QUEST_NAME_INDEX_MAP = new HashMap<String, Integer>();
	private static final List<Quest> QUESTS = new ArrayList<Quest>();
	private static final List<QuestTab> QUEST_TABS = new ArrayList<QuestTab>();
	
	public static final QuestTab TAB_STAGE_0 = addTab(new QuestTab(PRStages.STAGE_0, PRItems.PLANT_FIBER.get()));
	public static final QuestTab TAB_STAGE_1 = addTab(new QuestTab(PRStages.STAGE_1, PRBlocks.OAK_LOG.get().asStagedItem()));
	public static final QuestTab TAB_STAGE_2 = addTab(new QuestTab(PRStages.STAGE_2, PRBlocks.COBBLESTONE.get().asStagedItem()));
	public static final QuestTab TAB_STAGE_3 = addTab(new QuestTab(PRStages.STAGE_3, PRItems.PLANT_FIBER.get()));
	public static final QuestTab TAB_STAGE_4 = addTab(new QuestTab(PRStages.STAGE_4, PRItems.PLANT_FIBER.get()));
	
	public static final Quest STAGE_0_ROOT = addRoot(QuestBuilder.root(PRStages.STAGE_0, TAB_STAGE_0));
	public static final Quest STAGE_1_ROOT = addRoot(QuestBuilder.root(PRStages.STAGE_1, TAB_STAGE_1));
	public static final Quest STAGE_2_ROOT = addRoot(QuestBuilder.root(PRStages.STAGE_2, TAB_STAGE_2));
	public static final Quest STAGE_3_ROOT = addRoot(QuestBuilder.root(PRStages.STAGE_3, TAB_STAGE_3));
	public static final Quest STAGE_4_ROOT = addRoot(QuestBuilder.root(PRStages.STAGE_4, TAB_STAGE_4));
	
	//@formatter:off
	//STAGE 0
	public static final Quest GET_PLANT_FIBER   = addQuest(QuestBuilder.create("get_plant_fiber",   STAGE_0_ROOT,      0,  0, new ItemRequirement(PRItems.PLANT_FIBER.get(),             12)).finish());
	public static final Quest GET_PLANT_MESH    = addQuest(QuestBuilder.create("get_plant_mesh",    GET_PLANT_FIBER,   0,  0, new ItemRequirement(PRItems.PLANT_MESH.get(),               1)).finish());
	public static final Quest GET_PLANT_ROPE    = addQuest(QuestBuilder.create("get_plant_rope",    GET_PLANT_FIBER,   0, -1, new ItemRequirement(PRItems.PLANT_ROPE.get(),               4)).finish());
	public static final Quest GET_THATCH        = addQuest(QuestBuilder.create("get_thatch",        GET_PLANT_FIBER,   0, -2, new ItemRequirement(PRBlocks.THATCH.get().asStagedItem(),  16)).finish());
	public static final Quest GET_FLINT         = addQuest(QuestBuilder.create("get_flint",         STAGE_0_ROOT,      0,  1, new ItemRequirement(PRItems.FLINT.get(),                    4)).finish());
	public static final Quest GET_KNAPPED_FLINT = addQuest(QuestBuilder.create("get_knapped_flint", GET_FLINT,         0,  0, new ItemRequirement(PRItems.KNAPPED_FLINT.get(),            2)).finish());
	public static final Quest GET_FLINT_POINT   = addQuest(QuestBuilder.create("get_flint_point",   GET_KNAPPED_FLINT, 0,  0, new ItemRequirement(PRItems.FLINT_POINT.get(),              2)).finish());
	public static final Quest GET_CLAY          = addQuest(QuestBuilder.create("get_clay",          STAGE_0_ROOT,      0,  2, new ItemRequirement(PRItems.CLAY_BALL.get(),               16)).finish());
	public static final Quest GET_CLAY_SHOVEL   = addQuest(QuestBuilder.create("get_clay_shovel",   GET_CLAY,          0,  0, new ItemRequirement(PRItems.CLAY_SHOVEL.get(),              1)).finish());
	public static final Quest GET_SALT          = addQuest(QuestBuilder.create("get_salt",          GET_CLAY_SHOVEL,   0,  0, new ItemRequirement(PRItems.SALT.get(),                    16)).finish());
	public static final Quest GET_CLAY_AXE      = addQuest(QuestBuilder.create("get_clay_axe",      GET_CLAY,          0,  1, new ItemRequirement(PRItems.CLAY_AXE.get(),                 1)).finish());
	public static final Quest GET_LOGS          = addQuest(QuestBuilder.create("get_logs",          GET_CLAY_AXE,      0,  0, new ItemRequirement(PRBlocks.OAK_LOG.get().asStagedItem(),  1)).finish()); //TODO switch this to staged tag requirement
	public static final Quest GET_BARK          = addQuest(QuestBuilder.create("get_bark",          GET_CLAY_AXE,      0,  1, new ItemRequirement(PRItems.BARK.get(),                     4)).finish());
	public static final Quest GET_CLAY_POT      = addQuest(QuestBuilder.create("get_clay_pot",      GET_CLAY,          0,  2, new ItemRequirement(PRItems.CLAY_BALL.get(),                1)).finish()); //TODO switch this to clay pot
	public static final Quest GET_STICK         = addQuest(QuestBuilder.create("get_stick",         STAGE_0_ROOT,      0,  3, new ItemRequirement(PRItems.STICK.get(),                    4)).finish());
	//@formatter:on
	
	public static void load() {
		System.out.println("Loaded '" + QUEST_TABS.size() + "' quest tabs!");
		System.out.println("Loaded '" + QUESTS.size() + "' quests!");
	}
	
	private static Quest addQuest(Quest q) {
		QUEST_NAME_INDEX_MAP.put(q.getName(), QUESTS.size());
		QUESTS.add(q);
		q.getTab().addQuestToTab(q);
		
		if (!q.isRoot()) {
			q.getParent().addChild(q);
		}
		
		return q;
	}
	
	private static Quest addRoot(Quest q) {
		q.getTab().setRoot(q);
		return addQuest(q);
	}
	
	private static QuestTab addTab(QuestTab tab) {
		QUEST_TABS.add(tab);
		return tab;
	}
	
	public static List<Quest> getQuests() {
		return QUESTS;
	}
	
	public static List<QuestTab> getTabs() {
		return QUEST_TABS;
	}
	
	public static Quest getFromName(String name) {
		return QUESTS.get(QUEST_NAME_INDEX_MAP.getOrDefault(name, -1));
	}
}
