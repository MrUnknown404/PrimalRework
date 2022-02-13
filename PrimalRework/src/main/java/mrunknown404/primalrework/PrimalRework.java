package mrunknown404.primalrework;

import com.mojang.brigadier.CommandDispatcher;

import mrunknown404.primalrework.commands.CommandQuest;
import mrunknown404.primalrework.commands.CommandStage;
import mrunknown404.primalrework.utils.proxy.ClientProxy;
import mrunknown404.primalrework.utils.proxy.CommonProxy;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PrimalRework.MOD_ID)
public class PrimalRework {
	public static final String MOD_ID = "primalrework";
	
	public static CommonProxy proxy;
	
	public PrimalRework() {
		System.out.println("#-# Thank you for playing PrimalRework! #-#");
		
		proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
		proxy.preSetup(this);
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
	}
	
	private void commonSetup(@SuppressWarnings("unused") FMLCommonSetupEvent e) {
		proxy.setup();
	}
	
	@SubscribeEvent
	public void onRegisterCommandEvent(RegisterCommandsEvent e) {
		CommandDispatcher<CommandSource> commandDispatcher = e.getDispatcher();
		CommandStage.register(commandDispatcher);
		CommandQuest.register(commandDispatcher);
	}
}
