package mrunknown404.primalrework.util.helpers;

public class ColorH {
	public static String addColor(String str) {
		char[] b = str.toCharArray();
		for (int i = 0; i < b.length - 1; i++) {
			if (b[i] == '&' && getAllCodes().indexOf(b[i + 1]) > -1) {
				b[i] = '\u00A7';
			}
		}
		return new String(b);
		
	}
	
	public static String getAllColorCodes() {
		StringBuilder sb = new StringBuilder();
		for (ColorCodes c : ColorCodes.values()) {
			sb.append(c.code);
		}
		
		return sb.toString();
	}
	
	public static String getAllFormatCodes() {
		StringBuilder sb = new StringBuilder();
		for (FormatCodes f : FormatCodes.values()) {
			sb.append(f.code);
		}
		
		return sb.toString();
	}
	
	public static String getAllCodes() {
		return getAllColorCodes() + getAllFormatCodes();
	}
	
	public static String getRainbowCodes() {
		return "4c6e2ab319d5";
	}
	
	public enum ColorCodes {
		DARK_RED    ("4"),
		RED         ("c"),
		GOLD        ("6"),
		YELLOW      ("e"),
		DARK_GREEN  ("2"),
		GREEN       ("a"),
		AQUA        ("b"),
		DARK_AQUA   ("3"),
		DARK_BLUE   ("1"),
		BLUE        ("9"),
		LIGHT_PURPLE("d"),
		DARK_PURPLE ("5"),
		WHITE       ("f"),
		GRAY        ("7"),
		DARK_GRAY   ("8"),
		BLACK       ("9");
		
		public final String code;
		
		private ColorCodes(String code) {
			this.code = code;
		}
	}
	
	public enum FormatCodes {
		BOLD         ("l"),
		STRIKETHROUGH("m"),
		UNDERLINE    ("n"),
		ITALIC       ("o"),
		OBFUSCATED   ("k"),
		RESET        ("r");
		
		public final String code;
		
		private FormatCodes(String code) {
			this.code = code;
		}
	}
}
