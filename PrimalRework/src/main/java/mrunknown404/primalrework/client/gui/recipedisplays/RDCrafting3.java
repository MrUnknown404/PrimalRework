package mrunknown404.primalrework.client.gui.recipedisplays;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.events.client.TooltipCEvents;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.MathH;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class RDCrafting3 extends RecipeDisplay<SRCrafting3> {
	
	private static final ResourceLocation BG = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/craftingdisplay/primal_crafting_table.png");
	//TODO add shapeless symbol if the recipe's shapeless
	//TODO redo the texture to remove the extra slot
	
	private Cache<Item, ItemStack> lastItemCache = new Cache<Item, ItemStack>();
	private Cache<Item, ItemStack> lastIngCache = new Cache<Item, ItemStack>();
	private Ingredient lastIng;
	private int ingSize, curIng, ti;
	
	protected RDCrafting3(List<SRCrafting3> recipes, Item output) {
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
	protected void drawSlot(MatrixStack stack, int left, int top, int mouseX, int mouseY, int drawSlot) {
		mc.textureManager.bind(BG);
		blit(stack, left, top, 0, 0, 116, 54, 116, 54);
		SRCrafting3 recipe = recipes.get(drawSlot);
		
		ir.renderGuiItem(recipe.output, left + 95, top + 19);
		ir.renderGuiItemDecorations(font, recipe.output, left + 95, top + 19);
		
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
				
				if (!lastIngCache.is(item)) {
					lastIngCache.set(item, new ItemStack(item));
				}
				
				int xxx = left + (x * 18), yyy = top + (y * 18);
				ir.renderGuiItem(lastIngCache.get(), 1 + xxx, 1 + yyy);
				
				if (itemUnderMouse == null && MathH.within(mouseX, xxx + 1, xxx + 16) && MathH.within(mouseY, yyy + 1, yyy + 16)) {
					if (lastItemCache.is(item)) {
						itemUnderMouse = lastItemCache.get();
					} else {
						lastItemCache.set(item, new ItemStack(item));
						itemUnderMouse = lastItemCache.get();
					}
					
					GuiUtils.drawHoveringText(stack, TooltipCEvents.getTooltips(itemUnderMouse), mouseX, mouseY, listWidth, listHeight, -1, font);
				}
			}
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (itemUnderMouse != null) {
			if (button == 0) {
				setLeftClickScreen(itemUnderMouse.getItem());
				return true;
			} else if (button == 1) {
				setRightClickScreen(itemUnderMouse.getItem());
				return true;
			}
		}
		
		return false;
	}
}
