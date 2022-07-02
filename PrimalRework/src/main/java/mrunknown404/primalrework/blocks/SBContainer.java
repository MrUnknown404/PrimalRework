package mrunknown404.primalrework.blocks;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import mrunknown404.primalrework.init.InitPRItemGroups;
import mrunknown404.primalrework.stage.Stage;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public abstract class SBContainer extends StagedBlock {
	protected SBContainer(Supplier<Stage> stage, BlockInfo material, BlockStateType blockStateType, BlockModelType blockModelType, HarvestInfo info, HarvestInfo... extraInfos) {
		super(stage, 1, InitPRItemGroups.MACHINES, material, blockStateType, blockModelType, info, extraInfos);
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
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
}
