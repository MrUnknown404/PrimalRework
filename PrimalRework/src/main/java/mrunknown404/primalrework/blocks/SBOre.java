package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumAlloy;
import mrunknown404.primalrework.utils.enums.EnumOreValue;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;

public class SBOre extends StagedBlock {
	public final EnumAlloy alloy;
	public final EnumOreValue value;
	
	protected SBOre(String name, EnumStage stage, int stackSize, ItemGroup tab, Material material, SoundType sound, float hardness, float blastResist, EnumAlloy alloy,
			EnumOreValue value, HarvestInfo info) {
		super(name, stage, stackSize, tab, material, sound, true, 0, hardness, blastResist, false, BlockStateType.normal, BlockModelType.normal, info);
		this.alloy = alloy;
		this.value = value;
	}
	
	public static SBOre create(EnumStage stage, float hardness, float blastResist, EnumAlloy alloy, EnumOreValue value, EnumToolMaterial toolMat) {
		boolean isBlock = value == EnumOreValue.block;
		
		return new SBOre(isBlock ? alloy.toString() + "_block" : alloy.toString() + "_ore_" + value.toString(), stage, isBlock ? 32 : 16,
				isBlock ? PRItemGroups.BLOCKS : PRItemGroups.ORES, isBlock ? Material.METAL : Material.STONE, isBlock ? SoundType.METAL : SoundType.STONE, hardness,
				blastResist, alloy, value, new HarvestInfo(EnumToolType.pickaxe, toolMat));
	}
}
