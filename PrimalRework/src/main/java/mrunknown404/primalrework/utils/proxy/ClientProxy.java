package mrunknown404.primalrework.utils.proxy;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.client.CraftingDisplayH;
import mrunknown404.primalrework.client.gui.GuiNoToast;
import mrunknown404.primalrework.client.gui.screen.container.ScreenCampFire;
import mrunknown404.primalrework.client.gui.screen.container.ScreenPrimalCraftingTable;
import mrunknown404.primalrework.events.client.CraftingDisplayCEvents;
import mrunknown404.primalrework.events.client.HarvestDisplayCEvents;
import mrunknown404.primalrework.events.client.QuestCEvents;
import mrunknown404.primalrework.events.client.TooltipCEvents;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitContainers;
import mrunknown404.primalrework.items.utils.SIBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy {
	public static final KeyBinding OPEN_QUESTS = new KeyBinding("key.quest", InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "key.categories.primalrework");
	private static final KeyBinding EMPTY = new KeyBinding("empty", InputMappings.UNKNOWN.getValue(), "empty");
	
	@Override
	public void preSetup(PrimalRework main) {
		super.preSetup(main);
		
		MinecraftForge.EVENT_BUS.register(new TooltipCEvents());
		MinecraftForge.EVENT_BUS.register(new QuestCEvents());
		MinecraftForge.EVENT_BUS.register(new HarvestDisplayCEvents());
		MinecraftForge.EVENT_BUS.register(new CraftingDisplayCEvents());
	}
	
	@Override
	public void setup() {
		super.setup();
		
		Minecraft mc = Minecraft.getInstance();
		ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, new GuiNoToast(mc), "toast");
		
		int index = -1;
		for (int i = 0; i < mc.options.keyMappings.length; i++) {
			if (mc.options.keyMappings[i] == mc.options.keyAdvancements) {
				index = i;
				break;
			}
		}
		
		ObfuscationReflectionHelper.setPrivateValue(GameSettings.class, mc.options, EMPTY, "keyAdvancements");
		mc.options.keyMappings = ArrayUtils.remove(mc.options.keyMappings, index);
		
		ClientRegistry.registerKeyBinding(OPEN_QUESTS);
		
		RenderTypeLookup.setRenderLayer(InitBlocks.MUSHROOM_GRASS.get(), RenderType.cutoutMipped()); //TODO automate this
		RenderTypeLookup.setRenderLayer(InitBlocks.CAMPFIRE.get(), RenderType.cutoutMipped());
		
		ScreenManager.register(InitContainers.CAMPFIRE.get(), ScreenCampFire::new); //TODO automate this
		ScreenManager.register(InitContainers.PRIMAL_CRAFTING_TABLE.get(), ScreenPrimalCraftingTable::new);
		
		mc.getBlockColors().register((state, reader, pos, tintIndex) -> {
			return reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColors.get(0.5, 1);
		}, InitBlocks.MUSHROOM_GRASS.get());
		mc.getItemColors().register((itemstack, tintIndex) -> {
			BlockState blockstate = ((SIBlock) itemstack.getItem()).getBlock().defaultBlockState();
			return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, tintIndex);
		}, InitBlocks.MUSHROOM_GRASS.get());
		
		for (Item item : GameRegistry.findRegistry(Item.class)) {
			CraftingDisplayH.addItem(item);
		}
		CraftingDisplayH.finish();
	}
}
