package mrunknown404.primalrework.network.packets.server;

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
		PlayerEntity player = Minecraft.getInstance().player;
		if (player.containerMenu instanceof ContainerPrimalCraftingTable) {
			player.containerMenu.setItem(9, itemStack);
		}
	}
}
