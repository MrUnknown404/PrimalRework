package mrunknown404.primalrework.world.gen.feature;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mrunknown404.primalrework.utils.helpers.BlockH;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;

public class FeaturePrimalTree extends Feature<BaseTreeFeatureConfig> {
	public FeaturePrimalTree() {
		super(BaseTreeFeatureConfig.CODEC);
	}
	
	private static boolean isFree(IWorldGenerationBaseReader world, BlockPos pos) {
		return validTreePos(world, pos) || world.isStateAtPosition(pos, (state) -> BlockH.isLog(state.getBlock()));
	}
	
	private static boolean isVine(IWorldGenerationBaseReader world, BlockPos pos) {
		return world.isStateAtPosition(pos, (state) -> state.is(Blocks.VINE));
	}
	
	private static boolean isBlockWater(IWorldGenerationBaseReader world, BlockPos pos) {
		return world.isStateAtPosition(pos, (state) -> state.is(Blocks.WATER));
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isAirOrLeaves(IWorldGenerationBaseReader world, BlockPos pos) {
		return world.isStateAtPosition(pos, (state) -> state.isAir() || BlockH.isLeaves(state.getBlock()));
	}
	
	private static boolean isGrassOrDirtOrFarmland(IWorldGenerationBaseReader world, BlockPos pos) {
		return world.isStateAtPosition(pos, (state) -> BlockH.canSupportPlant(state.getBlock()));
	}
	
	private static boolean isReplaceablePlant(IWorldGenerationBaseReader world, BlockPos pos) {
		return world.isStateAtPosition(pos, (state) -> state.getMaterial() == Material.REPLACEABLE_PLANT);
	}
	
	private static boolean validTreePos(IWorldGenerationBaseReader world, BlockPos pos) {
		return isAirOrLeaves(world, pos) || isReplaceablePlant(world, pos) || isBlockWater(world, pos);
	}
	
	private static boolean doPlace(IWorldGenerationReader world, Random r, BlockPos pos, Set<BlockPos> set0, Set<BlockPos> set1, MutableBoundingBox bb,
			BaseTreeFeatureConfig config) {
		int i = config.trunkPlacer.getTreeHeight(r);
		int j = config.foliagePlacer.foliageHeight(r, i, config);
		int k = i - j;
		int l = config.foliagePlacer.foliageRadius(r, k);
		BlockPos blockpos;
		
		if (!config.fromSapling) {
			int i1 = world.getHeightmapPos(Heightmap.Type.OCEAN_FLOOR, pos).getY();
			int j1 = world.getHeightmapPos(Heightmap.Type.WORLD_SURFACE, pos).getY();
			if (j1 - i1 > config.maxWaterDepth) {
				return false;
			}
			
			int k1;
			if (config.heightmap == Heightmap.Type.OCEAN_FLOOR) {
				k1 = i1;
			} else if (config.heightmap == Heightmap.Type.WORLD_SURFACE) {
				k1 = j1;
			} else {
				k1 = world.getHeightmapPos(config.heightmap, pos).getY();
			}
			
			blockpos = new BlockPos(pos.getX(), k1, pos.getZ());
		} else {
			blockpos = pos;
		}
		
		if (blockpos.getY() >= 1 && blockpos.getY() + i + 1 <= 256) {
			if (!isGrassOrDirtOrFarmland(world, blockpos.below())) {
				return false;
			}
			
			OptionalInt optionalint = config.minimumSize.minClippedHeight();
			int l1 = getMaxFreeTreeHeight(world, i, blockpos, config);
			
			if (l1 >= i || optionalint.isPresent() && l1 >= optionalint.getAsInt()) {
				List<FoliagePlacer.Foliage> list = config.trunkPlacer.placeTrunk(world, r, l1, blockpos, set0, bb, config);
				list.forEach((foliage) -> config.foliagePlacer.createFoliage(world, r, config, l1, foliage, j, l, set1, bb));
				
				return true;
			}
			
			return false;
		}
		
		return false;
	}
	
	private static int getMaxFreeTreeHeight(IWorldGenerationBaseReader world, int value, BlockPos pos, BaseTreeFeatureConfig config) {
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
		
		for (int i = 0; i <= value + 1; ++i) {
			int j = config.minimumSize.getSizeAtHeight(value, i);
			
			for (int k = -j; k <= j; ++k) {
				for (int l = -j; l <= j; ++l) {
					blockpos$mutable.setWithOffset(pos, k, i, l);
					if (!isFree(world, blockpos$mutable) || !config.ignoreVines && isVine(world, blockpos$mutable)) {
						return i - 2;
					}
				}
			}
		}
		
		return value;
	}
	
	@Override
	protected void setBlock(IWorldWriter world, BlockPos pos, BlockState state) {
		world.setBlock(pos, state, 19);
	}
	
	@Override
	public boolean place(ISeedReader seed, ChunkGenerator chunkgen, Random r, BlockPos pos, BaseTreeFeatureConfig config) {
		Set<BlockPos> set0 = Sets.newHashSet();
		Set<BlockPos> set1 = Sets.newHashSet();
		Set<BlockPos> set2 = Sets.newHashSet();
		MutableBoundingBox mutableboundingbox = MutableBoundingBox.getUnknownBox();
		boolean flag = doPlace(seed, r, pos, set0, set1, mutableboundingbox, config);
		
		if (mutableboundingbox.x0 <= mutableboundingbox.x1 && flag && !set0.isEmpty()) {
			if (!config.decorators.isEmpty()) {
				List<BlockPos> list = Lists.newArrayList(set0);
				List<BlockPos> list1 = Lists.newArrayList(set1);
				list.sort(Comparator.comparingInt(Vector3i::getY));
				list1.sort(Comparator.comparingInt(Vector3i::getY));
				config.decorators.forEach((dec) -> {
					dec.place(seed, r, list, list1, set2, mutableboundingbox);
				});
			}
			
			VoxelShapePart voxelshapepart = updateLeaves(seed, mutableboundingbox, set0, set2);
			Template.updateShapeAtEdge(seed, 3, voxelshapepart, mutableboundingbox.x0, mutableboundingbox.y0, mutableboundingbox.z0);
			return true;
		}
		
		return false;
	}
	
	private static VoxelShapePart updateLeaves(IWorld world, MutableBoundingBox bb, Set<BlockPos> set0, Set<BlockPos> set1) {
		List<Set<BlockPos>> list = Lists.newArrayList();
		VoxelShapePart voxelshapepart = new BitSetVoxelShapePart(bb.getXSpan(), bb.getYSpan(), bb.getZSpan());
		
		for (int j = 0; j < 6; ++j) {
			list.add(Sets.newHashSet());
		}
		
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
		
		for (BlockPos blockpos : Lists.newArrayList(set1)) {
			if (bb.isInside(blockpos)) {
				voxelshapepart.setFull(blockpos.getX() - bb.x0, blockpos.getY() - bb.y0, blockpos.getZ() - bb.z0, true, true);
			}
		}
		
		for (BlockPos blockpos1 : Lists.newArrayList(set0)) {
			if (bb.isInside(blockpos1)) {
				voxelshapepart.setFull(blockpos1.getX() - bb.x0, blockpos1.getY() - bb.y0, blockpos1.getZ() - bb.z0, true, true);
			}
			
			for (Direction direction : Direction.values()) {
				blockpos$mutable.setWithOffset(blockpos1, direction);
				
				if (!set0.contains(blockpos$mutable)) {
					BlockState blockstate = world.getBlockState(blockpos$mutable);
					
					if (blockstate.hasProperty(BlockStateProperties.DISTANCE)) {
						list.get(0).add(blockpos$mutable.immutable());
						world.setBlock(blockpos$mutable, blockstate.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(1)), 19);
						
						if (bb.isInside(blockpos$mutable)) {
							voxelshapepart.setFull(blockpos$mutable.getX() - bb.x0, blockpos$mutable.getY() - bb.y0, blockpos$mutable.getZ() - bb.z0, true, true);
						}
					}
				}
			}
		}
		
		for (int l = 1; l < 6; ++l) {
			Set<BlockPos> set2 = list.get(l - 1);
			Set<BlockPos> set3 = list.get(l);
			
			for (BlockPos blockpos2 : set2) {
				if (bb.isInside(blockpos2)) {
					voxelshapepart.setFull(blockpos2.getX() - bb.x0, blockpos2.getY() - bb.y0, blockpos2.getZ() - bb.z0, true, true);
				}
				
				for (Direction direction1 : Direction.values()) {
					blockpos$mutable.setWithOffset(blockpos2, direction1);
					
					if (!set2.contains(blockpos$mutable) && !set3.contains(blockpos$mutable)) {
						BlockState blockstate1 = world.getBlockState(blockpos$mutable);
						
						if (blockstate1.hasProperty(BlockStateProperties.DISTANCE)) {
							int k = blockstate1.getValue(BlockStateProperties.DISTANCE);
							
							if (k > l + 1) {
								BlockState blockstate2 = blockstate1.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(l + 1));
								world.setBlock(blockpos$mutable, blockstate2, 19);
								
								if (bb.isInside(blockpos$mutable)) {
									voxelshapepart.setFull(blockpos$mutable.getX() - bb.x0, blockpos$mutable.getY() - bb.y0, blockpos$mutable.getZ() - bb.z0, true, true);
								}
								
								set3.add(blockpos$mutable.immutable());
							}
						}
					}
				}
			}
		}
		
		return voxelshapepart;
	}
}
