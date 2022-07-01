package mrunknown404.primalrework.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsBridgeScreen;

public class ScreenPause extends Screen {
	public ScreenPause(boolean pause) {
		super(pause ? WordH.translate("menu.game") : WordH.translate("menu.paused"));
	}
	
	@Override
	protected void init() {
		addButton(new Button(width / 2 - 102, height / 4 + 24, 204, 20, WordH.translate("menu.returnToGame"), (button) -> {
			minecraft.setScreen((Screen) null);
			minecraft.mouseHandler.grabMouse();
		}));
		
		addButton(new Button(width / 2 + 4, height / 4 + 48, 98, 20, WordH.translate("gui.stats"), (button) -> minecraft.setScreen(new StatsScreen(this, minecraft.player.getStats()))));
		addButton(new Button(width / 2 - 102, height / 4 + 48, 98, 20, WordH.translate("menu.options"), (button) -> minecraft.setScreen(new OptionsScreen(this, minecraft.options))));
		
		Button button = addButton(
				new Button(width / 2 - 102, height / 4 + 72, 204, 20, WordH.translate("menu.shareToLan"), (button0) -> minecraft.setScreen(new ScreenPrimalShareToLan(this))));
		button.active = minecraft.hasSingleplayerServer() && !minecraft.getSingleplayerServer().isPublished();
		
		Button button1 = addButton(new Button(width / 2 - 102, height / 4 + 96, 204, 20, WordH.translate("menu.returnToMenu"), (button0) -> {
			boolean flag = minecraft.isLocalServer();
			button0.active = false;
			minecraft.level.disconnect();
			
			if (flag) {
				minecraft.clearLevel(new DirtMessageScreen(WordH.translate("menu.savingLevel")));
			} else {
				minecraft.clearLevel();
			}
			
			if (flag) {
				minecraft.setScreen(new MainMenuScreen());
			} else if (minecraft.isConnectedToRealms()) {
				RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
				realmsbridgescreen.switchToRealms(new MainMenuScreen());
			} else {
				minecraft.setScreen(new MultiplayerScreen(new MainMenuScreen()));
			}
		}));
		
		if (!minecraft.isLocalServer()) {
			button1.setMessage(WordH.translate("menu.disconnect"));
		}
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partial) {
		renderBackground(stack);
		drawCenteredString(stack, font, title, width / 2, 40, 16777215);
		super.render(stack, mouseX, mouseY, partial);
	}
}
