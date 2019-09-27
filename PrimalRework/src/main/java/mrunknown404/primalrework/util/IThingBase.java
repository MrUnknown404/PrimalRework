package mrunknown404.primalrework.util;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.ColorH;
import net.minecraft.util.text.TextComponentTranslation;

public interface IThingBase<T, R> {
	public abstract void registerModels(T t);
	public abstract void addToModList(T t);
	public abstract int getAmountOfTooltips();
	public abstract R setAmountOfTooltops(int amountOfToolTips);
	public abstract EnumStage getStage();
	
	public default List<String> getTooltips(String name) {
		List<String> tooltip = new ArrayList<String>();
		
		for (int i = 0; i < getAmountOfTooltips(); i++) {
			TextComponentTranslation tip = new TextComponentTranslation(name + ".tooltip_" + i);
			tooltip.add(ColorH.addColor(tip.getUnformattedText().trim()));
		}
		return tooltip;
	}
}
