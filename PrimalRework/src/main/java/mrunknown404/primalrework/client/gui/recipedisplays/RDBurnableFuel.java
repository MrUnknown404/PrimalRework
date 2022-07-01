package mrunknown404.primalrework.client.gui.recipedisplays;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.items.raw.StagedItem;
import mrunknown404.primalrework.recipes.SRBurnableFuel;
import mrunknown404.primalrework.utils.helpers.ColorH;

public class RDBurnableFuel extends RecipeDisplay<SRBurnableFuel> {
	public RDBurnableFuel(List<SRBurnableFuel> recipes, StagedItem output) {
		super(recipes, output, 32); // figure out height
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void drawSlot(MatrixStack stack, int left, int top, int mouseX, int mouseY, int drawSlot) { // actually render this
		SRBurnableFuel recipe = recipes.get(drawSlot);
		drawOutputItem(recipe.getOutputNoCopy(), left, top);
		font.draw(stack, "" + recipe.getCookTime(), left + 20, top, ColorH.rgba2Int(17, 150, 74));
	}
}
