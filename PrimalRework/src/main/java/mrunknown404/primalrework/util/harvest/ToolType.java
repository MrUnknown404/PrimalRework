package mrunknown404.primalrework.util.harvest;

public enum ToolType {
	none   (0.00f, 0.00f),
	shears (1.75f, 1.75f),
	knife  (1.75f, 2.00f),
	pickaxe(1.25f, 1.00f),
	shovel (1.00f, 1.50f),
	axe    (0.90f, 5.00f),
	hoe    (2.00f, 0.00f),
	sword  (1.60f, 3.00f);
	
	public final float swingSpeed, baseDamage;
	
	private ToolType(float swingSpeed, float baseDamage) {
		this.swingSpeed = swingSpeed - 4;
		this.baseDamage = baseDamage;
	}
}
