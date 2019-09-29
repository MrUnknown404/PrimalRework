package mrunknown404.primalrework.util.harvest;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.enums.EnumToolType;

public abstract class HarvestInfo<T> {
	
	protected final List<EnumToolType> toolTypes;
	protected final T type;
	
	protected HarvestInfo(T type, List<EnumToolType> toolTypes) {
		this.type = type;
		this.toolTypes = toolTypes;
	}
	
	protected HarvestInfo(T type, EnumToolType toolType) {
		this(type, Arrays.asList(toolType));
	}
	
	public T getType() {
		return type;
	}
	
	public List<EnumToolType> getToolTypes() {
		return toolTypes;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HarvestInfo) {
			return ((HarvestInfo<T>) obj).type == type;
		}
		
		return false;
	}
}
