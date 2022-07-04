package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;

public enum RecipeType implements ICraftingInput {
	//@formatter:off
	CRAFTING_3(InitBlocks.PRIMAL_CRAFTING_TABLE),
	CAMPFIRE  (InitBlocks.CAMPFIRE);
	//@formatter:on
	
	private final ItemStack icon;
	private final IFormattableTextComponent name;
	
	private RecipeType(ISIProvider icon) {
		this.icon = new ItemStack(icon.getStagedItem());
		this.name = WordH.translate("recipetype." + toString() + ".name");
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
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
