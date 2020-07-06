package mrunknown404.primalrework.handlers;

import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityRightClickHandler {
	@SubscribeEvent
	public void milkCow(EntityInteract e) {
		if (e.getTarget() instanceof EntityCow && e.getItemStack().getItem() == ModItems.CLAY_BUCKET_EMPTY) {
			e.getEntityPlayer().setHeldItem(e.getHand(), new ItemStack(ModItems.CLAY_BUCKET_MILK, 1, e.getItemStack().getItemDamage()));
		}
	}
	
	@SubscribeEvent
	public void shearSheep(EntityInteract e) {
		if (e.getTarget() instanceof EntitySheep && HarvestHelper.hasToolType(e.getItemStack().getItem(), EnumToolType.shears)) {
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
