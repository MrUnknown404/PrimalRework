package mrunknown404.primalrework.utils.helpers;

import java.util.Locale;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class WordH {
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
	
	public static String formatNumber(int decimals, double value, Object... objs) {
		return String.format(Locale.ROOT, "%." + decimals + "f", value, objs);
	}
}
