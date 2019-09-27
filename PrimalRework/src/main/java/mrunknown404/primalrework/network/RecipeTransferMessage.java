package mrunknown404.primalrework.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class RecipeTransferMessage implements IMessage {

	Map<Integer, Integer> recipeMap;
	List<Integer> craftingSlots;
	List<Integer> inventorySlots;
	int playerID;
	boolean maxTransfer;
	boolean requireCompleteSets;
	
	public RecipeTransferMessage() {}
	
	public RecipeTransferMessage(EntityPlayer player, Map<Integer, Integer> recipeMap, List<Integer> craftingSlots, List<Integer> inventorySlots, boolean maxTransfer, boolean requireCompleteSets) {
		this.playerID = player.getEntityId();
		this.recipeMap = recipeMap;
		this.craftingSlots = craftingSlots;
		this.inventorySlots = inventorySlots;
		this.maxTransfer = maxTransfer;
		this.requireCompleteSets = requireCompleteSets;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		playerID = buf.readInt();
		int recipeMapSize = buf.readInt();
		recipeMap = new HashMap<>(recipeMapSize);
		for (int i = 0; i < recipeMapSize; i++) {
			int slotIndex = buf.readInt();
			int recipeItem = buf.readInt();
			recipeMap.put(slotIndex, recipeItem);
		}
		
		int craftingSlotsSize = buf.readInt();
		craftingSlots = new ArrayList<>(craftingSlotsSize);
		for (int i = 0; i < craftingSlotsSize; i++) {
			int slotIndex = buf.readInt();
			craftingSlots.add(slotIndex);
		}
		
		int inventorySlotsSize = buf.readInt();
		inventorySlots = new ArrayList<>(inventorySlotsSize);
		for (int i = 0; i < inventorySlotsSize; i++) {
			int slotIndex = buf.readInt();
			inventorySlots.add(slotIndex);
		}
		maxTransfer = buf.readBoolean();
		requireCompleteSets = buf.readBoolean();
		
		//BasicRecipeTransferHandlerServer.setItems(player, recipeMap, craftingSlots, inventorySlots, maxTransfer, requireCompleteSets);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(playerID);
		buf.writeInt(recipeMap.size());
		for (Map.Entry<Integer, Integer> recipeMapEntry : recipeMap.entrySet()) {
			buf.writeInt(recipeMapEntry.getKey());
			buf.writeInt(recipeMapEntry.getValue());
		}

		buf.writeInt(craftingSlots.size());
		for (Integer craftingSlot : craftingSlots) {
			buf.writeInt(craftingSlot);
		}

		buf.writeInt(inventorySlots.size());
		for (Integer inventorySlot : inventorySlots) {
			buf.writeInt(inventorySlot);
		}

		buf.writeBoolean(maxTransfer);
		buf.writeBoolean(requireCompleteSets);
	}
}
