package mrunknown404.primalrework.util.enums;

public enum EnumOreValue {
	poor  (10),
	low   (25),
	medium(35),
	good  (50),
	high  (75),
	block (900);
	
	public final int units;
	
	private EnumOreValue(int units) {
		this.units = units;
	}
}
