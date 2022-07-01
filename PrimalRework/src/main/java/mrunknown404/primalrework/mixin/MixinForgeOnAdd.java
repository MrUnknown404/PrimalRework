package mrunknown404.primalrework.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mrunknown404.primalrework.items.raw.SIBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryInternal;
import net.minecraftforge.registries.RegistryManager;

@Mixin(targets = "net/minecraftforge/registries/GameData$ItemCallbacks")
public class MixinForgeOnAdd {
	private static final ResourceLocation BLOCK_TO_ITEM = new ResourceLocation("minecraft:blocktoitemmap");
	
	@SuppressWarnings({ "static-method", "unused", "unchecked" })
	@Inject(at = @At("HEAD"), method = "onAdd(Lnet/minecraftforge/registries/IForgeRegistryInternal;Lnet/minecraftforge/registries/RegistryManager;ILnet/minecraft/item/Item;Lnet/minecraft/item/Item;)V", remap = false)
	private void onAdd(IForgeRegistryInternal<Item> owner, RegistryManager stage, int id, Item item, Item oldItem, CallbackInfo callback) {
		if (oldItem instanceof SIBlock) {
			Map<Block, Item> blockToItem = owner.getSlaveMap(BLOCK_TO_ITEM, Map.class);
			((SIBlock) oldItem).removeFromBlockToItemMap(blockToItem);
		}
		if (item instanceof SIBlock) {
			Map<Block, Item> blockToItem = owner.getSlaveMap(BLOCK_TO_ITEM, Map.class);
			((SIBlock) item).registerBlocks(blockToItem, item);
		}
	}
}
