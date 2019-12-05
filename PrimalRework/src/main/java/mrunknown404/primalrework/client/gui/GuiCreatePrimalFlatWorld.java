package mrunknown404.primalrework.client.gui;

import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiCreatePrimalFlatWorld extends GuiScreen {
	private final GuiCreatePrimalWorld createWorldGui;
	private FlatGeneratorInfo generatorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
	private String flatWorldTitle;
	private String materialText;
	private String heightText;
	private GuiCreatePrimalFlatWorld.Details createFlatWorldListSlotGui;
	private GuiButton addLayerButton;
	private GuiButton editLayerButton;
	private GuiButton removeLayerButton;
	
	public GuiCreatePrimalFlatWorld(GuiCreatePrimalWorld createWorldGuiIn, String preset) {
		createWorldGui = createWorldGuiIn;
		setPreset(preset);
	}
	
	String getPreset() {
		return generatorInfo.toString();
	}
	
	void setPreset(String preset) {
		generatorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(preset);
	}
	
	@Override
	public void initGui() {
		buttonList.clear();
		flatWorldTitle = I18n.format("createWorld.customize.flat.title");
		materialText = I18n.format("createWorld.customize.flat.tile");
		heightText = I18n.format("createWorld.customize.flat.height");
		createFlatWorldListSlotGui = new GuiCreatePrimalFlatWorld.Details();
		addLayerButton = addButton(new GuiButton(2, width / 2 - 154, height - 52, 100, 20, I18n.format("createWorld.customize.flat.addLayer") + " (NYI)"));
		editLayerButton = addButton(new GuiButton(3, width / 2 - 50, height - 52, 100, 20, I18n.format("createWorld.customize.flat.editLayer") + " (NYI)"));
		removeLayerButton = addButton(new GuiButton(4, width / 2 - 155, height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer")));
		buttonList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("gui.done")));
		buttonList.add(new GuiButton(5, width / 2 + 5, height - 52, 150, 20, I18n.format("createWorld.customize.presets")));
		buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel")));
		addLayerButton.visible = false;
		editLayerButton.visible = false;
		generatorInfo.updateLayers();
		onLayersChanged();
	}
	
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		createFlatWorldListSlotGui.handleMouseInput();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		int i = generatorInfo.getFlatLayers().size() - createFlatWorldListSlotGui.selectedLayer - 1;
		
		if (button.id == 1) {
			mc.displayGuiScreen(createWorldGui);
		} else if (button.id == 0) {
			createWorldGui.chunkProviderSettingsJson = getPreset();
			mc.displayGuiScreen(createWorldGui);
		} else if (button.id == 5) {
			mc.displayGuiScreen(new GuiFlatPrimalPresets(this));
		} else if (button.id == 4 && hasSelectedLayer()) {
			generatorInfo.getFlatLayers().remove(i);
			createFlatWorldListSlotGui.selectedLayer = Math.min(createFlatWorldListSlotGui.selectedLayer, generatorInfo.getFlatLayers().size() - 1);
		}
		
		generatorInfo.updateLayers();
		onLayersChanged();
	}
	
	private void onLayersChanged() {
		boolean flag = hasSelectedLayer();
		removeLayerButton.enabled = flag;
		editLayerButton.enabled = flag;
		editLayerButton.enabled = false;
		addLayerButton.enabled = false;
	}
	
	private boolean hasSelectedLayer() {
		return createFlatWorldListSlotGui.selectedLayer > -1 && createFlatWorldListSlotGui.selectedLayer < generatorInfo.getFlatLayers().size();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		createFlatWorldListSlotGui.drawScreen(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRenderer, flatWorldTitle, width / 2, 8, 16777215);
		int i = width / 2 - 92 - 16;
		drawString(fontRenderer, materialText, i, 32, 16777215);
		drawString(fontRenderer, heightText, i + 2 + 213 - fontRenderer.getStringWidth(heightText), 32, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@SideOnly(Side.CLIENT)
	class Details extends GuiSlot {
		public int selectedLayer = -1;
		
		public Details() {
			super(GuiCreatePrimalFlatWorld.this.mc, GuiCreatePrimalFlatWorld.this.width, GuiCreatePrimalFlatWorld.this.height, 43, GuiCreatePrimalFlatWorld.this.height - 60, 24);
		}
		
		private void drawItem(int x, int z, ItemStack itemToDraw) {
			drawItemBackground(x + 1, z + 1);
			GlStateManager.enableRescaleNormal();
			
			if (!itemToDraw.isEmpty()) {
				RenderHelper.enableGUIStandardItemLighting();
				GuiCreatePrimalFlatWorld.this.itemRender.renderItemIntoGUI(itemToDraw, x + 2, z + 2);
				RenderHelper.disableStandardItemLighting();
			}
			
			GlStateManager.disableRescaleNormal();
		}
		
		private void drawItemBackground(int x, int y) {
			drawItemBackground(x, y, 0, 0);
		}
		
		private void drawItemBackground(int x, int z, int textureX, int textureY) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.getTextureManager().bindTexture(Gui.STAT_ICONS);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos((double) (x + 0), (double) (z + 18), (double) GuiCreatePrimalFlatWorld.this.zLevel)
					.tex((double) ((float) (textureX + 0) * 0.0078125F), (double) ((float) (textureY + 18) * 0.0078125F)).endVertex();
			bufferbuilder.pos((double) (x + 18), (double) (z + 18), (double) GuiCreatePrimalFlatWorld.this.zLevel)
					.tex((double) ((float) (textureX + 18) * 0.0078125F), (double) ((float) (textureY + 18) * 0.0078125F)).endVertex();
			bufferbuilder.pos((double) (x + 18), (double) (z + 0), (double) GuiCreatePrimalFlatWorld.this.zLevel)
					.tex((double) ((float) (textureX + 18) * 0.0078125F), (double) ((float) (textureY + 0) * 0.0078125F)).endVertex();
			bufferbuilder.pos((double) (x + 0), (double) (z + 0), (double) GuiCreatePrimalFlatWorld.this.zLevel)
					.tex((double) ((float) (textureX + 0) * 0.0078125F), (double) ((float) (textureY + 0) * 0.0078125F)).endVertex();
			tessellator.draw();
		}
		
		@Override
		protected int getSize() {
			return GuiCreatePrimalFlatWorld.this.generatorInfo.getFlatLayers().size();
		}
		
		@Override
		protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
			selectedLayer = slotIndex;
			GuiCreatePrimalFlatWorld.this.onLayersChanged();
		}
		
		@Override
		protected boolean isSelected(int slotIndex) {
			return slotIndex == selectedLayer;
		}
		
		@Override
		protected void drawBackground() {}
		
		@Override
		protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
			FlatLayerInfo flatlayerinfo = (FlatLayerInfo) GuiCreatePrimalFlatWorld.this.generatorInfo.getFlatLayers()
					.get(GuiCreatePrimalFlatWorld.this.generatorInfo.getFlatLayers().size() - slotIndex - 1);
			IBlockState iblockstate = flatlayerinfo.getLayerMaterial();
			Block block = iblockstate.getBlock();
			Item item = Item.getItemFromBlock(block);
			
			if (item == Items.AIR) {
				if (block != Blocks.WATER && block != Blocks.FLOWING_WATER) {
					if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
						item = Items.LAVA_BUCKET;
					}
				} else {
					item = Items.WATER_BUCKET;
				}
			}
			
			ItemStack itemstack = new ItemStack(item, 1, item.getHasSubtypes() ? block.getMetaFromState(iblockstate) : 0);
			String s = item.getItemStackDisplayName(itemstack);
			drawItem(xPos, yPos, itemstack);
			GuiCreatePrimalFlatWorld.this.fontRenderer.drawString(s, xPos + 18 + 5, yPos + 3, 16777215);
			String s1;
			
			if (slotIndex == 0) {
				s1 = I18n.format("createWorld.customize.flat.layer.top", flatlayerinfo.getLayerCount());
			} else if (slotIndex == GuiCreatePrimalFlatWorld.this.generatorInfo.getFlatLayers().size() - 1) {
				s1 = I18n.format("createWorld.customize.flat.layer.bottom", flatlayerinfo.getLayerCount());
			} else {
				s1 = I18n.format("createWorld.customize.flat.layer", flatlayerinfo.getLayerCount());
			}
			
			GuiCreatePrimalFlatWorld.this.fontRenderer.drawString(s1, xPos + 2 + 213 - GuiCreatePrimalFlatWorld.this.fontRenderer.getStringWidth(s1), yPos + 3, 16777215);
		}
		
		@Override
		protected int getScrollBarX() {
			return width - 70;
		}
	}
}
