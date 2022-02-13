package mrunknown404.primalrework.items.utils;

import java.util.function.Supplier;

import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class SIDamageable extends StagedItem {
	protected SIDamageable(String name, Supplier<Stage> stage, EnumToolType type, EnumToolMaterial mat, ItemGroup tab, ItemType itemType) {
		super(name, stage, 1, type, mat, tab, Rarity.COMMON, null, false, false, itemType);
	}
	
	public SIDamageable(String name, Supplier<Stage> stage, EnumToolMaterial mat) {
		this(name, stage, EnumToolType.none, mat, PRItemGroups.ITEMS, ItemType.generated);
	}
}
