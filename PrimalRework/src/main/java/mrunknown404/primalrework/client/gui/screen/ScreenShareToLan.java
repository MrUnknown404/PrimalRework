package mrunknown404.primalrework.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.HTTPUtil;
import net.minecraft.world.GameType;

public class ScreenShareToLan extends Screen {
	private final Screen lastScreen;
	
	public ScreenShareToLan(Screen lastScreen) {
		super(WordH.translate("lanServer.title"));
		this.lastScreen = lastScreen;
	}
	
	@Override
	protected void init() {
		addButton(new Button(width / 2 - 75, height / 2 - 12, 150, 20, WordH.translate("lanServer.start"), button -> {
			minecraft.setScreen(null);
			int port = HTTPUtil.getAvailablePort();
			IntegratedServer server = minecraft.getSingleplayerServer();
			
			minecraft.gui.getChat()
					.addMessage(server.publishServer(GameType.SURVIVAL, server.getPlayerList().isOp(minecraft.player.getGameProfile()), port) ?
							WordH.translate("commands.publish.started", port) :
							WordH.translate("commands.publish.failed"));
			minecraft.updateTitle();
		}));
		
		addButton(new Button(width / 2 - 75, height / 2 + 12, 150, 20, DialogTexts.GUI_CANCEL, button -> minecraft.setScreen(lastScreen)));
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
		renderBackground(stack);
		drawCenteredString(stack, font, title, width / 2, height / 2 - 25, 16777215);
		super.render(stack, mouseX, mouseY, tick);
	}
}
