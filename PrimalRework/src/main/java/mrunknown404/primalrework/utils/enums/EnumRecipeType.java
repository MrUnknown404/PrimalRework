package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.utils.IName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum EnumRecipeType implements IName {
	//@formatter:off
	crafting_3(PRBlocks.PRIMAL_CRAFTING_TABLE.get().asItem()),
	campfire  (PRBlocks.CAMPFIRE.get().asItem());
	//@formatter:on
	
	public final ItemStack icon;
	private final IFormattableTextComponent name;
	
	private EnumRecipeType(Item icon) {
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
}
