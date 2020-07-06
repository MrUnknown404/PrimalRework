package mrunknown404.primalrework.handlers;

import java.util.Map;

import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockBreakingHandler {
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
