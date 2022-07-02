package mrunknown404.primalrework.utils;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.events.HarvestEvents;
import mrunknown404.primalrework.events.LeafEvents;
import mrunknown404.primalrework.events.MiscEvents;
import mrunknown404.primalrework.events.QuestEvents;
import mrunknown404.primalrework.init.InitPRFuels;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.init.InitRecipes;
import mrunknown404.primalrework.init.InitRegistry;
import mrunknown404.primalrework.network.NetworkHandler;
import mrunknown404.primalrework.network.packets.POpenInventory;
import mrunknown404.primalrework.network.packets.PSyncStage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkDirection;

public class Proxy {
	public void preSetup(PrimalRework main) {
		MinecraftForge.EVENT_BUS.register(new HarvestEvents());
		MinecraftForge.EVENT_BUS.register(new MiscEvents());
		MinecraftForge.EVENT_BUS.register(new LeafEvents());
		MinecraftForge.EVENT_BUS.register(new QuestEvents());
		MinecraftForge.EVENT_BUS.register(InitRegistry.class);
		MinecraftForge.EVENT_BUS.register(main);
		
		InitRegistry.register();
	}
	
	public void setup() {
		NetworkHandler.registerPacket(PSyncStage.class, NetworkDirection.PLAY_TO_CLIENT);
		NetworkHandler.registerPacket(POpenInventory.class, NetworkDirection.PLAY_TO_SERVER);
		
		InitQuests.load();
		InitRecipes.load();
		InitPRFuels.load();
	}
}
