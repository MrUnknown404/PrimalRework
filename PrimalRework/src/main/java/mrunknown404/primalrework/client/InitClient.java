package mrunknown404.primalrework.client;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import mrunknown404.primalrework.blocks.StagedBlock;
import mrunknown404.primalrework.client.gui.screen.container.ScreenCampFire;
import mrunknown404.primalrework.client.gui.screen.container.ScreenInventory;
import mrunknown404.primalrework.client.gui.screen.container.ScreenPrimalCraftingTable;
import mrunknown404.primalrework.client.terenderers.TERCampFire;
import mrunknown404.primalrework.events.client.HarvestDisplayEvents;
import mrunknown404.primalrework.events.client.MiscEvents;
import mrunknown404.primalrework.events.client.QuestEvents;
import mrunknown404.primalrework.events.client.RecipeBrowserEvents;
import mrunknown404.primalrework.events.client.TooltipEvents;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitContainers;
import mrunknown404.primalrework.init.InitTileEntities;
import mrunknown404.primalrework.items.SIBlock;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.utils.IMetalColored;
import mrunknown404.primalrework.utils.helpers.ColorH;
import net.minecraft.block.Block;
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
import net.minecraftforge.registries.ForgeRegistries;

public class InitClient {
	public static final KeyBinding QUESTS_OPEN = new KeyBinding("key.quests_open", InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "key.categories.primalrework");
	public static final KeyBinding RECIPE_BROWSER_HOW_TO_CRAFT = new KeyBinding("key.recipe_browser.how_to_craft", InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_R,
			"key.categories.primalrework");
	public static final KeyBinding RECIPE_BROWSER_WHAT_CAN_I_CRAFT = new KeyBinding("key.recipe_browser.what_can_i_craft", InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_U,
			"key.categories.primalrework");
	private static final KeyBinding EMPTY = new KeyBinding("empty", InputMappings.UNKNOWN.getValue(), "empty");
	
	private InitClient() {
		
	}
	
	public static void preSetup() {
		MinecraftForge.EVENT_BUS.register(new TooltipEvents());
		MinecraftForge.EVENT_BUS.register(new HarvestDisplayEvents());
		MinecraftForge.EVENT_BUS.register(new RecipeBrowserEvents());
		MinecraftForge.EVENT_BUS.register(new MiscEvents());
		MinecraftForge.EVENT_BUS.register(new QuestEvents());
	}
	
	public static void setup() {
		Minecraft mc = Minecraft.getInstance();
		int index = -1;
		
		for (int i = 0; i < mc.options.keyMappings.length; i++) {
			if (mc.options.keyMappings[i] == mc.options.keyAdvancements) {
				index = i;
				break;
			}
		}
		
		ObfuscationReflectionHelper.setPrivateValue(GameSettings.class, mc.options, EMPTY, "field_194146_ao"); //keyAdvancements
		mc.options.keyMappings = ArrayUtils.remove(mc.options.keyMappings, index);
		
		ClientRegistry.registerKeyBinding(QUESTS_OPEN);
		ClientRegistry.registerKeyBinding(RECIPE_BROWSER_HOW_TO_CRAFT);
		ClientRegistry.registerKeyBinding(RECIPE_BROWSER_WHAT_CAN_I_CRAFT);
		
		ClientRegistry.bindTileEntityRenderer(InitTileEntities.CAMPFIRE.get(), TERCampFire::new);
		
		RenderTypeLookup.setRenderLayer(InitBlocks.GRASS_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(InitBlocks.GRASS_SLAB.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(InitBlocks.OAK_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(InitBlocks.SPRUCE_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(InitBlocks.BIRCH_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(InitBlocks.JUNGLE_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(InitBlocks.ACACIA_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(InitBlocks.DARK_OAK_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(InitBlocks.SHORT_GRASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(InitBlocks.MEDIUM_GRASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(InitBlocks.TALL_GRASS.get(), RenderType.cutoutMipped());
		
		ScreenManager.register(InitContainers.CAMPFIRE.get(), ScreenCampFire::new);
		ScreenManager.register(InitContainers.PRIMAL_CRAFTING_TABLE.get(), ScreenPrimalCraftingTable::new);
		ScreenManager.register(InitContainers.INVENTORY.get(), ScreenInventory::new);
		
		ForgeRegistries.ITEMS.getValues().stream().filter((i) -> i instanceof StagedItem).forEach((item) -> RecipeBrowserH.addItem((StagedItem) item));
		RecipeBrowserH.finish();
		
		//Color setup
		Block[] metalBlocks = ForgeRegistries.BLOCKS.getValues().stream()
				.filter(b -> b instanceof StagedBlock && b instanceof IMetalColored && ((IMetalColored) b).getMetal().color != null).toArray(Block[]::new);
		Block[] biomeColored = ForgeRegistries.BLOCKS.getValues().stream().filter(b -> b instanceof StagedBlock && ((StagedBlock) b).coloredByBiome()).toArray(Block[]::new);
		
		//Block colors
		mc.getBlockColors().register((state, reader, pos, tintIndex) -> reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColors.get(0.5, 1),
				biomeColored);
		mc.getBlockColors().register((state, reader, pos, tintIndex) -> ColorH.rgba2Int(((IMetalColored) state.getBlock()).getMetal().color), metalBlocks);
		
		//Item colors
		mc.getItemColors().register((itemstack, tintIndex) -> mc.getBlockColors().getColor(((SIBlock) itemstack.getItem()).getBlock().defaultBlockState(), null, null, tintIndex),
				biomeColored);
		mc.getItemColors().register((itemstack, tintIndex) -> mc.getBlockColors().getColor(((SIBlock) itemstack.getItem()).getBlock().defaultBlockState(), null, null, tintIndex),
				metalBlocks);
		
		mc.getItemColors().register((itemstack, tintIndex) -> ColorH.rgba2Int(((IMetalColored) itemstack.getItem()).getMetal().color), ForgeRegistries.ITEMS.getValues().stream()
				.filter(i -> i instanceof StagedItem && i instanceof IMetalColored && ((IMetalColored) i).getMetal().color != null).toArray(Item[]::new));
	}
}
