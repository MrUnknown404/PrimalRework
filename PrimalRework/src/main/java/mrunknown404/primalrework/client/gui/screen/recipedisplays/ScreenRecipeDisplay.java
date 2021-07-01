package mrunknown404.primalrework.client.gui.screen.recipedisplays;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mrunknown404.primalrework.client.gui.screen.ScreenRecipeList;
import mrunknown404.primalrework.recipes.IStagedRecipe;
import mrunknown404.primalrework.recipes.SRCampFire;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.Item;

public abstract class ScreenRecipeDisplay<T extends IStagedRecipe<T, ?>> {
	
	protected final List<T> recipes;
	protected final Item output;
	protected Minecraft mc;
	protected FontRenderer font;
	protected ItemRenderer ir;
	protected int listWidth, listHeight;
	public final int thisHeight;
	private ScreenRecipeList list;
	
	protected ScreenRecipeDisplay(List<T> recipes, Item output, int thisHeight) {
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
		
		setup();
	}
	
	public final void render(MatrixStack stack, int x, int y, int mouseX, int mouseY) {
		draw(stack, x, y + 16, mouseX, mouseY);
	}
	
	//@formatter:off
	protected abstract void setup();
	public abstract void tick();
	protected abstract void draw(MatrixStack stack, int x, int y, int mouseX, int mouseY);
	public abstract boolean mouseClicked(double mouseX, double mouseY, int button);
	//@formatter:on
	
	protected void blit(MatrixStack stack, int x, int y, int xuv, int yuv, int w, int h, int wuv, int huv) {
		AbstractGui.blit(stack, x, y, xuv, yuv, w, h, wuv, huv);
	}
	
	protected void setLeftClickScreen(Item item) {
		list.setLeftClickScreen(item);
	}
	
	protected void setRightClickScreen(Item item) {
		list.setRightClickScreen(item);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends IStagedRecipe<?, ?>> ScreenRecipeDisplay<?> createFrom(EnumRecipeType type, List<T> recipes, Item output) {
		switch (type) {
			case campfire:
				return new ScreenRecipeDisplay<SRCampFire>((List<SRCampFire>) recipes, output, 1) { //TODO replace this with something permanent
					@Override
					protected void setup() {
						
					}
					
					@Override
					public void tick() {
						
					}
					
					@Override
					public void draw(MatrixStack stack, int x, int y, int mouseX, int mouseY) {
						
					}
					
					@Override
					public boolean mouseClicked(double mouseX, double mouseY, int button) {
						return false;
					}
				};
			case crafting_3:
				return new SRDCrafting3((List<SRCrafting3>) recipes, output);
		}
		
		return null;
	}
}
