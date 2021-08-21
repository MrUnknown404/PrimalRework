package mrunknown404.primalrework.client.gui.recipedisplays;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.helpers.ColorH;
import mrunknown404.primalrework.recipes.SRFuel;
import net.minecraft.item.Item;

public class RDFuel extends RecipeDisplay<SRFuel> {
	
	public RDFuel(List<SRFuel> recipes, Item output) {
		super(recipes, output, 32); // figure out height
	}
	
	@Override
	protected void drawSlot(MatrixStack stack, int left, int top, int mouseX, int mouseY, int drawSlot) { // actually render this
		SRFuel recipe = recipes.get(drawSlot);
		drawOutputItem(recipe.getOutput(), left, top);
		font.draw(stack, "" + recipe.getCookTime(), left + 20, top, ColorH.rgba2Int(17, 150, 74));
	}
}
