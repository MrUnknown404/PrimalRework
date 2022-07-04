package mrunknown404.primalrework.world.savedata;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.network.packets.PSyncStage;
import mrunknown404.primalrework.stage.Stage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class WSDStage extends WorldSavedData {
	public static final String NAME = PrimalRework.MOD_ID + "_stage";
	private static Stage stage = InitStages.STAGE_0.get();
	
	public WSDStage() {
		super(NAME);
	}
	
	public static void setStage(ServerWorld world, Stage stage) {
		WSDStage.stage = stage;
		PrimalRework.NETWORK.sendPacketToAll(new PSyncStage(WSDStage.stage));
		get(world).setDirty();
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	@Override
	public void load(CompoundNBT nbt) {
		loadStage(InitStages.byID(nbt.getByte("stage")));
	}
	
	/** Don't use this! */
	@Deprecated
	public static void loadStage(Stage stage) {
		WSDStage.stage = stage;
	}
	
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		nbt.putByte("stage", stage.id);
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
