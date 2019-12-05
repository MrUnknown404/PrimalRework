package mrunknown404.primalrework.blocks;

import java.util.Arrays;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo.ItemDropInfo;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSalt extends BlockBase {
	
	public BlockSalt() {
		super("salt_block", Material.GROUND, SoundType.GROUND, 1.25f, 1.25f, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.shovel, EnumToolMaterial.flint));
		harvestInfo.setDrops(Arrays.asList(new HarvestDropInfo(EnumToolType.shovel, true,
				new ItemDropInfo(ModItems.SALT, false, true, 100, 4, 0, 0, 0),
				new ItemDropInfo(ModItems.findBlock(this), true, false, 100, 1, 0, 0, 0))));
	}
}
