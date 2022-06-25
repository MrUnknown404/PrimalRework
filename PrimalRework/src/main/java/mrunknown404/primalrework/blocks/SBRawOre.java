package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.ToolType;

public class SBRawOre extends StagedBlock {
	public final Metal metal;
	
	public SBRawOre(Metal metal) {
		super("raw_" + metal + "_ore", metal.stage, 16, PRItemGroups.ORES, metal.blockInfo, BlockStateType.normal, BlockModelType.normal,
				new HarvestInfo(ToolType.PICKAXE, metal.toolMat));
		this.metal = metal;
	}
}
