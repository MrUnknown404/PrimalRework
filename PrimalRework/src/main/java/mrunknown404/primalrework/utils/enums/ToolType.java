package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.utils.IName;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.util.text.IFormattableTextComponent;

/**<pre> swingSpeed, baseDamage <br>
 * NONE   (2.00f, 0.00f),
 * SHEARS (1.75f, 1.50f),
 * KNIFE  (1.75f, 2.00f),
 * PICKAXE(1.25f, 1.00f),
 * SHOVEL (1.50f, 1.50f),
 * AXE    (0.90f, 4.50f),
 * HOE    (1.25f, 0.50f),
 * SWORD  (1.60f, 3.00f);
 * </pre> */
public enum ToolType implements IName {
	//@formatter:off
	NONE   (2.00f, 0.00f),
	SHEARS (1.75f, 1.50f),
	KNIFE  (1.75f, 2.00f),
	PICKAXE(1.25f, 1.00f),
	SHOVEL (1.50f, 1.50f),
	AXE    (0.90f, 4.50f),
	HOE    (1.25f, 0.50f),
	SWORD  (1.60f, 3.00f);
	//@formatter:on
	
	private final IFormattableTextComponent name;
	public final float swingSpeed, baseDamage;
	
	private ToolType(float swingSpeed, float baseDamage) {
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
