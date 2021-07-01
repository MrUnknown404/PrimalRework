package mrunknown404.primalrework.blocks.utils;

import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SBFlammable extends StagedBlock {
	
	protected final int flammability, fireSpreadSpeed;
	
	protected SBFlammable(String name, EnumStage stage, int stackSize, ItemGroup tab, Material material, SoundType sound, boolean hasCollision, int lightLevel, float hardness,
			float blastResist, boolean isRandomTick, int flammability, int fireSpreadSpeed, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info,
			HarvestInfo[] extraInfos) {
		super(name, stage, stackSize, tab, material, sound, hasCollision, lightLevel, hardness, blastResist, isRandomTick, blockStateType, blockModelType, info, extraInfos);
		this.flammability = flammability;
		this.fireSpreadSpeed = fireSpreadSpeed;
	}
	
	public SBFlammable(String name, EnumStage stage, Material material, SoundType sound, float hardness, float blastResist, int flammability, int fireSpreadSpeed,
			HarvestInfo info, HarvestInfo... extraInfos) {
		this(name, stage, 64, InitItemGroups.BLOCKS, material, sound, true, 0, hardness, blastResist, false, flammability, fireSpreadSpeed, BlockStateType.normal,
				BlockModelType.normal, info, extraInfos);
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
