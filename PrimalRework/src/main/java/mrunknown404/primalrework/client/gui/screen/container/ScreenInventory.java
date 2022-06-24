package mrunknown404.primalrework.client.gui.screen.container;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.inventory.container.ContainerInventory;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenInventory extends ContainerScreen<ContainerInventory> {
	public ScreenInventory(ContainerInventory container, PlayerInventory inv, ITextComponent text) {
		super(container, inv, text);
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
		renderBackground(stack);
		super.render(stack, mouseX, mouseY, tick);
		renderTooltip(stack, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(MatrixStack stack, float tick, int mouseX, int mouseY) {
		minecraft.getTextureManager().bind(INVENTORY_LOCATION);
		blit(stack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
		InventoryScreen.renderEntityInInventory(leftPos + 51, topPos + 75, 30, leftPos + 51 - mouseX, topPos + 75 - 50 - mouseY, minecraft.player);
	}
	
	@Override
	protected void renderLabels(MatrixStack stack, int mouseX, int mouseY) {
		
	}
}
