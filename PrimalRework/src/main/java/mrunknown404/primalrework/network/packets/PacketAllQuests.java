package mrunknown404.primalrework.network.packets;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketAllQuests implements IPacket {
	
	public List<String> names;
	public List<Boolean> finished;
	
	public PacketAllQuests() {
		
	}
	
	public PacketAllQuests(List<Quest> quests) {
		names = new ArrayList<String>();
		finished = new ArrayList<Boolean>();
		for (Quest q : quests) {
			names.add(q.getNameKey());
			finished.add(q.isFinished());
		}
	}
	
	@Override
	public void handle(Context ctx) {
		for (int i = 0; i < names.size(); i++) {
			InitQuests.getAllQuests().get(i).loadFinished(finished.get(i));
		}
	}
}
