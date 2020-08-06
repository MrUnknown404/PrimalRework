package mrunknown404.primalrework.client.gui;

import mrunknown404.primalrework.inventory.container.ContainerClayPot;
import mrunknown404.primalrework.tileentity.TileEntityClayPot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiClayPot extends GuiContainer {
	
	private static final ResourceLocation LOC = new ResourceLocation("minecraft", "textures/gui/container/dispenser.png");
	private final InventoryPlayer player;
	private final TileEntityClayPot te;
	
	public GuiClayPot(InventoryPlayer player, TileEntityClayPot te) {
		super(new ContainerClayPot(player, te));
		this.player = player;
		this.te = te;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = te.getDisplayName().getUnformattedText();
		fontRenderer.drawString(name, xSize - fontRenderer.getStringWidth(name) - 7, ySize - 94, 4210752);
		fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 8, ySize - 94, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(LOC);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
