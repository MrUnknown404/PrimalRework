package mrunknown404.primalrework.client.gui.recipedisplays;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.events.client.TooltipCEvents;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.utils.MathH;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class RDCrafting3 extends RecipeDisplay<SRCrafting3> {
	
	private static final ResourceLocation BG = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/craftingdisplay/primal_crafting_table.png");
	//TODO add shapeless symbol if the recipe's shapeless
	//TODO redo the texture to remove the extra slot
	
	protected RDCrafting3(List<SRCrafting3> recipes, Item output) {
		super(recipes, output, 54);
	}
	
	@Override
	protected void drawSlot(MatrixStack stack, int left, int top, int mouseX, int mouseY, int drawSlot) {
		mc.textureManager.bind(BG);
		blit(stack, left, top, 0, 0, 116, 54, 116, 54);
		SRCrafting3 recipe = recipes.get(drawSlot);
		
		drawOutputItem(recipe.getOutput(), left + 95, top + 19);
		
		if (MathH.within(mouseX, left + 96, left + 111) && MathH.within(mouseY, top + 20, top + 35)) {
			itemUnderMouse = recipe.getOutput();
			GuiUtils.drawHoveringText(stack, TooltipCEvents.getTooltips(itemUnderMouse), mouseX, mouseY, listWidth, listHeight, -1, font);
		}
		
		for (int y = 0; y < recipe.input.height; y++) {
			for (int x = 0; x < recipe.input.width; x++) {
				Ingredient ing = recipe.input.get(x, y);
				
				if (ing.isEmpty()) {
					continue;
				}
				
				ItemStack itemStack = getIngToRender(ing);
				int xxx = left + (x * 18), yyy = top + (y * 18);
				drawItem(itemStack, 1 + xxx, 1 + yyy);
				
				if (itemUnderMouse == null && MathH.within(mouseX, xxx + 1, xxx + 16) && MathH.within(mouseY, yyy + 1, yyy + 16)) {
					itemUnderMouse = itemStack;
					GuiUtils.drawHoveringText(stack, TooltipCEvents.getTooltips(itemUnderMouse), mouseX, mouseY, listWidth, listHeight, -1, font);
				}
			}
		}
	}
}
