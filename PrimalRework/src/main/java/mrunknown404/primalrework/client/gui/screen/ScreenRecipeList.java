package mrunknown404.primalrework.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.client.ColorH;
import mrunknown404.primalrework.client.gui.recipedisplays.RecipeDisplay;
import mrunknown404.primalrework.helpers.MathH;
import mrunknown404.primalrework.recipes.IStagedRecipe;
import mrunknown404.primalrework.registries.PRFuels;
import mrunknown404.primalrework.utils.Pair;
import mrunknown404.primalrework.utils.enums.EnumFuelType;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import mrunknown404.primalrework.utils.enums.ICraftingInput;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenRecipeList extends Screen {
	private static final ResourceLocation BG = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/craftingdisplay/crafting_display_background.png");
	private static final ResourceLocation TAB = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/craftingdisplay/crafting_display_tab.png");
	
	private final int imageWidth = 176;
	
	private final ContainerScreen<?> lastScreen;
	private final List<Data> recipes;
	private int leftPos, topPos, curRecipeTab;
	private Button leftButton, rightButton;
	private ITextComponent recipeName, pageCount;
	
	//TODO make a max amount of tabs and add arrows for tabs
	
	public ScreenRecipeList(ContainerScreen<?> lastScreen, Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> recipes, Map<EnumFuelType, Pair<Item, Integer>> fuels, Item output) {
		super(new TranslationTextComponent("screen.recipelist.title"));
		this.lastScreen = lastScreen;
		this.recipes = new ArrayList<Data>();
		
		if (recipes != null) {
			for (EnumRecipeType type : EnumRecipeType.values()) {
				if (recipes.containsKey(type)) {
					List<IStagedRecipe<?, ?>> list = recipes.get(type);
					this.recipes.add(new Data(type, RecipeDisplay.createFrom(type, list, output), list.size()));
				}
			}
		}
		
		if (fuels != null) {
			for (EnumFuelType type : EnumFuelType.values()) {
				if (fuels.containsKey(type)) {
					this.recipes.add(new Data(type, RecipeDisplay.createFrom(type, PRFuels.convertToRecipes(type, fuels.get(type)), output), fuels.size()));
				}
			}
		}
	}
	
	@Override
	protected void init() {
		leftPos = (width - imageWidth) / 2;
		topPos = 32;
		
		for (Data data : recipes) {
			data.display.init(minecraft, this);
		}
		
		curRecipeTab = 0;
		addButton(leftButton = new Button(leftPos - 22, topPos + 4, 20, 20, new StringTextComponent("<"), new IPressable() {
			@Override
			public void onPress(Button button) {
				recipes.get(curRecipeTab).display.decreasePage();
			}
		}));
		
		addButton(rightButton = new Button(leftPos + 182, topPos + 4, 20, 20, new StringTextComponent(">"), new IPressable() {
			@Override
			public void onPress(Button button) {
				recipes.get(curRecipeTab).display.increasePage();
			}
		}));
		
		onTabChange(true);
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float delta) {
		renderBackground(stack);
		
		Data curData = recipes.get(curRecipeTab);
		int h = Math.min(curData.size - (curData.display.getPage() * curData.display.getMaxRecipesSupported()), curData.display.getMaxRecipesSupported()) *
				(curData.display.thisHeight + 2) + 6;
		
		for (int i = 0; i < recipes.size(); i++) {
			itemRenderer.renderGuiItem(recipes.get(i).type.getIcon(), leftPos + 9 + (i * 29), topPos - 20);
		}
		
		minecraft.textureManager.bind(TAB);
		for (int i = 0; i < recipes.size(); i++) {
			if (i != curRecipeTab) {
				blit(stack, leftPos + 3 + (i * 29), topPos - 26, 0, 0, 28, 29, 28, 29);
			}
		}
		
		h += 8;
		minecraft.textureManager.bind(BG);
		blit(stack, leftPos, topPos, 0, 0, 180, 12, 180, 16);
		blit(stack, leftPos, topPos + 12, 0, h, 180, h, 180, (h) * 3);
		blit(stack, leftPos, topPos + h + 12, 0, 12, 180, 4, 180, 16);
		
		minecraft.textureManager.bind(TAB);
		blit(stack, leftPos + 3 + (curRecipeTab * 29), topPos - 26, 0, 0, 28, 29, 28, 29);
		
		font.draw(stack, recipeName, leftPos + 90 - font.width(recipeName) / 2, topPos + 5, ColorH.rgba2Int(45, 45, 45));
		font.draw(stack, pageCount, leftPos + 90 - font.width(pageCount) / 2, topPos + h + 3, ColorH.rgba2Int(45, 45, 45));
		
		curData.display.render(stack, leftPos, topPos + 16, mouseX, mouseY);
		super.render(stack, mouseX, mouseY, delta);
	}
	
	@Override
	public void tick() {
		recipes.get(curRecipeTab).display.tick();
	}
	
	@Override
	public void onClose() {
		minecraft.setScreen(lastScreen);
	}
	
	private void onTabChange(boolean isSilent) {
		Data curData = recipes.get(curRecipeTab);
		boolean flag = curData.display.getMaxPages() != 1;
		recipeName = curData.type.getFancyName();
		refreshPageCount(curData.display);
		leftButton.active = flag;
		rightButton.active = flag;
		
		if (!isSilent) {
			minecraft.getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
		}
	}
	
	public void refreshPageCount(RecipeDisplay<?> display) {
		pageCount = new StringTextComponent((display.getPage() + 1) + "/" + display.getMaxPages());
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (int i = 0; i < recipes.size(); i++) {
			int x = leftPos + 3 + (i * 29), y = topPos - 26;
			if (MathH.within(mouseX, x, x + 28) && MathH.within(mouseY, y, y + 26)) {
				curRecipeTab = i;
				onTabChange(false);
				break;
			}
		}
		
		return super.mouseClicked(mouseX, mouseY, button) || recipes.get(curRecipeTab).display.mouseClicked(button);
	}
	
	@Override
	public boolean keyPressed(int key, int p_231046_2_, int p_231046_3_) {
		if (key == GLFW.GLFW_KEY_E) {
			onClose();
			return true;
		}
		return super.keyPressed(key, p_231046_2_, p_231046_3_);
	}
	
	public ContainerScreen<?> getLastScreen() {
		return lastScreen;
	}
	
	private static class Data {
		private final ICraftingInput type;
		private final RecipeDisplay<?> display;
		private final int size;
		
		private Data(ICraftingInput type, RecipeDisplay<?> display, int size) {
			this.type = type;
			this.display = display;
			this.size = size;
		}
	}
}
