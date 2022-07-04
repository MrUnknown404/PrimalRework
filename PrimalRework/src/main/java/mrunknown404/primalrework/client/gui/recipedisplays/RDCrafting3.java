package mrunknown404.primalrework.client.gui.recipedisplays;

import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.utils.helpers.ItemH;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class RDCrafting3 extends RecipeDisplay<SRCrafting3> {
	private static final ResourceLocation BG = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/recipebrowser/primal_crafting_table.png");
	
	protected RDCrafting3(List<SRCrafting3> recipes, StagedItem output) {
		super(recipes, output, 54);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void drawSlot(MatrixStack stack, int left, int top, int mouseX, int mouseY, int drawSlot) {
		mc.textureManager.bind(BG);
		blit(stack, left, top, 0, 0, 116, 54, 116, 54);
		SRCrafting3 recipe = recipes.get(drawSlot);
		
		drawOutputItem(recipe.getOutputNoCopy(), left + 95, top + 19);
		
		if (mouseX >= left + 95 && mouseX <= left + 110 && mouseY >= top + 19 && mouseY <= top + 34) {
			itemUnderMouse = recipe.getOutputNoCopy();
			GuiUtils.drawHoveringText(stack, ItemH.getTooltips(itemUnderMouse), mouseX, mouseY, listWidth, listHeight, -1, font);
		}
		
		for (int y = 0; y < recipe.inputRecipe.height; y++) {
			for (int x = 0; x < recipe.inputRecipe.width; x++) {
				Ingredient ing = recipe.inputRecipe.get(x, y);
				
				if (ing.isEmpty()) {
					continue;
				}
				
				ItemStack itemStack = getIngToRender(ing);
				int xxx = left + (x * 18), yyy = top + (y * 18);
				drawItem(itemStack, 1 + xxx, 1 + yyy);
				
				if (itemUnderMouse == null && mouseX > xxx && mouseX <= xxx + 16 && mouseY > yyy && mouseY <= yyy + 16) {
					itemUnderMouse = itemStack;
					GuiUtils.drawHoveringText(stack, ing.getStagedItems().size() > 1 ? Arrays.asList(WordH.string(ing.toString())) : ItemH.getTooltips(itemUnderMouse), mouseX,
							mouseY, listWidth, listHeight, -1, font);
				}
			}
		}
	}
}
