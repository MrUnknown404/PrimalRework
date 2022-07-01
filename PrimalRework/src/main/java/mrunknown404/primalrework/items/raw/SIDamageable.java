package mrunknown404.primalrework.items.raw;

import java.util.function.Supplier;

import mrunknown404.primalrework.init.InitPRItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class SIDamageable extends StagedItem {
	protected SIDamageable(Supplier<Stage> stage, ToolType type, ToolMaterial mat, ItemGroup tab, ItemType itemType) {
		super(stage, 1, type, mat, tab, Rarity.COMMON, null, false, false, itemType);
	}
	
	public SIDamageable(Supplier<Stage> stage, ToolMaterial mat) {
		this(stage, ToolType.NONE, mat, InitPRItemGroups.ITEMS, ItemType.generated);
	}
}
