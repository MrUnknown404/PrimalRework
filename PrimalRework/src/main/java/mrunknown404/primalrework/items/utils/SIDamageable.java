package mrunknown404.primalrework.items.utils;

import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class SIDamageable extends StagedItem {
	protected SIDamageable(String name, EnumStage stage, EnumToolType type, EnumToolMaterial mat, ItemGroup tab, ItemType itemType) {
		super(name, stage, 1, type, mat, tab, Rarity.COMMON, null, false, false, itemType);
	}
	
	public SIDamageable(String name, EnumStage stage, EnumToolMaterial mat) {
		this(name, stage, EnumToolType.none, mat, InitItemGroups.ITEMS, ItemType.generated);
	}
}
