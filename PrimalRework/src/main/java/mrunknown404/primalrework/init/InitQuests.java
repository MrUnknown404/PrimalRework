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
	
	public static final QuestTab TAB_STAGE_0 = addTab(new QuestTab(EnumStage.stage0, new ItemStack(Blocks.LOG)));
	public static final QuestTab TAB_STAGE_1 = addTab(new QuestTab(EnumStage.stage1, new ItemStack(Blocks.COBBLESTONE)));
	public static final QuestTab TAB_STAGE_2 = addTab(new QuestTab(EnumStage.stage2, new ItemStack(ModBlocks.FIRE_PIT)));
	public static final QuestTab TAB_STAGE_3 = addTab(new QuestTab(EnumStage.stage3, new ItemStack(ModBlocks.CLAY_FURNACE)));
	
	public static final QuestRoot STAGE_0_ROOT = addRoot(new QuestRoot(EnumStage.stage0));
	public static final QuestRoot STAGE_1_ROOT = addRoot(new QuestRoot(EnumStage.stage1));
	public static final QuestRoot STAGE_2_ROOT = addRoot(new QuestRoot(EnumStage.stage2));
	public static final QuestRoot STAGE_3_ROOT = addRoot(new QuestRoot(EnumStage.stage3));
	
	//TODO convert all advancements to quests
	
	//@formatter:off
	public static final Quest STAGE_0_GET_PLANT_FIBER = addQuest(new Quest("get_plant_fiber", STAGE_0_ROOT, -1, new QuestRequirement(new ItemStack(ModItems.PLANT_FIBER, 1))));
	public static final Quest STAGE_0_GET_PLANT_ROPE = addQuest(new Quest("get_plant_rope", STAGE_0_GET_PLANT_FIBER, -1, new QuestRequirement(new ItemStack(ModItems.PLANT_ROPE, 1))));
	public static final Quest STAGE_0_GET_THATCH = addQuest(new Quest("get_thatch", STAGE_0_GET_PLANT_FIBER, -2, new QuestRequirement(new ItemStack(ModBlocks.THATCH, 1))));
	public static final Quest STAGE_0_GET_PLANT_MESH = addQuest(new Quest("get_plant_mesh", STAGE_0_GET_PLANT_FIBER, -3, new QuestRequirement(new ItemStack(ModItems.PLANT_MESH, 1))));
	public static final Quest STAGE_0_GET_STICKS = addQuest(new Quest("get_sticks", STAGE_0_ROOT, 0, new QuestRequirement(new ItemStack(Items.STICK, 1))));
	public static final Quest STAGE_0_GET_PRIMAL_TORCH_UNLIT = addQuest(new Quest("get_primal_torch_unlit", STAGE_0_GET_STICKS, 0, new QuestRequirement(new ItemStack(ModBlocks.UNLIT_PRIMAL_TORCH, 1))));
	public static final Quest STAGE_0_GET_ROCK = addQuest(new Quest("get_rock", STAGE_0_ROOT, 1, new QuestRequirement(new ItemStack(ModBlocks.ROCK, 1))));
	public static final Quest STAGE_0_GET_COBBLESTONE = addQuest(new Quest("get_cobblestone", STAGE_0_GET_ROCK, 1, new QuestRequirement(new ItemStack(Blocks.COBBLESTONE, 1))));
	public static final Quest STAGE_0_GET_FLINT = addQuest(new Quest("get_fint", STAGE_0_ROOT, 2, new QuestRequirement(new ItemStack(Items.FLINT, 1))));
	public static final Quest STAGE_0_GET_KNAPPED_FLINT = addQuest(new Quest("get_knapped_flint", STAGE_0_GET_FLINT, 2, new QuestRequirement(new ItemStack(ModItems.FLINT_KNAPPED, 1))));
	public static final Quest STAGE_0_GET_FLINT_POINT = addQuest(new Quest("get_flint_point", STAGE_0_GET_KNAPPED_FLINT, 2, new QuestRequirement(new ItemStack(ModItems.FLINT_POINT, 1))));
	public static final Quest STAGE_0_GET_FLINT_KNIFE = addQuest(new Quest("get_flint_knife", STAGE_0_GET_FLINT_POINT, 2, new QuestRequirement(new ItemStack(ModItems.FLINT_KNIFE, 1))));
	public static final Quest STAGE_0_GET_FLINT_AXE = addQuest(new Quest("get_flint_axe", STAGE_0_GET_KNAPPED_FLINT, 0, new QuestRequirement(new ItemStack(ModItems.FLINT_AXE, 1))));
	public static final Quest STAGE_0_BREAK_LOG = addQuest(new Quest("break_log", STAGE_0_GET_FLINT_AXE, 0, new QuestRequirement(Blocks.LOG)));
	//@formatter:on
	
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
