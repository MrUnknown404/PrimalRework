package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.EnumMetal;
import mrunknown404.primalrework.utils.enums.EnumMetalPart;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Rarity;

public class SIMetalPart extends StagedItem {
	public final EnumMetal metal;
	public final EnumMetalPart part;
	
	public SIMetalPart(EnumMetal metal, EnumMetalPart part) {
		super(metal.toString() + "_" + part.toString(), metal.stage, 32, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.METAL_PARTS, Rarity.COMMON, null, false, false,
				metal == EnumMetal.unknown ? ItemType.generated : ItemType.generated_colored);
		this.metal = metal;
		this.part = part;
	}
}
