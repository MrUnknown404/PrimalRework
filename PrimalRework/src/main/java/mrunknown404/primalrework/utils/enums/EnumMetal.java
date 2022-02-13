package mrunknown404.primalrework.utils.enums;

import java.awt.Color;
import java.util.function.Supplier;

import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.stage.Stage;

public enum EnumMetal {
	//@formatter:off
	unknown(EnumBlockInfo.soft_metal, PRStages.STAGE_3, EnumToolMaterial.stone,  true,  null),
	copper (EnumBlockInfo.metal,      PRStages.STAGE_3, EnumToolMaterial.stone,  false, new Color(255, 145, 0)),
	tin    (EnumBlockInfo.soft_metal, PRStages.STAGE_3, EnumToolMaterial.stone,  false, new Color(255, 237, 251)),
	bronze (EnumBlockInfo.hard_metal, PRStages.STAGE_4, EnumToolMaterial.copper, true,  new Color(208, 101, 0));
	//@formatter:on
	
	//These are only used for block/item creation
	public final EnumBlockInfo blockInfo;
	public final Supplier<Stage> stage;
	public final EnumToolMaterial toolMat;
	public final boolean isAlloy;
	public final Color ingotColor;
	
	private EnumMetal(EnumBlockInfo blockInfo, Supplier<Stage> stage, EnumToolMaterial toolMat, boolean isAlloy, Color ingotColor) {
		this.blockInfo = blockInfo;
		this.stage = stage;
		this.toolMat = toolMat;
		this.isAlloy = isAlloy;
		this.ingotColor = ingotColor;
	}
}
