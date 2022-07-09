package mrunknown404.primalrework.client.gui.widget;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mrunknown404.primalrework.client.gui.screen.ScreenQuestMenu;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.helpers.ColorH;
import mrunknown404.primalrework.utils.helpers.MathH;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class QuestTabList extends AbstractList<QuestTabList.QuestTabEntry> {
	private final ScreenQuestMenu screen;
	
	public QuestTabList(ScreenQuestMenu screen, Minecraft mc) {
		super(mc, 39, 0, 1, screen.height - 1, 28);
		this.screen = screen;
		this.x0 = 0;
		
		for (QuestTab tab : InitQuests.getTabs()) {
			addEntry(new QuestTabList.QuestTabEntry(screen, tab));
		}
		
		setRenderBackground(false);
		setRenderTopAndBottom(false);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
		if (getMaxScroll() > 0) {
			BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
			RenderSystem.disableTexture();
			
			int i = getScrollbarPosition(), l1 = MathH.clamp((int) ((float) ((y1 - y0) * (y1 - y0)) / (float) getMaxPosition()), 32, y1 - y0 - 8);
			int i2 = MathH.clamp((int) getScrollAmount() * (y1 - y0 - l1) / getMaxScroll() + y0, y0, Integer.MAX_VALUE);
			
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			bufferbuilder.vertex(i, y1, 61).uv(0f, 1f).color(0, 0, 0, 255).endVertex();
			bufferbuilder.vertex(i + 6, y1, 61).uv(1f, 1f).color(0, 0, 0, 255).endVertex();
			bufferbuilder.vertex(i + 6, y0, 61).uv(1f, 0f).color(0, 0, 0, 255).endVertex();
			bufferbuilder.vertex(i, y0, 61).uv(0f, 0f).color(0, 0, 0, 255).endVertex();
			bufferbuilder.vertex(i, i2 + l1, 61).uv(0f, 1f).color(128, 128, 128, 255).endVertex();
			bufferbuilder.vertex(i + 6, i2 + l1, 61).uv(1f, 1f).color(128, 128, 128, 255).endVertex();
			bufferbuilder.vertex(i + 6, i2, 61).uv(1f, 0f).color(128, 128, 128, 255).endVertex();
			bufferbuilder.vertex(i, i2, 61).uv(0f, 0f).color(128, 128, 128, 255).endVertex();
			bufferbuilder.vertex(i, i2 + l1 - 1, 61).uv(0f, 1f).color(192, 192, 192, 255).endVertex();
			bufferbuilder.vertex(i + 6 - 1, i2 + l1 - 1, 61).uv(1f, 1f).color(192, 192, 192, 255).endVertex();
			bufferbuilder.vertex(i + 6 - 1, i2, 61).uv(1f, 0f).color(192, 192, 192, 255).endVertex();
			bufferbuilder.vertex(i, i2, 61).uv(0f, 0f).color(192, 192, 192, 255).endVertex();
			Tessellator.getInstance().end();
			RenderSystem.enableTexture();
		}
		
		stack.translate(0, 0, 60);
		fill(stack, 0, 0, 32, screen.height, ColorH.rgba2Int(200, 200, 200));
		fill(stack, 32, 0, 40, screen.height, ColorH.rgba2Int(45, 45, 45));
		fill(stack, 33, 1, 39, screen.height - 1, ColorH.rgba2Int(0, 0, 0));
		renderList(stack, getRowLeft(), y0 + 4 - (int) getScrollAmount(), mouseX, mouseY, tick);
	}
	
	@Override
	protected int getScrollbarPosition() {
		return width - 6;
	}
	
	@Override
	public int getRowWidth() {
		return 32;
	}
	
	@Override
	public boolean changeFocus(boolean focus) {
		boolean flag = super.changeFocus(focus);
		if (flag) {
			ensureVisible(getFocused());
		}
		
		return flag;
	}
	
	@Override
	protected boolean isSelectedItem(int var) {
		return false;
	}
	
	private static class TabButton extends Button {
		private final Stage stage;
		private final QuestTab tab;
		private final ScreenQuestMenu screen;
		
		private TabButton(ScreenQuestMenu screen, int x, int y, Stage stage, QuestTab tab) {
			super(x, y, 22, 22, stage.getFancyName(), onPress -> {
				screen.selectedTab = tab;
				screen.setQuestInfo(null);
			});
			this.stage = stage;
			this.tab = tab;
			this.screen = screen;
			this.active = tab.hasAccessToCurrentStage();
		}
		
		@Override
		public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
			Minecraft mc = Minecraft.getInstance();
			
			boolean isSelected = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
			if (active) {
				if (screen.selectedTab == tab) {
					mc.getTextureManager().bind(isSelected ? ScreenQuestMenu.QUEST_ICON_SELECTED_HOVER : ScreenQuestMenu.QUEST_ICON_SELECTED);
				} else {
					mc.getTextureManager().bind(isSelected ? ScreenQuestMenu.QUEST_ICON_HOVER : ScreenQuestMenu.QUEST_ICON);
				}
			} else {
				mc.getTextureManager().bind(ScreenQuestMenu.QUEST_ICON_HOVER);
			}
			
			blit(stack, x, y, 0, 0, 22, 22, 22, 22);
			mc.getItemRenderer().renderGuiItem(tab.getIcon(), x + 3, y + 3);
			
			if (isSelected) {
				GuiUtils.drawHoveringText(stack, Arrays.asList(stage.getFancyName()), mouseX, mouseY, screen.width, screen.height, -1, mc.font);
			}
		}
	}
	
	class QuestTabEntry extends AbstractList.AbstractListEntry<QuestTabEntry> implements INestedGuiEventHandler {
		private IGuiEventListener focused;
		private boolean dragging;
		
		private final TabButton button;
		
		private QuestTabEntry(ScreenQuestMenu screen, QuestTab tab) {
			this.button = new TabButton(screen, 5, 0, tab.getStage(), tab);
		}
		
		@Override
		public void render(MatrixStack stack, int x, int y, int p_230432_4_, int p_230432_5_, int p_230432_6_, int mouseX, int mouseY, boolean p_230432_9_, float partial) {
			button.y = y;
			button.render(stack, mouseX, mouseY, partial);
		}
		
		@Override
		public List<? extends IGuiEventListener> children() {
			return ImmutableList.of(button);
		}
		
		@Override
		public boolean mouseClicked(double x, double y, int key) {
			return button.mouseClicked(x, y, key);
		}
		
		@Override
		public boolean mouseReleased(double x, double y, int key) {
			return button.mouseReleased(x, y, key);
		}
		
		@Override
		public boolean isDragging() {
			return dragging;
		}
		
		@Override
		public void setDragging(boolean dragging) {
			this.dragging = dragging;
		}
		
		@Override
		public void setFocused(IGuiEventListener focused) {
			this.focused = focused;
		}
		
		@Override
		public IGuiEventListener getFocused() {
			return focused;
		}
	}
}
