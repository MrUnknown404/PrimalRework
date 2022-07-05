package mrunknown404.primalrework.utils.enums;

public enum RawPart {
	PLATE,
	ROD,
	SCREW,
	RING,
	WIRE,
	GEAR,
	SPRING;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
