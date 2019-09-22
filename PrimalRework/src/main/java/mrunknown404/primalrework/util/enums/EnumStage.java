package mrunknown404.primalrework.util.enums;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public enum EnumStage {
	stage0("before", 0),
	stage1("early_stone", 1),
	stage2("late_stone", 2);
	
	public final ITextComponent name;
	public final int id;
	
	private EnumStage(String name, int id) {
		this.name = new TextComponentTranslation("stage." + name + ".name");
		this.id = id;
	}
	
	public String getName() {
		return name.getUnformattedText();
	}
	
	public static String[] getStringList() {
		String[] strs = new String[values().length];
		
		for (int i = 0; i < values().length; i++) {
			strs[i] = values()[i].toString();
		}
		
		return strs;
	}
}
