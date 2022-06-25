package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.SBRotatedPillar;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.BlockInfo.Hardness;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.helpers.BlockH;

/** Only used in {@link BlockH#isLog} */
public class SBLog extends SBRotatedPillar {
	public SBLog(String name) {
		super(name + "_log", PRStages.STAGE_0, 32, PRItemGroups.BLOCKS, BlockInfo.with(BlockInfo.WOOD, Hardness.MEDIUM_2), HarvestInfo.AXE_MIN);
	}
}
