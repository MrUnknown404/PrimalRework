package mrunknown404.primalrework.utils.enums;

public enum EnumBlockInfo {
	//@formatter:off
	instant        (0,     0),
	unbreakable    (-1,   -1),
	dirt           (0.5f,  0.5f),
	grass          (0.8f,  0.8f),
	thatch         (0.5f,  0.3f),
	soft_wood      (1.5f,  1),
	wood           (2,     2),
	hard_wood      (2.5f,  3),
	very_hard_wood (3.5f,  4),
	soft_stone     (1.5f,  4),
	stone          (2,     6),
	hard_stone     (3,     8),
	soft_metal     (1.5f,  4),
	metal          (2,     6),
	hard_metal     (2.5f,  8),
	very_hard_metal(3,     10);
	//@formatter:on
	
	public final float hardness, blast;
	
	private EnumBlockInfo(float hardness, float blast) {
		this.hardness = hardness;
		this.blast = blast;
	}
}
