package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.Rarity;

public class SIOre extends StagedItem {
	public final Metal metal;
	
	public SIOre(Metal metal) {
		super(metal.toString() + "_ore", metal.stage, 16, ToolType.NONE, ToolMaterial.HAND, PRItemGroups.ORES, Rarity.COMMON, null, false, false, ItemType.generated);
		this.metal = metal;
	}
}
