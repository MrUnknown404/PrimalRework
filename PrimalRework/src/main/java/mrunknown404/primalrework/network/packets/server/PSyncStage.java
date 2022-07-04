package mrunknown404.primalrework.network.packets.server;

import mrunknown404.primalrework.api.network.packet.IPacket;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.world.savedata.WSDStage;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PSyncStage implements IPacket {
	public Stage stage;
	
	public PSyncStage() {
		
	}
	
	public PSyncStage(Stage stage) {
		this.stage = stage;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void handle(Context ctx) {
		WSDStage.loadStage(stage);
	}
}
