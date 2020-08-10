package mrunknown404.primalrework.quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class QuestRequirement {
	private final QuestReq req;
	private final int amountNeeded;
	
	private QuestRequirement(QuestReq req, int amountNeeded, List<Block> block, ItemStack item, String oreDict) {
		this.req = req;
		this.amountNeeded = amountNeeded;
		
		this.block = block;
		this.item = item;
		this.oreDict = oreDict;
	}
	
	public QuestRequirement(List<Block> block) {
		this(QuestReq.block_break, 1, block, null, "");
	}
	
	public QuestRequirement(Block block) {
		this(QuestReq.block_break, 1, Arrays.asList(block), null, "");
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
	
	private final List<Block> block;
	private final ItemStack item;
	private final String oreDict;
	
	public List<Block> getBlocksToBreak() {
		return block;
	}
	
	public List<ItemStack> getItemsToCollect() {
		if (item != null) {
			return Arrays.asList(item);
		} else if (block != null) {
			List<ItemStack> items = new ArrayList<ItemStack>();
			for (Block b : block) {
				items.add(new ItemStack(b));
			}
			
			return items;
		} else {
			List<ItemStack> items = new ArrayList<ItemStack>();
			for (ItemStack i : OreDictionary.getOres(oreDict)) {
				if (i.getMetadata() != OreDictionary.WILDCARD_VALUE) {
					items.add(i);
				}
			}
			
			return items;
		}
	}
	
	public enum QuestReq {
		block_break,
		item_collect;
	}
}
