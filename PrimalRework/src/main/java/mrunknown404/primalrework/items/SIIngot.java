package mrunknown404.primalrework.items;

import mrunknown404.primalrework.init.InitPRItemGroups;
import mrunknown404.primalrework.registry.Metal;
import mrunknown404.primalrework.utils.IMetalColored;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.Rarity;

public class SIIngot extends StagedItem implements IMetalColored {
	public final Metal metal;
	
	public SIIngot(Metal metal, boolean isNugget) {
		super(metal.stage, isNugget ? 32 : 16, ToolType.NONE, ToolMaterial.HAND, InitPRItemGroups.ITEMS, Rarity.COMMON, null, false, false, ItemType.generated);
		this.metal = metal;
		this.elements.putAll(metal.elements);
	}
	
	@Override
	public Metal getMetal() {
		return metal;
	}
}
