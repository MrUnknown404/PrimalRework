package mrunknown404.primalrework.client.gui;

import java.io.IOException;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.inventory.container.ContainerPrimalEnchanting;
import mrunknown404.primalrework.network.message.PrimalEnchantingMessage;
import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;

public class GuiPrimalEnchantment extends GuiContainer {
	
	private static final ResourceLocation LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/primal_enchanting_table.png");
	
	private final TileEntityPrimalEnchanting te;
	private final InventoryPlayer player;
	private boolean hover;
	
	public GuiPrimalEnchantment(InventoryPlayer player, TileEntityPrimalEnchanting te) {
		super(new ContainerPrimalEnchanting(player, te));
		this.player = player;
		this.te = te;
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		
		int l = mouseX - (i + 7);
		int i1 = mouseY - (j + 7);
		
		if (l >= 0 && i1 >= 0 && l < 164 && i1 < 19) {
			int lvl = te.getContainer().enchantItem(mc.player);
			
			if (lvl != -1) {
				Main.NETWORK.sendToServer(new PrimalEnchantingMessage(player.player, te.getPos()));
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if (te.getStackInSlot(0) != null && !te.getStackInSlot(0).isEmpty() && isPointInRegion(26, 7, 143, 18, mouseX, mouseY)) {
			hover = true;
		} else {
			hover = false;
		}
		
		renderHoveredToolTip(mouseX, mouseY);
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
		
		if (te.getStackInSlot(1) != null && !te.getStackInSlot(1).isEmpty()) {
			if (te.getStackInSlot(1).getItem() == ModItems.MAGIC_DUST_RED) {
				drawTexturedModalRect(guiLeft + 6, guiTop + 7, 176, 0, 19, 19);
			} else if (te.getStackInSlot(1).getItem() == ModItems.MAGIC_DUST_GREEN) {
				drawTexturedModalRect(guiLeft + 6, guiTop + 7, 195, 0, 19, 19);
			} else if (te.getStackInSlot(1).getItem() == ModItems.MAGIC_DUST_BLUE) {
				drawTexturedModalRect(guiLeft + 6, guiTop + 7, 214, 0, 19, 19);
			}
		}
		
		if (te.getStackInSlot(0) != null && !te.getStackInSlot(0).isEmpty()) {
			EnchantmentNameParts.getInstance().reseedRandomGenerator(te.getContainer().xpSeed);
			String s1 = EnchantmentNameParts.getInstance().generateNewRandomName(fontRenderer, 144);
			
			if (hover) {
				drawTexturedModalRect(guiLeft + 25, guiTop + 7, 0, 185, 144, 19);
			}
			
			mc.standardGalacticFontRenderer.drawSplitString(s1, guiLeft + 27, guiTop + 9, 144, hover ? 2905988 : 3354148);
		} else {
			drawTexturedModalRect(guiLeft + 25, guiTop + 7, 0, 166, 144, 19);
		}
	}
}
