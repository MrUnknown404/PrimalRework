package mrunknown404.primalrework.utils.proxy;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.blocks.utils.IBiomeColored;
import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.client.CraftingDisplayH;
import mrunknown404.primalrework.client.gui.GuiNoToast;
import mrunknown404.primalrework.client.gui.screen.container.ScreenCampFire;
import mrunknown404.primalrework.client.gui.screen.container.ScreenInventory;
import mrunknown404.primalrework.client.gui.screen.container.ScreenPrimalCraftingTable;
import mrunknown404.primalrework.client.tileentities.TERCampFire;
import mrunknown404.primalrework.events.client.CraftingDisplayCEvents;
import mrunknown404.primalrework.events.client.HarvestDisplayCEvents;
import mrunknown404.primalrework.events.client.MiscCEvents;
import mrunknown404.primalrework.events.client.QuestCEvents;
import mrunknown404.primalrework.events.client.TooltipCEvents;
import mrunknown404.primalrework.items.utils.SIBlock;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.registries.PRContainers;
import mrunknown404.primalrework.registries.PRRegistry;
import mrunknown404.primalrework.registries.PRTileEntities;
import mrunknown404.primalrework.utils.IMetalColored;
import mrunknown404.primalrework.utils.enums.Metal;
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
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ClientProxy extends CommonProxy {
	public static final KeyBinding OPEN_QUESTS = new KeyBinding("key.quest", InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "key.categories.primalrework");
	private static final KeyBinding EMPTY = new KeyBinding("empty", InputMappings.UNKNOWN.getValue(), "empty");
	
	@Override
	public void preSetup(PrimalRework main) {
		super.preSetup(main);
		
		MinecraftForge.EVENT_BUS.register(new TooltipCEvents());
		MinecraftForge.EVENT_BUS.register(new HarvestDisplayCEvents());
		MinecraftForge.EVENT_BUS.register(new CraftingDisplayCEvents());
		MinecraftForge.EVENT_BUS.register(new MiscCEvents());
		MinecraftForge.EVENT_BUS.register(new QuestCEvents());
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
		
		ClientRegistry.bindTileEntityRenderer(PRTileEntities.CAMPFIRE.get(), TERCampFire::new);
		
		RenderTypeLookup.setRenderLayer(PRBlocks.GRASS_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.GRASS_SLAB.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.OAK_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.SPRUCE_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.BIRCH_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.JUNGLE_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.ACACIA_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.DARK_OAK_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.SHORT_GRASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.MEDIUM_GRASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.TALL_GRASS.get(), RenderType.cutoutMipped());
		
		ScreenManager.register(PRContainers.CAMPFIRE.get(), ScreenCampFire::new);
		ScreenManager.register(PRContainers.PRIMAL_CRAFTING_TABLE.get(), ScreenPrimalCraftingTable::new);
		ScreenManager.register(PRContainers.INVENTORY.get(), ScreenInventory::new);
		
		for (RegistryObject<Item> item : PRRegistry.getItems()) {
			CraftingDisplayH.addItem((StagedItem) item.get());
		}
		CraftingDisplayH.finish();
		
		//Color setup
		Block[] metalBlocks = PRRegistry.getBlocks().stream().filter((ro) -> {
			StagedBlock block = (StagedBlock) ro.get();
			return block instanceof IMetalColored && ((IMetalColored) block).getMetal() != Metal.UNKNOWN;
		}).map((ro) -> ro.get()).toArray(Block[]::new);
		Block[] biomeColored = PRRegistry.getBlocks().stream().filter((ro) -> ro.get() instanceof IBiomeColored).map((ro) -> ro.get()).toArray(Block[]::new);
		
		//Block colors
		mc.getBlockColors().register((state, reader, pos, tintIndex) -> reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColors.get(0.5, 1),
				biomeColored);
		mc.getBlockColors().register((state, reader, pos, tintIndex) -> ColorH.rgba2Int(((IMetalColored) state.getBlock()).getMetal().ingotColor), metalBlocks);
		
		//Item colors
		mc.getItemColors().register((itemstack, tintIndex) -> mc.getBlockColors().getColor(((SIBlock) itemstack.getItem()).getBlock().defaultBlockState(), null, null, tintIndex),
				biomeColored);
		mc.getItemColors().register((itemstack, tintIndex) -> mc.getBlockColors().getColor(((SIBlock) itemstack.getItem()).getBlock().defaultBlockState(), null, null, tintIndex),
				metalBlocks);
		
		mc.getItemColors().register((itemstack, tintIndex) -> ColorH.rgba2Int(((IMetalColored) itemstack.getItem()).getMetal().ingotColor),
				PRRegistry.getItems().stream().filter((ro) -> {
					StagedItem item = (StagedItem) ro.get();
					return item instanceof IMetalColored && ((IMetalColored) item).getMetal() != Metal.UNKNOWN;
				}).map((ro) -> ro.get()).toArray(Item[]::new));
	}
}
