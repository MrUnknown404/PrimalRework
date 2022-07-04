package mrunknown404.primalrework.events.client;

import mrunknown404.primalrework.utils.helpers.ItemH;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TooltipEvents {
	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent e) {
		ItemStack stack = e.getItemStack();
		e.getToolTip().clear();
		e.getToolTip().addAll(ItemH.getTooltips(stack));
	}
}
