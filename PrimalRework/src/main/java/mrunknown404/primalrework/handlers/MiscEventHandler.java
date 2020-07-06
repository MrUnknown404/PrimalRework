package mrunknown404.primalrework.handlers;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import mrunknown404.primalrework.client.gui.GuiCreatePrimalWorld;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.StageHelper;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MiscEventHandler {
	@SubscribeEvent
	public void onTrample(FarmlandTrampleEvent e) {
		e.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onBonemeal(BonemealEvent e) {
		e.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
		if (!e.getWorld().isRemote) {
			StageHelper.setWorld(e.getWorld());
		}
	}
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e) {
		if (e.getGui() instanceof GuiCreateWorld) {
			e.setGui(new GuiCreatePrimalWorld(new GuiMainMenu()));
		}
	}
	
	@SubscribeEvent
	public void addStageTooltips(ItemTooltipEvent e) {
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
}
