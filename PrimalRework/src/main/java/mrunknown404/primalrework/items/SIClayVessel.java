package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Rarity;

public class SIClayVessel extends StagedItem {
	public SIClayVessel() { //TODO this class (clay vessel)
		super("clay_vessel", EnumStage.stage2, 1, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.ITEMS, Rarity.COMMON, null, false, false, ItemType.generated);
	}
}
