package mrunknown404.primalrework.helpers;

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
}
