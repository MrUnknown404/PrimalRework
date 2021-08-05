package mrunknown404.primalrework.network.packets;

import mrunknown404.primalrework.helpers.StageH;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketSyncStage implements IPacket {
	public EnumStage stage;
	
	public PacketSyncStage() {
		
	}
	
	public PacketSyncStage(EnumStage stage) {
		this.stage = stage;
	}
	
	@Override
	public void handle(Context ctx) {
		StageH.loadStage(stage);
	}
}
