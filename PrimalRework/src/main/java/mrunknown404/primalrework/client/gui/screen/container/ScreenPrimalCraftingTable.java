package mrunknown404.primalrework.client.gui.screen.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mrunknown404.primalrework.inventory.container.ContainerPrimalCraftingTable;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScreenPrimalCraftingTable extends ContainerScreen<ContainerPrimalCraftingTable> {
	private static final ResourceLocation CRAFTING_TABLE_LOCATION = new ResourceLocation("textures/gui/container/crafting_table.png");
	
	public ScreenPrimalCraftingTable(ContainerPrimalCraftingTable container, PlayerInventory inv, ITextComponent text) {
		super(container, inv, text);
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float tick) {
		renderBackground(stack);
		renderBg(stack, tick, mouseX, mouseY);
		super.render(stack, mouseX, mouseY, tick);
		renderTooltip(stack, mouseX, mouseY);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void renderBg(MatrixStack stack, float partial, int mouseX, int mouseY) {
		RenderSystem.color4f(1, 1, 1, 1);
		minecraft.getTextureManager().bind(CRAFTING_TABLE_LOCATION);
		blit(stack, leftPos, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight);
	}
}
