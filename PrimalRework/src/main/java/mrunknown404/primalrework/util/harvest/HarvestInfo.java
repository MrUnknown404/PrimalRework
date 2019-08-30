package mrunknown404.primalrework.util.harvest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.DoubleValue;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class HarvestInfo {
	
	private final Item item;
	private final Block block;
	
	private final List<DoubleValue<ToolType, ToolHarvestLevel>> types_harvests;
	private final List<HarvestDropInfo> drops = new ArrayList<HarvestDropInfo>();
	
	public HarvestInfo(Item item, DoubleValue<ToolType, ToolHarvestLevel>... types_harvests) {
		this.item = item;
		this.block = null;
		this.types_harvests = Arrays.asList(types_harvests);
	}
	
	public HarvestInfo(Block block, DoubleValue<ToolType, ToolHarvestLevel>... types_harvests) {
		this.item = null;
		this.block = block;
		this.types_harvests = Arrays.asList(types_harvests);
	}
	
	public HarvestInfo(Item item) {
		this(item, new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.none, ToolHarvestLevel.hand));
	}
	
	public HarvestInfo(Block block) {
		this(block, new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.none, ToolHarvestLevel.hand));
	}
	
	public HarvestInfo setDrops(List<HarvestDropInfo> drops) {
		return setDrops(drops.toArray(new HarvestDropInfo[0]));
	}
	
	public HarvestInfo setDrops(HarvestDropInfo... drops) {
		for (HarvestDropInfo drop : drops) {
			if (this.drops.contains(drop)) {
				this.drops.remove(drop);
			}
			
			this.drops.add(drop);
		}
		
		return this;
	}
	
	public HarvestDropInfo getDrop(ToolType type) {
		for (HarvestDropInfo drop : drops) {
			if (drop.getToolType() == type) {
				return drop;
			}
		}
		
		return null;
	}
	
	public boolean isItem() {
		return item != null;
	}
	
	public boolean isBlock() {
		return block != null;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public Item getItem() {
		return item;
	}
	
	public List<DoubleValue<ToolType, ToolHarvestLevel>> getTypesHarvests() {
		return types_harvests;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HarvestInfo) {
			if (((HarvestInfo) obj).isBlock()) {
				return ((HarvestInfo) obj).block == block;
			} else {
				return ((HarvestInfo) obj).item == item;
			}
		}
		
		return false;
	}
}
