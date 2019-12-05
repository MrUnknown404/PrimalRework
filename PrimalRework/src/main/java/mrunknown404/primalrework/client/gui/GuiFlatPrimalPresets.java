package mrunknown404.primalrework.client.gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiFlatPrimalPresets extends GuiScreen {
	private static final List<GuiFlatPrimalPresets.LayerItem> FLAT_WORLD_PRESETS = Lists.<GuiFlatPrimalPresets.LayerItem>newArrayList();
	private final GuiCreatePrimalFlatWorld parentScreen;
	private String presetsTitle;
	private String presetsShare;
	private String listText;
	private GuiFlatPrimalPresets.ListSlot list;
	private GuiButton btnSelect;
	private GuiTextField export;
	
	public GuiFlatPrimalPresets(GuiCreatePrimalFlatWorld parent) {
		this.parentScreen = parent;
	}
	
	@Override
	public void initGui() {
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		presetsTitle = I18n.format("createWorld.customize.presets.title");
		presetsShare = I18n.format("createWorld.customize.presets.share");
		listText = I18n.format("createWorld.customize.presets.list");
		export = new GuiTextField(2, fontRenderer, 50, 40, width - 100, 20);
		list = new GuiFlatPrimalPresets.ListSlot();
		export.setMaxStringLength(1230);
		export.setText(parentScreen.getPreset());
		btnSelect = addButton(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("createWorld.customize.presets.select")));
		buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel")));
		updateButtonValidity();
	}
	
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		list.handleMouseInput();
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		export.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (!export.textboxKeyTyped(typedChar, keyCode)) {
			super.keyTyped(typedChar, keyCode);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0 && hasValidSelection()) {
			parentScreen.setPreset(export.getText());
			mc.displayGuiScreen(parentScreen);
		} else if (button.id == 1) {
			mc.displayGuiScreen(parentScreen);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		list.drawScreen(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRenderer, presetsTitle, width / 2, 8, 16777215);
		drawString(fontRenderer, presetsShare, 50, 30, 10526880);
		drawString(fontRenderer, listText, 50, 70, 10526880);
		export.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		export.updateCursorCounter();
		super.updateScreen();
	}
	
	private void updateButtonValidity() {
		btnSelect.enabled = hasValidSelection();
	}
	
	private boolean hasValidSelection() {
		return list.selected > -1 && list.selected < FLAT_WORLD_PRESETS.size() || export.getText().length() > 1;
	}
	
	private static void registerPreset(String name, Item icon, Biome biome, List<String> features, FlatLayerInfo... layers) {
		registerPreset(name, icon, 0, biome, features, layers);
	}
	
	private static void registerPreset(String name, Item icon, int iconMetadata, Biome biome, List<String> features, FlatLayerInfo... layers) {
		FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
		
		for (int i = layers.length - 1; i >= 0; --i) {
			flatgeneratorinfo.getFlatLayers().add(layers[i]);
		}
		
		flatgeneratorinfo.setBiome(Biome.getIdForBiome(biome));
		flatgeneratorinfo.updateLayers();
		
		for (String s : features) {
			flatgeneratorinfo.getWorldFeatures().put(s, Maps.newHashMap());
		}
		
		FLAT_WORLD_PRESETS.add(new GuiFlatPrimalPresets.LayerItem(icon, iconMetadata, name, flatgeneratorinfo.toString()));
	}
	
	static {
		registerPreset(I18n.format("createWorld.customize.preset.classic_flat"), Item.getItemFromBlock(Blocks.GRASS), Biomes.PLAINS, Arrays.asList("village"),
				new FlatLayerInfo(1, Blocks.GRASS), new FlatLayerInfo(2, Blocks.DIRT), new FlatLayerInfo(1, Blocks.BEDROCK));
		registerPreset(I18n.format("createWorld.customize.preset.tunnelers_dream"), Item.getItemFromBlock(Blocks.STONE), Biomes.EXTREME_HILLS,
				Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"), new FlatLayerInfo(1, Blocks.GRASS), new FlatLayerInfo(5, Blocks.DIRT),
				new FlatLayerInfo(230, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
		registerPreset(I18n.format("createWorld.customize.preset.water_world"), Items.WATER_BUCKET, Biomes.DEEP_OCEAN, Arrays.asList("biome_1", "oceanmonument"),
				new FlatLayerInfo(90, Blocks.WATER), new FlatLayerInfo(5, Blocks.SAND), new FlatLayerInfo(5, Blocks.DIRT), new FlatLayerInfo(5, Blocks.STONE),
				new FlatLayerInfo(1, Blocks.BEDROCK));
		registerPreset(I18n.format("createWorld.customize.preset.overworld"), Item.getItemFromBlock(Blocks.TALLGRASS), BlockTallGrass.EnumType.GRASS.getMeta(), Biomes.PLAINS,
				Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"), new FlatLayerInfo(1, Blocks.GRASS),
				new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(59, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
		registerPreset(I18n.format("createWorld.customize.preset.snowy_kingdom"), Item.getItemFromBlock(Blocks.SNOW_LAYER), Biomes.ICE_PLAINS, Arrays.asList("village", "biome_1"),
				new FlatLayerInfo(1, Blocks.SNOW_LAYER), new FlatLayerInfo(1, Blocks.GRASS), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(59, Blocks.STONE),
				new FlatLayerInfo(1, Blocks.BEDROCK));
		registerPreset(I18n.format("createWorld.customize.preset.bottomless_pit"), Items.FEATHER, Biomes.PLAINS, Arrays.asList("village", "biome_1"),
				new FlatLayerInfo(1, Blocks.GRASS), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(2, Blocks.COBBLESTONE));
		registerPreset(I18n.format("createWorld.customize.preset.desert"), Item.getItemFromBlock(Blocks.SAND), Biomes.DESERT,
				Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"), new FlatLayerInfo(8, Blocks.SAND), new FlatLayerInfo(52, Blocks.SANDSTONE),
				new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
		registerPreset(I18n.format("createWorld.customize.preset.redstone_ready"), Items.REDSTONE, Biomes.DESERT, Collections.emptyList(), new FlatLayerInfo(52, Blocks.SANDSTONE),
				new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
		registerPreset(I18n.format("createWorld.customize.preset.the_void"), Item.getItemFromBlock(Blocks.BARRIER), Biomes.VOID, Arrays.asList("decoration"),
				new FlatLayerInfo(1, Blocks.AIR));
	}
	
	@SideOnly(Side.CLIENT)
	static class LayerItem {
		public Item icon;
		public int iconMetadata;
		public String name;
		public String generatorInfo;
		
		public LayerItem(Item icon, int iconMetadata, String name, String generatorInfo) {
			this.icon = icon;
			this.iconMetadata = iconMetadata;
			this.name = name;
			this.generatorInfo = generatorInfo;
		}
	}
	
	@SideOnly(Side.CLIENT)
	class ListSlot extends GuiSlot {
		public int selected = -1;
		
		public ListSlot() {
			super(GuiFlatPrimalPresets.this.mc, GuiFlatPrimalPresets.this.width, GuiFlatPrimalPresets.this.height, 80, GuiFlatPrimalPresets.this.height - 37, 24);
		}
		
		private void renderIcon(int p_178054_1_, int p_178054_2_, Item icon, int iconMetadata) {
			blitSlotBg(p_178054_1_ + 1, p_178054_2_ + 1);
			GlStateManager.enableRescaleNormal();
			RenderHelper.enableGUIStandardItemLighting();
			GuiFlatPrimalPresets.this.itemRender.renderItemIntoGUI(new ItemStack(icon, 1, icon.getHasSubtypes() ? iconMetadata : 0), p_178054_1_ + 2, p_178054_2_ + 2);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableRescaleNormal();
		}
		
		private void blitSlotBg(int p_148173_1_, int p_148173_2_) {
			blitSlotIcon(p_148173_1_, p_148173_2_, 0, 0);
		}
		
		private void blitSlotIcon(int p_148171_1_, int p_148171_2_, int p_148171_3_, int p_148171_4_) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.getTextureManager().bindTexture(Gui.STAT_ICONS);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos((double) (p_148171_1_ + 0), (double) (p_148171_2_ + 18), (double) GuiFlatPrimalPresets.this.zLevel)
					.tex((double) ((float) (p_148171_3_ + 0) * 0.0078125F), (double) ((float) (p_148171_4_ + 18) * 0.0078125F)).endVertex();
			bufferbuilder.pos((double) (p_148171_1_ + 18), (double) (p_148171_2_ + 18), (double) GuiFlatPrimalPresets.this.zLevel)
					.tex((double) ((float) (p_148171_3_ + 18) * 0.0078125F), (double) ((float) (p_148171_4_ + 18) * 0.0078125F)).endVertex();
			bufferbuilder.pos((double) (p_148171_1_ + 18), (double) (p_148171_2_ + 0), (double) GuiFlatPrimalPresets.this.zLevel)
					.tex((double) ((float) (p_148171_3_ + 18) * 0.0078125F), (double) ((float) (p_148171_4_ + 0) * 0.0078125F)).endVertex();
			bufferbuilder.pos((double) (p_148171_1_ + 0), (double) (p_148171_2_ + 0), (double) GuiFlatPrimalPresets.this.zLevel)
					.tex((double) ((float) (p_148171_3_ + 0) * 0.0078125F), (double) ((float) (p_148171_4_ + 0) * 0.0078125F)).endVertex();
			tessellator.draw();
		}
		
		@Override
		protected int getSize() {
			return GuiFlatPrimalPresets.FLAT_WORLD_PRESETS.size();
		}
		
		@Override
		protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
			this.selected = slotIndex;
			GuiFlatPrimalPresets.this.updateButtonValidity();
			GuiFlatPrimalPresets.this.export.setText((GuiFlatPrimalPresets.FLAT_WORLD_PRESETS.get(GuiFlatPrimalPresets.this.list.selected)).generatorInfo);
		}
		
		@Override
		protected boolean isSelected(int slotIndex) {
			return slotIndex == this.selected;
		}
		
		@Override
		protected void drawBackground() {}
		
		@Override
		protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
			GuiFlatPrimalPresets.LayerItem GuiFlatPrimalPresets$layeritem = GuiFlatPrimalPresets.FLAT_WORLD_PRESETS.get(slotIndex);
			renderIcon(xPos, yPos, GuiFlatPrimalPresets$layeritem.icon, GuiFlatPrimalPresets$layeritem.iconMetadata);
			GuiFlatPrimalPresets.this.fontRenderer.drawString(GuiFlatPrimalPresets$layeritem.name, xPos + 18 + 5, yPos + 6, 16777215);
		}
	}
}
