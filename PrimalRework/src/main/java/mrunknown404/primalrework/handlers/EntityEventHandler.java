package mrunknown404.primalrework.handlers;

import java.util.Random;

import mrunknown404.primalrework.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler {
	@SubscribeEvent
	public void onEntityDrop(LivingDropsEvent e) {
		if (e.getEntity() instanceof EntityCow || e.getEntity() instanceof EntityHorse) {
			for (EntityItem drop : e.getDrops()) {
				if (drop.getItem().getItem() == Items.LEATHER) {
					e.getDrops().remove(drop);
				}
			}
		}
		
		if (e.getEntity() instanceof EntityAnimal) {
			Random r = new Random();
			e.getDrops().add(new EntityItem(e.getEntity().world, e.getEntity().posX, e.getEntity().posY, e.getEntity().posZ,
					new ItemStack(ModItems.ANIMAL_PELT, r.nextInt(1) + (e.getLootingLevel() != 0 ? r.nextInt(e.getLootingLevel()) : 0))));
		}
	}
}
