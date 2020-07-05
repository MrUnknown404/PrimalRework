package mrunknown404.primalrework.items.util;

import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.IThingBase;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.item.Item;

public interface IItemBase<R extends Item> extends IThingBase<Item, R> {
	@Override
	public default void addToModList(Item item) {
		ModItems.ITEMS.add(item);
	}
	
	public abstract EnumToolType getToolType();
	public abstract EnumToolMaterial getHarvestLevel();
}
