package mrunknown404.primalrework.items;

import mrunknown404.primalrework.init.InitPRItemGroups;
import mrunknown404.primalrework.init.InitToolMaterials;
import mrunknown404.primalrework.utils.IMetalColored;
import mrunknown404.primalrework.utils.Metal;
import mrunknown404.primalrework.utils.enums.CraftingToolType;
import mrunknown404.primalrework.utils.enums.ToolType;

public class SICraftingTool extends SIDamageable implements IMetalColored {
	public final Metal metal;
	public final CraftingToolType type;
	
	public SICraftingTool(Metal metal, CraftingToolType type) {
		super(metal.stage, ToolType.NONE, InitToolMaterials.HAND.get(), InitPRItemGroups.TOOLS, type == CraftingToolType.SAW ? ItemType.handheld_rod : ItemType.handheld);
		this.metal = metal;
		this.type = type;
	}
	
	@Override
	public Metal getMetal() {
		return metal;
	}
}
