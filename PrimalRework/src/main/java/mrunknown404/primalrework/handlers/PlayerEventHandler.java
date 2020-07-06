package mrunknown404.primalrework.handlers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import mrunknown404.primalrework.util.helpers.StageHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
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
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler {
	
	private final List<Block> fire_blocks = Arrays.asList(ModBlocks.LIT_PRIMAL_TORCH, Blocks.TORCH);
	
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent e) {
		Iterator<Entry<EnumStage, List<ItemStack>>> it = StageHelper.ITEM_STAGE_MAP.entrySet().iterator();
		while (it.hasNext()) {
			Entry<EnumStage, List<ItemStack>> pair = it.next();
			for (ItemStack item : pair.getValue()) {
				if (item.getItem() == e.getItemStack().getItem() && item.getMetadata() == e.getItemStack().getMetadata()) {
					e.getToolTip().add("Stage: " + pair.getKey().getName());
					return;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBonemeal(BonemealEvent e) {
		e.setCanceled(true);
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
					
					e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getHitVec().x, e.getHitVec().y, e.getHitVec().z,
							new ItemStack(ModItems.FLINT_KNAPPED, 1 + MathHelper.clamp(r.nextInt(3) - 1, 0, 1))));
				}
			} else if (item.getItem() == ModItems.FLINT_KNAPPED) {
				e.getWorld().playSound(e.getEntityPlayer(), e.getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				
				if (!e.getWorld().isRemote && r.nextInt(3) == 0) {
					item.shrink(1);
					
					e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getHitVec().x, e.getHitVec().y, e.getHitVec().z,
							new ItemStack(ModItems.FLINT_POINT, 1 + MathHelper.clamp(r.nextInt(3) - 1, 0, 1))));
				}
			} else if (item.getItem() == Items.BONE) {
				e.getWorld().playSound(e.getEntityPlayer(), e.getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				
				if (!e.getWorld().isRemote && r.nextInt(3) == 0) {
					item.shrink(1);
					
					e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getHitVec().x, e.getHitVec().y, e.getHitVec().z,
							new ItemStack(ModItems.BONE_SHARD, 1 + MathHelper.clamp(r.nextInt(3) - 1, 0, 1))));
					
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
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
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
	public void onRightClickEntity(EntityInteract e) {
		if (e.getTarget() instanceof EntityCow && e.getItemStack().getItem() == ModItems.CLAY_BUCKET_EMPTY) {
			e.getEntityPlayer().setHeldItem(e.getHand(), new ItemStack(ModItems.CLAY_BUCKET_MILK, 1, e.getItemStack().getItemDamage()));
		} else if (e.getTarget() instanceof EntitySheep && HarvestHelper.hasToolType(e.getItemStack().getItem(), EnumToolType.shears)) {
			int fortune = 0;
			if (e.getItemStack().isItemEnchanted()) {
				Map<Enchantment, Integer> it = EnchantmentHelper.getEnchantments(e.getItemStack());
				if (it.containsKey(Enchantments.FORTUNE)) {
					fortune = it.get(Enchantments.FORTUNE);
				}
			}
			
			List<ItemStack> wools = ((EntitySheep) e.getTarget()).onSheared(e.getItemStack(), e.getWorld(), e.getPos(), fortune);
			
			if (!e.getWorld().isRemote) {
				for (ItemStack wool : wools) {
					e.getWorld().spawnEntity(new EntityItem(e.getWorld(), e.getPos().getX(), e.getPos().getY(), e.getPos().getZ(), wool));
				}
				
				if (!e.getEntityPlayer().isCreative()) {
					e.getItemStack().damageItem(1, e.getEntityLiving());
				}
			}
		}
	}
}
