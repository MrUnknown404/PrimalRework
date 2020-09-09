package mrunknown404.primalrework.items.util;

import mrunknown404.primalrework.init.InitItems;
import mrunknown404.primalrework.util.IStaged;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.item.Item;

public interface IItemStaged<T extends Item> extends IStaged<T> {
	@Override
	public default void addToModList(T item) {
		InitItems.ITEMS.add(item);
	}
	
	public abstract EnumToolType getToolType();
	public abstract EnumToolMaterial getHarvestLevel();
}
