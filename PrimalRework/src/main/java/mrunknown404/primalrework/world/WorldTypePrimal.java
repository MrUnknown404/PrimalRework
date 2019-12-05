package mrunknown404.primalrework.world;

import mrunknown404.primalrework.world.gen.ChunkGeneratorPrimal;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldTypePrimal extends WorldType {
	
	public WorldTypePrimal() {
		super("primal");
	}
	
	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new ChunkGeneratorPrimal(world, world.getSeed());
	}
	
	@Override
	public float getCloudHeight() {
		return 256;
	}
}
