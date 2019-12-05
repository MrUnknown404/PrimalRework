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
		public final Item item;
		public final boolean needsSilk, usesFortune;
		public final int meta, dropAmount, randomDropMin, randomDropMax;
		/** drop chance * by this + 1 */
		public final float chanceFortune;
		/** 1-100 */
		public final int dropChance;
		
		public ItemDropInfo(Item item, int meta, boolean needsSilk, boolean usesFortune, int dropChance, int dropAmount, int randomDropMin, int randomDropMax, float chanceFortune) {
			this.item = item;
			this.meta = meta;
			this.needsSilk = needsSilk;
			this.usesFortune = usesFortune;
			this.dropChance = dropChance;
			this.dropAmount = dropAmount;
			this.randomDropMin = randomDropMin;
			this.randomDropMax = randomDropMax;
			this.chanceFortune = chanceFortune;
		}
		
		public ItemDropInfo(Block block, int meta, boolean needsSilk, boolean usesFortune, int dropChance, int dropAmount, int randomDropMin, int randomDropMax, float chanceFortune) {
			this(Item.getItemFromBlock(block), meta, needsSilk, usesFortune, dropChance, dropAmount, randomDropMin, randomDropMax, chanceFortune);
		}
		
		public ItemDropInfo(Item item, boolean needsSilk, boolean usesFortune, int dropChance, int dropAmount, int randomDropMin, int randomDropMax, float chanceFortune) {
			this(item, 0, needsSilk, usesFortune, dropChance, dropAmount, randomDropMin, randomDropMax, chanceFortune);
		}
		
		public ItemDropInfo(Block block, boolean needsSilk, boolean usesFortune, int dropChance, int dropAmount, int randomDropMin, int randomDropMax, float chanceFortune) {
			this(Item.getItemFromBlock(block), 0, needsSilk, usesFortune, dropChance, dropAmount, randomDropMin, randomDropMax, chanceFortune);
		}
		
		@Override
		public String toString() {
			return "(" + item.getUnlocalizedName() + ", " + needsSilk + ", " + dropAmount + ", " + randomDropMin + ", " + randomDropMax + ", " +
					chanceFortune + ", " + dropChance + ")";
		}
	}
}
