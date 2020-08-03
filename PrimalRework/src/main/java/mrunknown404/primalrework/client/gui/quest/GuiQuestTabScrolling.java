package mrunknown404.primalrework.client.gui.quest;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.unknownlibs.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GuiQuestTabScrolling extends Gui {
	
	private static final ResourceLocation TAB_LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_tab.png");
	private static final ResourceLocation TAB_SEL_LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_tab_selected.png");
	private static final int SIZE = Math.max(InitQuests.QUEST_TABS.size(), 6);
	
	private final int xMod;
	private final Minecraft mc;
	private final RenderItem itemRender;
	private final FontRenderer fontRenderer;
	private final GuiQuestMenu parent;
	
	private final int listWidth;
	private final int top;
	private final int bottom;
	private final int left;
	private final int slotHeight;
	private float initialMouseClickY = -2;
	private float scrollFactor;
	private float scrollDistance;
	private int selectedTab;
	
	private final boolean[] buttonsClicked = new boolean[SIZE];
	
	public GuiQuestTabScrolling(Minecraft mc, GuiQuestMenu parent, int xMod, int yMod) {
		this.mc = mc;
		this.parent = parent;
		this.xMod = xMod;
		this.fontRenderer = mc.fontRenderer;
		this.itemRender = mc.getRenderItem();
		
		this.listWidth = 105;
		this.top = yMod + 1;
		this.bottom = yMod + 238;
		this.slotHeight = 48;
		this.left = 1 + xMod;
		
		for (int i = 0; i < SIZE; i++) {
			buttonsClicked[i] = false;
		}
	}
	
	private void elementClicked(int i) {
		if (Mouse.isButtonDown(0)) {
			buttonsClicked[i] = true;
			selectedTab = i;
			parent.setupQuestTree(EnumStage.values()[i]);
		}
	}
	
	private void drawSlot(int slotIdx, int slotTop) {
		if (!Mouse.isButtonDown(0)) {
			buttonsClicked[slotIdx] = false;
		}
		
		if (buttonsClicked[slotIdx]) {
			GlStateManager.color(0.75f, 0.75f, 0.75f);
		} else {
			GlStateManager.color(1, 1, 1);
		}
		
		mc.getTextureManager().bindTexture(selectedTab == slotIdx ? TAB_SEL_LOC : TAB_LOC);
		drawModalRectWithCustomSizedTexture(xMod + 4, slotTop, 0, 0, 93, 45, 93, 45);
		
		QuestTab questTab = InitQuests.QUEST_TABS.get(EnumStage.values()[Math.min(slotIdx, EnumStage.values().length - 3)]);
		String name = questTab.getName().substring(questTab.getName().indexOf('(') + 1, questTab.getName().length() - 1);
		fontRenderer.drawStringWithShadow(name, xMod + 50 - fontRenderer.getStringWidth(name) / 2, slotTop + 5, ColorUtils.rgbaToInt(255, 255, 255, 255));
		
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		itemRender.renderItemAndEffectIntoGUI(mc.player, questTab.getIcon(), xMod + 4 + (93 / 2) - 8, slotTop + 20);
	}
	
	private int getContentHeight() {
		return SIZE * slotHeight + -1;
	}
	
	private void applyScrollLimits() {
		int listHeight = getContentHeight() - (bottom - top - 4);
		
		if (listHeight < 0) {
			listHeight /= 2;
		}
		
		if (scrollDistance < 0) {
			scrollDistance = 0;
		}
		
		if (scrollDistance > listHeight) {
			scrollDistance = listHeight;
		}
	}
	
	void handleMouseInput(int mouseX, int mouseY) {
		if (!(mouseX >= left && mouseX <= left + listWidth && mouseY >= top && mouseY <= bottom)) {
			return;
		}
		
		int scroll = Mouse.getEventDWheel();
		if (scroll != 0) {
			scrollDistance += (-1 * scroll / 120) * slotHeight / 2;
		}
	}
	
	void drawScreen(int mouseX, int mouseY) {
		boolean isHovering = mouseX >= left && mouseX <= left + listWidth && mouseY >= top && mouseY <= bottom;
		boolean isHoveringScrollbar = isHovering && mouseX >= left + listWidth - 6;
		int scrollBarRight = left + listWidth;
		int scrollBarLeft = scrollBarRight - 6;
		int entryRight = scrollBarLeft - 1;
		int viewHeight = bottom - top;
		
		if (Mouse.isButtonDown(0)) {
			if (initialMouseClickY == -1) {
				if (isHoveringScrollbar) {
					isHovering = false;
					int mouseListY = mouseY - top - -1 + (int) scrollDistance - 4;
					int slotIndex = mouseListY / slotHeight;
					
					if (mouseX >= left && mouseX <= entryRight && slotIndex >= 0 && mouseListY >= 0 && slotIndex < SIZE) {
						elementClicked(slotIndex);
					}
					
					if (mouseX >= scrollBarLeft && mouseX <= scrollBarRight) {
						scrollFactor = -1;
						int scrollHeight = getContentHeight() - viewHeight - 4;
						if (scrollHeight < 1) {
							scrollHeight = 1;
						}
						
						int var13 = (int) ((float) (viewHeight * viewHeight) / (float) getContentHeight());
						
						if (var13 < 32) {
							var13 = 32;
						}
						if (var13 > viewHeight - 4 * 2) {
							var13 = viewHeight - 4 * 2;
						}
						
						scrollFactor /= (float) (viewHeight - var13) / (float) scrollHeight;
					} else {
						scrollFactor = 1;
					}
					
					initialMouseClickY = mouseY;
				} else {
					initialMouseClickY = -2;
				}
				
				if (isHovering) {
					int mouseListY = mouseY - top - -1 + (int) scrollDistance - 4;
					int slotIndex = mouseListY / slotHeight;
					
					if (mouseX >= left && mouseX <= entryRight && slotIndex >= 0 && mouseListY >= 0 && slotIndex < SIZE) {
						elementClicked(slotIndex);
					}
				}
			} else if (initialMouseClickY >= 0) {
				scrollDistance -= (mouseY - initialMouseClickY) * scrollFactor;
				initialMouseClickY = mouseY;
			}
		} else {
			initialMouseClickY = -1;
		}
		
		applyScrollLimits();
		
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buff = tess.getBuffer();
		
		ScaledResolution res = new ScaledResolution(mc);
		double scaleW = mc.displayWidth / res.getScaledWidth_double();
		double scaleH = mc.displayHeight / res.getScaledHeight_double();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor((int) (left * scaleW), (int) (mc.displayHeight - (bottom * scaleH)), (int) (listWidth * scaleW), (int) (viewHeight * scaleH));
		
		int baseY = top + 4 - (int) scrollDistance;
		
		for (int slotIdx = 0; slotIdx < SIZE; ++slotIdx) {
			int slotTop = baseY + slotIdx * slotHeight + -1;
			int slotBuffer = slotHeight - 4;
			
			if (slotTop <= bottom && slotTop + slotBuffer >= top) {
				drawSlot(slotIdx, slotTop);
			}
		}
		
		GlStateManager.disableDepth();
		
		int extraHeight = (getContentHeight() + 4) - viewHeight;
		if (extraHeight > 0) {
			int height = (viewHeight * viewHeight) / getContentHeight();
			
			if (height < 32) {
				height = 32;
			}
			
			if (height > viewHeight - 4 * 2) {
				height = viewHeight - 4 * 2;
			}
			
			int barTop = (int) scrollDistance * (viewHeight - height) / extraHeight + top;
			if (barTop < top) {
				barTop = top;
			}
			
			GlStateManager.disableTexture2D();
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
			buff.pos(scrollBarLeft, bottom, 0).tex(0, 1).color(0x00, 0x00, 0x00, 0xFF).endVertex();
			buff.pos(scrollBarRight, bottom, 0).tex(1, 1).color(0x00, 0x00, 0x00, 0xFF).endVertex();
			buff.pos(scrollBarRight, top, 0).tex(1, 0).color(0x00, 0x00, 0x00, 0xFF).endVertex();
			buff.pos(scrollBarLeft, top, 0).tex(0, 0).color(0x00, 0x00, 0x00, 0xFF).endVertex();
			tess.draw();
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
			buff.pos(scrollBarLeft, barTop + height, 0).tex(0, 1).color(0x80, 0x80, 0x80, 0xFF).endVertex();
			buff.pos(scrollBarRight, barTop + height, 0).tex(1, 1).color(0x80, 0x80, 0x80, 0xFF).endVertex();
			buff.pos(scrollBarRight, barTop, 0).tex(1, 0).color(0x80, 0x80, 0x80, 0xFF).endVertex();
			buff.pos(scrollBarLeft, barTop, 0).tex(0, 0).color(0x80, 0x80, 0x80, 0xFF).endVertex();
			tess.draw();
			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
			buff.pos(scrollBarLeft, barTop + height - 1, 0).tex(0, 1).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
			buff.pos(scrollBarRight - 1, barTop + height - 1, 0).tex(1, 1).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
			buff.pos(scrollBarRight - 1, barTop, 0).tex(1, 0).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
			buff.pos(scrollBarLeft, barTop, 0).tex(0, 0).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
			tess.draw();
		}
		
		GlStateManager.enableTexture2D();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
}
