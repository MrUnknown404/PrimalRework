package mrunknown404.primalrework.network.packets;

import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.helpers.StageH;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketSyncStage implements IPacket {
	public Stage stage;
	
	public PacketSyncStage() {
		
	}
	
	public PacketSyncStage(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void handle(Context ctx) {
		StageH.loadStage(stage);
	}
}
