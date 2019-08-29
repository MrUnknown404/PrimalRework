package mrunknown404.primalrework.util;

public enum ToolHarvestLevel {
	hand       (0, 1f),
	flint      (1, 1.5f),
	stone      (2, 2f),
	copper     (3, 2.5f),
	bronze     (4, 3f),
	iron       (5, 4f),
	steel      (6, 5f),
	diamond    (7, 6f),
	//is
	unbreakable(99, 1f);
	
	public final int level;
	public final float speed;
	
	private ToolHarvestLevel(int level, float speed) {
		this.level = level;
		this.speed = speed;
	}
	
	@Override
	public String toString() {
		return name() + ":" + level + ":" + speed;
	}
}
