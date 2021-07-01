package mrunknown404.primalrework.client.gui.screen.container;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.inventory.container.ContainerCampFire;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScreenCampFire extends ContainerScreen<ContainerCampFire> {
	private static final ResourceLocation BG = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/container/campfire.png");
	
	public ScreenCampFire(ContainerCampFire container, PlayerInventory inv, ITextComponent text) {
		super(container, inv, text);
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
		renderBackground(stack);
		renderBg(stack, tick, mouseX, mouseY);
		super.render(stack, mouseX, mouseY, tick);
		renderTooltip(stack, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(MatrixStack stack, float tick, int mouseX, int mouseY) {
		minecraft.getTextureManager().bind(BG);
		blit(stack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
		
		if (menu.isBurning()) {
			int mod = menu.getBurnTimeLeft() * 14 / menu.getMaxBurnTime();
			blit(stack, leftPos + 81, topPos + 65 - mod, 176, 13 - mod, 14, mod + 1);
		}
		
		if (menu.isCooking()) {
			int maxTime = menu.getMaxCookTime();
			int timeLeft = maxTime - menu.getCookTimeLeft();
			int mod = maxTime != 0 && timeLeft != 0 ? timeLeft * 24 / maxTime : 0;
			blit(stack, leftPos + 103, topPos + 34 - mod, 176, 14, 2, mod);
		}
	}
}
