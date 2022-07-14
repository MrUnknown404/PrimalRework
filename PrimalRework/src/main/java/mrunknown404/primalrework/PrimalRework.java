package mrunknown404.primalrework;

import com.mojang.brigadier.CommandDispatcher;

import mrunknown404.primalrework.api.PrimalMod;
import mrunknown404.primalrework.api.network.NetworkH;
import mrunknown404.primalrework.api.registry.PRRegistries;
import mrunknown404.primalrework.client.InitClient;
import mrunknown404.primalrework.commands.CommandQuest;
import mrunknown404.primalrework.commands.CommandStage;
import mrunknown404.primalrework.events.HarvestEvents;
import mrunknown404.primalrework.events.LeafEvents;
import mrunknown404.primalrework.events.MiscEvents;
import mrunknown404.primalrework.events.QuestEvents;
import mrunknown404.primalrework.init.InitRegistry;
import mrunknown404.primalrework.network.packets.toclient.PSyncAllQuests;
import mrunknown404.primalrework.network.packets.toclient.PSyncPrimalCraftingTableOutput;
import mrunknown404.primalrework.network.packets.toclient.PSyncQuestState;
import mrunknown404.primalrework.network.packets.toclient.PSyncStage;
import mrunknown404.primalrework.network.packets.toserver.POpenInventory;
import mrunknown404.primalrework.network.packets.toserver.PQuestClaimRewards;
import mrunknown404.primalrework.utils.Logger;
import mrunknown404.primalrework.utils.PRConfig;
import net.minecraft.command.CommandSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;

/** If anyone is reading this looking for code examples, there's a lot of bad practices here. Don't do anything I do. You have been warned! */
@PrimalMod
@Mod(PrimalRework.MOD_ID)
public class PrimalRework {
	public static final String MOD_ID = "primalrework";
	public static final boolean IS_DEBUG = true;
	
	public static final NetworkH NETWORK = NetworkH.create(PrimalRework.MOD_ID, "main", "1", n -> {
		n.registerPacket(POpenInventory.class, NetworkDirection.PLAY_TO_SERVER);
		n.registerPacket(PQuestClaimRewards.class, NetworkDirection.PLAY_TO_SERVER);
		n.registerPacket(PSyncAllQuests.class, NetworkDirection.PLAY_TO_CLIENT);
		n.registerPacket(PSyncPrimalCraftingTableOutput.class, NetworkDirection.PLAY_TO_CLIENT);
		n.registerPacket(PSyncQuestState.class, NetworkDirection.PLAY_TO_CLIENT);
		n.registerPacket(PSyncStage.class, NetworkDirection.PLAY_TO_CLIENT);
	});
	
	//TODO setup registries or something for quests,recipes,fuels
	
	/* TODO list of stuff to do progression-wise
	 * after breaking log should work on getting a primal crafting table. which unlocks flint tools.
	 * 
	 */
	
	@SuppressWarnings("deprecation")
	public PrimalRework() {
		Logger.multiLine("#-# Thank you for playing PrimalRework! #-#");
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, PRConfig.CLIENT_SPEC);
		
		MinecraftForge.EVENT_BUS.register(new HarvestEvents());
		MinecraftForge.EVENT_BUS.register(new MiscEvents());
		MinecraftForge.EVENT_BUS.register(new LeafEvents());
		MinecraftForge.EVENT_BUS.register(new QuestEvents());
		MinecraftForge.EVENT_BUS.addListener(PRRegistries::biomeLoad);
		MinecraftForge.EVENT_BUS.addListener((RegisterCommandsEvent e) -> {
			CommandDispatcher<CommandSource> cmd = e.getDispatcher();
			CommandStage.register(cmd);
			CommandQuest.register(cmd);
		});
		
		InitRegistry.preSetup();
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> InitClient::preSetup);
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener((FMLConstructModEvent e) -> PRRegistries.setup());
		bus.addListener((FMLCommonSetupEvent e) -> InitRegistry.setup());
		bus.addListener((FMLClientSetupEvent e) -> InitClient.setup());
		bus.addListener((FMLLoadCompleteEvent e) -> Logger.multiLine("#-# Finished loading PrimalRework! #-#"));
	}
}
