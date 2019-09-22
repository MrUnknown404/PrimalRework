package mrunknown404.primalrework.items.util;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import net.minecraft.item.ItemStack;

public class ItemDamageableBase extends ItemBase {

	protected ItemDamageableBase(String name, int maxStackSize, EnumToolMaterial level) {
		super(name, ModCreativeTabs.PRIMALREWORK_TOOLS, maxStackSize);
		setMaxDamage(level.durability);
	}
	
	public ItemDamageableBase(String name, EnumToolMaterial level) {
		this(name, 1, level);
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
