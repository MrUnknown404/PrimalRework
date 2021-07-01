package mrunknown404.primalrework.network.packets;

import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketSyncQuestFinished implements IPacket {
	public String name;
	public boolean isFinished;
	
	public PacketSyncQuestFinished() {
		
	}
	
	public PacketSyncQuestFinished(Quest q) {
		name = q.getNameKey();
		isFinished = q.isFinished();
	}
	
	@Override
	public void handle(Context ctx) {
		InitQuests.findQuest(name).loadFinished(isFinished);
		InitQuests.resetQuestCache();
	}
}
