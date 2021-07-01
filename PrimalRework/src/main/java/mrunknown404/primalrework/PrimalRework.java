package mrunknown404.primalrework;

import com.mojang.brigadier.CommandDispatcher;

import mrunknown404.primalrework.client.gui.GuiNoToast;
import mrunknown404.primalrework.commands.CommandStage;
import mrunknown404.primalrework.events.HarvestEvents;
import mrunknown404.primalrework.events.LeafEvents;
import mrunknown404.primalrework.events.MiscEvents;
import mrunknown404.primalrework.events.QuestEvents;
import mrunknown404.primalrework.events.client.CraftingDisplayCEvents;
import mrunknown404.primalrework.events.client.HarvestDisplayCEvents;
import mrunknown404.primalrework.events.client.QuestCEvents;
import mrunknown404.primalrework.events.client.TooltipCEvents;
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
import mrunknown404.primalrework.utils.proxy.ClientProxy;
import mrunknown404.primalrework.utils.proxy.ICommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;

@Mod(PrimalRework.MOD_ID)
public class PrimalRework {
	public static final String MOD_ID = "primalrework";
	
	public static final ResourceLocation STAGE_DATA = new ResourceLocation(MOD_ID, "stage_data");
	
	public static ICommonProxy proxy; //TODO remove this? (proxy system)
	
	public PrimalRework() {
		System.out.println("#-# Thank you for playing PrimalRework! #-#");
		Registry.register();
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		
		MinecraftForge.EVENT_BUS.register(new HarvestEvents());
		MinecraftForge.EVENT_BUS.register(new MiscEvents());
		MinecraftForge.EVENT_BUS.register(new QuestEvents());
		MinecraftForge.EVENT_BUS.register(new LeafEvents());
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent e) {
		proxy = new ClientProxy();
		proxy.setup();
		
		ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getInstance(), new GuiNoToast(Minecraft.getInstance()), "toast");
		
		MinecraftForge.EVENT_BUS.register(new TooltipCEvents());
		MinecraftForge.EVENT_BUS.register(new QuestCEvents());
		MinecraftForge.EVENT_BUS.register(new HarvestDisplayCEvents());
		MinecraftForge.EVENT_BUS.register(new CraftingDisplayCEvents());
	}
	
	private void commonSetup(@SuppressWarnings("unused") FMLCommonSetupEvent e) {
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
	
	@SubscribeEvent
	public void onRegisterCommandEvent(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
		CommandStage.register(commandDispatcher);
	}
	
	/*
	 * --high
	 * TODO remove recipe book from inventory
	 * TODO setup new crafting in vanilla inventory
	 * TODO remove advancements button from pause screen
	 * TODO setup config (see --low)
	 * 
	 * --low
	 * TODO disable xp? remove xp bar?
	 * TODO add armor & edit vanilla armor
	 * TODO redo farmland
	 * TODO remove wool/leather drop (maybe setup something similar to HarvestInfo but for mobs?)
	 * TODO add worldgen (custom forced worldtype with custom biome system)
	 * TODO add realistic item entity
	 * TODO add config for tooltips
	 * TODO make scroll wheel move items
	 * TODO map system
	 * TODO add quest command (check/set)
	 * TODO make harvest display position configurable
	 * TODO when i add the clay pot fix the texture
	 * TODO render item in campfire
	 * TODO right click crop harvesting (require hoe)
	 * TODO when metal casting is added add fancy tools with xp and shit
	 * TODO change stack sizes of everything
	 * TODO disable F3 screen (add fps display option)
	 * TODO items for figuring out F3 data
	 */
}
