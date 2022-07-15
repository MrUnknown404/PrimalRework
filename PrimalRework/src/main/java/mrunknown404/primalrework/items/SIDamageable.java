package mrunknown404.primalrework.items;

import mrunknown404.primalrework.api.registry.PRRegistryObject;
import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class SIDamageable extends StagedItem {
	protected SIDamageable(PRRegistryObject<Stage>  stage, ToolType type, ToolMaterial mat, ItemGroup tab, ItemType itemType) {
		super(stage, 1, type, mat, tab, Rarity.COMMON, null, false, false, itemType);
	}
	
	public SIDamageable(PRRegistryObject<Stage>  stage, ToolMaterial mat) {
		this(stage, ToolType.NONE, mat, InitItemGroups.ITEMS, ItemType.generated);
	}
}
