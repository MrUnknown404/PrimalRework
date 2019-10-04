package mrunknown404.primalrework.client.gui;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.inventory.ContainerLoom;
import mrunknown404.primalrework.tileentity.TileEntityLoom;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiLoom extends GuiContainer {

	private static final ResourceLocation LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/loom.png");
	private final InventoryPlayer player;
	private final TileEntityLoom te;
	
	public GuiLoom(InventoryPlayer player, TileEntityLoom te) {
		super(new ContainerLoom(player, te));
		this.player = player;
		this.te = te;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = te.getDisplayName().getUnformattedText();
		fontRenderer.drawString(name, xSize - fontRenderer.getStringWidth(name) - 7, ySize - 94, 5592405);
		fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 8, ySize - 94, 5592405);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(LOC);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
