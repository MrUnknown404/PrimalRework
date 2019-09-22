package mrunknown404.primalrework.util.harvest;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class HarvestDropInfo {
	private final EnumToolType toolType;
	private final boolean replace;
	private final List<ItemDropInfo> drops;
	
	/**@param toolType
	 * @param replace
	 * @param drops
	 */
	public HarvestDropInfo(EnumToolType toolType, boolean replace, ItemDropInfo... drops) {
		this.toolType = toolType;
		this.replace = replace;
		this.drops = Arrays.asList(drops);
	}
	
	public boolean isReplace() {
		return replace;
	}
	
	public EnumToolType getToolType() {
		return toolType;
	}
	
	public List<ItemDropInfo> getDrops() {
		return drops;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HarvestDropInfo) {
			return ((HarvestDropInfo) obj).toolType == toolType;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + toolType + ", " + replace + " | " + drops + ")";
	}
	
	public static class ItemDropInfo {
		private final Item item;
		private final boolean needsSilk;
		private final int dropAmount, randomDropMin, randomDropMax;
		private final float dropFortune, chanceFortune;
		/** 1-100 */
		private final int dropChance;
		
		public ItemDropInfo(Item item, boolean needsSilk, int dropChance, int dropAmount, int randomDropMin, int randomDropMax, float dropFortune, float chanceFortune) {
			this.item = item;
			this.needsSilk = needsSilk;
			this.dropChance = dropChance;
			this.dropAmount = dropAmount;
			this.randomDropMin = randomDropMin;
			this.randomDropMax = randomDropMax;
			this.dropFortune = dropFortune;
			this.chanceFortune = chanceFortune;
		}
		
		public ItemDropInfo(Block block, boolean needsSilk, int dropChance, int dropAmount, int randomDropMin, int randomDropMax, float dropFortune, float chanceFortune) {
			this(Item.getItemFromBlock(block), needsSilk, dropChance, dropAmount, randomDropMin, randomDropMax, dropFortune, chanceFortune);
		}
		
		public Item getItem() {
			return item;
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
		@Override
		public String toString() {
			return "(" + item.getUnlocalizedName() + ", " + needsSilk + ", " + dropAmount + ", " + randomDropMin + ", " + randomDropMax + ", " + dropFortune + ", " +
					chanceFortune + ", " + dropChance + ")";
		}
	}
}
