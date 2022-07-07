package mrunknown404.primalrework.network.packets.toclient;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.api.network.packet.IPacket;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.QuestState;
import mrunknown404.primalrework.world.savedata.WSDQuestStates;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PSyncAllQuests implements IPacket {
	public String[] names;
	public Boolean[] finishs, claims;
	
	public PSyncAllQuests() {
		
	}
	
	private PSyncAllQuests(String[] names, Boolean[] finishs, Boolean[] claims) {
		this.names = names;
		this.finishs = finishs;
		this.claims = claims;
	}
	
	public static PSyncAllQuests create() {
		List<String> names = new ArrayList<String>();
		List<Boolean> finishs = new ArrayList<Boolean>(), claims = new ArrayList<Boolean>();
		
		InitQuests.getQuests().forEach(q -> {
			names.add(q.getName());
			QuestState s = WSDQuestStates.getQuestState(q.getName());
			finishs.add(s.isFinished());
			claims.add(s.wasClaimed());
		});
		
		return new PSyncAllQuests(names.toArray(new String[0]), finishs.toArray(new Boolean[0]), claims.toArray(new Boolean[0]));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void handle(Context ctx) {
		for (int i = 0; i < names.length; i++) {
			WSDQuestStates.loadQuestState(names[i], finishs[i], claims[i]);
		}
		WSDQuestStates.loadParents();
	}
}
