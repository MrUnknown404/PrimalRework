package mrunknown404.primalrework.client.gui.quest;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestRequirement;
import mrunknown404.primalrework.quests.QuestRequirement.QuestReq;
import mrunknown404.primalrework.quests.QuestRoot;
import mrunknown404.unknownlibs.utils.ColorUtils;
import net.minecraft.block.Block;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiQuestInfo extends Gui {
	private static final ResourceLocation INFO_BG_LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_info_background.png");
	private static final ResourceLocation INFO_BORDER_LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_info_border.png");
	private static final ResourceLocation ICON_LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_icon.png");
	private static final ResourceLocation ICON_CHECK_MARK_LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_icon_checkmark.png");
	
	private final Minecraft mc;
	private final RenderItem itemRender;
	private final FontRenderer fontRenderer;
	private final GuiQuestMenu parent;
	private final Quest quest;
	
	private final int xMod, listWidth, top, bottom, left, size;
	private float initialMouseClickY = -2, scrollFactor, scrollDistance;
	
	public GuiQuestInfo(Minecraft mc, GuiQuestMenu parent, Quest quest, int xMod, int yMod) {
		this.mc = mc;
		this.parent = parent;
		this.quest = quest;
		this.xMod = xMod;
		this.fontRenderer = mc.fontRenderer;
		this.itemRender = mc.getRenderItem();
		
		this.listWidth = 312;
		this.top = yMod + 144;
		this.bottom = yMod + 239;
		this.left = 107 + xMod;
		
		int size = 50 + ((quest.getFancyDesc().size() - 1) * fontRenderer.FONT_HEIGHT);
		
		if (!(quest instanceof QuestRoot)) {
			int amount = 0;
			
			if (quest.getRequirement().getQuestReq() == QuestReq.block_break) {
				amount = getDifferentBlocks(quest.getRequirement()).size();
			} else {
				amount = quest.getRequirement().getItemsToCollect().size();
			}
			
			size += (amount + 1) * fontRenderer.FONT_HEIGHT;
			size += 22 + (amount > 1 ? 1 : 0);
		}
		
		this.size = size;
	}
	
	private void applyScrollLimits() {
		int listHeight = size - (bottom - top - 4);
		
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
			scrollDistance += (-1 * scroll / 120) * 8;
		}
	}
	
	void drawScreen(int mouseX, int mouseY) {
		boolean isHovering = mouseX >= left && mouseX <= left + listWidth && mouseY >= top && mouseY <= bottom;
		boolean isHoveringScrollbar = isHovering && mouseX >= left + listWidth - 6;
		int scrollBarRight = left + listWidth;
		int scrollBarLeft = scrollBarRight - 6;
		int viewHeight = bottom - top;
		
		if (Mouse.isButtonDown(0)) {
			if (initialMouseClickY == -1) {
				if (isHoveringScrollbar) {
					parent.questTree.canDrag = false;
					isHovering = false;
					
					if (mouseX >= scrollBarLeft && mouseX <= scrollBarRight) {
						scrollFactor = -1;
						int scrollHeight = size - viewHeight - 4;
						if (scrollHeight < 1) {
							scrollHeight = 1;
						}
						
						int var13 = (int) ((float) (viewHeight * viewHeight) / (float) size);
						
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
		
		GlStateManager.disableDepth();
		mc.getTextureManager().bindTexture(INFO_BG_LOC);
		drawModalRectWithCustomSizedTexture(left, top, 0, 0, 312, 95, 312, 95);
		
		int extraHeight = (size + 4) - viewHeight;
		if (extraHeight > 0) {
			int height = (viewHeight * viewHeight) / size;
			
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
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		
		int xLeft = xMod + 107, yTop = top - (int) scrollDistance;
		if (scrollDistance < 0) {
			yTop = top;
		}
		
		mc.getTextureManager().bindTexture(ICON_LOC);
		drawModalRectWithCustomSizedTexture(xLeft + 10, yTop + 10, 0, 0, 22, 22, 22, 22);
		
		GlStateManager.enableDepth();
		itemRender.renderItemAndEffectIntoGUI(mc.player, quest.getIcon(), xLeft + 13, yTop + 13);
		GlStateManager.disableDepth();
		
		if (quest.isFinished()) {
			mc.getTextureManager().bindTexture(ICON_CHECK_MARK_LOC);
			drawModalRectWithCustomSizedTexture(xLeft + 10, yTop + 10, 0, 0, 22, 22, 22, 22);
		}
		
		int color = ColorUtils.rgbaToInt(255, 255, 255, 255);
		drawString(fontRenderer, quest.getFancyName(), xLeft + 40, yTop + 18, color);
		for (int i = 0; i < quest.getFancyDesc().size(); i++) {
			drawString(fontRenderer, quest.getFancyDesc().get(i), xLeft + 10, yTop + 40 + (i * fontRenderer.FONT_HEIGHT), color);
		}
		
		QuestRequirement req = quest.getRequirement();
		if (req != null) {
			drawString(fontRenderer, (req.getQuestReq() == QuestReq.block_break ? "Break one of the following blocks:" : "Collect one of the following items:"), xLeft + 10,
					yTop + 40 + ((quest.getFancyDesc().size() + 2) * fontRenderer.FONT_HEIGHT), color);
			
			if (req.getQuestReq() == QuestReq.block_break) {
				int i = 0;
				for (String b : getDifferentBlocks(req)) {
					drawString(fontRenderer, " " + req.getAmountNeeded() + " " + b, xLeft + 10, yTop + 50 + ((quest.getFancyDesc().size() + 2 + i) * fontRenderer.FONT_HEIGHT),
							color);
					i++;
				}
			} else {
				int i = 0;
				for (ItemStack it : req.getItemsToCollect()) {
					drawString(fontRenderer, " " + req.getAmountNeeded() + " " + it.getDisplayName(), xLeft + 10,
							yTop + 50 + ((quest.getFancyDesc().size() + 2 + i) * fontRenderer.FONT_HEIGHT), color);
					i++;
				}
			}
			
		}
		
		mc.getTextureManager().bindTexture(INFO_BORDER_LOC);
		drawModalRectWithCustomSizedTexture(left, top, 0, 0, 312, 95, 312, 95);
		
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
	
	private static final List<String> BLOCK_CACHE = new ArrayList<String>();
	
	private List<String> getDifferentBlocks(QuestRequirement req) {
		if (BLOCK_CACHE.isEmpty()) {
			for (Block b : req.getBlocksToBreak()) {
				if (BLOCK_CACHE.isEmpty()) {
					BLOCK_CACHE.add(b.getLocalizedName());
				} else if (!BLOCK_CACHE.contains(b.getLocalizedName())) {
					BLOCK_CACHE.add(b.getLocalizedName());
				}
			}
		}
		
		return BLOCK_CACHE;
	}
}
