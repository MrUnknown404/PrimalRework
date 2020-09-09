package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.blocks.util.ISlabStaged;
import mrunknown404.primalrework.init.InitBiomes;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitItems;
import mrunknown404.primalrework.init.InitSounds;
import mrunknown404.primalrework.world.biome.BiomeBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler {
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(InitItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> e) {
		e.getRegistry().registerAll(InitBlocks.BLOCKS.toArray(new Block[0]));
	}

	@SubscribeEvent
	public static void onSoundRegister(RegistryEvent.Register<SoundEvent> e) {
		e.getRegistry().registerAll(InitSounds.SOUNDS.toArray(new SoundEvent[0]));
	}
	
	@SubscribeEvent
	public static void onBiomeRegister(RegistryEvent.Register<Biome> e) {
		e.getRegistry().registerAll(InitBiomes.BIOMES.toArray(new Biome[0]));
		
		for (Biome b2 : InitBiomes.BIOMES) {
			if (b2 instanceof BiomeBase) {
				BiomeBase b = (BiomeBase) b2;
				
				BiomeManager.addBiome(b.getBiomeType(), new BiomeEntry(b, b.getWeight()));
				BiomeDictionary.addTypes(b2, b.getBiomeTypes());
				
				BiomeManager.addSpawnBiome(b);
				if (b.isStrongholdBiome()) {
					BiomeManager.addStrongholdBiome(b);
				}
				if (b.isVillageBiome()) {
					BiomeManager.addVillageBiome(b, true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onModelRegister(@SuppressWarnings("unused") ModelRegistryEvent e) {
		for (Item item : InitItems.ITEMS) {
			Main.proxy.registerItemRenderer(item, 0, "inventory");
		}
		
		for (Block block : InitBlocks.BLOCKS) {
			if (block instanceof ISlabStaged) {
				if (!((ISlabStaged<BlockSlab>) block).isDouble()) {
					Main.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
				}
			} else {
				Main.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
			}
		}
	}
}
