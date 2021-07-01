package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.utils.IName;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**<pre> level, durability, speed, extraDamage <br>
 * hand       (0,    0,  1.00f, 0.00f),
 * clay       (1,    1,  1.25f, 0.00f),
 * wood       (1,    2,  1.75f, 0.25f),
 * flint      (2,    4,  3.00f, 0.25f),
 * bone       (2,    3,  6.00f, 2.00f),
 * terracotta (2,    6,  5.00f, 0.50f),
 * stone      (3,    8,  4.25f, 0.50f),
 * unknown    (3,    9,  2.00f, 1.50f),
 * gold       (4,    7,  9.00f, 1.00f),
 * tin        (4,   10,  4.50f, 1.00f),
 * copper     (5,   12,  5.25f, 1.25f),
 * bronze     (6,   16,  7.00f, 1.75f),
 * obsidian   (6,   24,  5.00f, 3.00f),
 * iron       (7,   20,  7.50f, 3.00f),
 * steel      (8,   32,  9.25f, 3.50f),
 * diamond    (9,   48, 11.75f, 4.25f),
 * netherite  (10,  56, 10.00f, 5.00f),
 * 
 * unbreakable(99,   0,  0.00f, 0.00f);
 * </pre> */
public enum EnumToolMaterial implements IName {
	//@formatter:off
	hand       (0,    0,  1.00f, 0.00f),
	clay       (1,    1,  1.25f, 0.00f),
	wood       (1,    2,  1.75f, 0.25f),
	flint      (2,    4,  3.00f, 0.25f),
	bone       (2,    3,  6.00f, 2.00f),
	terracotta (2,    6,  5.00f, 0.50f),
	stone      (3,    8,  4.25f, 0.50f),
	unknown    (3,    9,  2.00f, 1.50f),
	gold       (4,    7,  9.00f, 1.00f),
	tin        (4,   10,  4.50f, 1.00f),
	copper     (5,   12,  5.25f, 1.25f),
	bronze     (6,   16,  7.00f, 1.75f),
	obsidian   (6,   24,  5.00f, 3.00f),
	iron       (7,   20,  7.50f, 3.00f),
	steel      (8,   32,  9.25f, 3.50f),
	diamond    (9,   48, 11.75f, 4.25f),
	netherite  (10,  56, 10.00f, 5.00f),
	
	unbreakable(99,   0,  0.00f, 0.00f);
	//@formatter:on
	
	private final IFormattableTextComponent name;
	public final int level, durability;
	public final float speed, extraDamage;
	
	private EnumToolMaterial(int level, int durability, float speed, float extraDamage) {
		this.level = level;
		this.durability = durability * 8;
		this.speed = speed;
		this.extraDamage = extraDamage;
		this.name = new TranslationTextComponent("tool.material." + name() + ".name");
	}
	
	@Override
	public IFormattableTextComponent getFancyName() {
		return name;
	}
	
	@Override
	public String getName() {
		return name.getString();
	}
}
