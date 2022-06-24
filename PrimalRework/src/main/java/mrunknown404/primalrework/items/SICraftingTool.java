package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.IColoredItem;
import mrunknown404.primalrework.items.utils.SIDamageable;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.CraftingToolType;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;

public class SICraftingTool extends SIDamageable implements IColoredItem {
	public final Metal metal;
	public final CraftingToolType type;
	
	public SICraftingTool(Metal metal, CraftingToolType type) {
		super(metal.toString() + "_" + type.toString(), metal.stage, ToolType.NONE, ToolMaterial.HAND, PRItemGroups.TOOLS,
				type == CraftingToolType.SAW ? ItemType.handheld_rod : ItemType.handheld);
		this.metal = metal;
		this.type = type;
	}
	
	@Override
	public Metal getMetal() {
		return metal;
	}
}
