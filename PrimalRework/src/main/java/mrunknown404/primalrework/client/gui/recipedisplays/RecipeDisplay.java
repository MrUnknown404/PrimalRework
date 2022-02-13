package mrunknown404.primalrework.client.gui.recipedisplays;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.client.CraftingDisplayH;
import mrunknown404.primalrework.client.gui.screen.ScreenRecipeList;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.IStagedRecipe;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.SRCampFire;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.recipes.SRFuel;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.utils.enums.EnumFuelType;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import mrunknown404.primalrework.utils.enums.ICraftingInput;
import mrunknown404.primalrework.utils.helpers.MathH;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

public abstract class RecipeDisplay<T extends IStagedRecipe<T, ?>> {
	public final int thisHeight;
	protected final List<T> recipes;
	protected final StagedItem output;
	protected Minecraft mc;
	protected FontRenderer font;
	protected ItemStack itemUnderMouse;
	protected int listWidth, listHeight, maxRecipesSupported, maxPages, page;
	
	private final DoubleCache<Ingredient, Integer, ItemStack> lastIngCache = new DoubleCache<Ingredient, Integer, ItemStack>() {
		@Override
		public boolean is(Ingredient key0, Integer key1) {
			return this.key0 != null && key0 != null ? (this.key0.matches(key0) && this.key1 == key1) : false;
		}
	};
	
	private ItemRenderer ir;
	private ScreenRecipeList list;
	private Ingredient lastIng;
	private int ti, ingSize, curIng;
	
	protected RecipeDisplay(List<T> recipes, StagedItem output, int thisHeight) {
		this.recipes = recipes;
		this.output = output;
		this.thisHeight = thisHeight;
	}
	
	public final void init(Minecraft mc, ScreenRecipeList list) {
		this.mc = mc;
		this.font = mc.font;
		this.ir = mc.getItemRenderer();
		this.list = list;
		this.listWidth = list.width;
		this.listHeight = list.height;
		this.maxRecipesSupported = MathH.floor((double) list.height / (double) (thisHeight + 26));
		this.page = 0;
		this.maxPages = MathH.ceil((double) recipes.size() / (double) maxRecipesSupported);
		
		setup();
	}
	
	public final void render(MatrixStack stack, int x, int y, int mouseX, int mouseY) {
		itemUnderMouse = null;
		int imin = page * maxRecipesSupported;
		int imax = Math.min(recipes.size() - (page * maxRecipesSupported), maxRecipesSupported);
		
		for (int i = imin; i < imin + imax; i++) {
			drawSlot(stack, x + (116 / 4), y + ((i - imin) * (thisHeight + 2)), mouseX, mouseY, i);
		}
	}
	
	protected abstract void drawSlot(MatrixStack stack, int left, int top, int mouseX, int mouseY, int drawSlot);
	
	protected void setup() {
		
	}
	
	protected void update() {
		
	}
	
	public final void tick() {
		if (ti == 0) {
			ti = 20;
			if (curIng == 0) {
				curIng = ingSize - 1;
			} else {
				curIng--;
			}
		} else {
			ti--;
		}
		
		update();
	}
	
	protected ItemStack getIngToRender(Ingredient ing) {
		if (lastIngCache.is(ing, ti)) {
			return lastIngCache.get();
		}
		
		StagedItem item = null;
		List<StagedItem> items = ing.getStagedItems();
		
		if (items.isEmpty()) {
			System.err.println("Recipe stage < Ingredient stage?");
			return ItemStack.EMPTY;
		}
		
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
		
		ItemStack stack = new ItemStack(item);
		lastIngCache.set(ing, ti, stack);
		return stack;
	}
	
	protected void drawOutputItem(ItemStack output, int x, int y) {
		drawItem(output, x, y);
		ir.renderGuiItemDecorations(font, output, x, y);
	}
	
	protected void drawItem(ItemStack output, int x, int y) {
		ir.renderGuiItem(output, x, y);
	}
	
	public boolean mouseClicked(int button) {
		if (itemUnderMouse != null) {
			if (button == 0) {
				CraftingDisplayH.showHowToCraft(mc, (StagedItem) itemUnderMouse.getItem(), list.getLastScreen());
				return true;
			} else if (button == 1) {
				CraftingDisplayH.showWhatCanBeMade(mc, (StagedItem) itemUnderMouse.getItem(), list.getLastScreen());
				return true;
			}
		}
		
		return false;
	}
	
	protected void blit(MatrixStack stack, int x, int y, int xuv, int yuv, int w, int h, int wuv, int huv) {
		AbstractGui.blit(stack, x, y, xuv, yuv, w, h, wuv, huv);
	}
	
	public int getMaxPages() {
		return maxPages;
	}
	
	public int getPage() {
		return page;
	}
	
	public int getMaxRecipesSupported() {
		return maxRecipesSupported;
	}
	
	public void increasePage() {
		page++;
		if (page > maxPages - 1) {
			page = 0;
		}
		list.refreshPageCount(this);
	}
	
	public void decreasePage() {
		page--;
		if (page < 0) {
			page = maxPages - 1;
		}
		list.refreshPageCount(this);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends IStagedRecipe<?, ?>> RecipeDisplay<?> createFrom(ICraftingInput type, List<T> recipes, StagedItem output) {
		if (type instanceof EnumRecipeType) {
			switch ((EnumRecipeType) type) {
				case campfire:
					return new RecipeDisplay<SRCampFire>((List<SRCampFire>) recipes, output, 1) {
						@Override
						protected void drawSlot(MatrixStack stack, int left, int top, int mouseX, int mouseY, int drawSlot) {
							
						}
					};
				case crafting_3:
					return new RDCrafting3((List<SRCrafting3>) recipes, output);
			}
		} else if (type instanceof EnumFuelType) {
			switch ((EnumFuelType) type) {
				case campfire:
					return new RDFuel((List<SRFuel>) recipes, output);
			}
		} else {
			System.err.println("Invalid input type??");
		}
		
		return null;
	}
}
