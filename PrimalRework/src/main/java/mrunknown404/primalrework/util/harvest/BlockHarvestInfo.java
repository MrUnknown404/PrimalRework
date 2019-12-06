package mrunknown404.primalrework.util.harvest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.helpers.HarvestHelper;

public class BlockHarvestInfo {
	
	private final List<DoubleValue<EnumToolType, EnumToolMaterial>> harvests;
	private final List<HarvestDropInfo> drops = new ArrayList<HarvestDropInfo>();
	
	public BlockHarvestInfo(List<DoubleValue<EnumToolType, EnumToolMaterial>> harvests) {
		this.harvests = harvests;
	}
	
	public BlockHarvestInfo(DoubleValue<EnumToolType, EnumToolMaterial>... harvests) {
		this(Arrays.asList(harvests));
	}
	
	public BlockHarvestInfo(EnumToolType toolType, EnumToolMaterial toolMaterial) {
		this(new DoubleValue<EnumToolType, EnumToolMaterial>(toolType, toolMaterial));
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
	
	public List<HarvestDropInfo> getAllDrops() {
		return drops;
	}
	
	public HarvestDropInfo getDrop(EnumToolType type) {
		for (HarvestDropInfo drop : drops) {
			if (drop.getToolType() == type) {
				return drop;
			}
		}
		
		return null;
	}
	
	public boolean canBreakWithNone() {
		return HarvestHelper.hasToolMaterial(HarvestHelper.getBlock(this), EnumToolMaterial.hand) || HarvestHelper.hasToolType(HarvestHelper.getBlock(this), EnumToolType.none);
	}
	
	public List<DoubleValue<EnumToolType, EnumToolMaterial>> getHarvests() {
		return harvests;
	}
	
	public List<EnumToolType> getToolTypes() {
		List<EnumToolType> types = new ArrayList<EnumToolType>();
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> t : harvests) {
			types.add(t.getL());
		}
		
		return types;
	}
	
	public List<EnumToolMaterial> getToolMaterials() {
		List<EnumToolMaterial> types = new ArrayList<EnumToolMaterial>();
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> t : harvests) {
			types.add(t.getR());
		}
		
		return types;
	}
}
