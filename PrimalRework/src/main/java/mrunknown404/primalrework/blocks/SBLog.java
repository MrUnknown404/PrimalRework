package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.BlockInfo.Hardness;
import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.init.InitStages;

/** Only used for instanceof checks */
public class SBLog extends SBRotatedPillar {
	public SBLog() {
		super(InitStages.STAGE_0, 32, InitItemGroups.BLOCKS, BlockInfo.with(BlockInfo.WOOD, Hardness.MEDIUM_2), HarvestInfo.AXE_MIN);
	}
}
