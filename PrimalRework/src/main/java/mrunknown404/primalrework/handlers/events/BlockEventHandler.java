package mrunknown404.primalrework.handlers.events;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import mrunknown404.primalrework.handlers.HarvestHandler;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo.ItemDropInfo;
import mrunknown404.primalrework.util.harvest.ToolHarvestLevel;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockEventHandler {

	@SubscribeEvent
	public void onBlockPunch(PlayerInteractEvent.LeftClickBlock e) {
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
			}
		}
	}
	
	@SubscribeEvent
	public void onHarvestDrop(HarvestDropsEvent e) {
		if (e.getHarvester() == null) {
			return;
		} else if (e.getHarvester().getHeldItemMainhand() == null) {
			e.getDrops().clear();
			return;
		}
		
		Block b = e.getState().getBlock();
		Item item = e.getHarvester().getHeldItemMainhand().getItem();
		
		BlockHarvestInfo binfo = HarvestHandler.getHarvestInfo(b);
		if (binfo == null || HarvestHandler.getHarvestInfo(item) == null) {
			return;
		}
		HarvestDropInfo drop = binfo.getDrop(HarvestHandler.getItemsToolType(b, item));
		if (drop == null) {
			return;
		}
		
		Random r = new Random();
		boolean isSilk = e.isSilkTouching();
		int after_rdrop = MathHelper.clamp(r.nextInt(e.getFortuneLevel() + 1), 0, Integer.MAX_VALUE);
		
		if (drop.isReplace()) {
			e.getDrops().clear();
		}
		
		for (ItemDropInfo itemDrop : drop.getDrops()) {
			if (itemDrop.needsSilk() == isSilk) {
				float dropFort = (itemDrop.getDropFortune() * after_rdrop) + 1;
				float chanceFort = (itemDrop.getChanceFortune() * after_rdrop) + 1;
				
				int random = r.nextInt(100) + 1;
				
				if (random <= itemDrop.getDropChance() * chanceFort) {
					e.getDrops().add(new ItemStack(itemDrop.getItem(), (int) ((itemDrop.getDropAmount() + ThreadLocalRandom.current().nextInt(
							itemDrop.getRandomDropMin(), itemDrop.getRandomDropMax() + 1)) * dropFort)));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.HarvestDropsEvent e) {
		if (HarvestHandler.getHarvestInfo(e.getState().getBlock()).canBreakWithNone()) {
			return;
		} else if (e.getHarvester() == null) {
			e.setDropChance(0);
			return;
		}
		
		if (!HarvestHandler.canBreak(e.getState().getBlock(), e.getHarvester().getHeldItemMainhand().getItem())) {
			e.setDropChance(0);
		}
	}
	
	@SubscribeEvent
	public void onBreakSpeed(PlayerEvent.BreakSpeed e) {
		if (HarvestHandler.getHarvestInfo(e.getState().getBlock()).isUnbreakable()) {
			e.setNewSpeed(0);
			return;
		}
		
		float newSpeed = MathHelper.clamp(0.75f / e.getState().getBlockHardness(e.getEntityPlayer().world, e.getPos()), 0, 0.3f);
		if (HarvestHandler.canBreak(e.getState().getBlock(), e.getEntityPlayer().getHeldItemMainhand().getItem())) {
			ToolHarvestLevel harvest = HarvestHandler.getItemsHarvestLevel(e.getState().getBlock(), e.getEntityPlayer().getHeldItemMainhand().getItem());
			e.setNewSpeed((2 * harvest.speed) * newSpeed);
		} else {
			e.setNewSpeed(newSpeed);
		}
	}
}
