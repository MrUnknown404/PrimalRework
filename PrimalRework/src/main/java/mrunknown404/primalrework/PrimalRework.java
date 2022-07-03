package mrunknown404.primalrework;

import com.mojang.brigadier.CommandDispatcher;

import mrunknown404.primalrework.api.PrimalMod;
import mrunknown404.primalrework.api.network.NetworkH;
import mrunknown404.primalrework.commands.CommandQuest;
import mrunknown404.primalrework.commands.CommandStage;
import mrunknown404.primalrework.network.packets.POpenInventory;
import mrunknown404.primalrework.network.packets.PSyncStage;
import mrunknown404.primalrework.utils.PRConfig;
import mrunknown404.primalrework.utils.Proxy;
import mrunknown404.primalrework.utils.ProxyClient;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;

/** If anyone is reading this looking for code examples, there's a lot of bad practices here. Don't do anything I do. You have been warned! */
@PrimalMod
@Mod(PrimalRework.MOD_ID)
public class PrimalRework {
	public static final String MOD_ID = "primalrework";
	
	private static final String OUTPUT_DIVIDER = "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=";
	private static Proxy proxy;
	
	public static final NetworkH NETWORK = NetworkH.create(PrimalRework.MOD_ID, "main", "1", (n) -> {
		n.registerPacket(PSyncStage.class, NetworkDirection.PLAY_TO_CLIENT);
		n.registerPacket(POpenInventory.class, NetworkDirection.PLAY_TO_SERVER);
	});
	
	public PrimalRework() {
		printDivider();
		System.out.println("#-# Thank you for playing PrimalRework! #-#");
		printDivider();
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, PRConfig.CLIENT_SPEC);
		
		proxy = DistExecutor.safeRunForDist(() -> ProxyClient::new, () -> Proxy::new);
		proxy.preSetup(this);
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener((FMLCommonSetupEvent e) -> proxy.setup());
		bus.addListener((FMLLoadCompleteEvent e) -> System.err.println("#-# Finished loading PrimalRework! #-#"));
	}
	
	@SubscribeEvent
	public void onRegisterCommandEvent(RegisterCommandsEvent e) {
		CommandDispatcher<CommandSource> commandDispatcher = e.getDispatcher();
		CommandStage.register(commandDispatcher);
		CommandQuest.register(commandDispatcher);
	}
	
	public static void printDivider() {
		System.out.println(OUTPUT_DIVIDER);
	}
}
