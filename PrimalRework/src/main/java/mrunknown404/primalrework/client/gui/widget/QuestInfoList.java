package mrunknown404.primalrework.client.gui.widget;

import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import mrunknown404.primalrework.client.gui.screen.ScreenQuestMenu;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.utils.helpers.ColorH;
import mrunknown404.primalrework.utils.helpers.MathH;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;

public class QuestInfoList extends AbstractList<QuestInfoList.QuestInfoEntry> {
	private final ScreenQuestMenu screen;
	private final Quest quest;
	
	private final IFormattableTextComponent arrow = WordH.string(" -> ");
	
	public QuestInfoList(ScreenQuestMenu screen, Minecraft mc, Quest quest) {
		super(mc, screen.width - 1, 0, screen.height - getInfoHeight(screen) + 1, screen.height - 1, 10);
		this.screen = screen;
		this.quest = quest;
		this.x0 = 40;
		this.headerHeight = 26;
		this.headerHeight += quest.hasReward() ? (headerHeight == 26 ? 20 : 10) : 0;
		this.headerHeight += quest.isRoot() ? 0 : (headerHeight == 26 ? 20 : 10);
		
		for (ITextComponent text : quest.getDescription()) {
			addEntry(new QuestInfoEntry(this, mc, text));
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
		
		fill(stack, x0, y0 - 1, width + 1, y1 + 1, ColorH.rgba2Int(200, 200, 200));
		fill(stack, x0, y0 - 1, width + 1, y0, ColorH.rgba2Int(45, 45, 45));
		fill(stack, width - 7, y0 - 1, width + 1, y1 + 1, ColorH.rgba2Int(45, 45, 45));
		fill(stack, width - 6, y0, width, y1, ColorH.rgba2Int(0, 0, 0));
		
		MainWindow window = minecraft.getWindow();
		GlStateManager._enableScissorTest();
		GlStateManager._scissorBox(x0, 0, window.getScreenWidth(), MathH.floor((getInfoHeight(screen) - 1) * window.getGuiScale()));
		
		int x = x0 + 4, y = y0 + 4 - (int) getScrollAmount();
		
		minecraft.textureManager.bind(quest.isEnd() ? ScreenQuestMenu.QUEST_END_ICON : ScreenQuestMenu.QUEST_ICON);
		blit(stack, x, y, 0, 0, 22, 22, 22, 22);
		if (quest.isFinished()) {
			stack.translate(0, 0, 60);
			minecraft.textureManager.bind(ScreenQuestMenu.QUEST_CHECKMARK);
			blit(stack, x, y, 0, 0, 22, 22, 22, 22);
		}
		
		minecraft.getItemRenderer().renderGuiItem(quest.getIcon(), x + 3, y + 3);
		x++;
		
		minecraft.font.draw(stack, quest.getFancyName(), x + 25, y + 7, ColorH.rgba2Int(45, 45, 45));
		
		if (quest.hasReward()) {
			ITextComponent reward = quest.getReward().getDescription();
			minecraft.font.draw(stack, WordH.translate("quest.info.reward").append(arrow).append(reward), x, y + 26, ColorH.rgba2Int(45, 45, 45));
		}
		
		if (!quest.isRoot()) {
			ITextComponent requirement = quest.getRequirement().getDescription();
			minecraft.font.draw(stack, WordH.translate("quest.info.requirement").append(arrow).append(requirement), x, y + (quest.hasReward() ? 36 : 26),
					ColorH.rgba2Int(45, 45, 45));
		}
		
		renderList(stack, getRowLeft(), y, mouseX, mouseY, tick);
		GlStateManager._disableScissorTest();
	}
	
	@Override
	protected int getScrollbarPosition() {
		return width - 6;
	}
	
	@Override
	public int getRowWidth() {
		return screen.width - 40;
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
	protected boolean isSelectedItem(int p_230957_1_) {
		return false;
	}
	
	public boolean isQuest(Quest quest) {
		return this.quest == quest;
	}
	
	public static int getInfoHeight(Screen screen) {
		return screen.height / 3;
	}
	
	private static class QuestInfoGui extends AbstractGui implements IRenderable {
		private final ITextComponent text;
		private final QuestInfoList questInfo;
		private final Minecraft mc;
		private int x, y;
		
		private QuestInfoGui(QuestInfoList questInfo, Minecraft mc, int x, int y, ITextComponent text) {
			this.questInfo = questInfo;
			this.mc = mc;
			this.text = text;
			this.x = x;
			this.y = y;
		}
		
		@Override
		public void render(MatrixStack stack, int mouseX, int mouseY, float partial) {
			int x = this.x + questInfo.x0;
			mc.font.draw(stack, text, x, y, ColorH.rgba2Int(45, 45, 45));
		}
	}
	
	class QuestInfoEntry extends AbstractList.AbstractListEntry<QuestInfoEntry> implements INestedGuiEventHandler {
		private IGuiEventListener focused;
		private boolean dragging;
		
		private final QuestInfoGui info;
		
		private QuestInfoEntry(QuestInfoList questInfo, Minecraft mc, ITextComponent text) {
			this.info = new QuestInfoGui(questInfo, mc, 5, 0, text);
		}
		
		@Override
		public void render(MatrixStack stack, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_,
				boolean p_230432_9_, float p_230432_10_) {
			info.y = p_230432_3_;
			info.render(stack, p_230432_7_, p_230432_8_, p_230432_10_);
		}
		
		@Override
		public List<? extends IGuiEventListener> children() {
			return Collections.emptyList();
		}
		
		@Override
		public boolean mouseClicked(double x, double y, int p_231044_5_) {
			return false;
		}
		
		@Override
		public boolean mouseReleased(double x, double y, int p_231048_5_) {
			return false;
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
