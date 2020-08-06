package mrunknown404.primalrework.client.gui;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.inventory.container.ContainerClayFurnace;
import mrunknown404.primalrework.tileentity.TileEntityClayFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiClayFurnace extends GuiContainer {
	
	private static final ResourceLocation LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/container/clay_furnace.png");
	private final InventoryPlayer player;
	private final TileEntityClayFurnace te;
	
	public GuiClayFurnace(InventoryPlayer player, TileEntityClayFurnace te) {
		super(new ContainerClayFurnace(player, te));
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
			drawTexturedModalRect(guiLeft + 80, guiTop + 36 + l, 176, l, 14, 14 - l);
		}
	}
	
	private int getCookProgressScale(int pixels) {
		int i = te.getCookTime();
		int j = TileEntityClayFurnace.MAX_COOK_TIME;
		
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
