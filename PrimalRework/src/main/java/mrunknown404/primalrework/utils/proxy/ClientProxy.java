package mrunknown404.primalrework.utils.proxy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.blocks.SBOre;
import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.blocks.utils.StagedBlock.BlockModelType;
import mrunknown404.primalrework.client.CraftingDisplayH;
import mrunknown404.primalrework.client.gui.GuiNoToast;
import mrunknown404.primalrework.client.gui.screen.container.ScreenCampFire;
import mrunknown404.primalrework.client.gui.screen.container.ScreenPrimalCraftingTable;
import mrunknown404.primalrework.client.tileentities.TERCampFire;
import mrunknown404.primalrework.events.client.CraftingDisplayCEvents;
import mrunknown404.primalrework.events.client.HarvestDisplayCEvents;
import mrunknown404.primalrework.events.client.MiscCEvents;
import mrunknown404.primalrework.events.client.QuestCEvents;
import mrunknown404.primalrework.events.client.TooltipCEvents;
import mrunknown404.primalrework.helpers.ColorH;
import mrunknown404.primalrework.items.SIIngot;
import mrunknown404.primalrework.items.SIMetalPart;
import mrunknown404.primalrework.items.SIOreNugget;
import mrunknown404.primalrework.items.utils.SIBlock;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.registries.PRContainers;
import mrunknown404.primalrework.registries.PRRegistry;
import mrunknown404.primalrework.registries.PRTileEntities;
import mrunknown404.primalrework.utils.enums.EnumMetal;
import mrunknown404.primalrework.utils.enums.EnumOreValue;
import net.minecraft.block.Block;
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
import net.minecraftforge.fml.RegistryObject;
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
		MinecraftForge.EVENT_BUS.register(new MiscCEvents());
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
		
		RenderTypeLookup.setRenderLayer(PRBlocks.MUSHROOM_GRASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(PRBlocks.CAMPFIRE.get(), RenderType.cutoutMipped());
		
		ScreenManager.register(PRContainers.CAMPFIRE.get(), ScreenCampFire::new);
		ScreenManager.register(PRContainers.PRIMAL_CRAFTING_TABLE.get(), ScreenPrimalCraftingTable::new);
		
		//Color setup
		List<Item> items = new ArrayList<Item>();
		for (RegistryObject<Item> ro : PRRegistry.getItems()) {
			StagedItem item = (StagedItem) ro.get();
			if (item.getItemType() == ItemType.generated_colored) {
				if (item instanceof SIIngot) {
					if (((SIIngot) item).metal == EnumMetal.unknown) {
						continue;
					}
				} else if (item instanceof SIMetalPart) {
					if (((SIMetalPart) item).metal == EnumMetal.unknown) {
						continue;
					}
				}
				
				items.add(item);
			}
		}
		
		List<Block> blocks = new ArrayList<Block>();
		for (RegistryObject<Block> ro : PRRegistry.getBlocks()) {
			StagedBlock block = (StagedBlock) ro.get();
			if (block.getBlockModelType() == BlockModelType.normal_colored) {
				if (((SBOre) block).metal == EnumMetal.unknown) {
					continue;
				}
				
				blocks.add(block);
				RenderTypeLookup.setRenderLayer(block, RenderType.translucent());
			}
		}
		
		//Block colors
		mc.getBlockColors().register((state, reader, pos, tintIndex) -> {
			return reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColors.get(0.5, 1);
		}, PRBlocks.MUSHROOM_GRASS.get());
		
		mc.getBlockColors().register((state, reader, pos, tintIndex) -> {
			StagedBlock block = (StagedBlock) state.getBlock();
			if (block instanceof SBOre) {
				SBOre ore = ((SBOre) block);
				return ColorH.rgba2Int(ore.value == EnumOreValue.block ? ore.metal.ingotColor : ore.metal.oreColor);
			}
			
			return 0;
		}, blocks.toArray(new Block[0]));
		
		//Item colors
		mc.getItemColors().register((itemstack, tintIndex) -> {
			BlockState blockstate = ((SIBlock) itemstack.getItem()).getBlock().defaultBlockState();
			return mc.getBlockColors().getColor(blockstate, null, null, tintIndex);
		}, PRBlocks.MUSHROOM_GRASS.get());
		
		mc.getItemColors().register((itemstack, tintIndex) -> {
			BlockState blockstate = ((SIBlock) itemstack.getItem()).getBlock().defaultBlockState();
			return mc.getBlockColors().getColor(blockstate, null, null, tintIndex);
		}, blocks.toArray(new Block[0]));
		
		mc.getItemColors().register((itemstack, tintIndex) -> {
			StagedItem item = (StagedItem) itemstack.getItem();
			if (item instanceof SIIngot) {
				return ColorH.rgba2Int(((SIIngot) item).metal.ingotColor);
			} else if (item instanceof SIMetalPart) {
				return ColorH.rgba2Int(((SIMetalPart) item).metal.ingotColor);
			} else if (item instanceof SIOreNugget) {
				return ColorH.rgba2Int(((SIOreNugget) item).metal.oreColor);
			}
			
			return 0;
		}, items.toArray(new Item[0]));
		
		for (Item item : GameRegistry.findRegistry(Item.class)) {
			CraftingDisplayH.addItem(item);
		}
		CraftingDisplayH.finish();
	}
}
