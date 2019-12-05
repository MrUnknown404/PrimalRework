package mrunknown404.primalrework.client.gui;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.inventory.ContainerFirePit;
import mrunknown404.primalrework.tileentity.TileEntityFirePit;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFirePit extends GuiContainer {
	
	private static final ResourceLocation LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/fire_pit.png");
	private final InventoryPlayer player;
	private final TileEntityFirePit te;
	
	public GuiFirePit(InventoryPlayer player, TileEntityFirePit te) {
		super(new ContainerFirePit(player, te));
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
		
		if (te.isBurning()) {
			int k = getFuelLeftScale(14);
			drawTexturedModalRect(guiLeft + 81, guiTop + 65 - k, 176, 13 - k, 14, k + 1);
		}
		
		int l = getCookProgressScale(24);
		drawTexturedModalRect(guiLeft + 103, guiTop + 34 - l, 176, 14, 2, l);
	}
	
	private int getCookProgressScale(int pixels) {
		int i = te.getTotalCookTime() - te.getCookTime();
		int j = te.getTotalCookTime();
		
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
	
	private int getFuelLeftScale(int pixels) {
		int i = te.getTotalBurnTime();
		if (i == 0) {
			i = 200;
		}
		
		return te.getBurnTime() * pixels / i;
	}
}
