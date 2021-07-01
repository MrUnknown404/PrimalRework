package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class SBGroundItem extends StagedBlock {
	private static final VoxelShape SHAPE = box(3, 0, 3, 13, 1, 13);
	
	public SBGroundItem(String name, SoundType sound) {
		this(name, sound, null);
	}
	
	public SBGroundItem(String name, SoundType sound, Item dropInstead) {
		super("ground_" + name, EnumStage.stage0, 64, InitItemGroups.BLOCKS, Material.DECORATION, sound, false, 0, 0, 0, false, BlockStateType.random_direction, BlockModelType.none,
				dropInstead == null ? HarvestInfo.HAND : new HarvestInfo(EnumToolType.none, EnumToolMaterial.hand, new DropInfo(dropInstead)));
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState state2, IWorld world, BlockPos pos, BlockPos pos2) {
		return direction == Direction.DOWN && !canSurvive(state, world, pos) ? Blocks.AIR.defaultBlockState() : state;
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
		return reader.getBlockState(pos.below()).isFaceSturdy(reader, pos, Direction.UP);
	}
	
	@Override
	public ItemType getItemType() {
		return ItemType.itemblock;
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
