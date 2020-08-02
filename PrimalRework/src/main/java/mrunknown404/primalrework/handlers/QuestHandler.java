package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.client.gui.GuiHandler;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestRequirement.QuestReq;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.StageHelper;
import mrunknown404.primalrework.util.proxy.ClientProxy;
import mrunknown404.primalrework.world.storage.WorldSaveDataQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class QuestHandler {
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		if (e.phase == Phase.END || e.side == Side.SERVER) {
			return;
		}
		
		if (ClientProxy.KEY_OPEN_QUESTS.isPressed()) {
			e.player.openGui(Main.main, GuiHandler.GuiID.QUESTS.toID(), e.player.world, e.player.getPosition().getX(), e.player.getPosition().getY(),
					e.player.getPosition().getZ());
		}
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
		if (!e.getWorld().isRemote) {
			WorldSaveDataQuest.load(e.getWorld());
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BreakEvent e) {
		for (Quest q : InitQuests.getQuestCache()) {
			if (q.getRequirement().getQuestReq() == QuestReq.block_break) {
				if (e.getWorld().getBlockState(e.getPos()).getBlock() == q.getRequirement().getBlockToBreak()) {
					q.checkRequirements(e.getWorld(), e.getPlayer(), 1);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void checkRoots(WorldTickEvent e) {
		for (EnumStage stage : StageHelper.getAllPrevStages()) {
			if (InitQuests.QUEST_TABS.get(stage) != null) {
				if (!InitQuests.QUEST_TABS.get(stage).getRoot().isFinished()) {
					InitQuests.QUEST_TABS.get(stage).getRoot().finishQuest(e.world, null);
				}
			}
		}
		
		for (Quest q : InitQuests.getQuestCache()) {
			if (q.getRequirement().getQuestReq() == QuestReq.item_collect) {
				int amount = 0, highest = 0;
				EntityPlayer highestPlayer = null;
				
				itemCheck:
				for (EntityPlayer pl : e.world.playerEntities) {
					for (ItemStack item : pl.inventory.mainInventory) {
						if (item.getItem() == q.getRequirement().getItemToCollect()) {
							amount += item.getCount();
							
							if (item.getCount() > highest) {
								highest = item.getCount();
								highestPlayer = pl;
							}
							
							if (amount >= q.getRequirement().getAmountNeeded()) {
								break itemCheck;
							}
						}
					}
				}
				
				if (highestPlayer != null) {
					q.checkRequirements(e.world, highestPlayer, amount);
				}
			}
		}
	}
}
