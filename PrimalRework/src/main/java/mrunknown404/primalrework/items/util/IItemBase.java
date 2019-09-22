package mrunknown404.primalrework.items.util;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.IThingBase;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.item.Item;

public interface IItemBase<R extends ItemBase> extends IThingBase<Item, R> {
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
