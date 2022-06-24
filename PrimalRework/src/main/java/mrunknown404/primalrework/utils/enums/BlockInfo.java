package mrunknown404.primalrework.utils.enums;

public enum BlockInfo {
	//@formatter:off
	INSTANT        (0,     0),
	UNBREAKABLE    (-1,   -1),
	
	GRASS          (0.8f,  0.8f),
	
	SOFT_PLANT     (0.1f,  0),
	SOFT_DIRT      (0.3f,  0.2f),
	SOFT_WOOD      (1.5f,  1),
	SOFT_STONE     (1.5f,  4),
	SOFT_METAL     (1.8f,  4),
	
	PLANT          (0.3f,  0.1f),
	DIRT           (0.5f,  0.5f),
	WOOD           (2,     2),
	STONE          (2,     6),
	METAL          (2.5f,  6),
	
	HARD_PLANT     (0.5f,  0.3f),
	HARD_DIRT      (0.8f,  0.8f),
	HARD_WOOD      (2.5f,  3),
	HARD_STONE     (3,     8),
	HARD_METAL     (3f,    8),
	
	VERY_HARD_WOOD (3.5f,  4),
	VERY_HARD_METAL(4,     10);
	//@formatter:on
	
	public final float hardness, blast;
	
	private BlockInfo(float hardness, float blast) {
		this.hardness = hardness;
		this.blast = blast;
	}
}
