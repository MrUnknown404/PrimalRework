package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.IColoredItem;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.EnumMetal;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Rarity;

public class SIIngot extends StagedItem implements IColoredItem {
	public final EnumMetal metal;
	public final boolean isNugget;
	
	public SIIngot(EnumMetal metal, boolean isNugget) {
		super(metal.toString() + (isNugget ? "_nugget" : "_ingot"), metal.stage, isNugget ? 32 : 16, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.ITEMS, Rarity.COMMON,
				null, false, false, ItemType.generated);
		this.metal = metal;
		this.isNugget = isNugget;
	}
	
	@Override
	public EnumMetal getMetal() {
		return metal;
	}
}
