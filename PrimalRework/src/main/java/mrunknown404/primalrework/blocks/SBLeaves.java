package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.registries.PRStages;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.BlockH;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SBLeaves extends StagedBlock {
	public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
	
	public SBLeaves(String name) { // TODO add saplings/sticks
		super(name + "_leaves", PRStages.STAGE_0, 32, PRItemGroups.BLOCKS, BlockInfo.of(BlockInfo.LEAVES), BlockStateType.normal, BlockModelType.normal_colored,
				new HarvestInfo(ToolType.NONE, ToolMaterial.HAND, DropInfo.NONE), new HarvestInfo(ToolType.KNIFE, ToolMaterial.CLAY, DropInfo.NONE),
				new HarvestInfo(ToolType.AXE, ToolMaterial.CLAY, DropInfo.NONE), new HarvestInfo(ToolType.SHEARS, ToolMaterial.CLAY));
		registerDefaultState(stateDefinition.any().setValue(DISTANCE, Integer.valueOf(7)).setValue(PERSISTENT, Boolean.valueOf(false)));
	}
	
	@Override
	public VoxelShape getBlockSupportShape(BlockState state, IBlockReader reader, BlockPos pos) {
		return VoxelShapes.empty();
	}
	
	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(DISTANCE) == 7 && !state.getValue(PERSISTENT);
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random r) {
		if (!state.getValue(PERSISTENT) && state.getValue(DISTANCE) == 7) {
			dropResources(state, world, pos);
			world.removeBlock(pos, false);
		}
	}
	
	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random r) {
		world.setBlock(pos, updateDistance(state, world, pos), 3);
	}
	
	@Override
	public int getLightBlock(BlockState state, IBlockReader block, BlockPos pos) {
		return 1;
	}
	
	@Override
	public BlockState updateShape(BlockState state0, Direction dir, BlockState state1, IWorld world, BlockPos pos0, BlockPos pos1) {
		int i = getDistanceAt(state1) + 1;
		if (i != 1 || state0.getValue(DISTANCE) != i) {
			world.getBlockTicks().scheduleTick(pos0, this, 1);
		}
		
		return state0;
	}
	
	private static BlockState updateDistance(BlockState state, IWorld world, BlockPos pos) {
		int i = 7;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
		
		for (Direction direction : Direction.values()) {
			blockpos$mutable.setWithOffset(pos, direction);
			i = Math.min(i, getDistanceAt(world.getBlockState(blockpos$mutable)) + 1);
			if (i == 1) {
				break;
			}
		}
		
		return state.setValue(DISTANCE, Integer.valueOf(i));
	}
	
	private static int getDistanceAt(BlockState state) {
		if (BlockH.isLog(state.getBlock())) {
			return 0;
		}
		return state.getBlock() instanceof SBLeaves ? state.getValue(DISTANCE) : 7;
	}
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random r) { //TODO i want falling leaf particles one day
		if (world.isRainingAt(pos.above())) {
			if (r.nextInt(15) == 1) {
				BlockPos blockpos = pos.below();
				BlockState blockstate = world.getBlockState(blockpos);
				if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(world, blockpos, Direction.UP)) {
					double d0 = pos.getX() + r.nextDouble();
					double d1 = pos.getY() - 0.05;
					double d2 = pos.getZ() + r.nextDouble();
					world.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0, 0, 0);
				}
			}
		}
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(DISTANCE, PERSISTENT);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		return updateDistance(defaultBlockState().setValue(PERSISTENT, Boolean.valueOf(true)), ctx.getLevel(), ctx.getClickedPos());
	}
}
