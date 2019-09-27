package mrunknown404.primalrework.items.util;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import net.minecraft.item.ItemStack;

public class ItemDamageableBase extends ItemBase {

	protected ItemDamageableBase(String name, int maxStackSize, EnumToolMaterial level, EnumStage stage) {
		super(name, ModCreativeTabs.PRIMALREWORK_TOOLS, maxStackSize, stage);
		setMaxDamage(level.durability);
	}
	
	public ItemDamageableBase(String name, EnumToolMaterial level, EnumStage stage) {
		this(name, 1, level, stage);
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
