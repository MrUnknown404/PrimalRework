package mrunknown404.primalrework.util.enums;

public enum EnumToolMaterial {
	hand       (0,  0,     1.00f, 0.00f, 0 ),
	wood       (1,  16,    1.75f, 0.25f, 1 ),
	flint      (1,  32,    3.00f, 0.25f, 1 ),
	bone       (1,  24,    6.00f, 2.00f, 3 ),
	stone      (2,  64,    4.25f, 0.50f, 2 ),
	gold       (2,  48,    9.00f, 1.00f, 8 ),
	copper     (3,  128,   5.25f, 1.25f, 4 ),
	bronze     (4,  256,   7.00f, 1.75f, 6 ),
	iron       (5,  512,   7.50f, 3.00f, 8 ),
	steel      (6,  1024,  9.25f, 3.50f, 10),
	obsidian   (6,  1280,  8.00f, 4.00f, 8 ),
	diamond    (7,  1536, 11.75f, 4.25f, 14),
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
}
