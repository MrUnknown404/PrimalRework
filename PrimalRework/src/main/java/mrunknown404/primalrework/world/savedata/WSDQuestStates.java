package mrunknown404.primalrework.world.savedata;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.QuestState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class WSDQuestStates extends WorldSavedData {
	public static final String NAME = PrimalRework.MOD_ID + "_queststates";
	private static final Map<String, QuestState> QUEST_STATES = new HashMap<String, QuestState>();
	
	public WSDQuestStates() {
		super(NAME);
		
		if (QUEST_STATES.isEmpty()) {
			InitQuests.getQuests().forEach(q -> QUEST_STATES.put(q.getName(), new QuestState(q, false, false)));
			QUEST_STATES.values().stream().filter(q -> q.hasParent()).forEach(q -> q.load(QUEST_STATES.get(q.getParentName())));
		} else {
			QUEST_STATES.values().forEach(q -> q.load(false, false));
		}
	}
	
	/** Don't use this! */
	@Deprecated
	public static void loadQuestState(String name, boolean isFinished, boolean wasClaimed) {
		QUEST_STATES.get(name).load(isFinished, wasClaimed);
	}
	
	public static QuestState getQuestState(String questName) {
		return QUEST_STATES.get(questName);
	}
	
	public void forEach(Consumer<QuestState> consumer) {
		QUEST_STATES.values().forEach(consumer);
	}
	
	public Stream<QuestState> stream() {
		return QUEST_STATES.values().stream();
	}
	
	@Override
	public void load(CompoundNBT nbt) {
		ListNBT list = nbt.getList("quests", Constants.NBT.TAG_COMPOUND);
		for (INBT inbt : list) {
			CompoundNBT inner = (CompoundNBT) inbt;
			QUEST_STATES.get(inner.getString("name")).load(inner.getBoolean("finished"), inner.getBoolean("claimed"));
		}
	}
	
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		ListNBT list = new ListNBT();
		for (QuestState q : QUEST_STATES.values()) {
			CompoundNBT inner = new CompoundNBT();
			inner.putString("name", q.getName());
			inner.putBoolean("finished", q.isFinished());
			inner.putBoolean("claimed", q.wasClaimed());
			list.add(inner);
		}
		nbt.put("quests", list);
		return nbt;
	}
	
	public static WSDQuestStates get(MinecraftServer server) {
		return server.overworld().getDataStorage().computeIfAbsent(() -> new WSDQuestStates(), NAME);
	}
}