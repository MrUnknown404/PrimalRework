package mrunknown404.primalrework.network.packets.toclient;

import mrunknown404.primalrework.api.network.packet.IPacket;
import mrunknown404.primalrework.quests.QuestState;
import mrunknown404.primalrework.world.savedata.WSDQuestStates;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PSyncQuestState implements IPacket {
	public String name;
	public boolean isFinsihed, wasClaimed;
	
	public PSyncQuestState() {
		
	}
	
	public PSyncQuestState(QuestState state) {
		this.name = state.getName();
		this.isFinsihed = state.isFinished();
		this.wasClaimed = state.wasClaimed();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void handle(Context ctx) {
		WSDQuestStates.loadQuestState(name, isFinsihed, wasClaimed);
	}
}
