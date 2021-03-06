package mrunknown404.primalrework.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenPrimalPause extends Screen {
	public ScreenPrimalPause(boolean pause) {
		super(pause ? new TranslationTextComponent("menu.game") : new TranslationTextComponent("menu.paused"));
	}
	
	@Override
	protected void init() {
		addButton(new Button(width / 2 - 102, height / 4 + 24, 204, 20, new TranslationTextComponent("menu.returnToGame"), (button) -> {
			minecraft.setScreen((Screen) null);
			minecraft.mouseHandler.grabMouse();
		}));
		
		addButton(new Button(width / 2 + 4, height / 4 + 48, 98, 20, new TranslationTextComponent("gui.stats"), (button) -> {
			minecraft.setScreen(new StatsScreen(this, minecraft.player.getStats()));
		}));
		
		addButton(new Button(width / 2 - 102, height / 4 + 48, 98, 20, new TranslationTextComponent("menu.options"), (button) -> {
			minecraft.setScreen(new OptionsScreen(this, minecraft.options));
		}));
		
		Button button = addButton(new Button(width / 2 - 102, height / 4 + 72, 204, 20, new TranslationTextComponent("menu.shareToLan"), (button0) -> {
			minecraft.setScreen(new ScreenPrimalShareToLan(this));
		}));
		button.active = minecraft.hasSingleplayerServer() && !minecraft.getSingleplayerServer().isPublished();
		
		Button button1 = addButton(new Button(width / 2 - 102, height / 4 + 96, 204, 20, new TranslationTextComponent("menu.returnToMenu"), (button0) -> {
			boolean flag = minecraft.isLocalServer();
			boolean flag1 = minecraft.isConnectedToRealms();
			button0.active = false;
			minecraft.level.disconnect();
			if (flag) {
				minecraft.clearLevel(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
			} else {
				minecraft.clearLevel();
			}
			
			if (flag) {
				minecraft.setScreen(new MainMenuScreen());
			} else if (flag1) {
				RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
				realmsbridgescreen.switchToRealms(new MainMenuScreen());
			} else {
				minecraft.setScreen(new MultiplayerScreen(new MainMenuScreen()));
			}
		}));
		
		if (!minecraft.isLocalServer()) {
			button1.setMessage(new TranslationTextComponent("menu.disconnect"));
		}
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partial) {
		renderBackground(stack);
		drawCenteredString(stack, font, title, width / 2, 40, 16777215);
		super.render(stack, mouseX, mouseY, partial);
	}
}
