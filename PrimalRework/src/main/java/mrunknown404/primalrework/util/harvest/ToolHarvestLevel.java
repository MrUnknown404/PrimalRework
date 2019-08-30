package mrunknown404.primalrework.util.harvest;

public enum ToolHarvestLevel {
	hand       (0, 0.4f),
	flint      (1, 1f),
	stone      (2, 1.2f),
	copper     (3, 1.8f),
	bronze     (4, 2f),
	iron       (5, 3f),
	steel      (6, 3.5f),
	diamond    (7, 4f),
	//is
	unbreakable(99, 0f);
	
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
