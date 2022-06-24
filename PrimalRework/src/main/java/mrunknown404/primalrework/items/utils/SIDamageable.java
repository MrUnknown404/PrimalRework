package mrunknown404.primalrework.items.utils;

import java.util.function.Supplier;

import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class SIDamageable extends StagedItem {
	protected SIDamageable(String name, Supplier<Stage> stage, ToolType type, ToolMaterial mat, ItemGroup tab, ItemType itemType) {
		super(name, stage, 1, type, mat, tab, Rarity.COMMON, null, false, false, itemType);
	}
	
	public SIDamageable(String name, Supplier<Stage> stage, ToolMaterial mat) {
		this(name, stage, ToolType.NONE, mat, PRItemGroups.ITEMS, ItemType.generated);
	}
}
