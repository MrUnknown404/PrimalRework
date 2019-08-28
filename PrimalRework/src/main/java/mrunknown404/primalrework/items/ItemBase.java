package mrunknown404.primalrework.items;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name, CreativeTabs tab, int maxStackSize) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setMaxStackSize(maxStackSize);
		
		ModItems.ITEMS.add(this);
	}
	
	public ItemBase(String name, CreativeTabs tab) {
		this(name, tab, 64);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
