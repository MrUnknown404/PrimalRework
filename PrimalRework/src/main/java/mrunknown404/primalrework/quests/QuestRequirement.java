package mrunknown404.primalrework.quests;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class QuestRequirement {
	private final QuestReq req;
	private final int amountNeeded;
	
	private QuestRequirement(QuestReq req, int amountNeeded, Block block, ItemStack item, String oreDict) {
		this.req = req;
		this.amountNeeded = amountNeeded;
		
		this.block = block;
		this.item = item;
		this.oreDict = oreDict;
	}
	
	public QuestRequirement(Block block) {
		this(QuestReq.block_break, 1, block, null, "");
	}
	
	public QuestRequirement(ItemStack item) {
		this(QuestReq.item_collect, item.getCount(), null, item, "");
	}
	
	public QuestRequirement(String oreDict) {
		this(QuestReq.item_collect, 1, null, null, oreDict);
	}
	
	public QuestRequirement(String oreDict, int count) {
		this(QuestReq.item_collect, count, null, null, oreDict);
	}
	
	public int getAmountNeeded() {
		return amountNeeded;
	}
	
	public QuestReq getQuestReq() {
		return req;
	}
	
	private final Block block;
	private final ItemStack item;
	private final String oreDict;
	
	public Block getBlockToBreak() {
		return block;
	}
	
	public List<ItemStack> getItemToCollect() {
		if (item != null) {
			return Arrays.asList(item);
		} else if (block != null) {
			return Arrays.asList(new ItemStack(block));
		} else {
			return OreDictionary.getOres(oreDict);
		}
	}
	
	public enum QuestReq {
		block_break,
		item_collect;
	}
}
