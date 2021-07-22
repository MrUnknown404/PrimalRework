package mrunknown404.primalrework.registries;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.world.biome.PRBiome;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.RegistryObject;

public class PRBiomes {
	public static final RegistryObject<Biome> TEST0 = register(new PRBiome("test0", 100, BiomeType.WARM, Category.PLAINS, 1, 0.1f, 1, 1));
	public static final RegistryObject<Biome> TEST1 = register(new PRBiome("test1", 100, BiomeType.COOL, Category.OCEAN, 1, 0.1f, 1, 1));
	public static final RegistryObject<Biome> TEST2 = register(new PRBiome("test2", 100, BiomeType.DESERT, Category.FOREST, 1, 0.1f, 1, 1));
	
	private static RegistryObject<Biome> register(PRBiome biome) {
		ResourceLocation loc = new ResourceLocation(PrimalRework.MOD_ID, biome.name);
		BiomeManager.addBiome(biome.biomeType, new BiomeEntry(RegistryKey.create(Registry.BIOME_REGISTRY, loc), biome.weight));
		return PRRegistry.BIOMES.register(biome.name, () -> biome.biome);
	}
	
	//@formatter:off
	static void register() { }
	//@formatter:on
}
