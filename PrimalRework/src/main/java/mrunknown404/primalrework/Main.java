package mrunknown404.primalrework;

import mrunknown404.primalrework.client.gui.GuiHandler;
import mrunknown404.primalrework.commands.CommandStage;
import mrunknown404.primalrework.handlers.BlockEventHandler;
import mrunknown404.primalrework.handlers.PlayerEventHandler;
import mrunknown404.primalrework.handlers.WorldEventHandler;
import mrunknown404.primalrework.network.FireStarterMessage;
import mrunknown404.primalrework.network.FireStarterPacketHandler;
import mrunknown404.primalrework.network.PrimalEnchantingMessage;
import mrunknown404.primalrework.network.PrimalEnchantingPacketHandler;
import mrunknown404.primalrework.util.OreDict;
import mrunknown404.primalrework.util.VanillaOverrides;
import mrunknown404.primalrework.util.proxy.CommonProxy;
import mrunknown404.primalrework.world.WorldGen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Main.MOD_ID, useMetadata = true)
public class Main {
	
	public static final String MOD_ID = "primalrework";
	public static final int GUI_ID_FIRE_PIT = 1;
	public static final int GUI_ID_ENCHANTING = 2;
	public static final int GUI_ID_PRIMAL_ENCHANTING = 3;
	
	public static SimpleNetworkWrapper networkWrapper;
	
	@Instance
	public static Main main;
	
	@SidedProxy(clientSide = "mrunknown404.primalrework.util.proxy.ClientProxy", serverSide = "mrunknown404.primalrework.util.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.registerEntities();
		proxy.registerRenders();
		
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
		MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(main, new GuiHandler());
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
		networkWrapper.registerMessage(FireStarterPacketHandler.class, FireStarterMessage.class, 0, Side.SERVER);
		networkWrapper.registerMessage(PrimalEnchantingPacketHandler.class, PrimalEnchantingMessage.class, 1, Side.SERVER);
		
		proxy.registerSounds();
		proxy.setupRecipes();
		
		VanillaOverrides.overrideAll();
		OreDict.register();
		
		GameRegistry.registerWorldGenerator(new WorldGen(), 0);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandStage());
	}
}
