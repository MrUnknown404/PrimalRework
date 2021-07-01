package mrunknown404.primalrework.items;

import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.utils.enums.EnumAlloy;
import mrunknown404.primalrework.utils.enums.EnumOreValue;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Rarity;

public class SINugget extends StagedItem {
	public final EnumAlloy alloy;
	public final EnumOreValue value;
	
	public SINugget(EnumAlloy alloy, EnumOreValue value) {
		super(alloy + "_nugget_" + value, alloy.stage, 16, EnumToolType.none, EnumToolMaterial.hand, InitItemGroups.NUGGETS, Rarity.COMMON, null, false, false,
				ItemType.generated);
		this.alloy = alloy;
		this.value = value;
	}
}
