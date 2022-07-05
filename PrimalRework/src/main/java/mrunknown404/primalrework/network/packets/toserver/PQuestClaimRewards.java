package mrunknown404.primalrework.network.packets.toserver;

import mrunknown404.primalrework.api.network.packet.IPacket;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.world.savedata.WSDQuestStates;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PQuestClaimRewards implements IPacket {
	public String questName;
	
	public PQuestClaimRewards() {
		
	}
	
	public PQuestClaimRewards(Quest quest) {
		this.questName = quest.getName();
	}
	
	@Override
	public void handle(Context ctx) {
		ServerPlayerEntity player = ctx.getSender();
		WSDQuestStates.getQuestState(questName).claimQuest(player);
	}
}
