package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.utils.IColoredItem;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class SIRawPart<T extends Enum<T>> extends StagedItem implements IColoredItem {
	public final Metal metal;
	public final T part;
	
	private SIRawPart(String name, ItemGroup group, Metal metal, T part) {
		super(name, metal.stage, 32, ToolType.NONE, ToolMaterial.HAND, group, Rarity.COMMON, null, false, false, ItemType.generated);
		this.metal = metal;
		this.part = part;
	}
	
	public static <T extends Enum<T>> SIRawPart<T> rawPart(Metal metal, T part) {
		return new SIRawPart<T>(metal.toString() + "_" + part.toString(), PRItemGroups.RAW_PARTS, metal, part);
	}
	
	public static <T extends Enum<T>> SIRawPart<T> toolHead(Metal metal, T part) {
		return new SIRawPart<T>(metal.toString() + "_" + part.toString() + "_part", PRItemGroups.TOOL_PARTS, metal, part);
	}
	
	@Override
	public Metal getMetal() {
		return metal;
	}
}
