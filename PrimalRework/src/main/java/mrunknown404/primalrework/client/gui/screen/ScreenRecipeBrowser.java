package mrunknown404.primalrework.client.gui.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.client.gui.recipedisplays.RecipeDisplay;
import mrunknown404.primalrework.init.InitFuels;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.recipes.StagedRecipe;
import mrunknown404.primalrework.utils.Pair;
import mrunknown404.primalrework.utils.enums.FuelType;
import mrunknown404.primalrework.utils.enums.ICraftingInput;
import mrunknown404.primalrework.utils.enums.RecipeType;
import mrunknown404.primalrework.utils.helpers.ColorH;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.client.MainWindow;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class ScreenRecipeBrowser extends Screen {
	private static final ResourceLocation BG = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/recipebrowser/background.png");
	private static final ResourceLocation TAB = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/recipebrowser/tab.png");
	
	private final int imageWidth = 176;
	
	private final ScreenRecipeBrowserItems recipeBrowserItems = new ScreenRecipeBrowserItems();
	private final ContainerScreen<?> lastScreen;
	private final List<Data> recipes = new ArrayList<Data>();
	private int leftPos, topPos, curRecipeTab;
	private Button leftButton, rightButton;
	private ITextComponent recipeName, pageCount;
	
	public ScreenRecipeBrowser(ContainerScreen<?> lastScreen, Map<RecipeType, List<StagedRecipe<?, ?>>> recipes, Map<FuelType, Pair<StagedItem, Integer>> fuels,
			StagedItem output) {
		super(WordH.translate("screen.recipelist.title"));
		this.lastScreen = lastScreen;
		
		if (recipes != null) {
			recipes.entrySet().stream().forEach(e -> this.recipes.add(new Data(e.getKey(), RecipeDisplay.createFrom(e.getKey(), e.getValue(), output))));
		}
		
		if (fuels != null) {
			fuels.entrySet().stream()
					.forEach(e -> this.recipes.add(new Data(e.getKey(), RecipeDisplay.createFrom(e.getKey(), InitFuels.convertToRecipes(e.getKey(), e.getValue()), output))));
		}
	}
	
	@Override
	protected void init() {
		leftPos = (width - imageWidth) / 2;
		topPos = 32;
		
		recipes.forEach(d -> d.display.init(minecraft, this));
		
		curRecipeTab = 0;
		leftButton = addButton(new Button(leftPos - 22, topPos + 4, 20, 20, WordH.string("<"), button -> recipes.get(curRecipeTab).display.decreasePage()));
		rightButton = addButton(new Button(leftPos + 182, topPos + 4, 20, 20, WordH.string(">"), button -> recipes.get(curRecipeTab).display.increasePage()));
		
		MainWindow w = minecraft.getWindow();
		recipeBrowserItems.init(minecraft, w.getGuiScaledWidth(), w.getGuiScaledHeight());
		
		onTabChange(true);
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float delta) {
		renderBackground(stack);
		
		Data curData = recipes.get(curRecipeTab);
		int h = Math.min(curData.display.getAmountOfRecipes() - (curData.display.getPage() * curData.display.getMaxRecipesSupported()), curData.display.getMaxRecipesSupported()) *
				(curData.display.thisHeight + 2) + 6;
		
		for (int i = 0; i < recipes.size(); i++) {
			int left = leftPos + 9 + (i * 29), top = topPos - 20;
			itemRenderer.renderGuiItem(recipes.get(i).type.getIcon(), left, top);
			
			if (mouseX >= left && mouseX <= left + 15 && mouseY >= top && mouseY <= top + 15) {
				GuiUtils.drawHoveringText(stack, Arrays.asList(recipes.get(i).type.getFancyName()), mouseX, mouseY, width, height, -1, font);
			}
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
		blit(stack, leftPos, topPos + 12, 0, h, 180, h, 180, h * 3);
		blit(stack, leftPos, topPos + h + 12, 0, 12, 180, 4, 180, 16);
		
		minecraft.textureManager.bind(TAB);
		blit(stack, leftPos + 3 + (curRecipeTab * 29), topPos - 26, 0, 0, 28, 29, 28, 29);
		
		font.draw(stack, recipeName, leftPos + 90 - font.width(recipeName) / 2, topPos + 5, ColorH.rgba2Int(45, 45, 45));
		font.draw(stack, pageCount, leftPos + 90 - font.width(pageCount) / 2, topPos + h + 3, ColorH.rgba2Int(45, 45, 45));
		
		curData.display.render(stack, leftPos, topPos + 16, mouseX, mouseY);
		recipeBrowserItems.render(stack, mouseX, mouseY, delta);
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
		pageCount = WordH.string((display.getPage() + 1) + "/" + display.getMaxPages());
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (int i = 0; i < recipes.size(); i++) {
			int x = leftPos + 3 + (i * 29), y = topPos - 26;
			if (mouseX >= x && mouseX <= x + 28 && mouseY >= y && mouseY <= y + 26) {
				curRecipeTab = i;
				onTabChange(false);
				break;
			}
		}
		
		return recipeBrowserItems.click(mouseX, button) || super.mouseClicked(mouseX, mouseY, button) || recipes.get(curRecipeTab).display.mouseClicked(button);
	}
	
	@Override
	public boolean keyPressed(int key, int p_231046_2_, int p_231046_3_) {
		if (key == GLFW.GLFW_KEY_E) {
			onClose();
			return true;
		}
		
		return recipeBrowserItems.keyPressed(key, p_231046_2_, p_231046_3_) || super.keyPressed(key, p_231046_2_, p_231046_3_) || recipes.get(curRecipeTab).display.keyPressed(key);
	}
	
	public ContainerScreen<?> getLastScreen() {
		return lastScreen;
	}
	
	private static class Data {
		private final ICraftingInput type;
		private final RecipeDisplay<?> display;
		
		private Data(ICraftingInput type, RecipeDisplay<?> display) {
			this.type = type;
			this.display = display;
		}
	}
}
