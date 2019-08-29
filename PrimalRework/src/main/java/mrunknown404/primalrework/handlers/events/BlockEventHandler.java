package mrunknown404.primalrework.handlers.events;

import java.util.Random;

import mrunknown404.primalrework.handlers.HarvestHandler;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.ToolHarvestLevel;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockEventHandler {

	@SubscribeEvent
	public void onHarvestDrop(HarvestDropsEvent e) {
		Block b = e.getState().getBlock();
		Random r = new Random();
		boolean isSilk = e.isSilkTouching();
		int fortune = e.getFortuneLevel();
		int after_rdrop = MathHelper.clamp(r.nextInt(fortune + 1), 0, Integer.MAX_VALUE);
		
		if (b == Blocks.GRAVEL) {
			if (!isSilk) {
				e.getDrops().clear();
				e.getDrops().add(new ItemStack(ModItems.GRAVEL, (r.nextInt(4) + 1) + after_rdrop ));
			}
		} else if (b == Blocks.STONE) {
			if (!isSilk) {
				e.getDrops().clear();
				
				for (int i = 0; i < after_rdrop + 1; i++) {
					if (r.nextBoolean()) {
						e.getDrops().add(new ItemStack(Blocks.COBBLESTONE));
						break;
					}
				}
				
				if (e.getDrops().isEmpty()) {
					e.getDrops().add(new ItemStack(ModBlocks.ROCK, (r.nextInt(6) + 4) + (after_rdrop * 2)));
				}
			}
		} else if (b == Blocks.TALLGRASS) {
			if (HarvestHandler.canBreak(b, e.getHarvester().getHeldItemMainhand().getItem())) {
				e.getDrops().add(new ItemStack(ModItems.PLANT_FIBER));
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.HarvestDropsEvent e) {
		if (e.getHarvester() == null || !(e.getHarvester() instanceof EntityPlayer)) {
			e.setDropChance(0);
			return;
		}
		
		if (!HarvestHandler.canBreak(e.getState().getBlock(), e.getHarvester().getHeldItemMainhand().getItem())) {
			e.setDropChance(0);
		}
	}
	
	@SubscribeEvent
	public void onBreakSpeed(PlayerEvent.BreakSpeed e) {
		if (HarvestHandler.canBreak(e.getState().getBlock(), e.getEntityPlayer().getHeldItemMainhand().getItem())) {
			ToolHarvestLevel harvest = HarvestHandler.getCurrentItemHarvestLevel(e.getState().getBlock(), e.getEntityPlayer().getHeldItemMainhand().getItem());
			e.setNewSpeed(e.getOriginalSpeed() * harvest.speed);
		} else {
			e.setNewSpeed(0.75f / e.getState().getBlockHardness(e.getEntityPlayer().world, e.getPos()));
		}
	}
}
