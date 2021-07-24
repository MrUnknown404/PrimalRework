package mrunknown404.primalrework.utils.enums;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum EnumFuelType implements ICraftingInput {
	campfire(Items.COAL);
	
	private final ItemStack icon;
	private final IFormattableTextComponent name;
	
	private EnumFuelType(Item icon) {
		this.icon = new ItemStack(icon);
		this.name = new TranslationTextComponent("recipetype." + name() + ".name");
	}
	
	@Override
	public IFormattableTextComponent getFancyName() {
		return name;
	}
	
	@Override
	public String getName() {
		return name.getString();
	}
	
	@Override
	public ItemStack getIcon() {
		return icon;
	}
}
