package mrunknown404.primalrework.utils.enums;

public enum EnumOreValue {
	//@formatter:off
	poor   (10),
	low    (25),
	medium (35),
	good   (50),
	high   (75),
	perfect(100),
	block  (900);
	//@formatter:on
	
	public final int units;
	
	private EnumOreValue(int units) {
		this.units = units;
	}
}
