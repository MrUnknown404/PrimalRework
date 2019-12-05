package mrunknown404.primalrework.util.jei.icantgetthistoworkwithoutcopyingeverything;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IIngredientType;
import mrunknown404.primalrework.util.jei.JEICompat;
import net.minecraft.client.Minecraft;

public class GuiIngredientGroup<T> implements IGuiIngredientGroup<T> {
	private final Map<Integer, GuiIngredient<T>> guiIngredients = new HashMap<>();
	private final Set<Integer> inputSlots = new HashSet<>();
	private final IIngredientHelper<T> ingredientHelper;
	private final IIngredientRenderer<T> ingredientRenderer;
	private final IIngredientType<T> ingredientType;
	private final int cycleOffset;
	private IFocus<T> focus;
	
	public GuiIngredientGroup(IIngredientType<T> ingredientType, @Nullable IFocus<T> focus, int cycleOffset) {
		this.ingredientType = ingredientType;
		if (focus == null) {
			this.focus = null;
		} else {
			this.focus = Focus.check(focus);
		}
		
		this.ingredientHelper = JEICompat.ingReg.getIngredientHelper(ingredientType);
		this.ingredientRenderer = JEICompat.ingReg.getIngredientRenderer(ingredientType);
		this.cycleOffset = cycleOffset;
	}
	
	@Override
	public void init(int slotIndex, boolean input, int xPosition, int yPosition) {
		init(slotIndex, input, ingredientRenderer, xPosition, yPosition, 16, 16, 0, 0);
	}
	
	@Override
	public void init(int slotIndex, boolean input, IIngredientRenderer<T> ingredientRenderer, int xPosition, int yPosition, int width, int height, int xPadding, int yPadding) {
		Rectangle rect = new Rectangle(xPosition, yPosition, width, height);
		GuiIngredient<T> guiIngredient = new GuiIngredient<>(input, ingredientRenderer, ingredientHelper, rect, xPadding, yPadding, cycleOffset);
		guiIngredients.put(slotIndex, guiIngredient);
		
		if (input) {
			inputSlots.add(slotIndex);
		}
	}
	
	@Override
	public void set(IIngredients ingredients) {
		List<List<T>> inputs = ingredients.getInputs(ingredientType);
		List<List<T>> outputs = ingredients.getOutputs(ingredientType);
		int inputIndex = 0;
		int outputIndex = 0;
		
		List<Integer> slots = new ArrayList<>(guiIngredients.keySet());
		Collections.sort(slots);
		for (Integer slot : slots) {
			if (inputSlots.contains(slot)) {
				if (inputIndex < inputs.size()) {
					List<T> input = inputs.get(inputIndex);
					inputIndex++;
					set(slot, input);
				}
			} else {
				if (outputIndex < outputs.size()) {
					List<T> output = outputs.get(outputIndex);
					outputIndex++;
					set(slot, output);
				}
			}
		}
	}
	
	@Override
	public void set(int slotIndex, @Nullable List<T> ingredients) {
		if (ingredients != null) {
			for (T ingredient : ingredients) {
				Class<? extends T> ingredientClass = ingredientType.getIngredientClass();
				
				if (!ingredientClass.isInstance(ingredient) && ingredient != null) {
					return;
				}
			}
		}
		
		GuiIngredient<T> guiIngredient = guiIngredients.get(slotIndex);
		IFocus.Mode ingredientMode = guiIngredient.isInput() ? IFocus.Mode.INPUT : IFocus.Mode.OUTPUT;
		
		if (focus == null || focus.getMode() == ingredientMode) {
			guiIngredient.set(ingredients, focus);
		} else {
			guiIngredient.set(ingredients, null);
		}
	}
	
	@Override
	public void set(int slotIndex, @Nullable T value) {
		set(slotIndex, Collections.singletonList(value));
	}
	
	@Override
	public void setBackground(int slotIndex, IDrawable background) {
		guiIngredients.get(slotIndex).setBackground(background);
	}
	
	@Override
	public Map<Integer, GuiIngredient<T>> getGuiIngredients() {
		return guiIngredients;
	}
	
	void draw(Minecraft minecraft, int xOffset, int yOffset, Color highlightColor, int mouseX, int mouseY) {
		for (GuiIngredient<T> ingredient : guiIngredients.values()) {
			ingredient.draw(minecraft, xOffset, yOffset);
			
			if (ingredient.isMouseOver(xOffset, yOffset, mouseX, mouseY)) {
				ingredient.drawHighlight(minecraft, highlightColor, xOffset, yOffset);
			}
		}
	}
	
	@Override
	public void setOverrideDisplayFocus(@Nullable IFocus<T> focus) {
		this.focus = focus == null ? null : Focus.check(focus);
	}
	
	@Override
	public void addTooltipCallback(ITooltipCallback<T> tooltipCallback) {}
}
