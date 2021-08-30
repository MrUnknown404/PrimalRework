package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumBlockInfo;
import mrunknown404.primalrework.utils.enums.EnumMetal;
import mrunknown404.primalrework.utils.enums.EnumOreValue;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;

public class SBOre extends StagedBlock {
	public final EnumMetal metal;
	public final EnumOreValue value;
	
	protected SBOre(String name, EnumStage stage, int stackSize, ItemGroup tab, Material material, SoundType sound, EnumBlockInfo blockInfo, EnumMetal metal, EnumOreValue value,
			HarvestInfo info) {
		super(name, stage, stackSize, tab, material, sound, true, 0, blockInfo, false, BlockStateType.normal,
				metal == EnumMetal.unknown ? BlockModelType.normal : BlockModelType.normal_colored, info);
		this.metal = metal;
		this.value = value;
	}
	
	public static SBOre create(EnumStage stage, EnumBlockInfo blockInfo, EnumMetal alloy, EnumOreValue value, EnumToolMaterial toolMat) {
		boolean isBlock = value == EnumOreValue.block;
		
		return new SBOre(isBlock ? alloy.toString() + "_block" : alloy.toString() + "_ore_" + value.toString(), stage, isBlock ? 32 : 16,
				isBlock ? PRItemGroups.BLOCKS : PRItemGroups.ORES, isBlock ? Material.METAL : Material.STONE, isBlock ? SoundType.METAL : SoundType.STONE, blockInfo, alloy,
				value, new HarvestInfo(EnumToolType.pickaxe, toolMat));
	}
}
