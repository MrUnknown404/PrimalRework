package mrunknown404.primalrework.util.jei.icantgetthistoworkwithoutcopyingeverything;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.recipe.IFocus;
import mrunknown404.primalrework.util.jei.JEICompat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class GuiIngredient<T> extends Gui implements IGuiIngredient<T> {
	private final boolean input;

	private final Rectangle rect;
	private final int xPadding;
	private final int yPadding;

	private final CycleTimer cycleTimer;
	private final List<T> displayIngredients = new ArrayList<>();
	private final List<T> allIngredients = new ArrayList<>();
	private final IIngredientRenderer<T> ingredientRenderer;
	private final IIngredientHelper<T> ingredientHelper;
	private IDrawable background;

	private boolean enabled;

	public GuiIngredient(boolean input, IIngredientRenderer<T> ingredientRenderer, IIngredientHelper<T> ingredientHelper, Rectangle rect,
			int xPadding, int yPadding, int cycleOffset) {
		this.ingredientRenderer = ingredientRenderer;
		this.ingredientHelper = ingredientHelper;
		this.input = input;
		this.rect = rect;
		this.xPadding = xPadding;
		this.yPadding = yPadding;
		
		this.cycleTimer = new CycleTimer(cycleOffset);
	}
	
	boolean isMouseOver(int xOffset, int yOffset, int mouseX, int mouseY) {
		return enabled && (mouseX >= xOffset + rect.x) && (mouseY >= yOffset + rect.y) && (mouseX < xOffset + rect.x + rect.width) && (mouseY < yOffset + rect.y + rect.height);
	}
	
	@Override
	public T getDisplayedIngredient() {
		return cycleTimer.getCycledItem(displayIngredients);
	}
	
	@Override
	public List<T> getAllIngredients() {
		return allIngredients;
	}
	
	void set(List<T> ingredients, IFocus<T> focus) {
		displayIngredients.clear();
		allIngredients.clear();
		List<T> displayIngredients;
		
		if (ingredients == null) {
			displayIngredients = Collections.emptyList();
		} else {
			displayIngredients = ingredientHelper.expandSubtypes(ingredients);
		}
		
		T match = getMatch(displayIngredients, focus);
		if (match != null) {
			this.displayIngredients.add(match);
		} else {
			displayIngredients = filterOutHidden(displayIngredients);
			this.displayIngredients.addAll(displayIngredients);
		}
		
		if (ingredients != null) {
			allIngredients.addAll(ingredients);
		}
		
		enabled = !displayIngredients.isEmpty();
	}
	
	private List<T> filterOutHidden(List<T> ingredients) {
		if (ingredients.isEmpty()) {
			return ingredients;
		}
		
		List<T> visible = new ArrayList<>();
		
		for (T ingredient : ingredients) {
			if (ingredient == null || JEICompat.helper.getIngredientBlacklist().isIngredientBlacklisted(ingredient)) {
				visible.add(ingredient);
			}
			
			if (visible.size() > 100) {
				return visible;
			}
		}
		
		if (visible.size() > 0) {
			return visible;
		}
		
		return ingredients;
	}
	
	void setBackground(IDrawable background) {
		this.background = background;
	}
	
	T getMatch(Collection<T> ingredients, IFocus<T> focus) {
		if (focus != null && isMode(focus.getMode())) {
			return ingredientHelper.getMatch(ingredients, focus.getValue());
		}
		
		return null;
	}
	
	void draw(Minecraft minecraft, int xOffset, int yOffset) {
		cycleTimer.onDraw();
		
		if (background != null) {
			background.draw(minecraft, xOffset + rect.x, yOffset + rect.y);
		}
		
		try {
			ingredientRenderer.render(minecraft, xOffset + rect.x + xPadding, yOffset + rect.y + yPadding, getDisplayedIngredient());
		} catch (RuntimeException | LinkageError e) {
			throw e;
		}
	}
	
	@Override
	public void drawHighlight(Minecraft minecraft, Color color, int xOffset, int yOffset) {
		int x = rect.x + xOffset + xPadding;
		int y = rect.y + yOffset + yPadding;
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		drawRect(x, y, x + rect.width - xPadding * 2, y + rect.height - yPadding * 2, color.getRGB());
		GlStateManager.color(1f, 1f, 1f, 1f);
	}
	
	@Override
	public boolean isInput() {
		return input;
	}
	
	boolean isMode(IFocus.Mode mode) {
		return (input && mode == IFocus.Mode.INPUT) || (!input && mode == IFocus.Mode.OUTPUT);
	}
}
