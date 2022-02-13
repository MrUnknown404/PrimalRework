package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRItems;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;

public enum EnumFuelType implements ICraftingInput {
	campfire(PRItems.PLANT_FIBER.get()); //TODO switch to fuel
	
	private final ItemStack icon;
	private final IFormattableTextComponent name;
	
	private EnumFuelType(StagedItem icon) {
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
