package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumMetal;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class SBMetal extends StagedBlock {
	public final EnumMetal metal;
	
	public SBMetal(EnumMetal metal) {
		super(metal.name() + "_block", metal.stage, 16, PRItemGroups.BLOCKS, Material.METAL, SoundType.METAL, true, true, 0, metal.blockInfo, false, BlockStateType.normal,
				metal == EnumMetal.unknown ? BlockModelType.normal : BlockModelType.normal_colored, new HarvestInfo(EnumToolType.pickaxe, metal.toolMat));
		this.metal = metal;
	}
}
