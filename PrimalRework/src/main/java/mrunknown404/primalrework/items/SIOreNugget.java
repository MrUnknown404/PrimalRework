package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.EnumMetal;
import mrunknown404.primalrework.utils.enums.EnumOreValue;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Rarity;

public class SIOreNugget extends StagedItem {
	public final EnumMetal metal;
	public final EnumOreValue value;
	
	public SIOreNugget(EnumMetal metal, EnumOreValue value) {
		super(metal + "_ore_nugget_" + value, metal.stage, 16, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.NUGGETS, Rarity.COMMON, null, false, false,
				ItemType.generated_colored);
		this.metal = metal;
		this.value = value;
	}
}
