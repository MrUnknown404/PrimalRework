package mrunknown404.primalrework.api.network.packet;

import net.minecraftforge.fml.network.NetworkEvent;

public interface IPacket {
	public abstract void handle(NetworkEvent.Context ctx);
}