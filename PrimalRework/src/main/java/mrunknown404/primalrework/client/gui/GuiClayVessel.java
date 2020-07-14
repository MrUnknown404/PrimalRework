package mrunknown404.primalrework.client.gui;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.inventory.container.ContainerClayVessel;
import mrunknown404.primalrework.items.ItemClayVessel;
import mrunknown404.primalrework.util.enums.EnumAlloy;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;

public class GuiClayVessel extends GuiContainer {
	
	private static final ResourceLocation LOC = new ResourceLocation(Main.MOD_ID, "textures/gui/clay_vessel.png");
	private final InventoryPlayer player;
	private final IItemHandler container;
	
	public GuiClayVessel(InventoryPlayer player, IItemHandler container) {
		super(new ContainerClayVessel(player, container));
		this.player = player;
		this.container = container;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 8, ySize - 94, 4210752);
		
		Map<EnumAlloy, Integer> metals = ItemClayVessel.convertInventoryToMap(container);
		
		int total = 0;
		Iterator<Entry<EnumAlloy, Integer>> it = metals.entrySet().iterator();
		while (it.hasNext()) {
			total += it.next().getValue();
		}
		
		String alloy = total + " Units of " + StringUtils.capitalize(EnumAlloy.getAlloy(metals).toString());
		fontRenderer.drawString(alloy, xSize / 2 - fontRenderer.getStringWidth(alloy) / 2, ySize - 138, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(LOC);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
