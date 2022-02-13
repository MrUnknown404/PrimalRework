package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.EnumMetal;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Rarity;

public class SIOre extends StagedItem {
	public final EnumMetal metal;
	
	public SIOre(EnumMetal metal) {
		super(metal.toString() + "_ore", metal.stage, 16, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.ORES, Rarity.COMMON, null, false, false, ItemType.generated);
		this.metal = metal;
	}
}
