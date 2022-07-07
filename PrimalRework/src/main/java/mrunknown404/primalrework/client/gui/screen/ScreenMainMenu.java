package mrunknown404.primalrework.client.gui.screen;

import java.util.Random;

import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.AccessibilityScreen;
import net.minecraft.client.gui.screen.LanguageScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WinGameScreen;
import net.minecraft.client.gui.screen.WorldSelectionScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.gui.NotificationModUpdateScreen;
import net.minecraftforge.fml.BrandingControl;
import net.minecraftforge.fml.client.gui.screen.ModListScreen;

public class ScreenMainMenu extends Screen {
	private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
	private static final ResourceLocation ACCESSIBILITY_TEXTURE = new ResourceLocation("textures/gui/accessibility.png");
	private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
	private static final ResourceLocation MINECRAFT_EDITION = new ResourceLocation("textures/gui/title/edition.png");
	private final RenderSkybox panorama = new RenderSkybox(MainMenuScreen.CUBE_MAP);
	private final boolean minceraftEasterEgg, fading;
	private String splash;
	private int copyrightX, copyrightWidth;
	private long fadeInStart;
	private NotificationModUpdateScreen modUpdateNotification;
	
	public ScreenMainMenu() {
		this(false);
	}
	
	public ScreenMainMenu(boolean fading) {
		super(WordH.translate("narrator.screen.title"));
		this.fading = fading;
		this.minceraftEasterEgg = new Random().nextFloat() < 1.0e-4d;
	}
	
