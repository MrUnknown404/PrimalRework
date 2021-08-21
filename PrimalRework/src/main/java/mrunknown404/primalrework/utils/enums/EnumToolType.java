package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.helpers.WordH;
import mrunknown404.primalrework.utils.IName;
import net.minecraft.util.text.IFormattableTextComponent;

/**<pre> swingSpeed, baseDamage <br>
 * none   (2.00f, 0.00f)
 * shears (1.75f, 1.50f)
 * knife  (1.75f, 2.00f)
 * pickaxe(1.25f, 1.00f)
 * shovel (1.50f, 1.50f)
 * axe    (0.90f, 4.50f)
 * hoe    (1.25f, 0.50f)
 * sword  (1.60f, 3.00f)
 * saw    (1.00f, 1.50f)
 * </pre> */
public enum EnumToolType implements IName {
	//@formatter:off
	none   (2.00f, 0.00f),
	shears (1.75f, 1.50f),
	knife  (1.75f, 2.00f),
	pickaxe(1.25f, 1.00f),
	shovel (1.50f, 1.50f),
	axe    (0.90f, 4.50f),
	hoe    (1.25f, 0.50f),
	sword  (1.60f, 3.00f),
	saw    (1.00f, 1.50f);
	//@formatter:on
	
	private final IFormattableTextComponent name;
	public final float swingSpeed, baseDamage;
	
	private EnumToolType(float swingSpeed, float baseDamage) {
		this.name = WordH.translate("tool.type." + name() + ".name");
		this.swingSpeed = swingSpeed - 4;
		this.baseDamage = baseDamage;
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
