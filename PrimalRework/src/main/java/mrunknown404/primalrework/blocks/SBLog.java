package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.SBRotatedPillar;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumBlockInfo;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SBLog extends SBRotatedPillar {
	public SBLog(String name) {
		super(name + "_log", PRStages.STAGE_0, 32, PRItemGroups.BLOCKS, Material.WOOD, SoundType.WOOD, true, true, 0, EnumBlockInfo.wood, false, HarvestInfo.AXE_MIN);
	}
	
	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 5;
	}
	
	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 5;
	}
}
