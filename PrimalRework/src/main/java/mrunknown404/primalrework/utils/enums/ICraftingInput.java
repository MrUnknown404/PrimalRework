package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.utils.IName;
import net.minecraft.item.ItemStack;

public interface ICraftingInput extends IName {
	public abstract ItemStack getIcon();
}
