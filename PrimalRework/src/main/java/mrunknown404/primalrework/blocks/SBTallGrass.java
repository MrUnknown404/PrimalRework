package mrunknown404.primalrework.blocks;

import java.util.function.Supplier;

import mrunknown404.primalrework.blocks.raw.IBiomeColored;
import mrunknown404.primalrework.blocks.raw.StagedBlock;
import mrunknown404.primalrework.init.InitItems;
import mrunknown404.primalrework.init.InitPRItemGroups;
import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.items.raw.StagedItem.ItemType;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.BlockInfo.Hardness;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.BlockH;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fml.RegistryObject;

@SuppressWarnings("deprecation")
public class SBTallGrass extends StagedBlock implements IBiomeColored {
	private final VoxelShape shape;
	
	public SBTallGrass(Supplier<RegistryObject<StagedBlock>> self, Hardness hardness, VoxelShape shape) {
		super(InitStages.STAGE_0, 64, InitPRItemGroups.BLOCKS, BlockInfo.with(BlockInfo.PLANT, hardness), BlockStateType.normal, BlockModelType.none,
				new HarvestInfo(ToolType.NONE, ToolMaterial.HAND, DropInfo.item(InitItems.PLANT_FIBER, 30)),
				new HarvestInfo(ToolType.KNIFE, ToolMaterial.CLAY, DropInfo.item(InitItems.PLANT_FIBER, 80)),
				new HarvestInfo(ToolType.HOE, ToolMaterial.CLAY, DropInfo.item(InitItems.PLANT_FIBER, 80) /* TODO add seeds here */),
				new HarvestInfo(ToolType.SHEARS, ToolMaterial.CLAY, DropInfo.block(self, 80)));
		this.shape = shape;
	}
	
	@Override
	public ItemType getItemType() {
		return ItemType.itemblock;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctx) {
		return shape;
	}
	
	@Override
	public BlockState updateShape(BlockState state0, Direction dir, BlockState state1, IWorld world, BlockPos pos0, BlockPos pos1) {
		return !state0.canSurvive(world, pos0) ? Blocks.AIR.defaultBlockState() : super.updateShape(state0, dir, state1, world, pos0, pos1);
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
		return BlockH.canSupportPlant(reader.getBlockState(pos.below()).getBlock());
	}
	
	@Override
	public boolean isPathfindable(BlockState state, IBlockReader reader, BlockPos pos, PathType path) {
		return path == PathType.AIR ? true : super.isPathfindable(state, reader, pos, path);
	}
	
	@Override
	public boolean isPossibleToRespawnInThis() {
		return true;
	}
}
