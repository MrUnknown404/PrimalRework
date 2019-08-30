package mrunknown404.primalrework.util.harvest;

import java.util.Arrays;
import java.util.List;

public class HarvestDropInfo {
	private final ToolType toolType;
	private final boolean replace;
	private final List<ItemDropInfo> drops;
	
	/**@param toolType
	 * @param replace
	 * @param drops
	 */
	public HarvestDropInfo(ToolType toolType, boolean replace, ItemDropInfo... drops) {
		this.toolType = toolType;
		this.replace = replace;
		this.drops = Arrays.asList(drops);
	}
	
	public boolean isReplace() {
		return replace;
	}
	
	public ToolType getToolType() {
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
}
