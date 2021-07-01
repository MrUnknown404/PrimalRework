package mrunknown404.primalrework.network.packets;

import net.minecraftforge.fml.network.NetworkEvent;

public interface IPacket {
	public abstract void handle(NetworkEvent.Context ctx);
}