package mrunknown404.primalrework.registries;

import java.util.Collection;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.world.biome.provider.BiomeProviderPrimal;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PRRegistry {
	static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PrimalRework.MOD_ID);
	static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PrimalRework.MOD_ID);
	static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PrimalRework.MOD_ID);
	static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, PrimalRework.MOD_ID);
	static final DeferredRegister<ForgeWorldType> WORLD_TYPES = DeferredRegister.create(ForgeRegistries.WORLD_TYPES, PrimalRework.MOD_ID);
	static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, PrimalRework.MOD_ID);
	
	public static void register() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		ITEMS.register(bus);
		TILE_ENTITIES.register(bus);
		CONTAINERS.register(bus);
		BIOMES.register(bus);
		WORLD_TYPES.register(bus);
		
		PRItems.register();
		PRBlocks.register();
		PRTools.register();
		PRTileEntities.register();
		PRContainers.register();
		PRBiomes.register();
		PRWorld.register();
		
		Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(PrimalRework.MOD_ID, "biome_source"), BiomeProviderPrimal.PRIMAL_CODEC);
	}
	
	public static Collection<RegistryObject<Item>> getItems() {
		return ITEMS.getEntries();
	}
	
	public static Collection<RegistryObject<Block>> getBlocks() {
		return BLOCKS.getEntries();
	}
	
	public static Collection<RegistryObject<Biome>> getBiomes() {
		return BIOMES.getEntries();
	}
}