	@Override
	protected void init() {
		if (splash == null) {
			splash = minecraft.getSplashManager().getSplash();
		}
		
		copyrightWidth = font.width("Copyright Mojang AB. Do not distribute!");
		copyrightX = width - copyrightWidth - 2;
		
		int y = height / 4 + 48;
		modUpdateNotification = new NotificationModUpdateScreen(
				addButton(new Button(width / 2 - 100, y + 48, 98, 20, WordH.translate("fml.menu.mods"), b -> minecraft.setScreen(new ModListScreen(this)))));
		modUpdateNotification.resize(minecraft, width, height);
		modUpdateNotification.init();
		
		addButton(new Button(width / 2 - 100, y, 200, 20, WordH.translate("menu.singleplayer"), b -> minecraft.setScreen(new WorldSelectionScreen(this))));
		boolean alloyMultiplayer = minecraft.allowsMultiplayer();
		addButton(new Button(width / 2 - 100, y + 24, 200, 20, WordH.translate("menu.multiplayer"), b -> minecraft.setScreen(new MultiplayerScreen(this)),
				alloyMultiplayer ? Button.NO_TOOLTIP : (b, stack, mouseX, mouseY) -> {
					if (!b.active) {
						renderTooltip(stack, minecraft.font.split(WordH.translate("title.multiplayer.disabled"), Math.max(width / 2 - 43, 170)), mouseX, mouseY);
					}
				})).active = alloyMultiplayer;
		
		GameSettings options = minecraft.options;
		addButton(new Button(width / 2 + 2, y + 48, 98, 20, WordH.translate("menu.options"), b -> minecraft.setScreen(new OptionsScreen(this, options))));
		addButton(new Button(width / 2 - 100, y + 84, 200, 20, WordH.translate("menu.quit"), b -> minecraft.stop()));
		addButton(new ImageButton(width / 2 - 124, y + 48, 20, 20, 0, 106, 20, Widget.WIDGETS_LOCATION, 256, 256,
				b -> minecraft.setScreen(new LanguageScreen(this, options, minecraft.getLanguageManager())), WordH.translate("narrator.button.language")));
		addButton(new ImageButton(width / 2 + 104, y + 48, 20, 20, 0, 0, 20, ACCESSIBILITY_TEXTURE, 32, 64, b -> minecraft.setScreen(new AccessibilityScreen(this, options)),
				WordH.translate("narrator.button.accessibility")));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partial) {
		if (fadeInStart == 0 && fading) {
			fadeInStart = Util.getMillis();
		}
		
		float fade = fading ? (Util.getMillis() - fadeInStart) / 1000f : 1f;
		int x = width / 2 - 137;
		
		fill(stack, 0, 0, width, height, -1);
		panorama.render(partial, MathHelper.clamp(fade, 0f, 1f));
		minecraft.getTextureManager().bind(PANORAMA_OVERLAY);
		
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.color4f(1f, 1f, 1f, fading ? (float) MathHelper.ceil(MathHelper.clamp(fade, 0f, 1f)) : 1f);
		blit(stack, 0, 0, width, height, 0f, 0f, 16, 128, 16, 128);
		
		float f1 = fading ? MathHelper.clamp(fade - 1f, 0f, 1f) : 1f;
		int l = MathHelper.ceil(f1 * 255f) << 24;
		
		if ((l & -67108864) != 0) {
			minecraft.getTextureManager().bind(MINECRAFT_LOGO);
			RenderSystem.color4f(1f, 1f, 1f, f1);
			
			if (minceraftEasterEgg) {
				blitOutlineBlack(x, 30, (xx, y) -> {
					blit(stack, xx + 0, y, 0, 0, 99, 44);
					blit(stack, xx + 99, y, 129, 0, 27, 44);
					blit(stack, xx + 99 + 26, y, 126, 0, 3, 44);
					blit(stack, xx + 99 + 26 + 3, y, 99, 0, 26, 44);
					blit(stack, xx + 155, y, 0, 45, 155, 44);
				});
			} else {
				blitOutlineBlack(x, 30, (xx, yy) -> {
					blit(stack, xx + 0, yy, 0, 0, 155, 44);
					blit(stack, xx + 155, yy, 0, 45, 155, 44);
				});
			}
			
			minecraft.getTextureManager().bind(MINECRAFT_EDITION);
			blit(stack, x + 88, 67, 0f, 0f, 98, 14, 128, 16);
			ForgeHooksClient.renderMainMenu(null, stack, font, width, height, l);
			
			if (splash != null) {
				float f2 = (1.8f - MathHelper.abs(MathHelper.sin(Util.getMillis() % 1000L / 1000f * ((float) Math.PI * 2f)) * 0.1f)) * 100f / (font.width(splash) + 32);
				RenderSystem.pushMatrix();
				RenderSystem.translatef(width / 2 + 90, 70f, 0f);
				RenderSystem.rotatef(-20f, 0f, 0f, 1f);
				RenderSystem.scalef(f2, f2, f2);
				drawCenteredString(stack, font, splash, 0, -8, 16776960 | l);
				RenderSystem.popMatrix();
			}
			
			BrandingControl.forEachLine(true, true, (brdline, brd) -> drawString(stack, font, brd, 2, height - (10 + brdline * (font.lineHeight + 1)), 16777215 | l));
			BrandingControl.forEachAboveCopyrightLine(
					(brdline, brd) -> drawString(stack, font, brd, width - font.width(brd), height - (10 + (brdline + 1) * (font.lineHeight + 1)), 16777215 | l));
			
			drawString(stack, font, "Copyright Mojang AB. Do not distribute!", copyrightX, height - 10, 16777215 | l);
			if (mouseX > copyrightX && mouseX < copyrightX + copyrightWidth && mouseY > height - 10 && mouseY < height) {
				fill(stack, copyrightX, height - 1, copyrightX + copyrightWidth, height, 16777215 | l);
			}
			
			buttons.forEach(w -> w.setAlpha(f1));
			
			super.render(stack, mouseX, mouseY, partial);
			modUpdateNotification.render(stack, mouseX, mouseY, partial);
		}
	}
	
	@Override
	public boolean mouseClicked(double x, double y, int button) {
		if (super.mouseClicked(x, y, button)) {
			return true;
		}
		
		if (x > copyrightX && x < copyrightX + copyrightWidth && y > height - 10 && y < height) {
			minecraft.setScreen(new WinGameScreen(false, Runnables.doNothing()));
		}
		
		return false;
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public boolean shouldCloseOnEsc() {
		return false;
	}
}
