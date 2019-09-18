package mrunknown404.primalrework.util.harvest;

public enum EnumToolMaterial {
	hand       (0,  0,    0.50f, 0.00f, 0 ),
	flint      (1,  32,   1.50f, 0.25f, 1 ),
	bone       (1,  24,   3.50f, 2.25f, 3 ),
	stone      (2,  64,   2.00f, 0.50f, 1 ),
	gold       (2,  48,   4.00f, 1.00f, 10),
	copper     (3,  128,  2.50f, 1.25f, 4 ),
	bronze     (4,  256,  3.50f, 1.75f, 6 ),
	iron       (5,  512,  3.75f, 3.00f, 8 ),
	steel      (6,  1024, 4.50f, 3.50f, 10),
	obsidian   (6,  1280, 4.25f, 3.75f, 12),
	diamond    (7,  1536, 5.75f, 4.00f, 14),
	//is
	unbreakable(99, 0,    0.00f, 0.00f, 0);
	
	public final int level, durability, enchantability;
	public final float speed, extraDamage;
	
	private EnumToolMaterial(int level, int durability, float speed, float extraDamage, int enchantability) {
		this.level = level;
		this.durability = durability;
		this.speed = speed;
		this.extraDamage = extraDamage;
		this.enchantability = enchantability;
	}
	
	@Override
	public String toString() {
		return "(" + name() + ", lvl:" + level + ", dur:" + durability + "spd:" + speed + ", dmg: " + extraDamage + ")";
	}
}
