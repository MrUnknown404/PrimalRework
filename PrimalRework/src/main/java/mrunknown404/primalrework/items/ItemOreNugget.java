package mrunknown404.primalrework.items;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.items.util.ItemBase;
import mrunknown404.primalrework.util.enums.EnumAlloy;
import mrunknown404.primalrework.util.enums.EnumOreValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import net.minecraft.util.text.TextComponentTranslation;

public class ItemOreNugget extends ItemBase {

	protected final EnumAlloy alloy;
	protected final EnumOreValue value;
	
	public ItemOreNugget(EnumStage stage, EnumAlloy alloy, EnumOreValue value) {
		super(alloy.toString() + "_nugget_" + value.toString(), stage);
		this.alloy = alloy;
		this.value = value;
	}
	
	@Override
	public int getAmountOfTooltips() {
		return 1;
	}
	
	@Override
	public List<String> getTooltips(String name) {
		return Arrays.asList("" + value.units + " " + new TextComponentTranslation("item.ore_units.tooltip").getUnformattedText());
	}
}