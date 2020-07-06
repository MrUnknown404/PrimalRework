package mrunknown404.primalrework.handlers;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockRightClickHandler {
	private static final List<Block> FIRE_BLOCKS = Arrays.asList(ModBlocks.LIT_PRIMAL_TORCH, Blocks.TORCH);
	
	@SubscribeEvent
	public void onTorchRightClick(RightClickBlock e) {
		World w = e.getWorld();
		BlockPos pos = e.getPos();
		EntityPlayer p = e.getEntityPlayer();
		
		if (e.getItemStack() == null || e.getItemStack().isEmpty()) {
			return;
		}
		
		if (e.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.UNLIT_PRIMAL_TORCH)) {
			if (FIRE_BLOCKS.contains(w.getBlockState(pos).getBlock())) {
				e.getItemStack().shrink(1);
				p.addItemStackToInventory(new ItemStack(ModBlocks.LIT_PRIMAL_TORCH));
				e.setCanceled(true);
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public void onShovelRightClick(RightClickBlock e) {
		World w = e.getWorld();
		BlockPos pos = e.getPos();
		EntityPlayer p = e.getEntityPlayer();
		
		if (e.getItemStack() == null || e.getItemStack().isEmpty()) {
			return;
		}
		
		if (e.getItemStack().getItem() instanceof ItemSpade || HarvestHelper.hasToolType(e.getItemStack().getItem(), EnumToolType.shovel)) {
			if ((w.getBlockState(pos).getBlock() == Blocks.DIRT || w.getBlockState(pos).getBlock() == Blocks.GRASS) && w.getBlockState(pos.up()).getMaterial() == Material.AIR) {
				w.playSound(p, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
				p.swingArm(e.getHand());
				
				if (!w.isRemote) {
					if (!p.isCreative()) {
						e.getItemStack().damageItem(1, p);
					}
					
					w.setBlockState(pos, Blocks.GRASS_PATH.getDefaultState());
				}
			} else if (w.getBlockState(pos.up()).getMaterial() == Material.AIR &&
					(w.getBlockState(pos).getBlock() == ModBlocks.DIRT_SLAB || w.getBlockState(pos).getBlock() == ModBlocks.DIRT_DOUBLE_SLAB ||
							w.getBlockState(pos).getBlock() == ModBlocks.GRASS_SLAB || w.getBlockState(pos).getBlock() == ModBlocks.GRASS_DOUBLE_SLAB ||
							w.getBlockState(pos).getBlock() == ModBlocks.MUSHROOM_GRASS_SLAB || w.getBlockState(pos).getBlock() == ModBlocks.MUSHROOM_GRASS_DOUBLE_SLAB)) {
				w.playSound(p, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
				p.swingArm(e.getHand());
				
				if (!w.isRemote) {
					if (!p.isCreative()) {
						e.getItemStack().damageItem(1, p);
					}
					
					if (w.getBlockState(pos).getBlock() == ModBlocks.DIRT_SLAB || w.getBlockState(pos).getBlock() == ModBlocks.GRASS_SLAB ||
							w.getBlockState(pos).getBlock() == ModBlocks.MUSHROOM_GRASS_SLAB) {
						w.setBlockState(pos, ModBlocks.PATH_SLAB.getStateFromMeta(w.getBlockState(pos).getBlock().getMetaFromState(w.getBlockState(pos))));
					} else {
						w.setBlockState(pos, ModBlocks.PATH_DOUBLE_SLAB.getDefaultState());
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEnchantingTableRightClick(RightClickBlock e) {
		World w = e.getWorld();
		BlockPos pos = e.getPos();
		
		if (!w.isRemote) {
			if (w.getTileEntity(pos) instanceof TileEntityEnchantmentTable) {
				e.getEntityPlayer().openGui(Main.main, Main.GUI_ID_ENCHANTING, w, pos.getX(), pos.getY(), pos.getZ());
				e.setCanceled(true);
				return;
			}
		}
	}
}
