package mrunknown404.primalrework.client.gui.quest;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.quests.Quest;
import mrunknown404.primalrework.util.enums.EnumStage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiQuestMenu extends GuiScreen {
	private static final ResourceLocation LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_menu_background.png");
	
	GuiQuestTabScrolling tabScroll;
	GuiQuestTree questTree;
	
	//TODO add quest info box
	
	@Override
	public void initGui() {
		tabScroll = new GuiQuestTabScrolling(mc, this, width / 2 - 420 / 2, height / 2 - 240 / 2);
		questTree = new GuiQuestTree(mc, this, EnumStage.stage0, width / 2 - 420 / 2, height / 2 - 240 / 2);
	}
	
	void setupQuestTree(EnumStage stage) {
		questTree = new GuiQuestTree(mc, this, stage, width / 2 - 420 / 2, height / 2 - 240 / 2);
	}
	
	void selectQuest(Quest q) { // TODO do this
		System.out.println("Quest '" + q.getName() + "' was clicked!");
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		mc.getTextureManager().bindTexture(LOC);
		drawModalRectWithCustomSizedTexture(width / 2 - 210, height / 2 - 120, 0, 0, 420, 240, 420, 240);
		tabScroll.handleMouseInput(mouseX, mouseY);
		tabScroll.drawScreen(mouseX, mouseY);
		questTree.drawScreen(mouseX, mouseY);
	}
}
