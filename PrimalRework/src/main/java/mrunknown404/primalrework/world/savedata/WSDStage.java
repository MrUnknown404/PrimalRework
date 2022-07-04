package mrunknown404.primalrework.world.savedata;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.network.packets.server.PSyncStage;
import mrunknown404.primalrework.stage.Stage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldSavedData;

public class WSDStage extends WorldSavedData {
	public static final String NAME = PrimalRework.MOD_ID + "_stage";
	private static Stage stage = InitStages.STAGE_0.get();
	
	public WSDStage() {
		super(NAME);
		
		stage = InitStages.STAGE_0.get();
	}
	
	public static void setStage(MinecraftServer server, Stage stage) {
		WSDStage.stage = stage;
		PrimalRework.NETWORK.sendPacketToAll(new PSyncStage(WSDStage.stage));
		get(server).setDirty();
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
	
	public static WSDStage get(MinecraftServer server) {
		return server.overworld().getDataStorage().computeIfAbsent(() -> new WSDStage(), NAME);
	}
}
