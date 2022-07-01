package mrunknown404.primalrework.utils.helpers;

import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.network.NetworkHandler;
import mrunknown404.primalrework.network.packets.PSyncStage;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.world.savedata.WSDStage;
import net.minecraft.world.server.ServerWorld;

public class StageH {
	private static Stage stage = InitStages.STAGE_0.get();
	
	public static void setStage(ServerWorld world, Stage stage) {
		StageH.stage = stage;
		NetworkHandler.sendPacketToAll(new PSyncStage(StageH.stage));
		WSDStage.get(world).setDirty();
	}
	
	public static void loadStage(Stage stage) {
		StageH.stage = stage;
	}
	
	public static boolean hasAccessToStage(Stage stage) {
		return StageH.stage.id >= stage.id;
	}
	
	public static Stage getStage() {
		return stage;
	}
}
