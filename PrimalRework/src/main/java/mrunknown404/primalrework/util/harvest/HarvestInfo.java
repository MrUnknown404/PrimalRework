package mrunknown404.primalrework.util.harvest;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.util.DoubleValue;
import net.minecraft.item.Item;

public class HarvestInfo {
	
	protected final List<DoubleValue<EnumToolType, EnumToolMaterial>> types_harvests;
	protected final Item item;
	
	public HarvestInfo(Item item, List<DoubleValue<EnumToolType, EnumToolMaterial>> types_harvests) {
		this.item = item;
		this.types_harvests = types_harvests;
	}
	
	public HarvestInfo(Item item, DoubleValue<EnumToolType, EnumToolMaterial> harvest) {
		this(item, Arrays.asList(harvest));
	}
	
	public Item getItem() {
		return item;
	}
	
	public List<DoubleValue<EnumToolType, EnumToolMaterial>> getTypesHarvests() {
		return types_harvests;
	}
	
	public int getHighestEnchantability() {
		int high = 0;
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> t : types_harvests) {
			if (t.getR().enchantability > high) {
				high = t.getR().enchantability;
			}
		}
		
		return high;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HarvestInfo) {
			return ((HarvestInfo) obj).item == item;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + item.getUnlocalizedName() + ":" + types_harvests + ")";
	}
}
