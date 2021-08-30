package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.EnumMetal;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Rarity;

public class SIIngot extends StagedItem {
	public final EnumMetal metal;
	public final boolean isNugget;
	
	public SIIngot(EnumMetal metal, boolean isNugget, int stackSize) {
		super(metal.toString() + (isNugget ? "_nugget" : "_ingot"), metal.stage, stackSize, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.ITEMS, Rarity.COMMON, null,
				false, false, metal == EnumMetal.unknown ? ItemType.generated : ItemType.generated_colored);
		this.metal = metal;
		this.isNugget = isNugget;
	}
}
