package mrunknown404.primalrework.handlers;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo;
import mrunknown404.primalrework.util.harvest.HarvestDropInfo.ItemDropInfo;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockEventHandler {
	
	@SubscribeEvent
	public void onHarvestDrop(HarvestDropsEvent e) {
		e.setDropChance(100);
		if (e.getHarvester() != null && !HarvestHelper.canBreak(e.getState().getBlock(), e.getHarvester().getHeldItemMainhand().getItem())) {
			e.getDrops().clear();
			return;
		}
		
		BlockHarvestInfo binfo = HarvestHelper.getHarvestInfo(e.getState().getBlock());
		if (binfo == null) {
			return;
		}
		
		HarvestDropInfo drop = binfo.getDrop(e.getHarvester() != null ?
				HarvestHelper.getItemsToolType(e.getState().getBlock(), e.getHarvester().getHeldItemMainhand().getItem()) : EnumToolType.none);
		if (drop == null) {
			return;
		}
		
		Random r = new Random();
		boolean isSilk = e.isSilkTouching();
		
		if (drop.isReplace()) {
			e.getDrops().clear();
		}
		
		for (ItemDropInfo itemDrop : drop.getDrops()) {
			if (itemDrop.needsSilk == isSilk) {
				int fort = itemDrop.usesFortune ? r.nextInt(e.getFortuneLevel() + 1) : 1;
				
				if (r.nextInt(100) + 1 <= itemDrop.dropChance * (itemDrop.chanceFortune + 1)) {
					int amount = itemDrop.dropAmount + (itemDrop.usesFortune ? fort : 0) + ThreadLocalRandom.current().nextInt(itemDrop.randomDropMin, itemDrop.randomDropMax + 1);
					
					e.getDrops().add(new ItemStack(itemDrop.item, amount, itemDrop.meta));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent e) {
		EntityPlayer p = e.getPlayer();
		if (p.isCreative() && !p.getActiveItemStack().isEmpty()) {
			if (HarvestHelper.hasToolType(p.getActiveItemStack().getItem(), EnumToolType.sword)) {
				e.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onBreakSpeed(PlayerEvent.BreakSpeed e) {
		if (HarvestHelper.hasToolMaterial(e.getState().getBlock(), EnumToolMaterial.unbreakable)) {
			e.setNewSpeed(0);
			return;
		}
		
		ItemStack item = e.getEntityPlayer().getHeldItemMainhand();
		float newSpeed = MathHelper.clamp(0.75f / e.getState().getBlockHardness(e.getEntityPlayer().world, e.getPos()), 0, 0.3f);
		float eff = 1;
		
		if (item != null && item.isItemEnchanted()) {
			Map<Enchantment, Integer> it = EnchantmentHelper.getEnchantments(item);
			if (it.containsKey(Enchantments.EFFICIENCY)) {
				eff = it.get(Enchantments.EFFICIENCY) * 0.25f + 1;
			}
		}
		
		e.setNewSpeed(HarvestHelper.getItemsHarvestSpeed(e.getState().getBlock(), item.getItem()) * newSpeed * eff);
	}
}
