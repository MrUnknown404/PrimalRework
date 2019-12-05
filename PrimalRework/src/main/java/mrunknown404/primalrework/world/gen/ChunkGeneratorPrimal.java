package mrunknown404.primalrework.world.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkGeneratorPrimal implements IChunkGenerator {

	private NoiseGeneratorOctaves minLimitPerlinNoise;
	private NoiseGeneratorOctaves maxLimitPerlinNoise;
	private NoiseGeneratorOctaves mainPerlinNoise;
	private NoiseGeneratorPerlin surfaceNoise;
	private NoiseGeneratorOctaves scaleNoise;
	private NoiseGeneratorOctaves depthNoise;
	private NoiseGeneratorOctaves forestNoise;
	
	private final World world;
	private final double[] heightMap;
	private final float[] biomeWeights;
	private final Random rand;
	
	private MapGenBase caveGenerator = new MapGenCaves();
	private MapGenBase ravineGenerator = new MapGenRavine();
	private Biome[] biomesForGeneration;
	private double[] depthBuffer = new double[256];
	private double[] mainNoiseRegion;
	private double[] minLimitRegion;
	private double[] maxLimitRegion;
	private double[] depthRegion;
	
	public ChunkGeneratorPrimal(World world, long seed) {
		caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, InitMapGenEvent.EventType.CAVE);
		ravineGenerator = TerrainGen.getModdedMapGen(ravineGenerator, InitMapGenEvent.EventType.RAVINE);
		
		this.world = world;
		rand = new Random(seed);
		minLimitPerlinNoise = new NoiseGeneratorOctaves(rand, 16);
		maxLimitPerlinNoise = new NoiseGeneratorOctaves(rand, 16);
		mainPerlinNoise = new NoiseGeneratorOctaves(rand, 8);
		surfaceNoise = new NoiseGeneratorPerlin(rand, 4);
		scaleNoise = new NoiseGeneratorOctaves(rand, 10);
		depthNoise = new NoiseGeneratorOctaves(rand, 16);
		forestNoise = new NoiseGeneratorOctaves(rand, 8);
		heightMap = new double[825];
		biomeWeights = new float[25];
		
		for (int i = -2; i <= 2; ++i) {
			for (int j = -2; j <= 2; ++j) {
				float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
				biomeWeights[i + 2 + (j + 2) * 5] = f;
			}
		}
		
		InitNoiseGensEvent.ContextOverworld ctx = new InitNoiseGensEvent.ContextOverworld(
				minLimitPerlinNoise, maxLimitPerlinNoise, mainPerlinNoise, surfaceNoise, scaleNoise, depthNoise, forestNoise);
		ctx = TerrainGen.getModdedNoiseGenerators(world, rand, ctx);
		minLimitPerlinNoise = ctx.getLPerlin1();
		maxLimitPerlinNoise = ctx.getLPerlin2();
		mainPerlinNoise = ctx.getPerlin();
		surfaceNoise = ctx.getHeight();
		scaleNoise = ctx.getScale();
		depthNoise = ctx.getDepth();
		forestNoise = ctx.getForest();
	}
	
	private void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
		biomesForGeneration = world.getBiomeProvider().getBiomesForGeneration(biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
		generateHeightmap(x * 4, 0, z * 4);
		
		for (int i = 0; i < 4; ++i) {
			int j = i * 5;
			int k = (i + 1) * 5;
			
			for (int l = 0; l < 4; ++l) {
				int i1 = (j + l) * 33;
				int j1 = (j + l + 1) * 33;
				int k1 = (k + l) * 33;
				int l1 = (k + l + 1) * 33;
				
				for (int i2 = 0; i2 < 32; ++i2) {
					double d1 = heightMap[i1 + i2];
					double d2 = heightMap[j1 + i2];
					double d3 = heightMap[k1 + i2];
					double d4 = heightMap[l1 + i2];
					double d5 = (heightMap[i1 + i2 + 1] - d1) * 0.125D;
					double d6 = (heightMap[j1 + i2 + 1] - d2) * 0.125D;
					double d7 = (heightMap[k1 + i2 + 1] - d3) * 0.125D;
					double d8 = (heightMap[l1 + i2 + 1] - d4) * 0.125D;
					
					for (int j2 = 0; j2 < 8; ++j2) {
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * 0.25D;
						double d13 = (d4 - d2) * 0.25D;
						
						for (int k2 = 0; k2 < 4; ++k2) {
							double d16 = (d11 - d10) * 0.25D;
							double lvt_45_1_ = d10 - d16;
							
							for (int l2 = 0; l2 < 4; ++l2) {
								if ((lvt_45_1_ += d16) > 0.0D) {
									primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.STONE.getDefaultState());
								} else if (i2 * 8 + j2 < WorldInfo.seaLevel) {
									primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.WATER.getDefaultState());
								}
							}
							
							d10 += d12;
							d11 += d13;
						}
						
						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}
	
	private void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
		if (!ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, world)) {
			return;
		}
		
		depthBuffer = surfaceNoise.getRegion(depthBuffer, (double) (x * 16), (double) (z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				Biome biome = biomesIn[j + i * 16];
				biome.genTerrainBlocks(world, rand, primer, x * 16 + i, z * 16 + j, depthBuffer[j + i * 16]);
			}
		}
	}
	
	@Override
	public Chunk generateChunk(int x, int z) {
		rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
		ChunkPrimer chunkprimer = new ChunkPrimer();
		setBlocksInChunk(x, z, chunkprimer);
		biomesForGeneration = this.world.getBiomeProvider().getBiomes(biomesForGeneration, x * 16, z * 16, 16, 16);
		replaceBiomeBlocks(x, z, chunkprimer, biomesForGeneration);
		
		caveGenerator.generate(world, x, z, chunkprimer);
		ravineGenerator.generate(world, x, z, chunkprimer);
		
		Chunk chunk = new Chunk(world, chunkprimer, x, z);
		byte[] abyte = chunk.getBiomeArray();
		
		for (int i = 0; i < abyte.length; ++i) {
			abyte[i] = (byte) Biome.getIdForBiome(biomesForGeneration[i]);
		}
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	private void generateHeightmap(int p_185978_1_, int p_185978_2_, int p_185978_3_) {
		depthRegion = depthNoise.generateNoiseOctaves(depthRegion, p_185978_1_, p_185978_3_, 5, 5, (double) WorldInfo.depthNoiseScaleX, (double) WorldInfo.depthNoiseScaleZ, (double) WorldInfo.depthNoiseScaleExponent);
		float f = WorldInfo.coordinateScale;
		float f1 = WorldInfo.heightScale;
		mainNoiseRegion = mainPerlinNoise.generateNoiseOctaves(mainNoiseRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double) (f / WorldInfo.mainNoiseScaleX), (double) (f1 / WorldInfo.mainNoiseScaleY), (double) (f / WorldInfo.mainNoiseScaleZ));
		minLimitRegion = minLimitPerlinNoise.generateNoiseOctaves(minLimitRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double) f, (double) f1, (double) f);
		maxLimitRegion = maxLimitPerlinNoise.generateNoiseOctaves(maxLimitRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (double) f, (double) f1, (double) f);
		int i = 0;
		int j = 0;
		
		for (int k = 0; k < 5; ++k) {
			for (int l = 0; l < 5; ++l) {
				float f2 = 0.0F;
				float f3 = 0.0F;
				float f4 = 0.0F;
				Biome biome = biomesForGeneration[k + 2 + (l + 2) * 10];
				
				for (int j1 = -2; j1 <= 2; ++j1) {
					for (int k1 = -2; k1 <= 2; ++k1) {
						Biome biome1 = biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
						float f5 = WorldInfo.biomeDepthOffset + biome1.getBaseHeight() * WorldInfo.biomeDepthWeight;
						float f6 = WorldInfo.biomeScaleOffset + biome1.getHeightVariation() * WorldInfo.biomeScaleWeight;
						float f7 = biomeWeights[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);
						
						if (biome1.getBaseHeight() > biome.getBaseHeight()) {
							f7 /= 2.0F;
						}
						
						f2 += f6 * f7;
						f3 += f5 * f7;
						f4 += f7;
					}
				}
				
				f2 = f2 / f4;
				f3 = f3 / f4;
				f2 = f2 * 0.9F + 0.1F;
				f3 = (f3 * 4.0F - 1.0F) / 8.0F;
				double d7 = depthRegion[j] / 8000.0D;
				
				if (d7 < 0.0D) {
					d7 = -d7 * 0.3D;
				}
				
				d7 = d7 * 3.0D - 2.0D;
				if (d7 < 0.0D) {
					d7 = d7 / 2.0D;
					
					if (d7 < -1.0D) {
						d7 = -1.0D;
					}
					
					d7 = d7 / 1.4D;
					d7 = d7 / 2.0D;
				} else {
					if (d7 > 1.0D) {
						d7 = 1.0D;
					}
					
					d7 = d7 / 8.0D;
				}
				
				++j;
				double d8 = (double) f3;
				double d9 = (double) f2;
				d8 = d8 + d7 * 0.2D;
				d8 = d8 * (double) WorldInfo.baseSize / 8.0D;
				double d0 = (double) WorldInfo.baseSize + d8 * 4.0D;
				
				for (int l1 = 0; l1 < 33; ++l1) {
					double d1 = ((double) l1 - d0) * (double) WorldInfo.stretchY * 128.0D / 256.0D / d9;
					
					if (d1 < 0.0D) {
						d1 *= 4.0D;
					}
					
					double d2 = minLimitRegion[i] / (double) WorldInfo.lowerLimitScale;
					double d3 = maxLimitRegion[i] / (double) WorldInfo.upperLimitScale;
					double d4 = (mainNoiseRegion[i] / 10.0D + 1.0D) / 2.0D;
					double d5 = MathHelper.clampedLerp(d2, d3, d4) - d1;
					
					if (l1 > 29) {
						double d6 = (double) ((float) (l1 - 29) / 3.0F);
						d5 = d5 * (1.0D - d6) + -10.0D * d6;
					}
					
					heightMap[i] = d5;
					++i;
				}
			}
		}
	}
	
	@Override
	public void populate(int x, int z) {
		BlockFalling.fallInstantly = true;
		int i = x * 16;
		int j = z * 16;
		BlockPos blockpos = new BlockPos(i, 0, j);
		Biome biome = world.getBiome(blockpos.add(16, 0, 16));
		rand.setSeed(world.getSeed());
		long k = rand.nextLong() / 2L * 2L + 1L;
		long l = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed((long) x * k + (long) z * l ^ world.getSeed());
		boolean flag = false;
		
		ForgeEventFactory.onChunkPopulate(true, this, world, rand, x, z, flag);
		
		biome.decorate(world, rand, new BlockPos(i, 0, j));
		if (TerrainGen.populate(this, world, rand, x, z, flag, PopulateChunkEvent.Populate.EventType.ANIMALS)) {
			WorldEntitySpawner.performWorldGenSpawning(world, biome, i + 8, j + 8, 16, 16, rand);
		}
		blockpos = blockpos.add(8, 0, 8);
		
		if (TerrainGen.populate(this, world, rand, x, z, flag, PopulateChunkEvent.Populate.EventType.ICE)) {
			for (int k2 = 0; k2 < 16; ++k2) {
				for (int j3 = 0; j3 < 16; ++j3) {
					BlockPos blockpos1 = world.getPrecipitationHeight(blockpos.add(k2, 0, j3));
					BlockPos blockpos2 = blockpos1.down();
					
					if (world.canBlockFreezeWater(blockpos2)) {
						world.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
					}
					
					if (world.canSnowAt(blockpos1, true)) {
						world.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
					}
				}
			}
		}
		
		ForgeEventFactory.onChunkPopulate(false, this, world, rand, x, z, flag);
		BlockFalling.fallInstantly = false;
	}
	
	/** Called to generate additional structures after initial worldgen, used by ocean monuments */
	@Override
	public boolean generateStructures(Chunk chunk, int x, int z) {
		return false;
	}
	
	/** Returns a list of possible spawns around features (ie swamp huts/ocean monuments) */
	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return world.getBiome(pos).getSpawnableList(creatureType);
	}
	
	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}
	
	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
		return null;
	}
	
	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		//TODO setup structures
	}
	
	public static final class WorldInfo {
		public static final float coordinateScale = 684.412F;
		public static final float heightScale = 684.412F;
		public static final float upperLimitScale = 512.0F;
		public static final float lowerLimitScale = 512.0F;
		public static final float depthNoiseScaleX = 200.0F;
		public static final float depthNoiseScaleZ = 200.0F;
		public static final float depthNoiseScaleExponent = 0.5F;
		public static final float mainNoiseScaleX = 80.0F;
		public static final float mainNoiseScaleY = 160.0F;
		public static final float mainNoiseScaleZ = 80.0F;
		public static final float baseSize = 8.5F;
		public static final float stretchY = 12.0F;
		public static final float biomeDepthWeight = 1.0F;
		public static final float biomeDepthOffset = 0.0F;
		public static final float biomeScaleWeight = 1.0F;
		public static final float biomeScaleOffset = 0.0F;
		public static final int seaLevel = 63;
	}
}
