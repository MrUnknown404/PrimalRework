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
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockEventHandler {

	@SubscribeEvent
	public void onHarvestDrop(HarvestDropsEvent e) {
		if (e.getHarvester() == null || !HarvestHelper.canBreak(e.getState().getBlock(), e.getHarvester().getHeldItemMainhand().getItem())) {
			e.getDrops().clear();
			return;
		}
		
		Block b = e.getState().getBlock();
		Item item = e.getHarvester().getHeldItemMainhand().getItem();
		
		BlockHarvestInfo binfo = HarvestHelper.getHarvestInfo(b);
		if (binfo == null || HarvestHelper.getHarvestInfo(item) == null) {
			return;
		}
		
		HarvestDropInfo drop = binfo.getDrop(HarvestHelper.getItemsToolType(b, item));
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
