package mrunknown404.primalrework.items.util;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import net.minecraft.item.ItemStack;

public class ItemDamageableBase extends ItemBase {

	public ItemDamageableBase(String name, EnumToolMaterial level, EnumStage stage) {
		super(name, ModCreativeTabs.PRIMALREWORK_TOOLS, 1, stage);
		setMaxDamage(level.durability);
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
