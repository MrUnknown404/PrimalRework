package mrunknown404.primalrework.handlers.events;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler {

	private final List<Block> fire_blocks = Arrays.asList(ModBlocks.LIT_PRIMAL_TORCH, Blocks.TORCH);
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent.RightClickBlock e) {
		World w = e.getWorld();
		
		if (!w.isRemote) {
			TileEntity tileentity = w.getTileEntity(e.getPos());
			
			if (tileentity instanceof TileEntityEnchantmentTable) {
				e.getEntityPlayer().openGui(Main.main, Main.GUI_ID_ENCHANTING, w, e.getPos().getX(), e.getPos().getY(), e.getPos().getZ());
				e.setCanceled(true);
				return;
			}
		}
		
		if (e.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.UNLIT_PRIMAL_TORCH)) {
			if (fire_blocks.contains(w.getBlockState(e.getPos()).getBlock())) {
				e.getItemStack().shrink(1);
				e.getEntityPlayer().addItemStackToInventory(new ItemStack(ModBlocks.LIT_PRIMAL_TORCH));
				e.setCanceled(true);
				return;
			}
		}
	}
}
