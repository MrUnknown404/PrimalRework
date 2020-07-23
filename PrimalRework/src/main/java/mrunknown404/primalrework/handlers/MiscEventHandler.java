package mrunknown404.primalrework.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.client.gui.GuiCreatePrimalWorld;
import mrunknown404.primalrework.client.gui.GuiHandler;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.StageHelper;
import mrunknown404.primalrework.util.proxy.ClientProxy;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

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
			
			try {
				Field f = ReflectionHelper.findField(AdvancementManager.class, "ADVANCEMENT_LIST", "field_192784_c");
				f.setAccessible(true);
				
				Field mf = Field.class.getDeclaredField("modifiers");
				mf.setAccessible(true);
				mf.setInt(f, f.getModifiers() & ~Modifier.FINAL);
				
				f.set(null, Main.ADV_LIST);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
			
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
		EnumStage stage = StageHelper.getItemStage(e.getItemStack());
		if (stage == null) {
			e.getToolTip().add("Error! Broken Staged Item!");
			return;
		}
		
		e.getToolTip().add("Stage: " + stage.getName());
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) { //TODO MOVE!
		if (e.phase == Phase.END || e.side == Side.SERVER) {
			return;
		}
		
		if (ClientProxy.KEY_OPEN_QUESTS.isPressed()) {
			e.player.openGui(Main.main, GuiHandler.GuiID.QUESTS.toID(), e.player.world, e.player.getPosition().getX(), e.player.getPosition().getY(),
					e.player.getPosition().getZ());
		}
	}
}
