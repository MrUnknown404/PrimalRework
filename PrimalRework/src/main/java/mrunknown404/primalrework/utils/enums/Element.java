package mrunknown404.primalrework.utils.enums;

public enum Element {
	UNKNOWN("??", 0, 0),
	HYDROGEN("H", 14, 20),
	HELIUM("He", 1, 4),
	LITHIUM("Li", 454, 1615),
	BERYLLIUM("Be", 1560, 2742),
	BORON("B", 2349, 4273),
	CARBON("C", 3823, 5100),
	NITROGEN("N", 63, 77),
	OXYGEN("O", 54, 90),
	FLUORINE("F", 54, 85),
	NEON("Ne", 25, 27),
	SODIUM("Na", 371, 1156),
	MAGNESIUM("Mg", 923, 1364),
	ALUMINUM("Al", 933, 2743),
	SILICON("Si", 1683, 2628),
	PHOSPHORUS("P", 317, 554),
	SULFUR("S", 386, 718),
	CHLORINE("Cl", 172, 239),
	ARGON("Ar", 84, 87),
	POTASSIUM("K", 337, 1032),
	CALCIUM("Ca", 1115, 1757),
	SCANDIUM("Sc", 1814, 3109),
	TITANIUM("Ti", 1941, 3560),
	VANADIUM("V", 2183, 3680),
	CHROMIUM("Cr", 2180, 2945),
	MANGANESE("Mn", 1519, 2334),
	IRON("Fe", 1811, 3135),
	COBALT("Co", 1768, 3143),
	NICKEL("Ni", 1728, 3003),
	COPPER("Cu", 1358, 2835),
	ZINC("Zn", 693, 1180),
	GALLIUM("Ga", 303, 2673),
	GERMANIUM("Ge", 1211, 3106),
	ARSENIC("As", 1090, 886),
	SELENIUM("Se", 494, 958),
	BROMINE("Br", 266, 332),
	KRYPTON("Kr", 116, 120),
	RUBIDIUM("Rb", 313, 961),
	STRONTIUM("Sr", 1050, 1655),
	YTTRIUM("Y", 1799, 3611),
	ZIRCONIUM("Zr", 2128, 4682),
	NIOBIUM("Nb", 2742, 5200),
	MOLYBENDUM("Mo", 2896, 4912),
	TECHNETIUM("Tc", 2477, 4538),
	RUTHENIUM("Ru", 2607, 4423),
	RHODIUM("Rh", 2236, 3970),
	PALLADIUM("Pd", 1828, 3236),
	SILVER("Ag", 1235, 2435),
	CADMIUM("Cd", 594, 1040),
	INDIUM("In", 430, 2345),
	TIN("Sn", 505, 2875),
	ANTIMONY("Sb", 904, 1860),
	TELLURIUM("Te", 723, 1261),
	IODINE("I", 387, 457),
	XENON("Xe", 161, 165),
	CAESIUM("Cs", 302, 944),
	BARIUM("Ba", 1000, 2170),
	LANTHANUM("La", 1193, 3737),
	CERIUM("Ce", 1068, 3530),
	PRASEODYMIUM("Pr", 1204, 3793),
	NEODYMIUM("Nd", 1294, 3347),
	PROMETHIUM("Pm", 1315, 3273),
	SAMARIUM("Sm", 1345, 2067),
	EUROPIUM("Eu", 1099, 1802),
	GADOLINIUM("Gd", 1585, 3545),
	TERBIUM("Td", 1629, 3503),
	DYSPROSIUM("Dy", 1685, 2840),
	HOLMIUM("Ho", 1747, 2968),
	ERBIUM("Er", 1802, 3141),
	THULIUM("Tm", 1818, 2223),
	YTTERBIUM("Yb", 1092, 1469),
	LUTETIUM("Lu", 1936, 3675),
	HAFNIUM("Hf", 2500, 4875),
	TANTALUM("Ta", 3293, 5730),
	TUNGSTEN("W", 3695, 5828),
	RHENIUM("Re", 3455, 5870),
	OSMIUM("Os", 3306, 5300),
	IRIDIUM("Ir", 2719, 4701),
	PLATINUM("Pt", 2041, 4098),
	GOLD("Au", 1337, 2973),
	MERCURY("Hg", 234, 630),
	THALLIUM("Tl", 577, 1746),
	LEAD("Pb", 601, 2022),
	BISMUTH("Bi", 545, 1837),
	POLONIUM("Po", 527, 1235),
	ASTATINE("At", 575, 610),
	RADON("Rn", 202, 211),
	FRANCIUM("Fr", 300, 950),
	RADIUM("Ra", 973, 2010),
	ACTINIUM("Ac", 1323, 3470),
	THORIUM("Th", 2028, 5060),
	PROTACTINIUM("Pa", 1841, 4273),
	URANIUM("U", 1405, 4404),
	NEPTUNIUM("Np", 917, 4175),
	PLUTONIUM("Pu", 913, 3505),
	AMERICIUM("Am", 1449, 2880),
	CURIUM("Cm", 1620, 3383),
	BERKELIUM("Bk", 1258, 2900),
	CALIFORNIUM("Cf", 1172, 1745);
	
	/* Unknown data!
	EINSTEINIUM("Es", 1133, ),
	FERMIUM("Fm", 1800, ),
	MENDELEVIUM("Md", 1100, ),
	NOBELIUM("No", 1100, ),
	LAWRENCEIUM("Lr", 1900, ),
	RUTHERFORDIUM("Rf", 2373, );
	DUBNIUM(),
	SEABORGIUM(),
	BOHRIUM(),
	HASSIUM(),
	MEITNERIUM(),
	DARMSTADTIUM(),
	ROENTGENIUM(),
	COPERNICIUM(),
	NIHONIUM(),
	FLEROVIUM(),
	MOSCOVIUM(),
	LIVERMORIUM(),
	TENNESSINE(),
	OGANESSON();
	*/
	
	private final String atomicSymbol;
	private final int meltingPoint, boilingPoint;
	
	private Element(String atomicSymbol, int meltingPoint, int boilingPoint) {
		this.atomicSymbol = atomicSymbol;
		this.meltingPoint = meltingPoint;
		this.boilingPoint = boilingPoint;
	}
	
	public String atomicSymbol() {
		return atomicSymbol;
	}
	
	public int atomicNumber() {
		return ordinal();
	}
	
	public static String subscript(int value) {
		String s = "";
		for (char c : String.valueOf(value).toCharArray()) {
			int i = Integer.parseInt(Character.toString(c));
			s += (char) (i + 0x2080);
		}
		
		return s;
	}
	
	public ElementState getStateAt(int kelvin) {
		return meltingPoint > boilingPoint ? (kelvin < boilingPoint ? ElementState.GAS : ElementState.SOLID) :
				(kelvin < meltingPoint ? ElementState.SOLID : kelvin < boilingPoint ? ElementState.LIQUID : ElementState.GAS);
	}
	
	/** In Kelvin */
	public int getMeltingPoint() {
		return meltingPoint;
	}
	
	/** In Kelvin */
	public int getBoilingPoint() {
		return boilingPoint;
	}
	
	public static int kelvinToCelsius(int degree) {
		return degree - 273;
	}
	
	public static int kelvinToFahrenheit(int degree) {
		return Math.round(1.8f * (degree - 273f) + 32f);
	}
	
	public enum ElementState {
		SOLID,
		LIQUID,
		GAS;
	}
}
