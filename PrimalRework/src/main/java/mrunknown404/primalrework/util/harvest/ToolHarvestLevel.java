package mrunknown404.primalrework.util.harvest;

public enum ToolHarvestLevel {
	hand       (0, 0.50f, 0.00f),
	flint      (1, 1.50f, 0.25f),
	stone      (2, 2.00f, 0.50f),
	copper     (3, 2.50f, 1.25f),
	bronze     (4, 3.25f, 1.75f),
	iron       (5, 3.50f, 3.00f),
	steel      (6, 4.25f, 3.50f),
	diamond    (7, 5.50f, 4.00f),
	//is
	unbreakable(99, 0.0f, 0.0f);
	
	public final int level;
	public final float speed, extraDamage;
	
	private ToolHarvestLevel(int level, float speed, float extraDamage) {
		this.level = level;
		this.speed = speed;
		this.extraDamage = extraDamage;
	}
	
	@Override
	public String toString() {
		return "(" + name() + ", " + level + ":" + speed + ")";
	}
}
