package mrunknown404.primalrework.world.biome;

import java.util.Random;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomePrimalForest extends BiomeBase {
	private static final WorldGenBirchTree SUPER_BIRCH_TREE = new WorldGenBirchTree(false, true);
	private static final WorldGenBirchTree BIRCH_TREE = new WorldGenBirchTree(false, false);
	private static final WorldGenTaiga1 SPRUCE_TREE = new WorldGenTaiga1();
	private final ForestType forestType;
	
	// TODO add mushrooms to ground & more cool trees
	
	public BiomePrimalForest(BiomeModifier biomeMod, ForestType forestType, int weight, int treesPerChunk) {
		super(forestType.toString().toLowerCase() + "_forest", BiomeType.WARM, biomeMod, new BiomeDictionary.Type[] { BiomeDictionary.Type.FOREST }, weight, true, false);
		this.forestType = forestType;
		this.decorator.treesPerChunk = treesPerChunk;
		this.decorator.grassPerChunk = 3;
		
		if (forestType == ForestType.NORMAL) {
			spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 2, 4));
		}
	}
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		switch (forestType) {
			case BIRCH:
				return rand.nextInt(6) == 0 ? SUPER_BIRCH_TREE : BIRCH_TREE;
			case MUSHROOM:
				return rand.nextInt(14) == 0 ? SPRUCE_TREE : TREE_FEATURE;
			case NORMAL:
				return rand.nextInt(32) == 0 ? SPRUCE_TREE : rand.nextInt(32) == 0 ? BIRCH_TREE : rand.nextInt(10) == 0 ? BIG_TREE_FEATURE : TREE_FEATURE;
		}
		
		return TREE_FEATURE;
	}
	
	@Override
	public void decorate(World world, Random rand, BlockPos pos) {
		if (forestType == ForestType.MUSHROOM) {
			addMushrooms(world, rand, pos);
		}
		
		if (TerrainGen.decorate(world, rand, new ChunkPos(pos), DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
			addDoublePlants(world, rand, pos, rand.nextInt(5) - 3);
		}
		
		super.decorate(world, rand, pos);
	}
	
	private void addMushrooms(World w, Random r, BlockPos pos) {
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				int k = i * 4 + 1 + 8 + r.nextInt(3);
				int l = j * 4 + 1 + 8 + r.nextInt(3);
				BlockPos blockpos = w.getHeight(pos.add(k, 0, l));
				
				if (r.nextInt(20) == 0 && TerrainGen.decorate(w, r, new ChunkPos(pos), blockpos, DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM)) {
					WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
					worldgenbigmushroom.generate(w, r, blockpos);
				} else if (TerrainGen.decorate(w, r, new ChunkPos(pos), blockpos, DecorateBiomeEvent.Decorate.EventType.TREE)) {
					WorldGenAbstractTree worldgenabstracttree = getRandomTreeFeature(r);
					worldgenabstracttree.setDecorationDefaults();
					
					if (worldgenabstracttree.generate(w, r, blockpos)) {
						worldgenabstracttree.generateSaplings(w, r, blockpos);
					}
				}
			}
		}
	}
	
	private void addDoublePlants(World w, Random r, BlockPos pos, int amount) {
		for (int i = 0; i < amount; ++i) {
			int j = r.nextInt(3);
			
			if (j == 0) {
				DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
			} else if (j == 1) {
				DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
			} else if (j == 2) {
				DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
			}
			
			for (int k = 0; k < 5; ++k) {
				int l = r.nextInt(16) + 8;
				int i1 = r.nextInt(16) + 8;
				int j1 = r.nextInt(w.getHeight(pos.add(l, 0, i1)).getY() + 32);
				
				if (DOUBLE_PLANT_GENERATOR.generate(w, r, new BlockPos(pos.getX() + l, j1, pos.getZ() + i1))) {
					break;
				}
			}
		}
	}
	
	@Override
	public Class<? extends Biome> getBiomeClass() {
		return BiomePrimalForest.class;
	}
	
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos) {
		return forestType == ForestType.MUSHROOM ? (super.getGrassColorAtPos(pos) & 16711422) + 2634762 >> 1 : super.getGrassColorAtPos(pos);
	}
	
	public static enum ForestType {
		NORMAL, BIRCH, MUSHROOM; //TODO add dead, spruce, big forest
	}
}
