package mrunknown404.primalrework.client.gui.quest;

import mrunknown404.primalrework.Main;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiQuestMenu extends GuiScreen {
	private static final ResourceLocation LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/quest/quest_menu_background.png");
	
	private GuiQuestTabScrolling tab_scroll;
	
	@Override
	public void initGui() {
		tab_scroll = new GuiQuestTabScrolling(mc, width / 2 - 420 / 2, height / 2 - 240 / 2);
	}
	
	@Override
	public void updateScreen() {
	
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		mc.getTextureManager().bindTexture(LOC);
		drawModalRectWithCustomSizedTexture(width / 2 - 210, height / 2 - 120, 0, 0, 420, 240, 420, 240);
		tab_scroll.handleMouseInput(mouseX, mouseY);
		tab_scroll.drawScreen(mouseX, mouseY);
	}
}
