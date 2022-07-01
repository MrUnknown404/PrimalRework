package mrunknown404.primalrework.network.packets;

import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.helpers.StageH;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PSyncStage implements IPacket {
	public Stage stage;
	
	public PSyncStage() {
		
	}
	
	public PSyncStage(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void handle(Context ctx) {
		StageH.loadStage(stage);
	}
}
