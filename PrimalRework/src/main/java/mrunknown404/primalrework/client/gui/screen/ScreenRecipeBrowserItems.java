package mrunknown404.primalrework.client.gui.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import mrunknown404.primalrework.client.InitClient;
import mrunknown404.primalrework.client.RecipeBrowserH;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.utils.helpers.ColorH;
import mrunknown404.primalrework.utils.helpers.MathH;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class ScreenRecipeBrowserItems extends Screen {
	private ItemRenderer ir;
	private TextureManager textureManager;
	private ContainerScreen<?> container;
	private final List<Data> curItems = new ArrayList<Data>(), items3D = new ArrayList<Data>(), items2D = new ArrayList<Data>(), preItems3D = new ArrayList<Data>(),
			preItems2D = new ArrayList<Data>();
	private int heightItems, padding, xStart, listSize;
	private StagedItem itemUnderMouse;
	private ITextComponent scrollFaster;
	
	private static int scroll, lastHeightItems, lastSize;
	
	public ScreenRecipeBrowserItems() {
		super(WordH.translate("screen.crafting_display.title"));
	}
	
	@Override
	protected void init() {
		ir = minecraft.getItemRenderer();
		textureManager = minecraft.textureManager;
		if (minecraft.screen instanceof ScreenRecipeBrowser) {
			container = ((ScreenRecipeBrowser) minecraft.screen).getLastScreen();
		} else {
			container = (ContainerScreen<?>) minecraft.screen;
		}
		
		int guiScale = minecraft.options.guiScale;
		int height = minecraft.getWindow().getHeight();
		int itemSize = MathH.floor(18 * guiScale);
		padding = height % itemSize / guiScale / 2 + 1;
		heightItems = MathH.floor(height / itemSize);
		int widthItems = MathH.floor(container.getGuiLeft() * guiScale / itemSize);
		xStart = width - (widthItems * 18) - (padding / 2);
		listSize = MathH.floor((float) (RecipeBrowserH.getItemList().size() - 1) / (float) widthItems); //Unsure if this fixed it tbh
		
		if (lastHeightItems != heightItems || lastSize != RecipeBrowserH.getItemList().size()) {
			scroll = 0;
		}
		
		lastSize = RecipeBrowserH.getItemList().size();
		lastHeightItems = heightItems;
		
		items3D.clear();
		items2D.clear();
		preItems3D.clear();
		preItems2D.clear();
		
		int j = 0;
		for (int i = 0; i < RecipeBrowserH.getItemList().size(); i++) {
			ItemStack item = RecipeBrowserH.getItemList().get(i);
			int y = MathH.floor((float) i / (float) widthItems);
			Data d = new Data(item, ir.getModel(item, null, null), i % widthItems, y, false);
			
			if (RecipeBrowserH.isItem3D(item)) {
				items3D.add(d);
			} else {
				items2D.add(d);
			}
			
			if (y >= listSize - heightItems + 1) {
				d = new Data(item, ir.getModel(item, null, null), j % widthItems, MathH.floor((float) j / (float) widthItems), true);
				if (RecipeBrowserH.isItem3D(item)) {
					preItems3D.add(d);
				} else {
					preItems2D.add(d);
				}
				j++;
			}
		}
		
		scrollFaster = WordH.translate("gui.crafting_display.pre").append(right).append(WordH.translate(minecraft.options.keyShift.getName())).append(left)
				.append(WordH.translate("gui.crafting_display.post"));
	}
	
	private final IFormattableTextComponent left = WordH.string("' "), right = WordH.string(" '");
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		IRenderTypeBuffer.Impl buf = minecraft.renderBuffers().bufferSource();
		
		textureManager.bind(AtlasTexture.LOCATION_BLOCKS);
		textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
		
		RenderHelper.setupForFlatItems();
		RenderSystem.enableRescaleNormal();
		RenderSystem.enableAlphaTest();
		RenderSystem.defaultAlphaFunc();
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		
		itemUnderMouse = null;
		RenderHelper.setupFor3DItems();
		
		curItems.clear();
		curItems.addAll(items3D);
		if (scroll < 0) {
			curItems.addAll(preItems3D);
		}
		
		renderItems(curItems, stack, buf, mouseX, mouseY);
		buf.endBatch();
		
		curItems.clear();
		curItems.addAll(items2D);
		if (scroll < 0) {
			curItems.addAll(preItems2D);
		}
		
		RenderHelper.setupForFlatItems();
		renderItems(curItems, stack, buf, mouseX, mouseY);
		buf.endBatch();
		
		if (heightItems < listSize) {
			drawString(stack, font, scrollFaster, 4, height - font.lineHeight - 2, ColorH.rgba2Int(255, 255, 255));
			drawString(stack, font, WordH.string((scroll < 0 ? listSize + scroll : scroll) + "/" + listSize), 4, height - font.lineHeight * 2 - 4, ColorH.rgba2Int(255, 255, 255));
		}
		
		RenderSystem.enableDepthTest();
		RenderSystem.disableAlphaTest();
		RenderSystem.disableRescaleNormal();
	}
	
	private void renderItems(List<Data> datas, MatrixStack stack, IRenderTypeBuffer.Impl buf, int mouseX, int mouseY) {
		for (Data data : datas) {
			int ymod = 0;
			if (data.isPre) {
				if (!(data.y >= heightItems - -scroll && data.y <= heightItems)) {
					continue;
				}
				ymod = (scroll + heightItems) * 18;
			} else {
				if (!(data.y >= scroll && data.y < scroll + heightItems)) {
					continue;
				}
				ymod = scroll * 18;
			}
			
			int xx = xStart + data.x * 18, yy = padding + data.y * 18 - ymod;
			renderGuiItem(data.itemStack, data.model, stack, buf, xx, yy);
			
			if (mouseX > xx && mouseX <= xx + 16 && mouseY > yy && mouseY <= yy + 16) {
				itemUnderMouse = (StagedItem) data.itemStack.getItem();
				GuiUtils.drawHoveringText(stack, getTooltipFromItem(data.itemStack), mouseX, mouseY, width, height, -1, font);
			}
		}
	}
	
	public boolean scroll(double mouseX, double scrollDelta) {
		if (mouseX >= xStart) {
			if (heightItems >= listSize) {
				return true;
			}
			
			scroll -= Math.round(scrollDelta * minecraft.options.mouseWheelSensitivity *
					(InputMappings.isKeyDown(minecraft.getWindow().getWindow(), minecraft.options.keyShift.getKey().getValue()) ? heightItems : 2));
			
			if (scroll > listSize - heightItems + 1) {
				scroll -= listSize;
			} else if (scroll < -heightItems) {
				scroll += listSize;
			}
			
			return true;
		}
		
		return false;
	}
	
	public boolean click(double mouseX, int button) {
		if (mouseX >= xStart) {
			if (itemUnderMouse != null) {
				if (button == 0) {
					RecipeBrowserH.showHowToCraft(minecraft, itemUnderMouse, container);
				} else if (button == 1) {
					RecipeBrowserH.showWhatCanBeMade(minecraft, itemUnderMouse, container);
				}
			}
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean keyPressed(int keycode, int var0, int var1) {
		if (itemUnderMouse != null) {
			if (InitClient.RECIPE_BROWSER_HOW_TO_CRAFT.getKey().getValue() == keycode) {
				RecipeBrowserH.showHowToCraft(minecraft, itemUnderMouse, container);
			} else if (InitClient.RECIPE_BROWSER_WHAT_CAN_I_CRAFT.getKey().getValue() == keycode) {
				RecipeBrowserH.showWhatCanBeMade(minecraft, itemUnderMouse, container);
			}
			return true;
		}
		
		return false;
	}
	
	private void renderGuiItem(ItemStack stack, IBakedModel model, MatrixStack matrixStack, IRenderTypeBuffer.Impl buffer, int x, int y) {
		matrixStack.pushPose();
		matrixStack.translate(x + 8, y + 8, 100 + ir.blitOffset);
		matrixStack.scale(16, -16, 16);
		ir.render(stack, ItemCameraTransforms.TransformType.GUI, false, matrixStack, buffer, 15728880, OverlayTexture.NO_OVERLAY, model);
		matrixStack.popPose();
	}
	
	private static class Data {
		private final ItemStack itemStack;
		private final IBakedModel model;
		private final int x, y;
		private final boolean isPre;
		
		private Data(ItemStack itemStack, IBakedModel model, int x, int y, boolean isPre) {
			this.itemStack = itemStack;
			this.model = model;
			this.x = x;
			this.y = y;
			this.isPre = isPre;
		}
	}
}