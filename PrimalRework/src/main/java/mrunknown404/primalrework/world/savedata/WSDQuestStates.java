package mrunknown404.primalrework.world.savedata;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
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
import net.minecraftforge.common.util.Lazy;

public class WSDQuestStates extends WorldSavedData {
	public static final String NAME = PrimalRework.MOD_ID + "_queststates";
	private static final Lazy<Map<String, QuestState>> QUEST_STATES = Lazy
			.of(() -> InitQuests.getQuests().stream().map(q -> new QuestState(q, false, false)).collect(Collectors.toMap(QuestState::getName, Function.identity())));
	
	public WSDQuestStates() {
		super(NAME);
		loadParents();
	}
	
	/** Don't use this! */
	@Deprecated
	public static void loadQuestState(String name, boolean isFinished, boolean wasClaimed) {
		QUEST_STATES.get().get(name).load(isFinished, wasClaimed);
	}
	
	/** Don't use this! */
	@Deprecated
	public static void loadParents() {
		QUEST_STATES.get().values().stream().filter(QuestState::hasParent).forEach(q -> q.load(QUEST_STATES.get().get(q.getParentName())));
	}
	
	public static QuestState getQuestState(String questName) {
		return QUEST_STATES.get().get(questName);
	}
	
	public void forEach(Consumer<QuestState> consumer) {
		QUEST_STATES.get().values().forEach(consumer);
	}
	
	public Stream<QuestState> stream() {
		return QUEST_STATES.get().values().stream();
	}
	
	@Override
	public void load(CompoundNBT nbt) {
		ListNBT list = nbt.getList("quests", Constants.NBT.TAG_COMPOUND);
		for (INBT inbt : list) {
			CompoundNBT inner = (CompoundNBT) inbt;
			QUEST_STATES.get().get(inner.getString("name")).load(inner.getBoolean("finished"), inner.getBoolean("claimed"));
		}
	}
	
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		ListNBT list = new ListNBT();
		for (QuestState q : QUEST_STATES.get().values()) {
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
