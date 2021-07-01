package mrunknown404.primalrework.items.utils;

import java.util.Map;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IWorldReader;

public class SIWallFloor extends SIBlock {
	private final Block wallBlock;
	
	public SIWallFloor(StagedBlock floorBlock, StagedBlock wallBlock) {
		super(floorBlock);
		this.wallBlock = wallBlock;
	}
	
	@Override
	protected BlockState getPlacementState(BlockItemUseContext context) {
		BlockState blockstate = this.wallBlock.getStateForPlacement(context);
		BlockState blockstate1 = null;
		IWorldReader iworldreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		
		for (Direction direction : context.getNearestLookingDirections()) {
			if (direction != Direction.UP) {
				BlockState blockstate2 = direction == Direction.DOWN ? getBlock().getStateForPlacement(context) : blockstate;
				if (blockstate2 != null && blockstate2.canSurvive(iworldreader, blockpos)) {
					blockstate1 = blockstate2;
					break;
				}
			}
		}
		
		return blockstate1 != null && iworldreader.isUnobstructed(blockstate1, blockpos, ISelectionContext.empty()) ? blockstate1 : null;
	}
	
	@Override
	public void registerBlocks(Map<Block, Item> map, Item item) {
		super.registerBlocks(map, item);
		map.put(wallBlock, item);
	}
	
	@Override
	public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap) {
		super.removeFromBlockToItemMap(blockToItemMap);
		blockToItemMap.remove(wallBlock);
	}
}
