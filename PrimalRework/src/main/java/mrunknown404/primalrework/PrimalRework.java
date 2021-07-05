package mrunknown404.primalrework;

import com.mojang.brigadier.CommandDispatcher;

import mrunknown404.primalrework.commands.CommandStage;
import mrunknown404.primalrework.utils.proxy.ClientProxy;
import mrunknown404.primalrework.utils.proxy.CommonProxy;
import net.minecraft.command.CommandSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PrimalRework.MOD_ID)
public class PrimalRework {
	public static final String MOD_ID = "primalrework";
	
	public static final ResourceLocation STAGE_DATA = new ResourceLocation(MOD_ID, "stage_data");
	
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
	}
	
	/*
	 * --high
	 * TODO remove recipe book from inventory
	 * TODO setup new crafting in vanilla inventory
	 * TODO remove advancements button from pause screen
	 * TODO setup config (see --low)
	 * 
	 * --low
	 * TODO add config for tooltips
	 * TODO make harvest display position configurable
	 * TODO render item in campfire
	 * TODO right click crop harvesting (require hoe)
	 * TODO change stack sizes of everything
	 * 
	 * --a bit later
	 * TODO disable F3 screen (add fps display option)
	 * TODO items for figuring out F3 data
	 * TODO when i add the clay pot fix the texture
	 * TODO make scroll wheel move items
	 * TODO remove wool/leather drop (maybe setup something similar to HarvestInfo but for mobs?)
	 * TODO disable xp? remove xp bar?
	 * TODO redo farmland
	 * 
	 * --way later
	 * TODO add armor & edit vanilla armor
	 * TODO add worldgen (custom forced worldtype with custom biome system)
	 * TODO add realistic item entity
	 * TODO map system
	 * TODO add quest command (check/set)
	 * TODO when metal casting is added add fancy tools with xp and shit
	 */
}
