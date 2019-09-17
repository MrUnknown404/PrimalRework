package mrunknown404.primalrework.util.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.util.recipes.FirePitRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FirePitRecipeCategory implements IRecipeCategory<FirePitRecipeWrapper> {
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID, "textures/gui/fire_pit.png");
	
	private final IDrawableStatic staticFuel;
	private final IDrawableAnimated animatedFuel;
	private final IDrawableAnimated animatedCookTime;
	
	private final IDrawable background, icon;
	
	public static final int SLOT_FUEL = 0, SLOT_INPUT = 1, SLOT_OUTPUT = 2;
	
	public FirePitRecipeCategory(IGuiHelper helper) {
		staticFuel = helper.createDrawable(TEXTURES, 176, 0, 14, 14);
		animatedFuel = helper.createAnimatedDrawable(staticFuel, 100, StartDirection.TOP, true);
		
		IDrawableStatic staticCookTime = helper.createDrawable(TEXTURES, 176, 14, 2, 24);
		animatedCookTime = helper.createAnimatedDrawable(staticCookTime, 100, StartDirection.BOTTOM, false);
		
		background = helper.createDrawable(TEXTURES, 0, 166, 73, 37);
		icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.FIRE_PIT));
	}
	
	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedFuel.draw(minecraft, 2, 6);
		animatedCookTime.draw(minecraft, 43, 1);
	}
	
	@Override
	public String getTitle() {
		return ModBlocks.FIRE_PIT.getLocalizedName();
	}
	
	@Override
	public String getUid() {
		return ModRecipes.CATEGORY_FIRE_PIT;
	}
	
	@Override
	public void setRecipe(IRecipeLayout layout, FirePitRecipeWrapper recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = layout.getItemStacks();
		
		stacks.init(SLOT_INPUT, true, 23, 4);
		stacks.init(SLOT_OUTPUT, false, 51, 4);
		
		stacks.set(ingredients);
		
		stacks.init(SLOT_FUEL, false, 0, 4);
		stacks.set(SLOT_FUEL, ModRecipes.getFirePitFuels());
	}
	
	@Override
	public String getModName() {
		return "Primal Rework";
	}
	
	@Override
	public IDrawable getIcon() {
		return icon;
	}
}
