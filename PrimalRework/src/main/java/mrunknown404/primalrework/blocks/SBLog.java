package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.BlockInfo.Hardness;
import mrunknown404.primalrework.init.InitPRItemGroups;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.utils.helpers.BlockH;

/** Only used in {@link BlockH#isLog} */
public class SBLog extends SBRotatedPillar {
	public SBLog() {
		super(InitStages.STAGE_0, 32, InitPRItemGroups.BLOCKS, BlockInfo.with(BlockInfo.WOOD, Hardness.MEDIUM_2), HarvestInfo.AXE_MIN);
	}
}
