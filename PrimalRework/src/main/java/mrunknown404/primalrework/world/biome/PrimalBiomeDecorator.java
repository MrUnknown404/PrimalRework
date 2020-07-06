package mrunknown404.primalrework.world.biome;

import java.util.Random;

import mrunknown404.primalrework.world.gen.WorldGenGroundItems;
import mrunknown404.primalrework.world.gen.WorldGenSalt;
import mrunknown404.primalrework.world.gen.WorldGenSlabs;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class PrimalBiomeDecorator extends BiomeDecorator {
	
	public WorldGenerator saltGen = new WorldGenSalt(5, 2);
	public WorldGenerator groundItemsGen = new WorldGenGroundItems();
	public WorldGenerator slabsGen = new WorldGenSlabs();
	
	public int saltPerChunk = 1;
	public int groundItemsPerChunk = 4;
	
	@Override
	public void decorate(World w, Random r, Biome biome, BlockPos pos) {
		if (decorating) {
			throw new RuntimeException("Already decorating");
		}
		
		chunkProviderSettings = ChunkGeneratorSettings.Factory.jsonToFactory(w.getWorldInfo().getGeneratorOptions()).build();
		chunkPos = pos;
		dirtGen = new WorldGenMinable(Blocks.DIRT.getDefaultState(), chunkProviderSettings.dirtSize);
		gravelOreGen = new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), chunkProviderSettings.gravelSize);
		graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), chunkProviderSettings.graniteSize);
		dioriteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), chunkProviderSettings.dioriteSize);
		andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), chunkProviderSettings.andesiteSize);
		genDecorations(biome, w, r);
		decorating = false;
	}
	
	@Override
	protected void genDecorations(Biome biome, World w, Random r) {
		ChunkPos forgeChunkPos = new ChunkPos(chunkPos);
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(w, r, forgeChunkPos));
		generateOres(w, r);
		
		for (int i = 0; i < sandPatchesPerChunk; ++i) {
			sandGen.generate(w, r, w.getTopSolidOrLiquidBlock(chunkPos.add(r.nextInt(16) + 8, 0, r.nextInt(16) + 8)));
		}
		
		for (int i = 0; i < clayPerChunk; ++i) {
			clayGen.generate(w, r, w.getTopSolidOrLiquidBlock(chunkPos.add(r.nextInt(16) + 8, 0, r.nextInt(16) + 8)));
		}
		
		for (int i = 0; i < saltPerChunk; ++i) {
			saltGen.generate(w, r, w.getTopSolidOrLiquidBlock(chunkPos.add(r.nextInt(16) + 8, 0, r.nextInt(16) + 8)));
		}
		
		for (int i = 0; i < gravelPatchesPerChunk; ++i) {
			gravelGen.generate(w, r, w.getTopSolidOrLiquidBlock(chunkPos.add(r.nextInt(16) + 8, 0, r.nextInt(16) + 8)));
		}
		
		for (int i = 0; i < groundItemsPerChunk; ++i) {
			groundItemsGen.generate(w, r, w.getTopSolidOrLiquidBlock(chunkPos.add(r.nextInt(16) + 8, 0, r.nextInt(16) + 8)));
		}
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				slabsGen.generate(w, r, w.getTopSolidOrLiquidBlock(chunkPos.add(x + 8, -1, z + 8)));
			}
		}
		
		int k1 = treesPerChunk;
		
		if (r.nextFloat() < extraTreeChance) {
			++k1;
		}
		
		if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.TREE))
			for (int j2 = 0; j2 < k1; ++j2) {
				int k6 = r.nextInt(16) + 8;
				int l = r.nextInt(16) + 8;
				WorldGenAbstractTree wgenabstracttree = biome.getRandomTreeFeature(r);
				wgenabstracttree.setDecorationDefaults();
				BlockPos blockpos = w.getHeight(chunkPos.add(k6, 0, l));
				
				if (wgenabstracttree.generate(w, r, blockpos)) {
					wgenabstracttree.generateSaplings(w, r, blockpos);
				}
			}
		
		if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM))
			for (int k2 = 0; k2 < bigMushroomsPerChunk; ++k2) {
				int l6 = r.nextInt(16) + 8;
				int k10 = r.nextInt(16) + 8;
				bigMushroomGen.generate(w, r, w.getHeight(chunkPos.add(l6, 0, k10)));
			}
		
		if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.FLOWERS))
			for (int l2 = 0; l2 < flowersPerChunk; ++l2) {
				int i7 = r.nextInt(16) + 8;
				int l10 = r.nextInt(16) + 8;
				int j14 = w.getHeight(chunkPos.add(i7, 0, l10)).getY() + 32;
				
				if (j14 > 0) {
					int k17 = r.nextInt(j14);
					BlockPos blockpos1 = chunkPos.add(i7, k17, l10);
					BlockFlower.EnumFlowerType blockflower$enumflowertype = biome.pickRandomFlower(r, blockpos1);
					BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
					
					if (blockflower.getDefaultState().getMaterial() != Material.AIR) {
						flowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
						flowerGen.generate(w, r, blockpos1);
					}
				}
			}
		
		if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.GRASS))
			for (int i3 = 0; i3 < grassPerChunk; ++i3) {
				int j7 = r.nextInt(16) + 8;
				int i11 = r.nextInt(16) + 8;
				int k14 = w.getHeight(chunkPos.add(j7, 0, i11)).getY() * 2;
				
				if (k14 > 0) {
					int l17 = r.nextInt(k14);
					biome.getRandomWorldGenForGrass(r).generate(w, r, chunkPos.add(j7, l17, i11));
				}
			}
		
		if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH))
			for (int j3 = 0; j3 < deadBushPerChunk; ++j3) {
				int k7 = r.nextInt(16) + 8;
				int j11 = r.nextInt(16) + 8;
				int l14 = w.getHeight(chunkPos.add(k7, 0, j11)).getY() * 2;
				
				if (l14 > 0) {
					int i18 = r.nextInt(l14);
					(new WorldGenDeadBush()).generate(w, r, chunkPos.add(k7, i18, j11));
				}
			}
		
		if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.LILYPAD))
			for (int k3 = 0; k3 < waterlilyPerChunk; ++k3) {
				int l7 = r.nextInt(16) + 8;
				int k11 = r.nextInt(16) + 8;
				int i15 = w.getHeight(chunkPos.add(l7, 0, k11)).getY() * 2;
				
				if (i15 > 0) {
					int j18 = r.nextInt(i15);
					BlockPos blockpos4;
					BlockPos blockpos7;
					
					for (blockpos4 = chunkPos.add(l7, j18, k11); blockpos4.getY() > 0; blockpos4 = blockpos7) {
						blockpos7 = blockpos4.down();
						
						if (!w.isAirBlock(blockpos7)) {
							break;
						}
					}
					
					waterlilyGen.generate(w, r, blockpos4);
				}
			}
		
		if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.SHROOM)) {
			for (int l3 = 0; l3 < mushroomsPerChunk; ++l3) {
				if (r.nextInt(4) == 0) {
					int i8 = r.nextInt(16) + 8;
					int l11 = r.nextInt(16) + 8;
					BlockPos blockpos2 = w.getHeight(chunkPos.add(i8, 0, l11));
					mushroomBrownGen.generate(w, r, blockpos2);
				}
				
				if (r.nextInt(8) == 0) {
					int j8 = r.nextInt(16) + 8;
					int i12 = r.nextInt(16) + 8;
					int j15 = w.getHeight(chunkPos.add(j8, 0, i12)).getY() * 2;
					
					if (j15 > 0) {
						int k18 = r.nextInt(j15);
						BlockPos blockpos5 = chunkPos.add(j8, k18, i12);
						mushroomRedGen.generate(w, r, blockpos5);
					}
				}
			}
			
			if (r.nextInt(4) == 0) {
				int i4 = r.nextInt(16) + 8;
				int k8 = r.nextInt(16) + 8;
				int j12 = w.getHeight(chunkPos.add(i4, 0, k8)).getY() * 2;
				
				if (j12 > 0) {
					int k15 = r.nextInt(j12);
					mushroomBrownGen.generate(w, r, chunkPos.add(i4, k15, k8));
				}
			}
			
			if (r.nextInt(8) == 0) {
				int j4 = r.nextInt(16) + 8;
				int l8 = r.nextInt(16) + 8;
				int k12 = w.getHeight(chunkPos.add(j4, 0, l8)).getY() * 2;
				
				if (k12 > 0) {
					int l15 = r.nextInt(k12);
					mushroomRedGen.generate(w, r, chunkPos.add(j4, l15, l8));
				}
			}
		} // End of Mushroom generation
		
		if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.REED)) {
			for (int k4 = 0; k4 < reedsPerChunk; ++k4) {
				int i9 = r.nextInt(16) + 8;
				int l12 = r.nextInt(16) + 8;
				int i16 = w.getHeight(chunkPos.add(i9, 0, l12)).getY() * 2;
				
				if (i16 > 0) {
					int l18 = r.nextInt(i16);
					reedGen.generate(w, r, chunkPos.add(i9, l18, l12));
				}
			}
			
			for (int l4 = 0; l4 < 10; ++l4) {
				int j9 = r.nextInt(16) + 8;
				int i13 = r.nextInt(16) + 8;
				int j16 = w.getHeight(chunkPos.add(j9, 0, i13)).getY() * 2;
				
				if (j16 > 0) {
					int i19 = r.nextInt(j16);
					reedGen.generate(w, r, chunkPos.add(j9, i19, i13));
				}
			}
		} // End of Reed generation
		if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
			if (r.nextInt(32) == 0) {
				int i5 = r.nextInt(16) + 8;
				int k9 = r.nextInt(16) + 8;
				int j13 = w.getHeight(chunkPos.add(i5, 0, k9)).getY() * 2;
				
				if (j13 > 0) {
					int k16 = r.nextInt(j13);
					(new WorldGenPumpkin()).generate(w, r, chunkPos.add(i5, k16, k9));
				}
			}
		
		if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.CACTUS))
			for (int j5 = 0; j5 < cactiPerChunk; ++j5) {
				int l9 = r.nextInt(16) + 8;
				int k13 = r.nextInt(16) + 8;
				int l16 = w.getHeight(chunkPos.add(l9, 0, k13)).getY() * 2;
				
				if (l16 > 0) {
					int j19 = r.nextInt(l16);
					cactusGen.generate(w, r, chunkPos.add(l9, j19, k13));
				}
			}
		
		if (generateFalls) {
			if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.LAKE_WATER))
				for (int k5 = 0; k5 < 50; ++k5) {
					int i10 = r.nextInt(16) + 8;
					int l13 = r.nextInt(16) + 8;
					int i17 = r.nextInt(248) + 8;
					
					if (i17 > 0) {
						int k19 = r.nextInt(i17);
						BlockPos blockpos6 = chunkPos.add(i10, k19, l13);
						(new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(w, r, blockpos6);
					}
				}
			
			if (TerrainGen.decorate(w, r, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.LAKE_LAVA))
				for (int l5 = 0; l5 < 20; ++l5) {
					int j10 = r.nextInt(16) + 8;
					int i14 = r.nextInt(16) + 8;
					int j17 = r.nextInt(r.nextInt(r.nextInt(240) + 8) + 8);
					BlockPos blockpos3 = chunkPos.add(j10, j17, i14);
					(new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(w, r, blockpos3);
				}
		}
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(w, r, forgeChunkPos));
	}
	
	/**
	 * Generates ores in the current chunk
	 */
	@Override
	protected void generateOres(World w, Random r) {
		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(w, r, chunkPos));
		genStandardOre1(w, r, chunkProviderSettings.dirtCount, dirtGen, chunkProviderSettings.dirtMinHeight, chunkProviderSettings.dirtMaxHeight);
		genStandardOre1(w, r, chunkProviderSettings.gravelCount, gravelOreGen, chunkProviderSettings.gravelMinHeight,
				chunkProviderSettings.gravelMaxHeight);
		genStandardOre1(w, r, chunkProviderSettings.dioriteCount, dioriteGen, chunkProviderSettings.dioriteMinHeight,
				chunkProviderSettings.dioriteMaxHeight);
		genStandardOre1(w, r, chunkProviderSettings.graniteCount, graniteGen, chunkProviderSettings.graniteMinHeight,
				chunkProviderSettings.graniteMaxHeight);
		genStandardOre1(w, r, chunkProviderSettings.andesiteCount, andesiteGen, chunkProviderSettings.andesiteMinHeight,
				chunkProviderSettings.andesiteMaxHeight);
		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(w, r, chunkPos));
	}
}
