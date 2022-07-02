package mrunknown404.primalrework.items;

import mrunknown404.primalrework.utils.IMetalColored;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class SIRawPart<T extends Enum<T>> extends StagedItem implements IMetalColored {
	public final Metal metal;
	public final T part;
	
	public SIRawPart(ItemGroup group, Metal metal, T part) {
		super(metal.stage, 32, ToolType.NONE, ToolMaterial.HAND, group, Rarity.COMMON, null, false, false, ItemType.generated);
		this.metal = metal;
		this.part = part;
		this.elements.putAll(metal.getElements());
	}
	
	@Override
	public Metal getMetal() {
		return metal;
	}
}
