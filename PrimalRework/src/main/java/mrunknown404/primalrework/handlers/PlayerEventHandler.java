package mrunknown404.primalrework.handlers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PlayerEventHandler {

	private final List<Block> fire_blocks = Arrays.asList(ModBlocks.LIT_PRIMAL_TORCH, Blocks.TORCH);
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		if (!e.player.isDead) {
			for (int i = 0; i < e.player.inventory.mainInventory.size(); i++) {
				ItemStack item = e.player.inventory.mainInventory.get(i);
				
				if (item.getItem() == Items.DIAMOND_AXE) {
					e.player.inventory.setInventorySlotContents(i, new ItemStack(ModItems.DIAMOND_AXE, 1, item.getItemDamage()));
				} else if (item.getItem() == Items.IRON_AXE) {
					e.player.inventory.setInventorySlotContents(i, new ItemStack(ModItems.IRON_AXE, 1, item.getItemDamage()));
				} else if (item.getItem() == Items.STONE_AXE) {
					e.player.inventory.setInventorySlotContents(i, new ItemStack(ModItems.STONE_AXE, 1, item.getItemDamage()));
				} else if (item.getItem() == Items.GOLDEN_AXE) {
					e.player.inventory.setInventorySlotContents(i, new ItemStack(ModItems.GOLDEN_AXE, 1, item.getItemDamage()));
				} else if (item.getItem() == Items.WOODEN_AXE) {
					e.player.inventory.setInventorySlotContents(i, new ItemStack(ModItems.WOODEN_AXE, 1, item.getItemDamage()));
				} else if (item.getItem() == Items.DIAMOND_HOE) {
					e.player.inventory.setInventorySlotContents(i, new ItemStack(ModItems.DIAMOND_HOE, 1, item.getItemDamage()));
				} else if (item.getItem() == Items.IRON_HOE) {
					e.player.inventory.setInventorySlotContents(i, new ItemStack(ModItems.IRON_HOE, 1, item.getItemDamage()));
				} else if (item.getItem() == Items.STONE_HOE) {
					e.player.inventory.setInventorySlotContents(i, new ItemStack(ModItems.STONE_HOE, 1, item.getItemDamage()));
				} else if (item.getItem() == Items.GOLDEN_HOE) {
					e.player.inventory.setInventorySlotContents(i, new ItemStack(ModItems.GOLDEN_HOE, 1, item.getItemDamage()));
				} else if (item.getItem() == Items.WOODEN_HOE) {
					e.player.inventory.setInventorySlotContents(i, new ItemStack(ModItems.WOODEN_HOE, 1, item.getItemDamage()));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockPunch(PlayerInteractEvent.LeftClickBlock e) {
		if (e.getEntityPlayer().isCreative()) {
			return;
		}
		
		Block b = e.getWorld().getBlockState(e.getPos()).getBlock();
		ItemStack item = e.getItemStack();
		Random r = new Random();
		
		if (b == Blocks.STONE || b == Blocks.COBBLESTONE) {
			if (item.getItem() == Items.FLINT) {
				e.getWorld().playSound(e.getEntityPlayer(), e.getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				
				if (!e.getWorld().isRemote && r.nextInt(3) == 0) {
					item.shrink(1);
					
					e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getHitVec().x, e.getHitVec().y, e.getHitVec().z, new ItemStack(ModItems.FLINT_KNAPPED,
							1 + MathHelper.clamp(r.nextInt(3) - 1, 0, 1))));
				}
			} else if (item.getItem() == ModItems.FLINT_KNAPPED) {
				e.getWorld().playSound(e.getEntityPlayer(), e.getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				
				if (!e.getWorld().isRemote && r.nextInt(3) == 0) {
					item.shrink(1);
					
					e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getHitVec().x, e.getHitVec().y, e.getHitVec().z, new ItemStack(ModItems.FLINT_POINT,
							1 + MathHelper.clamp(r.nextInt(3) - 1, 0, 1))));
				}
			} else if (item.getItem() == Items.BONE) {
				e.getWorld().playSound(e.getEntityPlayer(), e.getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				
				if (!e.getWorld().isRemote && r.nextInt(3) == 0) {
					item.shrink(1);
					
					e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getHitVec().x, e.getHitVec().y, e.getHitVec().z, new ItemStack(ModItems.BONE_SHARD,
							1 + MathHelper.clamp(r.nextInt(3) - 1, 0, 1))));
					
					if (r.nextInt(3) == 0) {
						e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getHitVec().x, e.getHitVec().y, e.getHitVec().z, new ItemStack(Items.DYE, 1, 15)));
					}
				}
			} else if (item.getItem() == ModItems.BONE_SHARD) {
				e.getWorld().playSound(e.getEntityPlayer(), e.getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				
				if (!e.getWorld().isRemote && r.nextInt(3) == 0) {
					item.shrink(1);
					e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getHitVec().x, e.getHitVec().y, e.getHitVec().z, new ItemStack(Items.DYE, 1, 15)));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent.RightClickBlock e) {
		World w = e.getWorld();
		BlockPos pos = e.getPos();
		EntityPlayer p = e.getEntityPlayer();
		
		if (!w.isRemote) {
			if (w.getTileEntity(pos) instanceof TileEntityEnchantmentTable) {
				e.getEntityPlayer().openGui(Main.main, Main.GUI_ID_ENCHANTING, w, pos.getX(), pos.getY(), pos.getZ());
				e.setCanceled(true);
				return;
			}
		}
		
		if (e.getItemStack() == null || e.getItemStack().isEmpty()) {
			return;
		}
		
		if (e.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.UNLIT_PRIMAL_TORCH)) {
			if (fire_blocks.contains(w.getBlockState(pos).getBlock())) {
				e.getItemStack().shrink(1);
				p.addItemStackToInventory(new ItemStack(ModBlocks.LIT_PRIMAL_TORCH));
				e.setCanceled(true);
				return;
			}
		} else if (e.getItemStack().getItem() instanceof ItemSpade || HarvestHelper.hasToolType(e.getItemStack().getItem(), EnumToolType.shovel)) {
			if (w.getBlockState(pos.up()).getMaterial() == Material.AIR) {
				w.playSound(p, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
				p.swingArm(e.getHand());
				
				if (!w.isRemote) {
					if (!p.isCreative()) {
						e.getItemStack().damageItem(1, p);
					}
					
					w.setBlockState(pos, Blocks.GRASS_PATH.getDefaultState());
				}
			}
		}
	}
}
