package mrunknown404.primalrework.client.gui.screen.recipedisplays;

import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.utils.MathH;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class SRDCrafting3 extends ScreenRecipeDisplay<SRCrafting3> {
	
	private static final ResourceLocation BG = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/craftingdisplay/primal_crafting_table.png");
	//TODO add shapeless symbol if the recipe's shapeless
	//TODO redo the texture to remove the extra slot
	
	private Item itemUnderMouse;
	private Ingredient lastIng;
	private int ingSize, curIng, ti;
	
	protected SRDCrafting3(List<SRCrafting3> recipes, Item output) {
		super(recipes, output, 54);
	}
	
	@Override
	protected void setup() {
		
	}
	
	@Override
	public void tick() {
		if (ti == 0) {
			ti = 30;
			if (curIng == 0) {
				curIng = ingSize - 1;
			} else {
				curIng--;
			}
		} else {
			ti--;
		}
	}
	
	@Override
	public void draw(MatrixStack stack, int left, int top, int mouseX, int mouseY) {
		int xx = left + (116 / 4);
		
		itemUnderMouse = null;
		for (int i = 0; i < recipes.size(); i++) {
			mc.textureManager.bind(BG);
			int yy = top + (i * (thisHeight + 2));
			blit(stack, xx, yy, 0, 0, 116, 54, 116, 54);
			SRCrafting3 recipe = recipes.get(i);
			
			ir.renderGuiItem(recipe.output, xx + 95, yy + 19);
			ir.renderGuiItemDecorations(font, recipe.output, xx + 95, yy + 19);
			
			if (MathH.within(mouseX, xx + 96, xx + 111) && MathH.within(mouseY, yy + 20, yy + 35)) {
				GuiUtils.drawHoveringText(stack, Arrays.asList(recipe.output.getHoverName()), mouseX, mouseY, listWidth, listHeight, -1, font);
				itemUnderMouse = recipe.getOutput().getItem();
			}
			
			for (int y = 0; y < recipe.input.height; y++) {
				for (int x = 0; x < recipe.input.width; x++) {
					Ingredient ing = recipe.input.get(x, y);
					
					if (ing.isEmpty()) {
						continue;
					}
					
					Item item = null;
					List<Item> items = ing.getItems();
					if (items.size() == 1) {
						item = items.get(0);
					} else {
						if (lastIng == null || !lastIng.matches(ing)) {
							lastIng = ing;
							ingSize = items.size();
							curIng = 0;
							ti = 0;
						}
						
						item = items.get(curIng);
					}
					
					int xxx = xx + (x * 18), yyy = yy + (y * 18);
					
					ir.renderGuiItem(new ItemStack(item), 1 + xxx, 1 + yyy);
					
					if (itemUnderMouse == null && MathH.within(mouseX, xxx + 1, xxx + 16) && MathH.within(mouseY, yyy + 1, yyy + 16)) {
						GuiUtils.drawHoveringText(stack, Arrays.asList(new StringTextComponent(ing.toString())), mouseX, mouseY, listWidth, listHeight, -1, font);
						itemUnderMouse = item;
					}
				}
			}
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (itemUnderMouse != null) {
			if (button == 0) {
				setLeftClickScreen(itemUnderMouse);
				return true;
			} else if (button == 1) {
				setRightClickScreen(itemUnderMouse);
				return true;
			}
		}
		
		return false;
	}
}
