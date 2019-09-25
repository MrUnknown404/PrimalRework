package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.blocks.util.BlockSlabBase;
import mrunknown404.primalrework.blocks.util.IBlockBase;
import mrunknown404.primalrework.blocks.util.ISlabBase;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModEntities;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.items.util.IItemBase;
import mrunknown404.primalrework.items.util.ItemBase;
import mrunknown404.primalrework.util.EntityInfo;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@EventBusSubscriber
public class RegistryHandler {
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> e) {
		e.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent e) {
		for (Item item : ModItems.ITEMS) {
			if (item instanceof IItemBase) {
				((IItemBase<ItemBase>) item).registerModels(item);
			}
		}
		
		for (Block block : ModBlocks.BLOCKS) {
			if (block instanceof IBlockBase) {
				((IBlockBase<BlockBase>) block).registerModels(block);
			} else if (block instanceof ISlabBase) {
				((ISlabBase<BlockSlabBase>) block).registerModels(block);
			}
		}
	}
	
	public static void registerEntities() {
		for (EntityInfo info : ModEntities.ENTITIES) {
			registerEntity(info.name, info.clazz, info.id, info.range, info.color1, info.color2);
		}
	}
	
	private static void registerEntity(String name, Class<? extends Entity> clazz, int id, int range, int color1, int color2) {
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID, name), clazz, name, id, Main.main, range, 1, true, color1, color2);
	}
}
