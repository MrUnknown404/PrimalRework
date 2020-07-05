package mrunknown404.primalrework;

import mrunknown404.primalrework.client.gui.GuiHandler;
import mrunknown404.primalrework.commands.CommandStage;
import mrunknown404.primalrework.entity.EntityItemDrop;
import mrunknown404.primalrework.handlers.BlockEventHandler;
import mrunknown404.primalrework.handlers.EntityEventHandler;
import mrunknown404.primalrework.handlers.GuiEventHandler;
import mrunknown404.primalrework.handlers.ItemEntityHandler;
import mrunknown404.primalrework.handlers.PlayerEventHandler;
import mrunknown404.primalrework.handlers.WorldEventHandler;
import mrunknown404.primalrework.init.ModEntities;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.init.ModSoundEvents;
import mrunknown404.primalrework.network.message.FireStarterMessage;
import mrunknown404.primalrework.network.message.PrimalEnchantingMessage;
import mrunknown404.primalrework.network.message.RecipeTransferMessage;
import mrunknown404.primalrework.tileentity.TileEntityCharcoalKiln;
import mrunknown404.primalrework.tileentity.TileEntityCharcoalPitMaster;
import mrunknown404.primalrework.tileentity.TileEntityCraftingStump;
import mrunknown404.primalrework.tileentity.TileEntityDryingTable;
import mrunknown404.primalrework.tileentity.TileEntityFirePit;
import mrunknown404.primalrework.tileentity.TileEntityLoom;
import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import mrunknown404.primalrework.util.OreDict;
import mrunknown404.primalrework.util.VanillaOverrides;
import mrunknown404.primalrework.world.WorldGen;
import mrunknown404.primalrework.world.WorldTypePrimal;
import mrunknown404.unknownlibs.entity.EntityRegisterHelper;
import mrunknown404.unknownlibs.utils.ICommonProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.WorldType;
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
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Main.MOD_ID, useMetadata = true, dependencies = "required-after:unknownlibs@[1.0.4,)")
public class Main {
	
	public static final String MOD_ID = "primalrework";
	public static final int GUI_ID_FIRE_PIT = 1;
	public static final int GUI_ID_ENCHANTING = 2;
	public static final int GUI_ID_PRIMAL_ENCHANTING = 3;
	public static final int GUI_ID_CHARCOAL_KILN = 4;
	
	public static final WorldType PRIMAL_WORLD = new WorldTypePrimal();
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
	
	@Instance
	public static Main main;
	
	@SidedProxy(clientSide = "mrunknown404.primalrework.util.proxy.ClientProxy", serverSide = "mrunknown404.primalrework.util.proxy.ServerProxy")
	public static ICommonProxy proxy;
	
	// TODO add map system similar to antique atlas
	// TODO think of a metal working system
	// TODO create biome decorator
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit();
		
		EntityRegisterHelper.registerEntities(ModEntities.ENTITIES, Main.main, Main.MOD_ID);
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID, "item_drop"), EntityItemDrop.class, "item_drop", 0, Main.main, 64, 1, true);
		
		GameRegistry.registerTileEntity(TileEntityFirePit.class, new ResourceLocation(Main.MOD_ID, "fire_pit"));
		GameRegistry.registerTileEntity(TileEntityCraftingStump.class, new ResourceLocation(Main.MOD_ID, "crafting_stump"));
		GameRegistry.registerTileEntity(TileEntityDryingTable.class, new ResourceLocation(Main.MOD_ID, "drying_table"));
		GameRegistry.registerTileEntity(TileEntityPrimalEnchanting.class, new ResourceLocation(Main.MOD_ID, "primal_enchanting_table"));
		GameRegistry.registerTileEntity(TileEntityLoom.class, new ResourceLocation(Main.MOD_ID, "loom"));
		GameRegistry.registerTileEntity(TileEntityCharcoalKiln.class, new ResourceLocation(Main.MOD_ID, "charcoal_kiln"));
		GameRegistry.registerTileEntity(TileEntityCharcoalPitMaster.class, new ResourceLocation(Main.MOD_ID, "charcoal_pit_master"));
		
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
		MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
		MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
		MinecraftForge.EVENT_BUS.register(new GuiEventHandler());
		MinecraftForge.EVENT_BUS.register(new ItemEntityHandler());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(main, new GuiHandler());
		NETWORK.registerMessage(FireStarterMessage.class, FireStarterMessage.class, 0, Side.SERVER);
		NETWORK.registerMessage(PrimalEnchantingMessage.class, PrimalEnchantingMessage.class, 1, Side.SERVER);
		NETWORK.registerMessage(RecipeTransferMessage.class, RecipeTransferMessage.class, 2, Side.SERVER);
		
		ForgeRegistries.SOUND_EVENTS.registerAll(ModSoundEvents.SOUNDS.toArray(new SoundEvent[0]));
		
		ModRecipes.removeRecipes();
		ModRecipes.addRecipes();
		
		VanillaOverrides.overrideAll();
		OreDict.register();
		
		GameRegistry.registerWorldGenerator(new WorldGen(), 0);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit();
		
		WorldType.WORLD_TYPES = new WorldType[16];
		WorldType.WORLD_TYPES[0] = PRIMAL_WORLD;
		WorldType.WORLD_TYPES[1] = WorldType.FLAT;
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandStage());
	}
}
