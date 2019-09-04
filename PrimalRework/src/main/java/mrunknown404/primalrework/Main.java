package mrunknown404.primalrework;

import mrunknown404.primalrework.commands.CommandStage;
import mrunknown404.primalrework.handlers.GuiHandler;
import mrunknown404.primalrework.handlers.HarvestHandler;
import mrunknown404.primalrework.handlers.RegistryHandler;
import mrunknown404.primalrework.handlers.events.BlockEventHandler;
import mrunknown404.primalrework.handlers.events.PlayerEventHandler;
import mrunknown404.primalrework.handlers.events.WorldEventHandler;
import mrunknown404.primalrework.proxy.CommonProxy;
import mrunknown404.primalrework.tileentity.TileEntityFirePit;
import net.minecraft.util.ResourceLocation;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Main.MOD_ID, useMetadata = true)
public class Main {
	
	public static final String MOD_ID = "primalrework";
	public static final int GUI_ID_FIRE_PIT = 1;
	
	@Instance
	public static Main main;
	
	@SidedProxy(clientSide = "mrunknown404.primalrework.proxy.ClientProxy", serverSide = "mrunknown404.primalrework.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		RegistryHandler.registerEntities();
		proxy.registerEntityRenders();
		
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
		MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(main, new GuiHandler());
		
		proxy.registerSounds();
		proxy.setupRecipes();
		
		GameRegistry.registerTileEntity(TileEntityFirePit.class, new ResourceLocation(Main.MOD_ID, "fire_pit"));
		
		HarvestHandler.changeHarvestLevels();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandStage());
	}
}
