package mrunknown404.primalrework.items;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.harvest.ToolHarvestLevel;
import mrunknown404.primalrework.util.harvest.ToolType;
import net.minecraft.item.Item;

public interface IItemBase {
	public default void registerModels(Item item) {
		Main.proxy.registerItemRenderer(item, 0, "inventory");
	}
	
	public default void addToModList(Item item) {
		ModItems.ITEMS.add(item);
	}
	
	public ToolType getToolType();
	public ToolHarvestLevel getHarvestLevel();
}
