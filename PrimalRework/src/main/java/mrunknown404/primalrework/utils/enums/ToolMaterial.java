package mrunknown404.primalrework.utils.enums;

/**<pre> level, durability, speed, extraDamage <br>
 * HAND       (0,    0,  1.00f, 0.00f),
 * CLAY       (1,    1,  1.25f, 0.00f),
 * WOOD       (1,    2,  1.75f, 0.25f),
 * FLINT      (2,    4,  3.00f, 0.25f),
 * BONE       (2,    3,  6.00f, 2.00f),
 * TERRACOTTA (2,    6,  5.00f, 0.50f),
 * STONE      (3,    8,  4.25f, 0.50f),
 * UNKNOWN    (3,    9,  2.00f, 1.50f),
 * GOLD       (4,    7,  9.00f, 1.00f),
 * TIN        (4,   10,  4.50f, 1.00f),
 * COPPER     (5,   12,  5.25f, 1.25f),
 * BRONZE     (6,   16,  7.00f, 1.75f),
 * OBSIDIAN   (6,   24,  5.00f, 3.00f),
 * IRON       (7,   20,  7.50f, 3.00f),
 * STEEL      (8,   32,  9.25f, 3.50f),
 * DIAMOND    (9,   48, 11.75f, 4.25f),
 * NETHERITE  (10,  56, 10.00f, 5.00f),
 * 
 * UNBREAKABLE(99,   0,  0.00f, 0.00f);
 * </pre> */
public enum ToolMaterial {
	//@formatter:off
	HAND       (0,    0,  1.00f, 0.00f),
	CLAY       (1,    1,  1.25f, 0.00f),
	WOOD       (1,    2,  1.75f, 0.25f),
	FLINT      (2,    4,  3.00f, 0.25f),
	BONE       (2,    3,  6.00f, 2.00f),
	TERRACOTTA (2,    6,  5.00f, 0.50f),
	STONE      (3,    8,  4.25f, 0.50f),
	UNKNOWN    (3,    9,  2.00f, 1.50f),
	GOLD       (4,    7,  9.00f, 1.00f),
	TIN        (4,   10,  4.50f, 1.00f),
	COPPER     (5,   12,  5.25f, 1.25f),
	BRONZE     (6,   16,  7.00f, 1.75f),
	OBSIDIAN   (6,   24,  5.00f, 3.00f),
	IRON       (7,   20,  7.50f, 3.00f),
	STEEL      (8,   32,  9.25f, 3.50f),
	DIAMOND    (9,   48, 11.75f, 4.25f),
	NETHERITE  (10,  56, 10.00f, 5.00f),
	
	UNBREAKABLE(99,   0,  0.00f, 0.00f);
	//@formatter:on
	
	public final int level, durability;
	public final float speed, extraDamage;
	
	private ToolMaterial(int level, int durability, float speed, float extraDamage) {
		this.level = level;
		this.durability = durability * 8;
		this.speed = speed;
		this.extraDamage = extraDamage;
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
