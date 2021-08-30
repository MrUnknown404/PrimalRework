package mrunknown404.primalrework.blocks.utils;

import javax.annotation.Nullable;

import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumBlockInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public abstract class SBContainer extends StagedBlock {
	protected SBContainer(String name, EnumStage stage, int stackSize, Material material, SoundType sound, boolean hasCollision, int lightLevel, EnumBlockInfo blockInfo,
			boolean isRandomTick, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info, HarvestInfo... extraInfos) {
		super(name, stage, stackSize, PRItemGroups.MACHINES, material, sound, hasCollision, lightLevel, blockInfo, isRandomTick, blockStateType, blockModelType, info,
				extraInfos);
	}
	
	@Override
	public boolean triggerEvent(BlockState state, World world, BlockPos pos, int p_189539_4_, int p_189539_5_) {
		super.triggerEvent(state, world, pos, p_189539_4_, p_189539_5_);
		TileEntity tileentity = world.getBlockEntity(pos);
		return tileentity == null ? false : tileentity.triggerEvent(p_189539_4_, p_189539_5_);
	}
	
	@Override
	@Nullable
	public INamedContainerProvider getMenuProvider(BlockState state, World world, BlockPos pos) {
		TileEntity tileentity = world.getBlockEntity(pos);
		return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return makeTileEntity(state, world);
	}
	
	public abstract TileEntity makeTileEntity(BlockState state, IBlockReader world);
}
