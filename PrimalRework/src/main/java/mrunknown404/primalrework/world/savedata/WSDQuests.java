package mrunknown404.primalrework.world.savedata;

import java.util.HashMap;
import java.util.Map;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class WSDQuests extends WorldSavedData {
	public static final String NAME = PrimalRework.MOD_ID + "_quests";
	
	public WSDQuests() {
		super(NAME);
	}
	
	@Override
	public void load(CompoundNBT nbt) {
		Map<String, Boolean> questMap = new HashMap<String, Boolean>();
		ListNBT list = nbt.getList("quests", Constants.NBT.TAG_COMPOUND);
		for (INBT inbt : list) {
			CompoundNBT inner = (CompoundNBT) inbt;
			questMap.put(inner.getString("name"), inner.getBoolean("finished"));
		}
		
		for (Quest q : InitQuests.getQuests()) {
			if (questMap.containsKey(q.getNameKey())) {
				q.loadFinished(questMap.get(q.getNameKey()));
			}
		}
	}
	
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		ListNBT list = new ListNBT();
		for (Quest q : InitQuests.getQuests()) {
			CompoundNBT inner = new CompoundNBT();
			inner.putString("name", q.getNameKey());
			inner.putBoolean("finished", q.isFinished());
			list.add(inner);
		}
		nbt.put("quests", list);
		return nbt;
	}
	
	public static WSDQuests get(ServerWorld world) {
		DimensionSavedDataManager storage = world.getDataStorage();
		WSDQuests data = storage.computeIfAbsent(() -> new WSDQuests(), NAME);
		
		if (data == null) {
			data = new WSDQuests();
			storage.set(data);
		}
		
		return data;
	}
}
