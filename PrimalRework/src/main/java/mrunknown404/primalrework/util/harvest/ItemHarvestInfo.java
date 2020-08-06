package mrunknown404.primalrework.util.harvest;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;

public class ItemHarvestInfo {
	
	private final List<EnumToolType> toolTypes;
	private final EnumToolMaterial material;
	
	public ItemHarvestInfo(List<EnumToolType> toolTypes, EnumToolMaterial material) {
		this.material = material;
		this.toolTypes = toolTypes;
	}
	
	public ItemHarvestInfo(EnumToolType toolType, EnumToolMaterial material) {
		this(Arrays.asList(toolType), material);
	}
	
	public EnumToolMaterial getMaterial() {
		return material;
	}
	
	public List<EnumToolType> getToolTypes() {
		return toolTypes;
	}
}
