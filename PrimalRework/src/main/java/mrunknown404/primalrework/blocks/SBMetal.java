package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class SBMetal extends StagedBlock {
	public final Metal metal;
	
	public SBMetal(Metal metal) {
		super(metal.name() + "_block", metal.stage, 16, PRItemGroups.BLOCKS, Material.METAL, SoundType.METAL, true, true, 0, metal.blockInfo, false, BlockStateType.normal,
				metal == Metal.UNKNOWN ? BlockModelType.normal : BlockModelType.normal_colored, new HarvestInfo(ToolType.PICKAXE, metal.toolMat));
		this.metal = metal;
	}
}
