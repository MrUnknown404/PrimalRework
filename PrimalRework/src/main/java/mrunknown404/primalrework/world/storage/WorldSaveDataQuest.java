package mrunknown404.primalrework.world.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.util.enums.EnumStage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class WorldSaveDataQuest extends WorldSavedData {
	
	private static final String DATA_NAME = Main.MOD_ID + "_QuestData";
	
	public WorldSaveDataQuest() {
		super(DATA_NAME);
	}
	
	public WorldSaveDataQuest(String name) {
		super(name);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		NBTTagList tabList = nbt.getTagList("tabs", Constants.NBT.TAG_COMPOUND);
		
		for (int i = 0; i < tabList.tagCount(); i++) {
			NBTTagList quests = tabList.getCompoundTagAt(i).getTagList("quests", Constants.NBT.TAG_COMPOUND);
			
			for (int j = 0; j < quests.tagCount(); j++) {
				NBTTagCompound quest = quests.getCompoundTagAt(j);
				map.put(quest.getString("name"), quest.getBoolean("finished"));
			}
		}
		
		InitQuests.loadQuestFromMap(map);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound c) {
		NBTTagList tabList = new NBTTagList();
		for (Entry<EnumStage, QuestTab> qt : InitQuests.QUEST_TABS.entrySet()) {
			NBTTagList tabQuests = new NBTTagList();
			for (Quest q : qt.getValue().get()) {
				NBTTagCompound quest = new NBTTagCompound();
				quest.setString("name", q.getName());
				quest.setBoolean("finished", q.isFinished());
				tabQuests.appendTag(quest);
			}
			
			NBTTagCompound temp = new NBTTagCompound();
			temp.setString("stage", qt.getKey().toString());
			temp.setTag("quests", tabQuests);
			tabList.appendTag(temp);
		}
		
		c.setTag("tabs", tabList);
		return c;
	}
	
	public static WorldSaveDataQuest load(World world) {
		MapStorage storage = world.getMapStorage();
		WorldSaveDataQuest instance = (WorldSaveDataQuest) world.getMapStorage().getOrLoadData(WorldSaveDataQuest.class, DATA_NAME);
		
		if (instance == null) {
			instance = new WorldSaveDataQuest();
			storage.setData(DATA_NAME, instance);
		}
		
		return instance;
	}
}
