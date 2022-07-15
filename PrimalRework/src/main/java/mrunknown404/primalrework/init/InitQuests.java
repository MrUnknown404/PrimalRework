package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestReward;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.quests.requirements.QRItem;
import mrunknown404.primalrework.quests.requirements.QRTag;

public class InitQuests {
	private static final Map<String, Integer> QUEST_NAME_INDEX_MAP = new HashMap<String, Integer>();
	private static final List<Quest> QUESTS = new ArrayList<Quest>();
	private static final List<QuestTab> QUEST_TABS = new ArrayList<QuestTab>();
	
	public static final QuestTab TAB_STAGE_BEFORE = addTab(new QuestTab(InitStages.STAGE_BEFORE, InitItems.PLANT_FIBER));
	public static final QuestTab TAB_STAGE_STONE = addTab(new QuestTab(InitStages.STAGE_STONE, InitBlocks.CAMPFIRE));
	public static final QuestTab TAB_STAGE_COPPER = addTab(new QuestTab(InitStages.STAGE_COPPER, InitItems.getIngot(InitMetals.COPPER)));
	public static final QuestTab TAB_STAGE_BRONZE = addTab(new QuestTab(InitStages.STAGE_BRONZE, InitItems.getIngot(InitMetals.BRONZE)));
	
	public static final Quest STAGE_BEFORE_ROOT = addRoot(Quest.root(InitStages.STAGE_BEFORE, TAB_STAGE_BEFORE));
	public static final Quest STAGE_STONE_ROOT = addRoot(Quest.root(InitStages.STAGE_STONE, TAB_STAGE_STONE));
	public static final Quest STAGE_COPPER_ROOT = addRoot(Quest.root(InitStages.STAGE_COPPER, TAB_STAGE_COPPER));
	public static final Quest STAGE_BRONZE_ROOT = addRoot(Quest.root(InitStages.STAGE_BRONZE, TAB_STAGE_BRONZE));
	
	//@formatter:off
	//STAGE 0
	public static final Quest GET_PLANT_FIBER    = addQuest(Quest.simple("get_plant_fiber",    STAGE_BEFORE_ROOT,      0,  0, new QRItem(InitItems.PLANT_FIBER,     4), new QuestReward("test", p -> System.out.println("reward given!"))));
	public static final Quest GET_PLANT_MESH     = addQuest(Quest.simple("get_plant_mesh",     GET_PLANT_FIBER,   0,  0, new QRItem(InitItems.PLANT_MESH,      1)));
	public static final Quest GET_PLANT_ROPE     = addQuest(Quest.simple("get_plant_rope",     GET_PLANT_FIBER,   0, -1, new QRItem(InitItems.PLANT_ROPE,      2)));
	public static final Quest GET_THATCH         = addQuest(Quest.simple("get_thatch",         GET_PLANT_FIBER,   0, -2, new QRItem(InitBlocks.THATCH,         4)));
	public static final Quest GET_FLINT          = addQuest(Quest.simple("get_flint",          STAGE_BEFORE_ROOT,      0,  1, new QRItem(InitItems.FLINT,           4)));
	public static final Quest GET_KNAPPED_FLINT  = addQuest(Quest.simple("get_knapped_flint",  GET_FLINT,         0,  0, new QRItem(InitItems.KNAPPED_FLINT,   2)));
	public static final Quest GET_FLINT_POINT    = addQuest(Quest.simple("get_flint_point",    GET_KNAPPED_FLINT, 0,  0, new QRItem(InitItems.FLINT_POINT,     2)));
	public static final Quest GET_CLAY           = addQuest(Quest.simple("get_clay",           STAGE_BEFORE_ROOT,      0,  2, new QRItem(InitItems.CLAY_BALL,       4)));
	public static final Quest GET_CLAY_SHOVEL    = addQuest(Quest.simple("get_clay_shovel",    GET_CLAY,          0,  0, new QRItem(InitItems.CLAY_SHOVEL,     1)));
	public static final Quest GET_SALT           = addQuest(Quest.simple("get_salt",           GET_CLAY_SHOVEL,   0,  0, new QRItem(InitItems.SALT,            16)));
	public static final Quest GET_CLAY_AXE       = addQuest(Quest.simple("get_clay_axe",       GET_CLAY,          0,  1, new QRItem(InitItems.CLAY_AXE,        1)));
	public static final Quest GET_LOGS           = addQuest(Quest.simple("get_logs",           GET_CLAY_AXE,      0,  0, new QRTag(InitStagedTags.LOGS,        1)));
	public static final Quest GET_CRAFTING_TABLE = addQuest(Quest.end(   "get_crafting_table", GET_LOGS,          0,  0, new QRItem(InitBlocks.CRAFTING_TABLE, 4)));
	public static final Quest GET_BARK           = addQuest(Quest.simple("get_bark",           GET_CLAY_AXE,      0,  1, new QRItem(InitItems.BARK,            4)));
	public static final Quest GET_CLAY_POT       = addQuest(Quest.simple("get_clay_pot",       GET_CLAY,          0,  2, new QRItem(InitItems.CLAY_BALL,       1))); //TODO switch this to clay pot
	public static final Quest GET_STICK          = addQuest(Quest.simple("get_stick",          STAGE_BEFORE_ROOT,      0,  3, new QRItem(InitItems.STICK,           4)));
	public static final Quest GET_ROCK           = addQuest(Quest.simple("get_rock",           STAGE_BEFORE_ROOT,      0,  5, new QRItem(InitItems.ROCK,            8)));
	public static final Quest GET_COBBLESTONE    = addQuest(Quest.simple("get_cobblestone",    GET_ROCK,          0,  0, new QRItem(InitBlocks.COBBLESTONE,    4)));
	//@formatter:on
	
	public static Quest addQuest(Quest q) {
		QUEST_NAME_INDEX_MAP.put(q.getName(), QUESTS.size());
		QUESTS.add(q);
		q.getTab().addQuestToTab(q);
		
		if (q.hasParent()) {
			q.getParent().addChild(q);
		}
		
		return q;
	}
	
	public static Quest addRoot(Quest q) {
		q.getTab().setRoot(q);
		return addQuest(q);
	}
	
	public static QuestTab addTab(QuestTab tab) {
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
	
	public static List<String> getRecipeListPrint() {
		List<String> list = new ArrayList<String>();
		list.add("Loaded " + QUEST_TABS.size() + " quest tabs!");
		list.add("Loaded " + QUESTS.size() + " quests!");
		return list;
	}
}
