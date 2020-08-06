package mrunknown404.primalrework.client.gui;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.inventory.container.ContainerCharcoalKiln;
import mrunknown404.primalrework.tileentity.TileEntityCharcoalKiln;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCharcoalKiln extends GuiContainer {
	
	private static final ResourceLocation LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/container/charcoal_kiln.png");
	private final InventoryPlayer player;
	private final TileEntityCharcoalKiln te;
	
	public GuiCharcoalKiln(InventoryPlayer player, TileEntityCharcoalKiln te) {
		super(new ContainerCharcoalKiln(player, te));
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
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 8, ySize - 94, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(LOC);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if (te.isBurning()) {
			int l = getCookProgressScale(13);
			drawTexturedModalRect(guiLeft + 95, guiTop + 36 + l, 176, l, 14, 14 - l);
		}
	}
	
	private int getCookProgressScale(int pixels) {
		int i = te.getCookTime();
		int j = TileEntityCharcoalKiln.MAX_COOK_TIME;
		
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
