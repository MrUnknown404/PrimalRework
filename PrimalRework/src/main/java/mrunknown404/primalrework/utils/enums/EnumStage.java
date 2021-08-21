package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.helpers.WordH;
import mrunknown404.primalrework.utils.IName;
import net.minecraft.util.text.IFormattableTextComponent;

/**<pre> name, id <br>
 * stage0  ("before", 0),
 * stage1  ("early_stone", 1),
 * stage2  ("late_stone", 2),
 * stage3  ("copper", 3),
 * stage4  ("bronze", 4),
 * 
 * do_later("do_later", 98)
 * no_show ("no_show", 99)
 * </pre> */
public enum EnumStage implements IName {
	//@formatter:off
	stage0  ("before", 0),
	stage1  ("early_stone", 1),
	stage2  ("late_stone", 2),
	stage3  ("copper", 3),
	stage4  ("bronze", 4),
	
	do_later("do_later", 98),
	no_show ("no_show", 99);
	//@formatter:on
	
	private final IFormattableTextComponent name;
	public final int id;
	
	private EnumStage(String name, int id) {
		this.name = WordH.translate("stage." + name + ".name");
		this.id = id;
	}
	
	@Override
	public IFormattableTextComponent getFancyName() {
		return name;
	}
	
	@Override
	public String getName() {
		return name.getString();
	}
	
	public static EnumStage fromString(String name) {
		for (EnumStage stage : values()) {
			if (stage.name().equals(name)) {
				return stage;
			}
		}
		
		return null;
	}
	
	public static EnumStage fromID(int id) {
		for (EnumStage stage : values()) {
			if (stage.id == id) {
				return stage;
			}
		}
		
		return null;
	}
}
