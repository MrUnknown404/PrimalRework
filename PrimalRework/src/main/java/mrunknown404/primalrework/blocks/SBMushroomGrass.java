package mrunknown404.primalrework.blocks;

import java.util.Random;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

public class SBMushroomGrass extends StagedBlock {
	
	public SBMushroomGrass() {
		super("mushroom_grass", EnumStage.stage0, 64, InitItemGroups.BLOCKS, Material.GRASS, SoundType.GRASS, true, 0, 0.8f, 0.8f, true, BlockStateType.none, BlockModelType.none,
				new HarvestInfo(EnumToolType.shovel, EnumToolMaterial.clay, new DropInfo(Items.DIRT)),
				new HarvestInfo(EnumToolType.none, EnumToolMaterial.hand, new DropInfo(Items.DIRT)));
		registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.SNOWY, Boolean.valueOf(false)));
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!canBeGrass(state, world, pos)) {
			if (!world.isAreaLoaded(pos, 3)) {
				return;
			}
			world.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
		} else {
			if (world.getMaxLocalRawBrightness(pos.above()) >= 9) {
				BlockState blockstate = defaultBlockState();
				
				for (int i = 0; i < 4; ++i) {
					BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (world.getBlockState(blockpos).is(Blocks.DIRT) && canPropagate(blockstate, world, blockpos)) {
						world.setBlockAndUpdate(blockpos, blockstate);
					}
				}
			}
		}
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState state2, IWorld world, BlockPos pos, BlockPos pos2) {
		return direction != Direction.UP ? state : state.setValue(BlockStateProperties.SNOWY, Boolean.valueOf(state2.is(Blocks.SNOW_BLOCK) || state2.is(Blocks.SNOW)));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().above());
		return defaultBlockState().setValue(BlockStateProperties.SNOWY, Boolean.valueOf(blockstate.is(Blocks.SNOW_BLOCK) || blockstate.is(Blocks.SNOW)));
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.SNOWY);
	}
	
	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		return true;
	}
	
	@Override
	public ItemType getItemType() {
		return ItemType.none;
	}
	
	private static boolean canBeGrass(BlockState state, IWorldReader reader, BlockPos pos) {
		BlockPos blockpos = pos.above();
		BlockState blockstate = reader.getBlockState(blockpos);
		if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowBlock.LAYERS) == 1) {
			return true;
		} else if (blockstate.getFluidState().getAmount() == 8) {
			return false;
		} else {
			return LightEngine.getLightBlockInto(reader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(reader, blockpos)) < reader.getMaxLightLevel();
		}
	}
	
	private static boolean canPropagate(BlockState state, IWorldReader reader, BlockPos pos) {
		return canBeGrass(state, reader, pos) && !reader.getFluidState(pos.above()).is(FluidTags.WATER);
	}
}
