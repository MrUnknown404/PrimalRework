package mrunknown404.primalrework.utils.proxy;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.events.HarvestEvents;
import mrunknown404.primalrework.events.LeafEvents;
import mrunknown404.primalrework.events.MiscEvents;
import mrunknown404.primalrework.events.QuestEvents;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.init.InitRecipes;
import mrunknown404.primalrework.init.InitStagedTags;
import mrunknown404.primalrework.init.Registry;
import mrunknown404.primalrework.network.NetworkHandler;
import mrunknown404.primalrework.network.packets.PacketAllQuests;
import mrunknown404.primalrework.network.packets.PacketRequestQuests;
import mrunknown404.primalrework.network.packets.PacketSyncQuestFinished;
import mrunknown404.primalrework.network.packets.PacketSyncStage;
import mrunknown404.primalrework.stage.VanillaRegistry;
import mrunknown404.primalrework.stage.storage.IStageData;
import mrunknown404.primalrework.stage.storage.StageDataFactory;
import mrunknown404.primalrework.stage.storage.StageDataStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.network.NetworkDirection;

public class CommonProxy {
	public void preSetup(PrimalRework main) {
		Registry.register();
		
		MinecraftForge.EVENT_BUS.register(new HarvestEvents());
		MinecraftForge.EVENT_BUS.register(new MiscEvents());
		MinecraftForge.EVENT_BUS.register(new QuestEvents());
		MinecraftForge.EVENT_BUS.register(new LeafEvents());
		MinecraftForge.EVENT_BUS.register(main);
	}
	
	public void setup() {
		VanillaRegistry.override();
		
		CapabilityManager.INSTANCE.register(IStageData.class, new StageDataStorage(), StageDataFactory::new);
		
		InitStagedTags.load();
		InitQuests.load();
		InitRecipes.load();
		
		NetworkHandler.registerPacket(PacketSyncStage.class, NetworkDirection.PLAY_TO_CLIENT);
		NetworkHandler.registerPacket(PacketSyncQuestFinished.class, NetworkDirection.PLAY_TO_CLIENT);
		NetworkHandler.registerPacket(PacketRequestQuests.class, NetworkDirection.PLAY_TO_SERVER);
		NetworkHandler.registerPacket(PacketAllQuests.class, NetworkDirection.PLAY_TO_CLIENT);
	}
}
