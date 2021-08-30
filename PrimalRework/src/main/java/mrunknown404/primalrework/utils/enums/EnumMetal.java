package mrunknown404.primalrework.utils.enums;

import java.awt.Color;

public enum EnumMetal {
	//@formatter:off
	unknown(EnumBlockInfo.soft_metal, EnumStage.stage3, EnumToolMaterial.stone,  null),
	copper (EnumBlockInfo.metal,      EnumStage.stage3, EnumToolMaterial.stone,  new Color(53, 188, 114),  new Color(255, 145, 0)),
	tin    (EnumBlockInfo.soft_metal, EnumStage.stage3, EnumToolMaterial.stone,  new Color(255, 237, 251), new Color(255, 237, 251)),
	bronze (EnumBlockInfo.hard_metal, EnumStage.stage4, EnumToolMaterial.copper, new Color(208, 101, 0));
	//@formatter:on
	
	//These are only used for block/item creation
	public final EnumBlockInfo blockInfo;
	public final EnumStage stage;
	public final EnumToolMaterial toolMat;
	public final boolean isAlloy;
	public final Color oreColor, ingotColor;
	
	private EnumMetal(EnumBlockInfo blockInfo, EnumStage stage, EnumToolMaterial toolMat, Color oreColor, Color ingotColor) {
		this.blockInfo = blockInfo;
		this.stage = stage;
		this.toolMat = toolMat;
		this.isAlloy = false;
		this.oreColor = oreColor;
		this.ingotColor = ingotColor;
	}
	
	private EnumMetal(EnumBlockInfo blockInfo, EnumStage stage, EnumToolMaterial toolMat, Color ingotColor) {
		this.blockInfo = blockInfo;
		this.stage = stage;
		this.toolMat = toolMat;
		this.isAlloy = true;
		this.oreColor = null;
		this.ingotColor = ingotColor;
	}
}
