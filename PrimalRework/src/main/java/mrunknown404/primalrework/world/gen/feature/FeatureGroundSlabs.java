package mrunknown404.primalrework.world.gen.feature;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.registries.PRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class FeatureGroundSlabs extends Feature<NoFeatureConfig> {
	private static final Map<StagedBlock, SlabSpawnInfo> SPAWN_MAP = new HashMap<StagedBlock, SlabSpawnInfo>();
	private static final Set<StagedBlock> SIDE_BLOCKS = new HashSet<StagedBlock>(Arrays.asList(PRBlocks.DIRT.get(), PRBlocks.GRASS_BLOCK.get(), PRBlocks.STONE.get()));
	
	static {
		add(PRBlocks.GRASS_BLOCK.get(), PRBlocks.DIRT.get(), PRBlocks.GRASS_SLAB.get());
		add(PRBlocks.DIRT.get(), null, PRBlocks.DIRT_SLAB.get());
	}
	
	public FeatureGroundSlabs() {
		super(NoFeatureConfig.CODEC);
	}
	
	@Override
	protected void setBlock(IWorldWriter world, BlockPos pos, BlockState state) {
		world.setBlock(pos, state, 19);
	}
	
	@Override
	public boolean place(ISeedReader seed, ChunkGenerator gen, Random r, BlockPos pos, NoFeatureConfig config) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = gen.getSeaLevel() - 1; y < seed.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX() + x, pos.getZ() + z); y++) {
					BlockPos originPos = new BlockPos(pos.getX() + x, y, pos.getZ() + z), abovePos = originPos.above();
					SlabSpawnInfo info = SPAWN_MAP.getOrDefault(seed.getBlockState(originPos).getBlock(), null);
					BlockState aboveState = seed.getBlockState(abovePos);
					
					if (info != null && aboveState.getBlock() != Blocks.CAVE_AIR && !aboveState.getMaterial().isLiquid() && aboveState.getMaterial().isReplaceable()) {
						float count = 0;
						
						for (Direction dir : Direction.values()) {
							if (dir == Direction.UP || dir == Direction.DOWN) {
								continue;
							}
							
							Block relAboveBlock = seed.getBlockState(abovePos.relative(dir)).getBlock();
							if (isSide(relAboveBlock)) {
								count++;
							} else if (relAboveBlock instanceof SlabBlock) {
								count += 0.5f;
							}
							
							BlockState relOriginBlock = seed.getBlockState(originPos.relative(dir));
							if (relOriginBlock.getMaterial().isReplaceable() || relOriginBlock.getBlock() instanceof SlabBlock) {
								count -= 0.5f;
							}
						}
						
						if (count > 1) {
							if (info.shouldReplace()) {
								setBlock(seed, originPos, info.replaceBlock.defaultBlockState());
							}
							setBlock(seed, abovePos, info.slabBlock.defaultBlockState());
						}
					}
				}
			}
		}
		
		return true;
	}
	
	private static void add(StagedBlock originBlock, StagedBlock replaceBlock, StagedBlock slabBlock) {
		SPAWN_MAP.put(originBlock, new SlabSpawnInfo(replaceBlock, slabBlock));
	}
	
	private static boolean isSide(Block b) {
		return SIDE_BLOCKS.contains(b);
	}
	
	private static class SlabSpawnInfo {
		private final StagedBlock slabBlock, replaceBlock;
		
		private SlabSpawnInfo(@Nullable StagedBlock replaceBlock, StagedBlock slabBlock) {
			this.slabBlock = slabBlock;
			this.replaceBlock = replaceBlock;
		}
		
		private boolean shouldReplace() {
			return replaceBlock != null;
		}
	}
}
