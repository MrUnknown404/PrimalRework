package mrunknown404.primalrework.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.client.ColorH;
import mrunknown404.primalrework.client.CraftingDisplayH;
import mrunknown404.primalrework.client.gui.screen.recipedisplays.ScreenRecipeDisplay;
import mrunknown404.primalrework.recipes.IStagedRecipe;
import mrunknown404.primalrework.utils.MathH;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenRecipeList extends Screen {
	
	private static final ResourceLocation BG = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/craftingdisplay/crafting_display_background.png");
	private static final ResourceLocation TAB = new ResourceLocation(PrimalRework.MOD_ID, "textures/gui/craftingdisplay/crafting_display_tab.png");
	
	private final int imageWidth = 176;
	
	private final ContainerScreen<?> lastScreen;
	private final List<Data> recipes;
	private int leftPos, topPos, curRecipe;
	
	//TODO make a max amount of tabs and add arrows for tabs
	//TODO add a max for amount of recipes to display at once
	
	public ScreenRecipeList(ContainerScreen<?> lastScreen, Map<EnumRecipeType, List<IStagedRecipe<?, ?>>> map, Item output) {
		super(new TranslationTextComponent("screen.recipelist.title"));
		this.lastScreen = lastScreen;
		this.recipes = new ArrayList<Data>();
		
		for (EnumRecipeType type : EnumRecipeType.values()) {
			if (map.containsKey(type)) {
				List<IStagedRecipe<?, ?>> list = map.get(type);
				recipes.add(new Data(type, ScreenRecipeDisplay.createFrom(type, list, output), list.size()));
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
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float delta) {
		renderBackground(stack);
		
		Data curData = recipes.get(curRecipe);
		int h = (curData.size * (curData.display.thisHeight + 2)) + 6;
		int y = height < h ? topPos : (height - 150) / 2;
		
		for (int i = 0; i < recipes.size(); i++) {
			itemRenderer.renderGuiItem(recipes.get(i).type.icon, leftPos + 9 + (i * 29), y - 20);
		}
		
		minecraft.textureManager.bind(TAB);
		for (int i = 0; i < recipes.size(); i++) {
			if (i != curRecipe) {
				blit(stack, leftPos + 3 + (i * 29), y - 26, 0, 0, 28, 29, 28, 29);
			}
		}
		
		minecraft.textureManager.bind(BG);
		blit(stack, leftPos, y, 0, 0, 180, 12, 180, 16);
		blit(stack, leftPos, y + 12, 0, h, 180, h, 180, h * 3);
		blit(stack, leftPos, y + 12 + h, 0, 12, 180, 4, 180, 16);
		
		minecraft.textureManager.bind(TAB);
		blit(stack, leftPos + 3 + (curRecipe * 29), y - 26, 0, 0, 28, 29, 28, 29);
		
		ITextComponent text = curData.type.getFancyName();
		font.draw(stack, text, leftPos + 90 - font.width(text) / 2, y + 4, ColorH.rgba2Int(45, 45, 45));
		
		curData.display.render(stack, leftPos, y, mouseX, mouseY);
	}
	
	@Override
	public void tick() {
		recipes.get(curRecipe).display.tick();
	}
	
	@Override
	public void onClose() {
		minecraft.setScreen(lastScreen);
	}
	
	public void setLeftClickScreen(Item item) {
		CraftingDisplayH.getLeftClick(minecraft, item, lastScreen);
	}
	
	public void setRightClickScreen(Item item) {
		CraftingDisplayH.getRightClick(minecraft, item, lastScreen);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (int i = 0; i < recipes.size(); i++) {
			int x = leftPos + 3 + (i * 29), y = topPos - 26;
			if (MathH.within(mouseX, x, x + 28) && MathH.within(mouseY, y, y + 26)) {
				curRecipe = i;
				break;
			}
		}
		
		return recipes.get(curRecipe).display.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean keyPressed(int key, int p_231046_2_, int p_231046_3_) {
		if (key == GLFW.GLFW_KEY_E) {
			onClose();
			return true;
		}
		return super.keyPressed(key, p_231046_2_, p_231046_3_);
	}
	
	private static class Data {
		private final EnumRecipeType type;
		private final ScreenRecipeDisplay<?> display;
		private final int size;
		
		private Data(EnumRecipeType type, ScreenRecipeDisplay<?> display, int size) {
			this.type = type;
			this.display = display;
			this.size = size;
		}
	}
}
