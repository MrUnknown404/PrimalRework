package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.IColoredItem;
import mrunknown404.primalrework.items.utils.SIDamageable;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.EnumCraftingToolType;
import mrunknown404.primalrework.utils.enums.EnumMetal;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;

public class SICraftingTool extends SIDamageable implements IColoredItem {
	public final EnumMetal metal;
	public final EnumCraftingToolType type;
	
	public SICraftingTool(EnumMetal metal, EnumCraftingToolType type) {
		super(metal.toString() + "_" + type.toString(), metal.stage, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.TOOLS,
				type == EnumCraftingToolType.saw ? ItemType.handheld_rod : ItemType.handheld);
		this.metal = metal;
		this.type = type;
	}
	
	@Override
	public EnumMetal getMetal() {
		return metal;
	}
}
