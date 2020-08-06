package mrunknown404.primalrework.util.enums;

/**<pre> int level, int durability, float speed, float extraDamage <br>
 * hand       (0,  0,     1.00f, 0.00f)
 * clay       (1,  8,     1.25f, 0.00f)
 * wood       (1,  16,    1.75f, 0.25f)
 * flint      (1,  32,    3.00f, 0.25f)
 * bone       (1,  24,    6.00f, 2.00f)
 * stone      (2,  64,    4.25f, 0.50f)
 * gold       (2,  48,    9.00f, 1.00f)
 * tin        (2,  100,   4.50f, 1.00f)
 * copper     (3,  128,   5.25f, 1.25f)
 * bronze     (4,  256,   7.00f, 1.75f)
 * iron       (5,  512,   7.50f, 3.00f)
 * steel      (6,  1024,  9.25f, 3.50f)
 * obsidian   (6,  1280,  8.00f, 4.00f)
 * diamond    (7,  1536, 11.75f, 4.25f)
 * 
 * unbreakable(99, 0,     0.00f, 0.00f, 0 )
 * </pre> */
public enum EnumToolMaterial {
	hand       (0,  0,     1.00f, 0.00f),
	clay       (1,  8,     1.25f, 0.00f),
	wood       (1,  16,    1.75f, 0.25f),
	flint      (1,  32,    3.00f, 0.25f),
	bone       (1,  24,    6.00f, 2.00f),
	stone      (2,  64,    4.25f, 0.50f),
	gold       (3,  48,    9.00f, 1.00f),
	tin        (3,  100,   4.50f, 1.00f),
	copper     (4,  128,   5.25f, 1.25f),
	bronze     (5,  256,   7.00f, 1.75f),
	iron       (6,  512,   7.50f, 3.00f),
	steel      (7,  1024,  9.25f, 3.50f),
	obsidian   (7,  1280,  8.00f, 4.00f),
	diamond    (8,  1536, 11.75f, 4.25f),
	//is
	unbreakable(99, 0,     0.00f, 0.00f);
	//haha jojo
	
	public final int level, durability;
	public final float speed, extraDamage;
	
	private EnumToolMaterial(int level, int durability, float speed, float extraDamage) {
		this.level = level;
		this.durability = durability;
		this.speed = speed;
		this.extraDamage = extraDamage;
	}
}
