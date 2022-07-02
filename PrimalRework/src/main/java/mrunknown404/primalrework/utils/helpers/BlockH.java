package mrunknown404.primalrework.utils.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mrunknown404.primalrework.blocks.HarvestInfo;
import mrunknown404.primalrework.blocks.SBSlab;
import mrunknown404.primalrework.blocks.StagedBlock;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlockH {
	public static HarvestInfo getBlockHarvestInfo(StagedBlock block, ToolType toolType) {
		HarvestInfo info = block.getHarvest().get(toolType);
		return info == null ? block.getHarvest().get(ToolType.NONE) : info;
	}
	
	public static List<HarvestInfo> getBlockHarvestInfos(StagedBlock block) {
		return new ArrayList<HarvestInfo>(block.getHarvest().values());
	}
	
	private static final Set<StagedBlock> CAN_SUPPORT_PLANTS = new HashSet<StagedBlock>(Arrays.asList(InitBlocks.DIRT.get(), InitBlocks.GRASS_BLOCK.get()));
	
	public static boolean canSupportPlant(Block block) {
		return CAN_SUPPORT_PLANTS.contains(block);
	}
	
	public static int getLightBlockInto(IBlockReader world, BlockState state0, BlockPos pos0, BlockState state1, BlockPos pos1, Direction dir, int value) {
		boolean flag = state0.canOcclude() && state0.useShapeForLightOcclusion();
		boolean flag1 = state1.canOcclude() && state1.useShapeForLightOcclusion();
		
		if (!flag && !flag1) {
			return value;
		} else if (flag && !flag1) {
			return state0.getBlock() instanceof SBSlab ? (state0.getValue(SBSlab.TYPE) == SlabType.BOTTOM ? 0 : value) : value;
		}
		
		VoxelShape voxelshape = flag ? state0.getOcclusionShape(world, pos0) : VoxelShapes.empty();
		VoxelShape voxelshape1 = flag1 ? state1.getOcclusionShape(world, pos1) : VoxelShapes.empty();
		return VoxelShapes.mergedFaceOccludes(voxelshape, voxelshape1, dir) ? 16 : value;
	}
}
