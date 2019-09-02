package mrunknown404.primalrework.util.harvest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.DoubleValue;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BlockHarvestInfo extends HarvestInfo {
	
	private final List<HarvestDropInfo> drops = new ArrayList<HarvestDropInfo>();
	
	public BlockHarvestInfo(Block block, List<DoubleValue<ToolType, ToolHarvestLevel>> harvest) {
		super(Item.getItemFromBlock(block), harvest);
	}
	
	public BlockHarvestInfo(Block block) {
		this(block, Arrays.asList(new DoubleValue<ToolType, ToolHarvestLevel>(ToolType.none, ToolHarvestLevel.hand)));
	}
	
	public BlockHarvestInfo(Block block, DoubleValue<ToolType, ToolHarvestLevel> harvest) {
		this(block, Arrays.asList(harvest));
	}
	
	public BlockHarvestInfo setDrops(List<HarvestDropInfo> drops) {
		return setDrops(drops.toArray(new HarvestDropInfo[0]));
	}
	
	public BlockHarvestInfo setDrops(HarvestDropInfo... drops) {
		for (HarvestDropInfo drop : drops) {
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
	
	public boolean canBreakWithNone() {
		for (DoubleValue<ToolType, ToolHarvestLevel> dv : types_harvests) {
			if (dv.getL() == ToolType.none || dv.getR() == ToolHarvestLevel.hand) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isUnbreakable() {
		for (DoubleValue<ToolType, ToolHarvestLevel> dv : types_harvests) {
			if (dv.getR() == ToolHarvestLevel.unbreakable) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + item.getUnlocalizedName() + ":" + types_harvests + ":" + drops + ")";
	}
}
