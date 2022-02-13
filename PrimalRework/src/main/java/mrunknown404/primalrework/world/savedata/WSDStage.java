package mrunknown404.primalrework.world.savedata;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.utils.helpers.StageH;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class WSDStage extends WorldSavedData {
	public static final String NAME = PrimalRework.MOD_ID + "_stage";
	
	public WSDStage() {
		super(NAME);
	}
	
	@Override
	public void load(CompoundNBT nbt) {
		StageH.loadStage(PRStages.byID(nbt.getByte("stage")));
	}
	
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		nbt.putByte("stage", StageH.getStage().id);
		return nbt;
	}
	
	public static WSDStage get(ServerWorld world) {
		DimensionSavedDataManager storage = world.getDataStorage();
		WSDStage data = storage.computeIfAbsent(() -> new WSDStage(), NAME);
		
		if (data == null) {
			data = new WSDStage();
			storage.set(data);
		}
		
		return data;
	}
}
