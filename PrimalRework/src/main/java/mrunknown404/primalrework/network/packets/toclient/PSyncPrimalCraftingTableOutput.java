package mrunknown404.primalrework.network.packets.toclient;

import mrunknown404.primalrework.api.network.packet.IPacket;
import mrunknown404.primalrework.inventory.container.ContainerPrimalCraftingTable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PSyncPrimalCraftingTableOutput implements IPacket {
	public ItemStack itemStack = ItemStack.EMPTY;
	
	public PSyncPrimalCraftingTableOutput() {
		
	}
	
	public PSyncPrimalCraftingTableOutput(ItemStack itemStack) {
		this.itemStack = itemStack.copy();
	}
	
	@Override
	public void handle(Context ctx) {
		try (Minecraft mc = Minecraft.getInstance()) {
			PlayerEntity player = mc.player;
			if (player.containerMenu instanceof ContainerPrimalCraftingTable) {
				player.containerMenu.setItem(9, itemStack);
			}
		}
	}
}
