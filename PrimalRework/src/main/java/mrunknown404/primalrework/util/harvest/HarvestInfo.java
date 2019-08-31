package mrunknown404.primalrework.util.harvest;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.DoubleValue;
import net.minecraft.item.Item;

public class HarvestInfo {
	
	protected final List<DoubleValue<ToolType, ToolHarvestLevel>> types_harvests;
	protected final Item item;
	
	public HarvestInfo(Item item, DoubleValue<ToolType, ToolHarvestLevel>... types_harvests) {
		this.item = item;
		this.types_harvests = Arrays.asList(types_harvests);
	}
	
	public HarvestInfo(Item item) {
		this(item, new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.none, ToolHarvestLevel.hand));
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
			return ((HarvestInfo) obj).item == item;
		}
		
		return false;
	}
}
