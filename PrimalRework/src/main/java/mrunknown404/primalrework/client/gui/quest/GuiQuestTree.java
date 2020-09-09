package mrunknown404.primalrework.client.gui.quest;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestRoot;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.unknownlibs.utils.ColorUtils;
import mrunknown404.unknownlibs.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

public class GuiQuestTree extends Gui {
	private static final ResourceLocation ICON_LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_icon.png");
	private static final ResourceLocation ICON_SEL_LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_icon_selected.png");
	private static final ResourceLocation ICON_CHECK_MARK_LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_icon_checkmark.png");
	
	private final Minecraft mc;
	private final RenderItem itemRender;
	private final FontRenderer fontRenderer;
	private final GuiQuestMenu parent;
	private final EnumStage stage;
	private final int guiLeft, guiTop;
	
	private int difX, difY, prevMouseX, prevMouseY, prevDifX, prevDifY;
	private boolean isMouseDown, didClick;
	boolean canDrag = true;
	
	public GuiQuestTree(Minecraft mc, GuiQuestMenu parent, EnumStage stage, int guiLeft, int guiTop) {
		this.mc = mc;
		this.parent = parent;
		this.fontRenderer = mc.fontRenderer;
		this.itemRender = mc.getRenderItem();
		this.stage = stage;
		this.guiLeft = guiLeft;
		this.guiTop = guiTop;
	}
	
	void drawScreen(int mouseX, int mouseY) {
		guiScissor(mc, guiLeft + 107, guiTop + 1, 312, 238);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		
		int xRootBase = -difX + guiLeft + 252, yRootBase = -difY + guiTop + 109;
		QuestTab tab = InitQuests.QUEST_TABS.get(stage);
		
		for (Quest q : tab.get()) {
			if (!(q instanceof QuestRoot) && q.hasParent()) {
				int x = MathUtils.ceil(q.getXPos() * 32f), y = MathUtils.ceil(q.getYPos() * 32f);
				int px = MathUtils.ceil(q.getParent().getXPos() * 32f), py = MathUtils.ceil(q.getParent().getYPos() * 32f);
				
				drawConnectivity(xRootBase + px, yRootBase + py, xRootBase + x, yRootBase + y);
			}
		}
		
		for (Quest q : tab.get()) {
			int x = MathUtils.ceil(q.getXPos() * 32f), y = MathUtils.ceil(q.getYPos() * 32f);
			
			if (didClick && MathUtils.within(mouseX, xRootBase + x, xRootBase + x + 22) && MathUtils.within(mouseY, yRootBase + y, yRootBase + y + 22)) {
				GlStateManager.color(0.75f, 0.75f, 0.75f);
			} else {
				GlStateManager.color(1, 1, 1);
			}
			
			mc.getTextureManager().bindTexture(parent.selectedQuest == q ? ICON_SEL_LOC : ICON_LOC);
			drawModalRectWithCustomSizedTexture(xRootBase + x, yRootBase + y, 0, 0, 22, 22, 22, 22);
			
			RenderHelper.disableStandardItemLighting();
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.enableDepth();
			itemRender.renderItemAndEffectIntoGUI(mc.player, q.getIcon(), xRootBase + 3 + x, yRootBase + 3 + y);
			GlStateManager.disableDepth();
			
			if (q.isFinished()) {
				mc.getTextureManager().bindTexture(ICON_CHECK_MARK_LOC);
				drawModalRectWithCustomSizedTexture(xRootBase + x, yRootBase + y, 0, 0, 22, 22, 22, 22);
			}
		}
		
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		
		for (Quest q : tab.get()) {
			int x = MathUtils.ceil(q.getXPos() * 32f), y = MathUtils.ceil(q.getYPos() * 32f);
			
			if (!MathUtils.within(mouseY, guiTop + 144, guiTop + 238) || parent.selectedQuest == null) {
				if (MathUtils.within(mouseX, guiLeft + 107, guiLeft + 418) && MathUtils.within(mouseY, guiTop + 1, guiTop + 238)) {
					if (MathUtils.within(mouseX, xRootBase + x, xRootBase + x + 22) && MathUtils.within(mouseY, yRootBase + y, yRootBase + y + 22)) {
						GuiUtils.drawHoveringText(Arrays.asList(q.getFancyName()), mouseX, mouseY, mc.displayWidth / 2 - guiLeft, mc.displayHeight, -1,
								fontRenderer);
						GlStateManager.disableLighting();
					}
				}
			}
		}
		
		if (Mouse.isButtonDown(0)) {
			if (!MathUtils.within(mouseY, guiTop + 144, guiTop + 238) || parent.selectedQuest == null) {
				if (MathUtils.within(mouseX, guiLeft + 107, guiLeft + 418) && MathUtils.within(mouseY, guiTop + 1, guiTop + 238)) {
					if (!didClick) {
						didClick = true;
						
						for (Quest q : tab.get()) {
							int x = MathUtils.ceil(q.getXPos() * 32f), y = MathUtils.ceil(q.getYPos() * 32f);
							
							if (MathUtils.within(mouseX, xRootBase + x, xRootBase + x + 22) && MathUtils.within(mouseY, yRootBase + y, yRootBase + y + 22)) {
								canDrag = false;
								if (parent.selectedQuest == null || !parent.selectedQuest.equals(q)) {
									parent.selectedQuest = q;
								} else {
									parent.selectedQuest = null;
								}
								
								parent.selectQuest();
								break;
							}
						}
					}
					
					if (canDrag) {
						if (!isMouseDown) {
							isMouseDown = true;
							prevMouseX = mouseX;
							prevMouseY = mouseY;
						} else {
							difX = prevMouseX - mouseX + prevDifX;
							difY = prevMouseY - mouseY + prevDifY;
						}
					}
				}
			} else {
				canDrag = false;
				didClick = true;
			}
		} else {
			isMouseDown = false;
			didClick = false;
			canDrag = true;
			prevDifX = difX;
			prevDifY = difY;
		}
	}
	
	private void drawConnectivity(int xFrom, int yFrom, int xTo, int yTo) {
		int i = xFrom + 13;
		int j = xFrom + 26;
		int k = yFrom + 11;
		int l = xTo + 13;
		int i1 = yTo + 11;
		int j1 = yFrom > yTo ? 1 : -1;
		int k1 = xFrom < xTo ? 1 : 0;
		int l1 = yFrom > yTo ? 0 : 1;
		
		int white = ColorUtils.rgba2Int(255, 255, 255, 255), black = ColorUtils.rgba2Int(0, 0, 0, 255);
		
		drawHorizontalLine(j - 1 + l1, i, k - 1, black);
		drawHorizontalLine(j - l1, i, k + 1, black);
		drawHorizontalLine(l, j - l1, i1 - 1, black);
		drawHorizontalLine(l, j - 1 + l1, i1 + 1, black);
		drawVerticalLine(j - 1, i1 + k1, k, black);
		drawVerticalLine(j + 1, i1 - j1, k + j1, black);
		
		drawHorizontalLine(j, i, k, white);
		drawHorizontalLine(l, j, i1, white);
		drawVerticalLine(j, i1, k, white);
	}
	
	private static void guiScissor(Minecraft mc, int x, int y, int w, int h) {
		ScaledResolution r = new ScaledResolution(mc);
		int f = r.getScaleFactor();
		GL11.glScissor(x * f, (r.getScaledHeight() - y - h) * f, w * f, h * f);
	}
}
