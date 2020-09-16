package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestEnd;
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
	
	public static final QuestTab TAB_STAGE_0 = addTab(new QuestTab(EnumStage.stage0, new ItemStack(Blocks.LOG)));
	public static final QuestTab TAB_STAGE_1 = addTab(new QuestTab(EnumStage.stage1, new ItemStack(Blocks.COBBLESTONE)));
	public static final QuestTab TAB_STAGE_2 = addTab(new QuestTab(EnumStage.stage2, new ItemStack(InitBlocks.FIRE_PIT)));
	public static final QuestTab TAB_STAGE_3 = addTab(new QuestTab(EnumStage.stage3, new ItemStack(InitBlocks.CLAY_FURNACE)));
	
	//@formatter:off
	public static final QuestRoot STAGE_0_ROOT =          addRoot(new QuestRoot(EnumStage.stage0));
	public static final Quest GET_PLANT_FIBER =           addQuest(new Quest("get_plant_fiber",           STAGE_0_ROOT,              new QuestRequirement(new ItemStack(InitItems.PLANT_FIBER))));
	public static final Quest GET_PLANT_ROPE =            addQuest(new Quest("get_plant_rope",            GET_PLANT_FIBER,           new QuestRequirement(new ItemStack(InitItems.PLANT_ROPE))));
	public static final Quest GET_THATCH =                addQuest(new Quest("get_thatch",                GET_PLANT_FIBER,           new QuestRequirement(new ItemStack(InitBlocks.THATCH))));
	public static final Quest GET_PLANT_MESH =            addQuest(new Quest("get_plant_mesh",            GET_PLANT_FIBER,           new QuestRequirement(new ItemStack(InitItems.PLANT_MESH))));
	public static final Quest GET_STICKS =                addQuest(new Quest("get_sticks",                STAGE_0_ROOT,              new QuestRequirement(new ItemStack(Items.STICK))));
	public static final Quest GET_PRIMAL_TORCH_UNLIT =    addQuest(new Quest("get_primal_torch_unlit",    GET_STICKS,                new QuestRequirement(new ItemStack(InitBlocks.UNLIT_PRIMAL_TORCH))));
	public static final Quest GET_ROCK =                  addQuest(new Quest("get_rock",                  STAGE_0_ROOT,              new QuestRequirement(new ItemStack(InitBlocks.ROCK))));
	public static final Quest GET_COBBLESTONE =           addQuest(new Quest("get_cobblestone",           GET_ROCK,                  new QuestRequirement(new ItemStack(Blocks.COBBLESTONE))));
	public static final Quest GET_FLINT =                 addQuest(new Quest("get_flint",                 STAGE_0_ROOT,              new QuestRequirement(new ItemStack(Items.FLINT))));
	public static final Quest GET_KNAPPED_FLINT =         addQuest(new Quest("get_knapped_flint",         GET_FLINT,                 new QuestRequirement(new ItemStack(InitItems.FLINT_KNAPPED))));
	public static final Quest GET_FLINT_POINT =           addQuest(new Quest("get_flint_point",           GET_KNAPPED_FLINT,         new QuestRequirement(new ItemStack(InitItems.FLINT_POINT))));
	public static final Quest GET_FLINT_KNIFE =           addQuest(new Quest("get_flint_knife",           GET_FLINT_POINT,           new QuestRequirement(new ItemStack(InitItems.FLINT_KNIFE))));
	public static final Quest GET_FLINT_AXE =             addQuest(new Quest("get_flint_axe",             GET_KNAPPED_FLINT,         new QuestRequirement(new ItemStack(InitItems.FLINT_AXE))));
	public static final QuestEnd BREAK_LOG =              addEnd(new QuestEnd("break_log",                GET_FLINT_AXE,             new QuestRequirement(Arrays.asList(Blocks.LOG, Blocks.LOG2))));
	
	public static final QuestRoot STAGE_1_ROOT =          addRoot(new QuestRoot(EnumStage.stage1));
	public static final Quest GET_ANIMAL_PELT =           addQuest(new Quest("get_animal_pelt",           STAGE_1_ROOT,              new QuestRequirement(new ItemStack(InitItems.ANIMAL_PELT))));
	public static final Quest GET_RAW_HIDE =              addQuest(new Quest("get_raw_hide",              GET_ANIMAL_PELT,           new QuestRequirement(new ItemStack(InitItems.RAW_HIDE))));
	public static final Quest GET_FLINT_CRAFTING_HAMMER = addQuest(new Quest("get_flint_crafting_hammer", STAGE_1_ROOT,              new QuestRequirement(new ItemStack(InitItems.FLINT_CRAFTING_HAMMER))));
	public static final Quest GET_CRAFTING_STUMP =        addQuest(new Quest("get_crafting_stump",        GET_FLINT_CRAFTING_HAMMER, new QuestRequirement(new ItemStack(InitBlocks.CRAFTING_STUMP))));
	public static final Quest GET_FLINT_SHEARS =          addQuest(new Quest("get_flint_shears",          GET_CRAFTING_STUMP,        new QuestRequirement(new ItemStack(InitItems.FLINT_SHEARS))));
	public static final Quest GET_STRING =                addQuest(new Quest("get_string",                GET_FLINT_SHEARS,          new QuestRequirement(new ItemStack(Items.STRING))));
	public static final Quest GET_ROPE =                  addQuest(new Quest("get_rope",                  GET_STRING,                new QuestRequirement(new ItemStack(InitItems.ROPE))));
	public static final Quest GET_FLINT_SHOVEL =          addQuest(new Quest("get_flint_shovel",          GET_CRAFTING_STUMP,        new QuestRequirement(new ItemStack(InitItems.FLINT_SHOVEL))));
	public static final Quest GET_FLINT_SAW =             addQuest(new Quest("get_flint_saw",             GET_CRAFTING_STUMP,        new QuestRequirement(new ItemStack(InitItems.FLINT_SAW))));
	public static final Quest GET_FLINT_PICKAXE =         addQuest(new Quest("get_flint_pickaxe",         GET_CRAFTING_STUMP,        new QuestRequirement(new ItemStack(InitItems.FLINT_PICKAXE))));
	public static final Quest GET_FLINT_HOE =             addQuest(new Quest("get_flint_hoe",             GET_CRAFTING_STUMP,        new QuestRequirement(new ItemStack(InitItems.FLINT_HOE))));
	public static final Quest GET_FIRE_STARTER =          addQuest(new Quest("get_fire_starter",          GET_CRAFTING_STUMP,        new QuestRequirement(new ItemStack(InitItems.FIRE_STARTER))));
	public static final Quest GET_FIRE_PIT =              addQuest(new Quest("get_fire_pit",              GET_FIRE_STARTER,          new QuestRequirement(new ItemStack(InitBlocks.FIRE_PIT))));
	public static final Quest GET_DRYING_TABLE =          addQuest(new Quest("get_drying_table",          GET_ROPE,                  new QuestRequirement(new ItemStack(InitBlocks.DRYING_TABLE))));
	public static final Quest GET_DRIED_HIDE =            addQuest(new Quest("get_dried_hide",            GET_DRYING_TABLE,          new QuestRequirement(new ItemStack(InitItems.DRIED_HIDE))));
	public static final Quest GET_SALT =                  addQuest(new Quest("get_salt",                  GET_FLINT_SHOVEL,          new QuestRequirement(new ItemStack(InitItems.SALT))));
	public static final Quest GET_PRIMAL_TORCH_LIT =      addQuest(new Quest("get_primal_torch_lit",      GET_FIRE_PIT,              new QuestRequirement(new ItemStack(InitBlocks.LIT_PRIMAL_TORCH))));
	public static final Quest GET_PLANK =                 addQuest(new Quest("get_plank",                 GET_FLINT_SAW,             new QuestRequirement("plankItem")));
	public static final Quest GET_MORTAR_PESTLE =         addQuest(new Quest("get_mortar_pestle",         GET_PLANK,                 new QuestRequirement(new ItemStack(InitItems.MORTAR_PESTLE))));
	public static final Quest GET_LOOM =                  addQuest(new Quest("get_loom",                  GET_PLANK,                 new QuestRequirement(new ItemStack(InitBlocks.LOOM))));
	public static final Quest GET_CLOTH =                 addQuest(new Quest("get_cloth",                 GET_LOOM,                  new QuestRequirement(new ItemStack(InitItems.CLOTH))));
	public static final Quest GET_CLAY_POT =              addQuest(new Quest("get_clay_pot",              GET_FLINT_SHOVEL,          new QuestRequirement(new ItemStack(InitBlocks.CLAY_POT))));
	public static final Quest GET_CLAY_BUCKET =           addQuest(new Quest("get_clay_bucket",           GET_FLINT_SHOVEL,          new QuestRequirement(new ItemStack(InitItems.CLAY_BUCKET_EMPTY))));
	public static final Quest GET_WHEAT_SEEDS =           addQuest(new Quest("get_wheat_seeds",           GET_FLINT_HOE,             new QuestRequirement(new ItemStack(Items.WHEAT_SEEDS))));
	public static final Quest GET_WOOL =                  addQuest(new Quest("get_wool",                  GET_CLOTH,                 new QuestRequirement("wool")));
	public static final Quest GET_DOUGH =                 addQuest(new Quest("get_dough",                 GET_MORTAR_PESTLE,         new QuestRequirement(new ItemStack(InitItems.DOUGH))));
	public static final Quest GET_BREAD =                 addQuest(new Quest("get_bread",                 GET_DOUGH,                 new QuestRequirement(new ItemStack(Items.BREAD))));
	public static final Quest GET_BED =                   addQuest(new Quest("get_bed",                   GET_WOOL,                  new QuestRequirement("bed")));
	public static final Quest GET_SALTED_HIDE =           addQuest(new Quest("get_salted_hide",           GET_SALT,                  new QuestRequirement(new ItemStack(InitItems.SALTED_HIDE))));
	public static final Quest GET_LEATHER =               addQuest(new Quest("get_leather",               GET_SALTED_HIDE,           new QuestRequirement(new ItemStack(Items.LEATHER))));
	public static final Quest GET_CHARCOAL =              addQuest(new Quest("get_charcoal",              GET_FIRE_STARTER,          new QuestRequirement(new ItemStack(Items.COAL, 1, 1))));
	
	public static final QuestRoot STAGE_2_ROOT =          addRoot(new QuestRoot(EnumStage.stage2));
	
	public static final QuestRoot STAGE_3_ROOT =          addRoot(new QuestRoot(EnumStage.stage3));
	public static final Quest GET_CLAY_VESSEL =           addQuest(new Quest("get_clay_vessel",           STAGE_3_ROOT,              new QuestRequirement(new ItemStack(InitItems.CLAY_VESSEL))));
	public static final Quest GET_BLANK_CAST =            addQuest(new Quest("get_blank_cast",            STAGE_3_ROOT,              new QuestRequirement(new ItemStack(InitItems.BLANK_CAST))));
	public static final Quest GET_CLAY_FURNACE =          addQuest(new Quest("get_clay_furnace",          GET_CLAY_VESSEL,           new QuestRequirement(new ItemStack(InitBlocks.CLAY_FURNACE))));
	public static final Quest GET_CHARCOAL_KILN =         addQuest(new Quest("get_charcoal_kiln",         STAGE_3_ROOT,              new QuestRequirement(new ItemStack(InitBlocks.CHARCOAL_KILN))));
	public static final Quest GET_CHARCOAL_BLOCKS =       addQuest(new Quest("get_charcoal_blocks",       GET_CHARCOAL_KILN,         new QuestRequirement(new ItemStack(InitBlocks.CHARCOAL_BLOCK, 64))));
	//@formatter:on
	
	private static Quest addQuest(Quest q) {
		QUESTS.add(q);
		QUEST_TABS.get(q.getStage()).addQuestToTab(q);
		
		if (q.hasParent()) {
			q.getParent().addChild(q);
		}
		
		return q;
	}
	
	private static QuestRoot addRoot(QuestRoot q) {
		addQuest(q);
		QUEST_TABS.get(q.getStage()).setRoot(q);
		return q;
	}
	
	private static QuestEnd addEnd(QuestEnd q) {
		addQuest(q);
		return q;
	}
	
	private static QuestTab addTab(QuestTab tab) {
		QUEST_TABS.put(tab.getStage(), tab);
		return tab;
	}
	
	public static void load() {
		System.out.println("Loaded " + QUESTS.size() + " quests!");
		for (QuestTab tab : QUEST_TABS.values()) {
			tab.sort();
			tab.getRoot().setupPositions();
		}
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
	
	public static Quest findQuest(String name) {
		for (Quest q : QUESTS) {
			if (q.getName().equalsIgnoreCase(name)) {
				return q;
			}
		}
		
		return null;
	}
	
	public static List<String> getAllQuestNames() {
		List<String> str = new ArrayList<String>();
		for (Quest q : QUESTS) {
			str.add(q.getName());
		}
		
		return str;
	}
}
