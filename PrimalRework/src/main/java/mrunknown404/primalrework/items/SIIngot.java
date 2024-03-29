package mrunknown404.primalrework.items;

import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.init.InitToolMaterials;
import mrunknown404.primalrework.utils.IMetalColored;
import mrunknown404.primalrework.utils.Metal;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.Rarity;

public class SIIngot extends StagedItem implements IMetalColored {
	public final Metal metal;
	
	public SIIngot(Metal metal) {
		super(metal.stage, 64, ToolType.NONE, InitToolMaterials.HAND.get(), InitItemGroups.ITEMS, Rarity.COMMON, null, false, false, ItemType.generated);
		this.metal = metal;
		this.elements.putAll(metal.elements);
	}
	
	@Override
	public Metal getMetal() {
		return metal;
	}
}
