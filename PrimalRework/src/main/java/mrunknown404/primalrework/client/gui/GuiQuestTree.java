package mrunknown404.primalrework.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import mrunknown404.primalrework.client.gui.screen.ScreenQuestMenu;
import mrunknown404.primalrework.client.gui.widget.QuestInfoList;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestState;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.utils.helpers.ColorH;
import mrunknown404.primalrework.utils.helpers.MathH;
import mrunknown404.primalrework.world.savedata.WSDQuests;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class GuiQuestTree extends FocusableGui implements IRenderable {
	private final List<QuestButton> children = new ArrayList<QuestButton>();
	private final ScreenQuestMenu screen;
	private double x, y;
	
	private static final int WHITE = ColorH.rgba2Int(255, 255, 255), BLACK = ColorH.rgba2Int(0, 0, 0), GREEN = ColorH.rgba2Int(0, 255, 0);
	
	public GuiQuestTree(ScreenQuestMenu screen, Minecraft mc, QuestTab tab) {
		this.screen = screen;
		
		for (Quest quest : tab.get()) {
			children.add(new QuestButton(WSDQuests.getQuestState(quest.getName()), mc, this));
		}
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int partial, double moveX, double moveY) {
		boolean flag = super.mouseDragged(mouseX, mouseY, partial, moveX, moveY);
		if (flag) {
			return true;
		}
		
		if (mouseX > 39) {
			float s = ScreenQuestMenu.getScale();
			x += moveX / s;
			y += moveY / s;
		}
		
		return true;
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int partial) {
		boolean flag = super.mouseClicked(mouseX, mouseY, partial);
		
		if (flag) {
			return true;
		}
		
		setFocused(null); //This isn't resetting?
		return false;
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partial) {
		for (IGuiEventListener c : children()) {
			QuestButton q = (QuestButton) c;
			q.render(stack, mouseX, mouseY, partial);
			q.renderBackgroundLines(stack);
			
			if (q.quest.isFinished()) {
				stack.translate(0, 0, 2);
				q.renderLines(stack, GREEN);
				stack.translate(0, 0, -2);
			} else {
				stack.translate(0, 0, 1);
				q.renderLines(stack, WHITE);
				stack.translate(0, 0, -1);
			}
		}
	}
	
	@Override
	public List<? extends IGuiEventListener> children() {
		return children;
	}
	
	private static class QuestButton extends Button {
		private final GuiQuestTree tree;
		private final QuestState quest;
		private final Minecraft mc;
		
		private QuestButton(QuestState quest, Minecraft mc, GuiQuestTree tree) {
			super(MathH.floor(quest.quest.getXPos() * 33) - 11 + tree.screen.width / 2, MathH.floor(quest.quest.getYPos() * 33) - 11 + tree.screen.height / 2, 22, 22,
					quest.quest.getFancyName(), (onPress) -> tree.screen.setQuestInfo(quest));
			this.quest = quest;
			this.mc = mc;
			this.tree = tree;
		}
		
		private void renderBackgroundLines(MatrixStack stack) {
			int x = this.x + MathH.floor(tree.x), y = this.y + MathH.floor(tree.y);
			
			if (quest.hasParent()) {
				QuestState p = quest.getParent();
				int px = MathH.floor(p.quest.getXPos() * 33) + MathH.floor(tree.x) - 11 + tree.screen.width / 2;
				int py = MathH.floor(p.quest.getYPos() * 33) + MathH.floor(tree.y) - 11 + tree.screen.height / 2;
				int d = x - (px + 22);
				
				if (py == y) {
					fill(stack, x, y + 10, x - d, y + 13, BLACK);
				} else {
					int i = py < y ? 1 : 0, hd = d / 2;
					fill(stack, x - hd - 2, py + 11 + i, x - hd + 1, y + 12 - i, BLACK);
					fill(stack, x, y + 10, x - hd, y + 13, BLACK);
					fill(stack, px + 22, py + 10, x - hd - 1, py + 13, BLACK);
					fill(stack, x - hd - 1, y + 11, x - hd, y + 12, BLACK);
					fill(stack, x - hd - 1, py + 11, x - hd, py + 12, BLACK);
				}
			}
		}
		
		private void renderLines(MatrixStack stack, int color) {
			int x = this.x + MathH.floor(tree.x), y = this.y + MathH.floor(tree.y);
			
			if (quest.hasParent()) {
				QuestState p = quest.getParent();
				int px = MathH.floor(p.quest.getXPos() * 33) + MathH.floor(tree.x) - 11 + tree.screen.width / 2;
				int py = MathH.floor(p.quest.getYPos() * 33) + MathH.floor(tree.y) - 11 + tree.screen.height / 2;
				int d = x - (px + 22);
				
				if (py == y) {
					fill(stack, x, y + 11, x - d, y + 12, color);
				} else {
					int i = py < y ? 1 : 0, hd = d / 2;
					fill(stack, x - hd - 1, py + 11 + i, x - hd, y + 12 - i, color);
					fill(stack, x, y + 11, x - hd, y + 12, color);
					fill(stack, px + 22, py + 11, x - hd - 1, py + 12, color);
				}
			}
		}
		
		@Override
		public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
			int x = this.x + MathH.floor(tree.x), y = this.y + MathH.floor(tree.y);
			float s = ScreenQuestMenu.getScale();
			
			boolean isHover = mouseX > 39 && (tree.screen.getQuestInfo() != null ? mouseY < tree.screen.height - QuestInfoList.getInfoHeight(tree.screen) : true) &&
					mouseX >= x * s && mouseX < (x + width) * s && mouseY >= y * s && mouseY < (y + height) * s;
			boolean isSelected = tree.screen.getQuestInfo() != null && tree.screen.getQuestInfo().isQuest(quest);
			
			if (quest.quest.isEnd()) {
				if (isSelected) {
					mc.getTextureManager().bind(isHover ? ScreenQuestMenu.QUEST_END_ICON_SELECTED_HOVER : ScreenQuestMenu.QUEST_END_ICON_SELECTED);
				} else {
					mc.getTextureManager().bind(isHover ? ScreenQuestMenu.QUEST_END_ICON_HOVER : ScreenQuestMenu.QUEST_END_ICON);
				}
			} else {
				if (isSelected) {
					mc.getTextureManager().bind(isHover ? ScreenQuestMenu.QUEST_ICON_SELECTED_HOVER : ScreenQuestMenu.QUEST_ICON_SELECTED);
				} else {
					mc.getTextureManager().bind(isHover ? ScreenQuestMenu.QUEST_ICON_HOVER : ScreenQuestMenu.QUEST_ICON);
				}
			}
			
			blit(stack, x, y, 0, 0, 22, 22, 22, 22);
			mc.getItemRenderer().blitOffset -= 50;
			renderGuiItem(mc, stack, quest.quest.getIcon(), MathH.floor((x + 3) * s), MathH.floor((y + 3) * s));
			mc.getItemRenderer().blitOffset += 50;
			
			if (quest.isFinished()) {
				stack.translate(0, 0, 60);
				mc.getTextureManager().bind(ScreenQuestMenu.QUEST_CHECKMARK);
				blit(stack, x, y, 0, 0, 22, 22, 22, 22);
				stack.translate(0, 0, -60);
			}
			
			if (isHover) {
				GuiUtils.drawHoveringText(new MatrixStack(), Arrays.asList(quest.quest.getFancyName()), mouseX, mouseY, tree.screen.width, tree.screen.height, -1, mc.font);
			}
		}
		
		@Override
		protected boolean clicked(double mouseX, double mouseY) {
			float s = ScreenQuestMenu.getScale();
			return active && visible && mouseX >= (x + tree.x) * s && mouseX < (x + tree.x + width) * s && mouseY >= (y + tree.y) * s && mouseY < (y + tree.y + height) * s;
		}
	}
	
	@SuppressWarnings("deprecation")
	private static void renderGuiItem(Minecraft mc, MatrixStack matrixstack, ItemStack stack, int x, int y) {
		ItemRenderer renderer = mc.getItemRenderer();
		TextureManager textureManager = mc.textureManager;
		IBakedModel model = renderer.getModel(stack, null, null);
		
		RenderSystem.pushMatrix();
		textureManager.bind(AtlasTexture.LOCATION_BLOCKS);
		textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
		RenderSystem.enableRescaleNormal();
		RenderSystem.enableAlphaTest();
		RenderSystem.defaultAlphaFunc();
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.translatef(x, y, 100 + renderer.blitOffset);
		RenderSystem.translatef(8 * ScreenQuestMenu.getScale(), 8 * ScreenQuestMenu.getScale(), 0); //Only difference is this
		RenderSystem.scalef(1, -1, 1);
		RenderSystem.scalef(16, 16, 16);
		IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
		boolean flag = !model.usesBlockLight();
		if (flag) {
			RenderHelper.setupForFlatItems();
		}
		
		renderer.render(stack, ItemCameraTransforms.TransformType.GUI, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, model);
		irendertypebuffer$impl.endBatch();
		RenderSystem.enableDepthTest();
		if (flag) {
			RenderHelper.setupFor3DItems();
		}
		
		RenderSystem.disableAlphaTest();
		RenderSystem.disableRescaleNormal();
		RenderSystem.popMatrix();
	}
}
