package mrunknown404.primalrework.util.harvest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BlockHarvestInfo extends HarvestInfo {
	
	private final List<HarvestDropInfo> drops = new ArrayList<HarvestDropInfo>();
	
	public BlockHarvestInfo(Block block, List<DoubleValue<EnumToolType, EnumToolMaterial>> harvest) {
		super(Item.getItemFromBlock(block), harvest);
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
	
	public HarvestDropInfo getDrop(EnumToolType type) {
		for (HarvestDropInfo drop : drops) {
			if (drop.getToolType() == type) {
				return drop;
			}
		}
		
		return null;
	}
	
	public boolean canBreakWithNone() {
		for (DoubleValue<EnumToolType, EnumToolMaterial> dv : types_harvests) {
			if (dv.getL() == EnumToolType.none || dv.getR() == EnumToolMaterial.hand) {
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
