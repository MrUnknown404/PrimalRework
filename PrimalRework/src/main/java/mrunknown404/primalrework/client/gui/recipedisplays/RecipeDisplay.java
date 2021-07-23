package mrunknown404.primalrework.client.gui.recipedisplays;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.client.CraftingDisplayH;
import mrunknown404.primalrework.client.gui.screen.ScreenRecipeList;
import mrunknown404.primalrework.recipes.IStagedRecipe;
import mrunknown404.primalrework.recipes.SRCampFire;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.utils.MathH;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class RecipeDisplay<T extends IStagedRecipe<T, ?>> {
	protected final List<T> recipes;
	protected final Item output;
	protected Minecraft mc;
	protected FontRenderer font;
	protected ItemRenderer ir;
	protected ItemStack itemUnderMouse;
	protected int listWidth, listHeight, maxPages, page;
	public int maxRecipesSupported;
	public final int thisHeight;
	private ScreenRecipeList list;
	
	protected RecipeDisplay(List<T> recipes, Item output, int thisHeight) {
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
	
	//@formatter:off
	protected abstract void setup();
	public abstract void tick();
	protected abstract void drawSlot(MatrixStack stack, int left, int top, int mouseX, int mouseY, int drawSlot);
	public abstract boolean mouseClicked(double mouseX, double mouseY, int button);
	//@formatter:on
	
	protected void blit(MatrixStack stack, int x, int y, int xuv, int yuv, int w, int h, int wuv, int huv) {
		AbstractGui.blit(stack, x, y, xuv, yuv, w, h, wuv, huv);
	}
	
	public int getMaxPages() {
		return maxPages;
	}
	
	public int getPage() {
		return page;
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
	
	protected void setLeftClickScreen(Item item) {
		CraftingDisplayH.showHowToCraft(mc, item, list.getLastScreen());
	}
	
	protected void setRightClickScreen(Item item) {
		CraftingDisplayH.showWhatCanBeMade(mc, item, list.getLastScreen());
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends IStagedRecipe<?, ?>> RecipeDisplay<?> createFrom(EnumRecipeType type, List<T> recipes, Item output) {
		switch (type) {
			case campfire:
				return new RecipeDisplay<SRCampFire>((List<SRCampFire>) recipes, output, 1) { //TODO replace this with something permanent
					@Override
					protected void setup() {
						
					}
					
					@Override
					public void tick() {
						
					}
					
					@Override
					protected void drawSlot(MatrixStack stack, int left, int top, int mouseX, int mouseY, int drawSlot) {
						
					}
					
					@Override
					public boolean mouseClicked(double mouseX, double mouseY, int button) {
						return false;
					}
				};
			case crafting_3:
				return new RDCrafting3((List<SRCrafting3>) recipes, output);
		}
		
		return null;
	}
}
