package mrunknown404.primalrework.utils.enums;

import java.awt.Color;
import java.util.function.Supplier;

import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.BlockInfo.Hardness;

public enum Metal {
	//@formatter:off
	UNKNOWN(BlockInfo.with(BlockInfo.METAL, Hardness.MEDIUM_0), PRStages.STAGE_3, ToolMaterial.STONE,  true,  null),
	COPPER (BlockInfo.with(BlockInfo.METAL, Hardness.MEDIUM_0), PRStages.STAGE_3, ToolMaterial.STONE,  false, new Color(255, 145, 0)),
	TIN    (BlockInfo.with(BlockInfo.METAL, Hardness.SOFT_2),   PRStages.STAGE_3, ToolMaterial.STONE,  false, new Color(255, 237, 251)),
	BRONZE (BlockInfo.with(BlockInfo.METAL, Hardness.MEDIUM_1), PRStages.STAGE_4, ToolMaterial.COPPER, true,  new Color(208, 101, 0));
	//@formatter:on
	
	//TODO add melting/boiling temp
	
	//These are only used for block/item creation
	public final BlockInfo blockInfo;
	public final Supplier<Stage> stage;
	public final ToolMaterial toolMat;
	public final boolean isAlloy;
	public final Color ingotColor;
	
	private Metal(BlockInfo blockInfo, Supplier<Stage> stage, ToolMaterial toolMat, boolean isAlloy, Color ingotColor) {
		this.blockInfo = blockInfo;
		this.stage = stage;
		this.toolMat = toolMat;
		this.isAlloy = isAlloy;
		this.ingotColor = ingotColor;
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
