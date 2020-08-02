package mrunknown404.primalrework.quests;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class QuestRequirement {
	private final QuestReq req;
	private final int amountNeeded;
	
	private QuestRequirement(QuestReq req, int amountNeeded, Block block, ItemStack item) {
		this.req = req;
		this.amountNeeded = amountNeeded;
		
		this.block = block;
		this.item = item;
	}
	
	public QuestRequirement(Block block) {
		this(QuestReq.block_break, 1, block, null);
	}
	
	public QuestRequirement(ItemStack item) {
		this(QuestReq.item_collect, item.getCount(), null, item);
	}
	
	public int getAmountNeeded() {
		return amountNeeded;
	}
	
	public QuestReq getQuestReq() {
		return req;
	}
	
	private final Block block;
	private final ItemStack item;
	
	public Block getBlockToBreak() {
		return block;
	}
	
	public Item getItemToCollect() {
		return item.getItem();
	}
	
	public enum QuestReq {
		block_break,
		item_collect;
	}
}
