package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.helpers.WordH;
import mrunknown404.primalrework.registries.PRBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;

public enum EnumRecipeType implements ICraftingInput {
	//@formatter:off
	crafting_3(PRBlocks.PRIMAL_CRAFTING_TABLE.get().asItem()),
	campfire  (PRBlocks.CAMPFIRE.get().asItem());
	//@formatter:on
	
	private final ItemStack icon;
	private final IFormattableTextComponent name;
	
	private EnumRecipeType(Item icon) {
		this.icon = new ItemStack(icon);
		this.name = WordH.translate("recipetype." + name() + ".name");
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
