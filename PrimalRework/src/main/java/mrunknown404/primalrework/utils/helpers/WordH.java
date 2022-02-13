package mrunknown404.primalrework.utils.helpers;

import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class WordH {
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
}
