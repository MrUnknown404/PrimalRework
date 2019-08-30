package mrunknown404.primalrework.util.harvest;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDropInfo {
	private final ItemStack item;
	private final boolean needsSilk;
	private final int dropAmount, randomDropMin, randomDropMax;
	private final float dropFortune, chanceFortune;
	/** 1-100 */
	private final int dropChance;
	
	/**@param item
	 * @param needsSilk
	 * @param dropChance
	 * @param dropAmount
	 * @param randomDropMin
	 * @param randomDropMax
	 * @param dropFortune
	 * @param chanceFortune
	 */
	public ItemDropInfo(Item item, boolean needsSilk, int dropChance, int dropAmount, int randomDropMin, int randomDropMax, float dropFortune, float chanceFortune) {
		this.item = new ItemStack(item);
		this.needsSilk = needsSilk;
		this.dropChance = dropChance;
		this.dropAmount = dropAmount;
		this.randomDropMin = randomDropMin;
		this.randomDropMax = randomDropMax;
		this.dropFortune = dropFortune;
		this.chanceFortune = chanceFortune;
	}
	
	/**@param block
	 * @param needsSilk
	 * @param dropChance
	 * @param dropAmount
	 * @param randomDropMin
	 * @param randomDropMax
	 * @param dropFortune
	 * @param chanceFortune
	 */
	public ItemDropInfo(Block block, boolean needsSilk, int dropChance, int dropAmount, int randomDropMin, int randomDropMax, float dropFortune, float chanceFortune) {
		this.item = new ItemStack(block);
		this.needsSilk = needsSilk;
		this.dropChance = dropChance;
		this.dropAmount = dropAmount;
		this.randomDropMin = randomDropMin;
		this.randomDropMax = randomDropMax;
		this.dropFortune = dropFortune;
		this.chanceFortune = chanceFortune;
	}
	
	public Item getItem() {
		return item.getItem();
	}
	
	public boolean needsSilk() {
		return needsSilk;
	}
	
	public int getDropChance() {
		return dropChance;
	}
	
	public int getDropAmount() {
		return dropAmount;
	}
	
	public int getRandomDropMin() {
		return randomDropMin;
	}
	
	public int getRandomDropMax() {
		return randomDropMax;
	}
	
	public float getDropFortune() {
		return dropFortune;
	}
	
	public float getChanceFortune() {
		return chanceFortune;
	}
}
