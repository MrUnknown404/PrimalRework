package mrunknown404.primalrework.items;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.items.util.ItemStaged;
import mrunknown404.primalrework.util.enums.EnumAlloy;
import mrunknown404.primalrework.util.enums.EnumOreValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import net.minecraft.util.text.TextComponentTranslation;

public class ItemIngot extends ItemStaged {
	
	protected final EnumAlloy alloy;
	
	public ItemIngot(EnumStage stage, EnumAlloy alloy) {
		super(alloy.toString() + "_ingot", stage);
		this.alloy = alloy;
	}
	
	@Override
	public List<String> getTooltips(String name) {
		return Arrays.asList("" + getOreValue().units + " " + new TextComponentTranslation("item.ore_units.tooltip").getUnformattedText());
	}
	
	public EnumAlloy getAlloy() {
		return alloy;
	}
	
	public EnumOreValue getOreValue() {
		return EnumOreValue.perfect;
	}
}
