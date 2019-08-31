package mrunknown404.primalrework.util.harvest;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.util.DoubleValue;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BlockHarvestInfo extends HarvestInfo {
	
	private final List<HarvestDropInfo> drops = new ArrayList<HarvestDropInfo>();
	
	public BlockHarvestInfo(Block block, DoubleValue<ToolType, ToolHarvestLevel>... types_harvests) {
		super(Item.getItemFromBlock(block), types_harvests);
	}
	
	public BlockHarvestInfo(Block block) {
		this(block, new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.none, ToolHarvestLevel.hand));
	}
	
	public BlockHarvestInfo setDrops(List<HarvestDropInfo> drops) {
		return setDrops(drops.toArray(new HarvestDropInfo[0]));
	}
	
	public BlockHarvestInfo setDrops(HarvestDropInfo... drops) {
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
}
