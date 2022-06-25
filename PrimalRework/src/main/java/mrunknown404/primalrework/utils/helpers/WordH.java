package mrunknown404.primalrework.utils.helpers;

import java.text.NumberFormat;
import java.util.Locale;

import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class WordH {
	private static final NumberFormat FORMAT_NUMBER = NumberFormat.getNumberInstance(Locale.ENGLISH);
	
	public static final Style STYLE_GRAY = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.GRAY));
	public static final Style STYLE_LIGHT_PURPLE = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.LIGHT_PURPLE));
	
	public static StringTextComponent string(String str) {
		return new StringTextComponent(str);
	}
	
	public static TranslationTextComponent translate(String str) {
		return new TranslationTextComponent(str);
	}
	
	public static TranslationTextComponent translate(String str, Object... objs) {
		return new TranslationTextComponent(str, objs);
	}
	
	public static StringTextComponent space() {
		return string(" ");
	}
	
	public static String toPrintableNumber(double value) {
		String s = FORMAT_NUMBER.format(MathH.roundTo(value, 1));
		return s.contains(".") ? s : s + ".0";
	}
	
	public static String toPrintableNumber(long value) {
		return FORMAT_NUMBER.format(value);
	}
}
