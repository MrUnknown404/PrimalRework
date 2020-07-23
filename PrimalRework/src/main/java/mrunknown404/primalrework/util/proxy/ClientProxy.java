package mrunknown404.primalrework.util.proxy;

import org.lwjgl.input.Keyboard;

import mrunknown404.primalrework.client.render.TileEntityCraftingStumpRenderer;
import mrunknown404.primalrework.client.render.TileEntityDryingTableRenderer;
import mrunknown404.primalrework.client.render.TileEntityPrimalEnchantingRenderer;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.tileentity.TileEntityCraftingStump;
import mrunknown404.primalrework.tileentity.TileEntityDryingTable;
import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import mrunknown404.primalrework.util.helpers.EntityRenderHelper;
import mrunknown404.unknownlibs.utils.ICommonProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements ICommonProxy {
	public static final KeyBinding KEY_OPEN_QUESTS = new KeyBinding("key.open_quests", Keyboard.KEY_R, "key.primalrework.category");
	
	@Override
	public void preInit() {
		EntityRenderHelper.registerEntityRenderers();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraftingStump.class, new TileEntityCraftingStumpRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDryingTable.class, new TileEntityDryingTableRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPrimalEnchanting.class, new TileEntityPrimalEnchantingRenderer());
		
		ClientRegistry.registerKeyBinding(KEY_OPEN_QUESTS);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void postInit() {
		registerColors();
	}
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	private void registerColors() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
				return ColorizerGrass.getGrassColor(0.5D, 1.0D);
			}
		}, ModBlocks.GRASS_SLAB, ModBlocks.GRASS_DOUBLE_SLAB, ModBlocks.MUSHROOM_GRASS, ModBlocks.MUSHROOM_GRASS_SLAB, ModBlocks.MUSHROOM_GRASS_DOUBLE_SLAB);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				return ColorizerGrass.getGrassColor(0.5D, 1.0D);
			}
		}, ModBlocks.GRASS_SLAB, ModBlocks.GRASS_DOUBLE_SLAB, ModBlocks.MUSHROOM_GRASS, ModBlocks.MUSHROOM_GRASS_SLAB, ModBlocks.MUSHROOM_GRASS_DOUBLE_SLAB);
	}
}
