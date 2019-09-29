package mrunknown404.primalrework.util.harvest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.block.Block;

public class BlockHarvestInfo extends HarvestInfo<Block> {
	
	private final List<DoubleValue<EnumToolType, EnumToolMaterial>> harvests;
	private final List<HarvestDropInfo> drops = new ArrayList<HarvestDropInfo>();
	
	public BlockHarvestInfo(Block block, List<DoubleValue<EnumToolType, EnumToolMaterial>> harvests) {
		super(block, new ArrayList<EnumToolType>());
		this.harvests = harvests;
	}
	
	public static BlockHarvestInfo create(DoubleValue<EnumToolType, EnumToolMaterial>... harvest) {
		return new BlockHarvestInfo(null, Arrays.asList(harvest));
	}
	
	public static BlockHarvestInfo create() {
		return new BlockHarvestInfo(null, Arrays.asList(new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand)));
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
		return HarvestHelper.hasToolMaterial(type, EnumToolMaterial.hand) || HarvestHelper.hasToolType(type, EnumToolType.none);
	}
	
	public List<DoubleValue<EnumToolType, EnumToolMaterial>> getHarvests() {
		return harvests;
	}
	
	@Override
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
