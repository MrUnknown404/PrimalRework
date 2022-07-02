package mrunknown404.primalrework;

import com.mojang.brigadier.CommandDispatcher;

import mrunknown404.primalrework.commands.CommandQuest;
import mrunknown404.primalrework.commands.CommandStage;
import mrunknown404.primalrework.utils.PRConfig;
import mrunknown404.primalrework.utils.PrimalMod;
import mrunknown404.primalrework.utils.Proxy;
import mrunknown404.primalrework.utils.ProxyClient;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@PrimalMod
@Mod(PrimalRework.MOD_ID)
public class PrimalRework {
	public static final String MOD_ID = "primalrework";
	
	public static Proxy proxy;
	
	public PrimalRework() {
		System.out.println("#-# Thank you for playing PrimalRework! #-#");
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, PRConfig.CLIENT_SPEC);
		
		proxy = DistExecutor.safeRunForDist(() -> ProxyClient::new, () -> Proxy::new);
		proxy.preSetup(this);
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent e) -> proxy.setup());
		FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLLoadCompleteEvent e) -> System.err.println("#-# Finished loading PrimalRework! #-#"));
	}
	
	@SubscribeEvent
	public void onRegisterCommandEvent(RegisterCommandsEvent e) {
		CommandDispatcher<CommandSource> commandDispatcher = e.getDispatcher();
		CommandStage.register(commandDispatcher);
		CommandQuest.register(commandDispatcher);
	}
}
