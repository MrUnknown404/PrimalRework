package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;

public enum EnumRecipeType implements ICraftingInput {
	//@formatter:off
	crafting_3(PRBlocks.PRIMAL_CRAFTING_TABLE.get().asStagedItem()),
	campfire  (PRBlocks.CAMPFIRE.get().asStagedItem());
	//@formatter:on
	
	private final ItemStack icon;
	private final IFormattableTextComponent name;
	
	private EnumRecipeType(StagedItem icon) {
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
