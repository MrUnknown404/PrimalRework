package mrunknown404.primalrework.client.gui.quest;

import java.util.Arrays;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.InitQuests;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.quests.QuestRoot;
import mrunknown404.primalrework.quests.QuestTab;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.unknownlibs.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

public class GuiQuestTree extends Gui {
	private static final ResourceLocation ICON_LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_icon.png");
	
	private final Minecraft mc;
	private final RenderItem itemRender;
	private final FontRenderer fontRenderer;
	private final EnumStage stage;
	private final int xMod, yMod;
	
	public GuiQuestTree(Minecraft mc, EnumStage stage, int xMod, int yMod) {
		this.mc = mc;
		this.fontRenderer = mc.fontRenderer;
		this.itemRender = mc.getRenderItem();
		this.stage = stage;
		this.xMod = xMod;
		this.yMod = yMod;
	}
	
	void drawScreen(int mouseX, int mouseY) {
		QuestTab tab = InitQuests.QUEST_TABS.get(stage);
		
		int x = 0, y = 0;
		
		for (Quest q : tab.get()) {
			if (!(q instanceof QuestRoot)) {
				x = 0;
				y = 40;
			}
			
			mc.getTextureManager().bindTexture(ICON_LOC);
			drawModalRectWithCustomSizedTexture(xMod + 252 + x, yMod + 109 + y, 0, 0, 22, 22, 22, 22);
			
			RenderHelper.disableStandardItemLighting();
			RenderHelper.enableGUIStandardItemLighting();
			itemRender.renderItemAndEffectIntoGUI(mc.player, q.getIcon(), xMod + 255 + x, yMod + 112 + y);
			
			if (MathUtils.within(mouseX, xMod + x + 252, xMod + x + 252 + 22) && MathUtils.within(mouseY, yMod + y + 109, yMod + y + 109 + 22)) {
				GuiUtils.drawHoveringText(Arrays.asList(q.getFancyName().getUnformattedText()), mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, fontRenderer);
				GlStateManager.disableLighting();
			}
		}
	}
}
