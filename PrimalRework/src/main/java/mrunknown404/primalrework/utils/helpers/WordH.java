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
	
	public static final Style STYLE_BLACK = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.BLACK));
	public static final Style STYLE_DARK_BLUE = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.DARK_BLUE));
	public static final Style STYLE_DARK_GREEN = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.DARK_GREEN));
	public static final Style STYLE_DARK_AQUA = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.DARK_AQUA));
	public static final Style STYLE_DARK_RED = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.DARK_RED));
	public static final Style STYLE_DARK_PURPLE = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.DARK_PURPLE));
	public static final Style STYLE_GOLD = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.GOLD));
	public static final Style STYLE_GRAY = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.GRAY));
	public static final Style STYLE_DARK_GRAY = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.DARK_GRAY));
	public static final Style STYLE_BLUE = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.BLUE));
	public static final Style STYLE_GREEN = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.GREEN));
	public static final Style STYLE_AQUA = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.AQUA));
	public static final Style STYLE_RED = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.RED));
	public static final Style STYLE_LIGHT_PURPLE = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.LIGHT_PURPLE));
	public static final Style STYLE_YELLOW = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.YELLOW));
	public static final Style STYLE_WHITE = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.WHITE));
	public static final Style STYLE_OBFUSCATED = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.OBFUSCATED));
	public static final Style STYLE_BOLD = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.BOLD));
	public static final Style STYLE_STRIKETHROUGH = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.STRIKETHROUGH));
	public static final Style STYLE_UNDERLINE = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.UNDERLINE));
	public static final Style STYLE_ITALIC = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.ITALIC));
	public static final Style STYLE_RESET = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.RESET));
	
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
