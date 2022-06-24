package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.IColoredItem;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.Rarity;

public class SIIngot extends StagedItem implements IColoredItem {
	public final Metal metal;
	public final boolean isNugget;
	
	public SIIngot(Metal metal, boolean isNugget) {
		super(metal.toString() + (isNugget ? "_nugget" : "_ingot"), metal.stage, isNugget ? 32 : 16, ToolType.NONE, ToolMaterial.HAND, PRItemGroups.ITEMS, Rarity.COMMON,
				null, false, false, ItemType.generated);
		this.metal = metal;
		this.isNugget = isNugget;
	}
	
	@Override
	public Metal getMetal() {
		return metal;
	}
}
