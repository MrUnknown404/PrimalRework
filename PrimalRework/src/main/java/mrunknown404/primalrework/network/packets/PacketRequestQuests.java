package mrunknown404.primalrework.network.packets;

import mrunknown404.primalrework.network.NetworkHandler;
import mrunknown404.primalrework.registries.PRQuests;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketRequestQuests implements IPacket {
	@Override
	public void handle(Context ctx) {
		NetworkHandler.sendPacketToTarget(ctx.getSender(), new PacketAllQuests(PRQuests.getAllQuests()));
	}
}
