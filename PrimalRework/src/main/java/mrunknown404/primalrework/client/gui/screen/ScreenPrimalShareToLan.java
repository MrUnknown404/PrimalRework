package mrunknown404.primalrework.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.HTTPUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

public class ScreenPrimalShareToLan extends Screen {
	private final Screen lastScreen;
	
	public ScreenPrimalShareToLan(Screen lastScreen) {
		super(new TranslationTextComponent("lanServer.title"));
		this.lastScreen = lastScreen;
	}
	
	@Override
	protected void init() {
		addButton(new Button(width / 2 - 75, height / 2 - 12, 150, 20, new TranslationTextComponent("lanServer.start"), (button) -> {
			minecraft.setScreen(null);
			int port = HTTPUtil.getAvailablePort();
			ITextComponent itextcomponent;
			
			IntegratedServer server = minecraft.getSingleplayerServer();
			if (server.publishServer(GameType.SURVIVAL, server.getPlayerList().isOp(minecraft.player.getGameProfile()), port)) {
				itextcomponent = new TranslationTextComponent("commands.publish.started", port);
			} else {
				itextcomponent = new TranslationTextComponent("commands.publish.failed");
			}
			
			minecraft.gui.getChat().addMessage(itextcomponent);
			minecraft.updateTitle();
		}));
		
		addButton(new Button(width / 2 - 75, height / 2 + 12, 150, 20, DialogTexts.GUI_CANCEL, (button) -> {
			minecraft.setScreen(lastScreen);
		}));
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
		renderBackground(stack);
		drawCenteredString(stack, font, title, width / 2, height / 2 - 25, 16777215);
		super.render(stack, mouseX, mouseY, tick);
	}
}
