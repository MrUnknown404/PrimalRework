package mrunknown404.primalrework.handlers;

import java.util.Random;

import mrunknown404.primalrework.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockPunchHandler {
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
}
