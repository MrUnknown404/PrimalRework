package mrunknown404.primalrework.utils.enums;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;

public interface ICraftingInput {
	public abstract ItemStack getIcon();
	public abstract IFormattableTextComponent getFancyName();
	public abstract String getName();
}
