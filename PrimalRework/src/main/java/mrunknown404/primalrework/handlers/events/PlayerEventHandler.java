package mrunknown404.primalrework.handlers.events;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler {

	private final List<Block> fire_blocks = Arrays.asList(ModBlocks.LIT_PRIMAL_TORCH, Blocks.TORCH);
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent.RightClickBlock e) {
		if (e.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.UNLIT_PRIMAL_TORCH)) {
			if (fire_blocks.contains(e.getWorld().getBlockState(e.getPos()).getBlock())) {
				e.getItemStack().shrink(1);
				e.getEntityPlayer().addItemStackToInventory(new ItemStack(ModBlocks.LIT_PRIMAL_TORCH));
				e.setCanceled(true);
			}
		}
	}
}
