package mrunknown404.primalrework.network.packets.toserver;

import mrunknown404.primalrework.api.network.packet.IPacket;
import mrunknown404.primalrework.inventory.container.ContainerInventory;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkHooks;

public class POpenInventory implements IPacket {
	@Override
	public void handle(Context ctx) {
		NetworkHooks.openGui(ctx.getSender(), new SimpleNamedContainerProvider((id, inv, var) -> new ContainerInventory(id, inv), WordH.translate("container.inventory")));
	}
}
