package mrunknown404.primalrework.items;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.IThingBase;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import net.minecraft.item.Item;

public interface IItemBase extends IThingBase<Item> {
	@Override
	public default void registerModels(Item item) {
		Main.proxy.registerItemRenderer(item, 0, "inventory");
	}
	
	@Override
	public default void addToModList(Item item) {
		ModItems.ITEMS.add(item);
	}
	
	public EnumToolType getToolType();
	public EnumToolMaterial getHarvestLevel();
}
