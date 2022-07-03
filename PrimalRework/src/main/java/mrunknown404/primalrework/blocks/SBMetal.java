package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.init.InitMetals;
import mrunknown404.primalrework.init.InitPRItemGroups;
import mrunknown404.primalrework.registry.Metal;
import mrunknown404.primalrework.utils.IMetalColored;
import mrunknown404.primalrework.utils.enums.ToolType;

public class SBMetal extends StagedBlock implements IMetalColored {
	public final Metal metal;
	
	public SBMetal(Metal metal) {
		super(metal.stage, 16, InitPRItemGroups.BLOCKS, metal.blockInfo, BlockStateType.normal, metal == InitMetals.UNKNOWN.get() ? BlockModelType.normal : BlockModelType.normal,
				new HarvestInfo(ToolType.PICKAXE, metal.toolMat));
		this.metal = metal;
		this.elements.putAll(metal.elements);
	}
	
	@Override
	public Metal getMetal() {
		return metal;
	}
}
