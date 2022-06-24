package mrunknown404.primalrework.blocks.utils;

import java.util.function.Supplier;

import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.BlockInfo;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SBFlammable extends StagedBlock {
	protected final int flammability, fireSpreadSpeed;
	
	protected SBFlammable(String name, Supplier<Stage> stage, int stackSize, ItemGroup tab, Material material, SoundType sound, boolean hasCollision, boolean canOcclude,
			int lightLevel, BlockInfo blockInfo, boolean isRandomTick, int flammability, int fireSpreadSpeed, BlockStateType blockStateType, BlockModelType blockModelType,
			HarvestInfo info, HarvestInfo[] extraInfos) {
		super(name, stage, stackSize, tab, material, sound, hasCollision, canOcclude, lightLevel, blockInfo, isRandomTick, blockStateType, blockModelType, info, extraInfos);
		this.flammability = flammability;
		this.fireSpreadSpeed = fireSpreadSpeed;
	}
	
	public SBFlammable(String name, Supplier<Stage> stage, Material material, SoundType sound, BlockInfo blockInfo, int flammability, int fireSpreadSpeed, HarvestInfo info,
			HarvestInfo... extraInfos) {
		this(name, stage, 64, PRItemGroups.BLOCKS, material, sound, true, true, 0, blockInfo, false, flammability, fireSpreadSpeed, BlockStateType.normal, BlockModelType.normal,
				info, extraInfos);
	}
	
	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return flammability;
	}
	
	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return fireSpreadSpeed;
	}
}
