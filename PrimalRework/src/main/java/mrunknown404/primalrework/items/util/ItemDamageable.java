package mrunknown404.primalrework.items.util;

import mrunknown404.primalrework.init.InitCreativeTabs;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemDamageable extends ItemStaged {
	
	protected ItemDamageable(String name, CreativeTabs tab, EnumToolMaterial level, EnumStage stage) {
		super(name, tab, 1, stage);
		setMaxDamage(level.durability);
	}
	
	public ItemDamageable(String name, EnumToolMaterial level, EnumStage stage) {
		this(name, InitCreativeTabs.PRIMALREWORK_TOOLS, level, stage);
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack s = stack.copy();
		s.setItemDamage(stack.getItemDamage() + 1);
		return s;
	}
}
