package mrunknown404.primalrework.utils.proxy;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.events.HarvestEvents;
import mrunknown404.primalrework.events.LeafEvents;
import mrunknown404.primalrework.events.MiscEvents;
import mrunknown404.primalrework.events.QuestEvents;
import mrunknown404.primalrework.network.NetworkHandler;
import mrunknown404.primalrework.network.packets.PacketOpenInventory;
import mrunknown404.primalrework.network.packets.PacketSyncStage;
import mrunknown404.primalrework.registries.PRFuels;
import mrunknown404.primalrework.registries.PRQuests;
import mrunknown404.primalrework.registries.PRRecipes;
import mrunknown404.primalrework.registries.PRRegistry;
import mrunknown404.primalrework.stage.StagedTag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkDirection;

public class CommonProxy {
	public void preSetup(PrimalRework main) {
		MinecraftForge.EVENT_BUS.register(new HarvestEvents());
		MinecraftForge.EVENT_BUS.register(new MiscEvents());
		MinecraftForge.EVENT_BUS.register(new LeafEvents());
		MinecraftForge.EVENT_BUS.register(new QuestEvents());
		MinecraftForge.EVENT_BUS.register(PRRegistry.class);
		MinecraftForge.EVENT_BUS.register(main);
		
		PRRegistry.register();
	}
	
	public void setup() {
		NetworkHandler.registerPacket(PacketSyncStage.class, NetworkDirection.PLAY_TO_CLIENT);
		NetworkHandler.registerPacket(PacketOpenInventory.class, NetworkDirection.PLAY_TO_SERVER);
		
		for (RegistryObject<StagedTag> tag : PRRegistry.getTags()) {
			tag.get().finish();
		}
		
		PRQuests.load();
		PRRecipes.load();
		PRFuels.load();
	}
}
