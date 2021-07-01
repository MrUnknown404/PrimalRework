package mrunknown404.primalrework.utils.enums;

public enum EnumAlloy {
	//@formatter:off
	unknown(false, 2f,   2f,   EnumStage.stage3, EnumToolMaterial.stone),
	copper (true,  2f,   2f,   EnumStage.stage3, EnumToolMaterial.stone),
	tin    (true,  1.5f, 1.5f, EnumStage.stage3, EnumToolMaterial.stone),
	bronze (false, 2.5f, 2.5f, EnumStage.stage4, EnumToolMaterial.copper);
	//@formatter:on
	
	//These are only used for block/item creation
	public final float hardness, blastResist;
	public final EnumStage stage;
	public final EnumToolMaterial toolMat;
	public final boolean hasOre;
	
	private EnumAlloy(boolean hasOre, float hardness, float blastResist, EnumStage stage, EnumToolMaterial toolMat) {
		this.hardness = hardness;
		this.blastResist = blastResist;
		this.stage = stage;
		this.toolMat = toolMat;
		this.hasOre = hasOre;
	}
}
